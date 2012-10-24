package calendar;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.FocusTraversalPolicy;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import calendar.action.CalendarActionManager;
import calendar.dnd.CalendarTransferHandler;
import calendar.util.CalendarUtil;
import calendar.view.ICalendarDay;

/**
 * @author idanilov
 *
 */
public class CalendarUI extends JPanel {

	public static final ImageIcon CALENDAR_ICON = new ImageIcon(CalendarUI.class
			.getResource("calendar.gif"));

	/**
	 * Contains keystroke to swith up focus traversal cycle.
	 * Used by {@link DayUI}. 
	 */
	public static final Set<AWTKeyStroke> UP_CYCLE_KEYS = new HashSet<AWTKeyStroke>();

	/**
	 * Contains keystroke to swith down focus traversal cycle.
	 * Used by <code>viewerContainer</code>. 
	 */
	private static final Set<AWTKeyStroke> DOWN_CYCLE_KEYS = new HashSet<AWTKeyStroke>();

	static {
		//Ctrl+Page Down.
		DOWN_CYCLE_KEYS.add(KeyStroke
				.getKeyStroke(KeyEvent.VK_PAGE_DOWN, InputEvent.CTRL_DOWN_MASK));
		//Ctrl+Page Up.
		UP_CYCLE_KEYS.add(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, InputEvent.CTRL_DOWN_MASK));
	}

	private static final Border CALENDAR_FOCUS_BORDER = CalendarUIUtil.FOCUS_BORDER;
	private static final Border CALENDAR_NON_FOCUS_BORDER = CalendarUIUtil.NON_FOCUS_BORDER;

	private JLabel currentIntervalLabel;
	private JToggleButton dayButton;
	private JToggleButton weekButton;
	private JToggleButton monthButton;
	private JButton todayButton;
	private JButton prevButton;
	private JButton nextButton;
	private ICalendarViewer calendarViewer;
	private DayViewer dayViewer;
	private WeekViewer weekViewer;
	private MonthViewer monthViewer;

	private JPanel viewerContainer;
	private FocusListener viewerContainerFocusListener;

	private ItemListener calendarDisplayTypeListener;

	private ActionListener prevActionListener;
	private ActionListener nextActionListener;
	private ActionListener todayActionListener;

	private CalendarSchedule calendarSchedule;//XXX:get rid of this variable???.

	public CalendarUI(final CalendarSchedule schedule) {
		super(new BorderLayout());
		this.calendarSchedule = schedule;
		dayViewer = new DayViewer(schedule);
		weekViewer = new WeekViewer(schedule);
		monthViewer = new MonthViewer(schedule);
		add(createTopPanel(), BorderLayout.NORTH);
		viewerContainer = createViewerContainer();
		add(viewerContainer, BorderLayout.CENTER);
		setDragEnabled(true);
	}

	private JPanel createTopPanel() {
		JPanel result = new JPanel(new BorderLayout());
		currentIntervalLabel = new JLabel("", SwingConstants.CENTER);
		currentIntervalLabel.setIcon(CALENDAR_ICON);

		JPanel typeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		calendarDisplayTypeListener = new CalendarDisplayTypeListener();

		dayButton = new JToggleButton("Day | 1", false);
		dayButton.addItemListener(calendarDisplayTypeListener);
		typeButtonsPanel.add(dayButton);

		weekButton = new JToggleButton("Week | 7", false);
		weekButton.addItemListener(calendarDisplayTypeListener);
		typeButtonsPanel.add(weekButton);

		monthButton = new JToggleButton("Month | 31", false);
		monthButton.addItemListener(calendarDisplayTypeListener);
		typeButtonsPanel.add(monthButton);
		ButtonGroup bg = new ButtonGroup();
		bg.add(dayButton);
		bg.add(weekButton);
		bg.add(monthButton);

		JPanel scrollButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		prevButton = new JButton("<<");
		prevActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				Calendar newCal = (Calendar) calendarSchedule.getCalendar().clone();
				newCal.add(calendarViewer.getModel().getInterval(), -1);
				calendarSchedule.setCalendar(newCal);
			}

		};
		prevButton.addActionListener(prevActionListener);
		scrollButtonsPanel.add(prevButton);

		todayButton = new JButton("Today");
		todayActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				Calendar today = calendarSchedule.getToday();
				Calendar calendar = calendarSchedule.getCalendar();
				if (!CalendarUtil.equalDays(calendar, today)) {
					calendarSchedule.setCalendar(today);
				}
				calendarSchedule.getSelectionModel().setSelectedDay(today.getTime());
			}

		};
		todayButton.addActionListener(todayActionListener);
		scrollButtonsPanel.add(todayButton);

		nextButton = new JButton(">>");
		nextActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Calendar newCal = (Calendar) calendarSchedule.getCalendar().clone();
				newCal.add(calendarViewer.getModel().getInterval(), 1);
				calendarSchedule.setCalendar(newCal);
			}

		};
		nextButton.addActionListener(nextActionListener);
		scrollButtonsPanel.add(nextButton);

		result.add(typeButtonsPanel, BorderLayout.WEST);
		result.add(currentIntervalLabel, BorderLayout.CENTER);
		result.add(scrollButtonsPanel, BorderLayout.EAST);

		result.add(new JSeparator(), BorderLayout.SOUTH);
		return result;
	}

	private JPanel createViewerContainer() {
		final JPanel result = new JPanel(new BorderLayout());
		result.setBorder(CALENDAR_NON_FOCUS_BORDER);
		result.setFocusCycleRoot(true);
		result.setFocusTraversalPolicyProvider(true);
		result.setFocusable(true);
		result.setFocusTraversalKeys(KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS,
				DOWN_CYCLE_KEYS);
		FocusTraversalPolicy focusPolicy = createFocusTraversalPolicy(result);
		result.setFocusTraversalPolicy(focusPolicy);

		viewerContainerFocusListener = new FocusListener() {

			public void focusGained(FocusEvent e) {
				result.setBorder(CALENDAR_FOCUS_BORDER);
			}

			public void focusLost(FocusEvent e) {
				result.setBorder(CALENDAR_NON_FOCUS_BORDER);
			}

		};
		result.addFocusListener(viewerContainerFocusListener);
		return result;
	}

	private FocusTraversalPolicy createFocusTraversalPolicy(final JComponent cycleRoot) {
		return new CalendarFocusTraversalPolicy(cycleRoot);
	}

	public void dispose() {
		viewerContainer.removeFocusListener(viewerContainerFocusListener);
		dayButton.removeItemListener(calendarDisplayTypeListener);
		weekButton.removeItemListener(calendarDisplayTypeListener);
		monthButton.removeItemListener(calendarDisplayTypeListener);
		prevButton.removeActionListener(prevActionListener);
		todayButton.removeActionListener(todayActionListener);
		nextButton.removeActionListener(nextActionListener);
		calendarDisplayTypeListener = null;
		prevActionListener = null;
		todayActionListener = null;
		nextActionListener = null;
		dayViewer.dispose();
		weekViewer.dispose();
		monthViewer.dispose();
		calendarViewer = null;
		dayViewer = null;
		weekViewer = null;
		monthViewer = null;
	}

	public void init() {
		weekButton.setSelected(true);//select week by default.
	}

	void setActionManager(final CalendarActionManager actionManager) {
		dayViewer.getUI().registerShortCuts(actionManager);
		weekViewer.getUI().registerShortCuts(actionManager);
		monthViewer.getUI().registerShortCuts(actionManager);
	}

	void setCalendarTransferHandler(final CalendarTransferHandler cth) {
		dayViewer.getUI().setTransferHandlerObject(cth);
		weekViewer.getUI().setTransferHandlerObject(cth);
		monthViewer.getUI().setTransferHandlerObject(cth);
	}

	void setDragEnabled(final boolean isDragEnabled) {
		dayViewer.getUI().setDragEnabled(isDragEnabled);
		weekViewer.getUI().setDragEnabled(isDragEnabled);
		monthViewer.getUI().setDragEnabled(isDragEnabled);
	}

	/**
	 * Set item label provider for all viewers.
	 * @param labelProvider
	 */
	public void setLabelProvider(final ILabelProvider labelProvider) {
		if (labelProvider == null) {
			throw new IllegalArgumentException("Specify non-null item label provider");
		}
		dayViewer.getUI().setLabelProvider(labelProvider);
		weekViewer.getUI().setLabelProvider(labelProvider);
		monthViewer.getUI().setLabelProvider(labelProvider);
	}

	void setViewer(final ICalendarViewer viewer) {
		if (viewer == null) {
			throw new IllegalArgumentException("Specify non-null calendar viewer");
		}
		if (calendarViewer != viewer) {
			if (calendarViewer != null) {
				viewerContainer.remove(calendarViewer.getUI());
			}
			calendarViewer = viewer;
			viewerContainer.add(viewer.getUI(), BorderLayout.CENTER);
			CalendarViewerModel cvm = (CalendarViewerModel) calendarViewer.getModel();
			cvm.updateInterval(calendarSchedule.getCalendar());
			drawCalendar();
			refresh();
		}
	}

	public ICalendarViewer getViewer() {
		return calendarViewer;
	}

	void drawCalendar() {
		drawTitle();
		drawDays();
		drawDaysContent();
	}

	void drawTitle() {
		String title = getViewer().getTitle();
		currentIntervalLabel.setText(title);
	}

	void drawDays() {
		getViewer().updateDays();
	}

	void drawDaysContent() {
		getViewer().updateDaysContent();
	}

	void drawSelection(final Date oldSelectedDay, final Date newSelectedDay) {
		getViewer().updateSelection(oldSelectedDay, newSelectedDay);
	}

	void refresh() {
		getViewer().getUI().invalidate();
		getViewer().getUI().repaint();
	}

	private class CalendarDisplayTypeListener
			implements ItemListener {

		public void itemStateChanged(ItemEvent ie) {
			if (ie.getStateChange() == ItemEvent.SELECTED) {
				ICalendarViewer targetViewer = null;
				Object source = ie.getItemSelectable();
				if (source == dayButton) {
					targetViewer = dayViewer;
				} else if (source == weekButton) {
					targetViewer = weekViewer;
				} else if (source == monthButton) {
					targetViewer = monthViewer;
				}
				setViewer(targetViewer);
			}
		}

	}

	private class CalendarFocusTraversalPolicy extends LayoutFocusTraversalPolicy {

		private JComponent cycleRoot;

		public CalendarFocusTraversalPolicy(final JComponent cycleRoot) {
			this.cycleRoot = cycleRoot;
		}

		@Override
		protected boolean accept(Component aComponent) {
			if (aComponent == cycleRoot) {//don't accept container.
				return false;
			}
			/* Container ancestorFocusRoot = result.getFocusCycleRootAncestor();
			 if (aComponent == ancestorFocusRoot) {
			 return false;
			 }*/
			return super.accept(aComponent);
		}

		@Override
		public Component getDefaultComponent(Container aContainer) {
			Date selectedDate = calendarSchedule.getSelectionModel().getSelectedDay();
			ICalendarDay selectedDay = getViewer().getModel().getDay(selectedDate);
			DayUI dayUI = ((AbstractCalendarViewer) getViewer()).getDayUI(selectedDay);
			if (dayUI != null) {
				return dayUI.getDayTable();
			}
			return super.getDefaultComponent(aContainer);
		}

	}

}