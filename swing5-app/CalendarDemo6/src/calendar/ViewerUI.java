package calendar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

import calendar.action.AbstractCalendarAction;
import calendar.action.CalendarActionManager;
import calendar.model.CalendarTask;
import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
class ViewerUI extends JPanel {

	private JLabel[] dayOfWeekLabels;
	private DayUI[] dayUIs;

	private int rows;
	private int columns;

	private ILabelProvider labelProvider;
	private CalendarCellRenderer itemRenderer;

	public ViewerUI(final int rows, final int cols) {
		super(new BorderLayout());
		this.rows = rows;
		this.columns = cols;
		labelProvider = createDefaultLabelProvider();
		itemRenderer = new CalendarCellRenderer(this);

		add(createDaysOfWeekPanel(), BorderLayout.NORTH);
		add(createDaysPanel(), BorderLayout.CENTER);
		updateDaysOfWeek(Calendar.getInstance());
	}

	public ILabelProvider getLabelProvider() {
		return labelProvider;
	}

	public void setLabelProvider(final ILabelProvider labelProvider) {
		this.labelProvider = labelProvider;
		repaint();
	}

	protected ILabelProvider createDefaultLabelProvider() {
		ILabelProvider result = new ILabelProvider() {

			public Icon getIcon(final CalendarItem item) {
				return CalendarUI.CALENDAR_ICON;
			}

			public String getText(final CalendarItem item) {
				CalendarTask parentTask = item.getParentTask();
				String timeString = CalendarUtil.TIME_FORMAT.format(parentTask.getRecurrentRule()
						.getTime());
				return timeString + " " + parentTask.getLabel();
			}

		};
		return result;
	}

	private JComponent createDaysOfWeekPanel() {
		JPanel result = new JPanel(new GridLayout(1, 7));
		dayOfWeekLabels = new JLabel[7];
		for (int i = 0; i < 7; i++) {
			dayOfWeekLabels[i] = new JLabel("", SwingConstants.CENTER);
			result.add(dayOfWeekLabels[i]);
		}
		return result;
	}

	private JComponent createDaysPanel() {
		JPanel result = new JPanel(new GridLayout(rows, columns));
		dayUIs = new DayUI[rows * columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				int index = x + (columns * y);
				dayUIs[index] = createDayUI(index);
				result.add(dayUIs[index]);
			}
		}
		return result;
	}

	protected DayUI createDayUI(final int i) {
		DayUI result = new DayUI();
		result.getDayTable().putClientProperty(DayUI.INDEX, new Integer(i));
		//result.getDayTable().putClientProperty(DAY, days[i]);
		result.getDayTable().setDefaultRenderer(Object.class, itemRenderer);
		return result;
	}

	DayUI[] getDayUIs() {
		return dayUIs;
	}

	/**
	 * Registers key strokes for action if they were provided.
	 * @param actionManager
	 */
	void registerShortCuts(final CalendarActionManager am) {
		if (am != null) {
			unregisterShortCuts(am);
			final List<Action> actions = am.getActions();
			for (Action nextAction : actions) {
				KeyStroke nextKeyStroke = (KeyStroke) nextAction.getValue(Action.ACCELERATOR_KEY);
				if (nextKeyStroke != null) {
					//System.err.println("key stroke : " + nextKeyStroke);
					for (DayUI nextDayUI : dayUIs) {
						if (nextAction.getValue(AbstractCalendarAction.FAKE_ACCELERATOR) == null) {
							nextDayUI.installKeyboardAction(nextAction, nextKeyStroke,
									JComponent.WHEN_FOCUSED);
						}
					}
				}
			}
		}
	}

	/**
	 * Unregisters all key strokes. 
	 */
	void unregisterShortCuts(final CalendarActionManager am) {
		if (am != null) {
			final List<Action> actions = am.getActions();
			for (Action nextAction : actions) {
				KeyStroke nextKeyStroke = (KeyStroke) nextAction.getValue(Action.ACCELERATOR_KEY);
				if (nextKeyStroke != null) {
					for (DayUI nextDayUI : dayUIs) {
						nextDayUI.uninstallKeyboardAction(nextKeyStroke);
					}
				}
			}
		}
	}

	void setTransferHandlerObject(final TransferHandler handler) {
		for (DayUI nextDayUI : dayUIs) {
			nextDayUI.setTransferHandlerObject(handler);
		}
	}

	void setDragEnabled(final boolean dragEnabled) {
		for (DayUI nextDayUI : dayUIs) {
			nextDayUI.setDragEnabled(dragEnabled);
		}
	}

	private void updateDaysOfWeek(final Calendar calendar) {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		int day = firstDayOfWeek;
		for (int i = 0; i < 7; i++) {
			dayOfWeekLabels[i].setText(CalendarUtil.getDayOfWeekName(day));
			if (day < 7) {
				day++;
			} else {
				day -= 6;
			}
		}
	}

	protected void dispose() {
		for (DayUI next : dayUIs) {
			next.dispose();
		}
	}

}
