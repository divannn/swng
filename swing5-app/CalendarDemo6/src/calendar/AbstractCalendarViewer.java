package calendar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import calendar.action.CalendarActionManager;
import calendar.model.CalendarModel;
import calendar.model.CalendarRule;
import calendar.model.CalendarSelectionModel;
import calendar.model.CalendarTask;
import calendar.model.OneDayRule;
import calendar.util.CalendarUtil;
import calendar.view.ICalendarDay;

/**
 * Controller between CalendarViewerModel and ViewerUI.
 * XXX: myabe introduce controller class "DayViewer" to adapt CalendarDay and DayUI?
 * @author idanilov
 *
 */
public abstract class AbstractCalendarViewer
		implements ICalendarViewer {

	private CalendarSchedule calendarSchedule;//parent edit part.
	private CalendarViewerModel viewerModel;
	private ViewerUI viewerUI;
	private HashMap<CalendarDay, DayUI> day2UIMap;

	//TODO:do not create insyances for all viewers.
	private MouseListener tableMouseListener;
	private MouseListener dayLabelMouseListener;

	private FocusListener tableFocusListener;
	private ItemSelectionListener itemSelectionListener;

	private LeftFocusTransferAction goLeftAction;
	private RightFocusTransferAction goRightAction;
	private UpFocusTransferAction goUpAction;
	private DownFocusTransferAction goDownAction;

	public AbstractCalendarViewer(final CalendarSchedule calendarSchedule) {
		super();
		this.calendarSchedule = calendarSchedule;
		createDayUIListeners();
		createDayExtraFocusActions();
		day2UIMap = new HashMap<CalendarDay, DayUI>();
		viewerModel = createModel();
		viewerUI = createUI();
	}

	protected abstract CalendarViewerModel createModel();

	protected CalendarSchedule getCalendarSchedule() {
		return calendarSchedule;
	}

	public CalendarViewerModel getModel() {
		return viewerModel;
	}

	public ViewerUI getUI() {
		return viewerUI;
	}

	ViewerUI createUI() {
		ViewerUI result = new ViewerUI(viewerModel.getRows(), viewerModel.getColumns());
		int rows = viewerModel.getRows();
		int cols = viewerModel.getColumns();

		//configure day UI.
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				final int index = x + (cols * y);
				DayUI nextDayUI = result.getDayUIs()[index];
				CalendarDay nextDay = (CalendarDay) viewerModel.getDays()[index];
				day2UIMap.put(nextDay, nextDayUI);
				configureDayUI(nextDayUI, nextDay);
			}
		}
		return result;
	}

	protected void configureDayUI(final DayUI dayUI, final CalendarDay day) {
		dayUI.getDayTable().putClientProperty(DayUI.DAY, day);
		installDayExtraFocusKeys(dayUI);

		installDayUIListeners(dayUI);
	}

	private void installDayExtraFocusKeys(final DayUI dayUI) {
		dayUI.getDayTable().registerKeyboardAction(goRightAction,
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_FOCUSED);

		dayUI.getDayTable().registerKeyboardAction(goLeftAction,
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_FOCUSED);

		dayUI.getDayTable().registerKeyboardAction(goUpAction,
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_FOCUSED);

		dayUI.getDayTable().registerKeyboardAction(goDownAction,
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK),
				JComponent.WHEN_FOCUSED);
	}

	private void uninstallDayExtraFocusKeys(final DayUI dayUI) {
		dayUI.getDayTable().unregisterKeyboardAction(
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_DOWN_MASK));

		dayUI.getDayTable().unregisterKeyboardAction(
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_DOWN_MASK));

		dayUI.getDayTable().unregisterKeyboardAction(
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_DOWN_MASK));

		dayUI.getDayTable().unregisterKeyboardAction(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK));
	}

	private void createDayUIListeners() {
		tableMouseListener = new CalendarPopupListener();
		dayLabelMouseListener = new CalendarNameLabelListener();
		tableFocusListener = new CalendarFocusListener();
		itemSelectionListener = new ItemSelectionListener();
	}

	private void createDayExtraFocusActions() {
		goLeftAction = new LeftFocusTransferAction(calendarSchedule);
		goRightAction = new RightFocusTransferAction(calendarSchedule);
		goUpAction = new UpFocusTransferAction(calendarSchedule);
		goDownAction = new DownFocusTransferAction(calendarSchedule);
	}

	/**
	 * Attach to the view (UI). Listen to the day UI to update model.
	 * @param dayUI
	 */
	private void installDayUIListeners(final DayUI dayUI) {
		dayUI.getDayNameLabel().addMouseListener(dayLabelMouseListener);
		dayUI.getDayTable().addMouseListener(tableMouseListener);
		dayUI.getDayTable().getSelectionModel().addListSelectionListener(itemSelectionListener);

		dayUI.getDayTable().addFocusListener(tableFocusListener);
	}

	/**
	 * Detach from view (UI). Stops listen to the day UI.
	 * @param dayUI
	 */
	private void uninstallDayUIListeners(final DayUI dayUI) {
		dayUI.getDayTable().removeFocusListener(tableFocusListener);

		dayUI.getDayTable().getSelectionModel().removeListSelectionListener(itemSelectionListener);
		dayUI.getDayTable().removeMouseListener(tableMouseListener);
		dayUI.getDayNameLabel().removeMouseListener(dayLabelMouseListener);
	}

	public void updateDays() {
		CalendarModel cm = calendarSchedule.getModel();
		CalendarSelectionModel csm = calendarSchedule.getSelectionModel();
		Date selectedDay = csm.getSelectedDay();
		Calendar calendar = calendarSchedule.getCalendar();
		Calendar tmpCalendar = (Calendar) calendar.clone();
		for (int i = 0; i < viewerModel.getDays().length; i++) {
			//day label.
			ICalendarDay nextDay = viewerModel.getDays()[i];
			DayUI nextDayUI = getDayUI(nextDay);
			tmpCalendar.setTime(nextDay.getDate());
			int nextDayNum = tmpCalendar.get(Calendar.DAY_OF_MONTH);
			String dayText = "" + nextDayNum;
			if (i == 0) {//mark first day in current calendar view.
				dayText += " " + CalendarUtil.getMonthName(tmpCalendar.get(Calendar.MONTH));
			}
			nextDayUI.setDayLabelText(dayText);

			//day selection.
			boolean isSelected = false;
			if (selectedDay != null) {
				isSelected = CalendarUtil.equalDays(selectedDay, nextDay.getDate());
			}
			nextDayUI.setDaySelection(isSelected, false, isToday(tmpCalendar));

			//highlight day background.				
			if (cm.isSpecial(nextDay.getDate())) {
				nextDayUI.setDayBackground(DayUI.SPECIAL_DAY_CELL_COLOR);
			} else {
				if (viewerModel.isViewportFull()) {
					nextDayUI.setDayBackground(DayUI.CELL_COLOR);
				} else {
					if (tmpCalendar.get(viewerModel.getInterval()) % 2 == 0) {
						nextDayUI.setDayBackground(DayUI.CELL_COLOR);
					} else {
						nextDayUI.setDayBackground(DayUI.ADJACENT_CELL_COLOR);
					}
				}
			}
		}
	}

	public void updateDaysContent() {
		CalendarModel cm = calendarSchedule.getModel();
		for (ICalendarDay nextCalendarDay : viewerModel.getDays()) {
			((CalendarDay) nextCalendarDay).removeItems();
		}
		List<CalendarTask> tasks = cm.getTasks();
		for (CalendarTask nextTask : tasks) {
			processTask(nextTask);
		}

		for (int i = 0; i < viewerModel.getDays().length; i++) {
			ICalendarDay nextDay = viewerModel.getDays()[i];
			DayUI nextDayUI = getDayUI(nextDay);
			nextDayUI.setData(nextDay);
		}
	}

	public void updateSelection(final Date oldSelectedDay, final Date selectedDay) {
		//		repaint UI for old selection.
		DayUI oldSelectedUI = getDayUI(viewerModel.getDay(oldSelectedDay));
		if (oldSelectedUI != null) {
			Calendar tmpCalendar = Calendar.getInstance();
			tmpCalendar.setTime(oldSelectedDay);
			boolean isToday = isToday(tmpCalendar);
			oldSelectedUI.setDaySelection(false, false, isToday);
		}
		//repaint UI for new selection.
		DayUI selectedUI = getDayUI(viewerModel.getDay(selectedDay));
		if (selectedUI != null) {
			Calendar tmpCalendar = Calendar.getInstance();
			tmpCalendar.setTime(selectedDay);
			boolean isToday = isToday(tmpCalendar);
			selectedUI.setDaySelection(true, true, isToday);
			//selectedUI.setFocused();
			//System.err.println("GGG");
		}
	}

	DayUI getDayUI(final ICalendarDay day) {
		return day2UIMap.get(day);
	}

	private void selectDay(final JTable table) {
		if (table != null) {
			Integer ind = (Integer) table.getClientProperty(DayUI.INDEX);
			calendarSchedule.getSelectionModel().setSelectedDay(
					viewerModel.getDays()[ind.intValue()].getDate());
		}
	}

	private boolean isToday(final Calendar cal) {
		return CalendarUtil.equalDays(cal, calendarSchedule.getToday());
	}

	private void processTask(final CalendarTask task) {
		CalendarRule rule = task.getRecurrentRule();
		List<ICalendarDay> calendarDays = getDaysForRule(rule);
		for (ICalendarDay nextDay : calendarDays) {
			CalendarItem nextNewItem = new CalendarItem(task, nextDay);
			((CalendarDay) nextDay).addItem(nextNewItem);
		}
	}

	public List<ICalendarDay> getDaysForRule(final CalendarRule rule) {
		List<ICalendarDay> result = new ArrayList<ICalendarDay>();
		CalendarModel cm = calendarSchedule.getModel();
		for (int i = 0; i < viewerModel.getDays().length; i++) {
			ICalendarDay nextCalDay = viewerModel.getDays()[i];
			//OneDayRule is checked only for special day distinction -
			//because special day should overlap recurrent tasks. 
			if (!(rule instanceof OneDayRule) && cm.isSpecial(nextCalDay.getDate())) {
				continue;
			}
			if (rule.isMappedTo(nextCalDay.getDate())) {
				result.add(nextCalDay);
			}
		}
		return result;
	}

	public void dispose() {
		viewerUI.unregisterShortCuts(calendarSchedule.getActionManager());
		viewerUI.dispose();
		for (DayUI nextDayUI : viewerUI.getDayUIs()) {
			uninstallDayExtraFocusKeys(nextDayUI);
			uninstallDayUIListeners(nextDayUI);
		}
		itemSelectionListener.focusedTable = null;
		viewerUI = null;
		viewerModel = null;
		day2UIMap.clear();
		day2UIMap = null;
	}

	/**
	 * Listen to the day UI and updates calendar selection.
	 * @author idanilov
	 *
	 */
	private class CalendarFocusListener
			implements FocusListener {

		public void focusGained(FocusEvent e) {
			if (!e.isTemporary()) {
				JTable t = (JTable) e.getComponent();
				selectDay(t);
				itemSelectionListener.focusedTable = t;
			}
		}

		public void focusLost(FocusEvent e) {
			if (!e.isTemporary()) {
				JTable t = (JTable) e.getComponent();
				if (!t.getSelectionModel().isSelectionEmpty()) {
					t.getSelectionModel().clearSelection();
				}
				itemSelectionListener.focusedTable = null;
			}
		}

	}

	/**
	 * Forces item selection.
	 * @author idanilov
	 *
	 */
	private class ItemSelectionListener
			implements ListSelectionListener {

		/**
		 * Used to ensure that item selection happends inside one day.
		 */
		private JTable focusedTable;

		public void valueChanged(ListSelectionEvent lse) {
			if (!lse.getValueIsAdjusting()) {
				if (focusedTable != null) {
					ListSelectionModel source = (ListSelectionModel) lse.getSource();
					if (source == focusedTable.getSelectionModel()) {
						//System.err.println("item sel - " + Arrays.toString(getSelectedRows(source)));
						calendarSchedule.getSelectionModel().setSelectedItems(
								getSelectedRows(source));
					}
				}
			}
		}

		/**
		 * Copy of JTable#getSelectedRows().
		 */
		private int[] getSelectedRows(final ListSelectionModel lsm) {
			int iMin = lsm.getMinSelectionIndex();
			int iMax = lsm.getMaxSelectionIndex();
			if ((iMin == -1) || (iMax == -1)) {
				return new int[0];
			}
			int[] rvTmp = new int[1 + (iMax - iMin)];
			int n = 0;
			for (int i = iMin; i <= iMax; i++) {
				if (lsm.isSelectedIndex(i)) {
					rvTmp[n++] = i;
				}
			}
			int[] rv = new int[n];
			System.arraycopy(rvTmp, 0, rv, 0, n);
			return rv;
		}
	}

	private abstract class CalendarMouseAdapter extends MouseAdapter {

		public void mousePressed(MouseEvent me) {
			requestDay(me);
			showPopup(me);
		}

		public void mouseReleased(MouseEvent me) {
			showPopup(me);
		}

		protected void requestDay(final MouseEvent me) {
			JTable table = getTargetTable(me);
			if (!table.isFocusOwner()) {
				table.requestFocusInWindow();//will select day in model.
			}
		}

		protected abstract JTable getTargetTable(final MouseEvent me);

		protected void showPopup(MouseEvent me) {
			if (!calendarSchedule.isEditable()) {
				return;
			}
			CalendarActionManager am = calendarSchedule.getActionManager();
			if (am == null) {
				return;
			}
			if (me.isPopupTrigger()) {
				JTable table = getTargetTable(me);
				CalendarDay selectedDay = (CalendarDay) table.getClientProperty(DayUI.DAY);
				am.init(selectedDay, table.getSelectedRows());
				Component comp = me.getComponent();
				am.showPopup(comp, me.getX(), me.getY());
			}
		}
		//~XTSce70768

	}

	/**
	 * Used just to select day on clicking on day label.
	 */
	private class CalendarNameLabelListener extends CalendarMouseAdapter {

		protected JTable getTargetTable(final MouseEvent me) {
			JTable result = null;
			Component comp = me.getComponent();
			for (DayUI nextDayUI : viewerUI.getDayUIs()) {
				if (nextDayUI.getDayNameLabel() == comp) {
					result = nextDayUI.getDayTable();
					break;
				}
			}
			return result;
		}

	}

	private class CalendarPopupListener extends CalendarMouseAdapter {

		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() == 2) {
				if (!calendarSchedule.isEditable()) {
					return;
				}
				CalendarActionManager am = calendarSchedule.getActionManager();
				if (am == null) {
					return;
				}
				JTable t = (JTable) me.getSource();
				Action defaultAction = am.getDefaultAction(t.getSelectedRows());
				if (defaultAction != null) {
					if (defaultAction.isEnabled()) {
						defaultAction.actionPerformed(new ActionEvent(t,
								ActionEvent.ACTION_PERFORMED, null));
					}
				}
			}
		}

		protected JTable getTargetTable(final MouseEvent me) {
			Component comp = me.getComponent();
			return (JTable) comp;
		}

	}

	private static abstract class DayFocusTransferAction extends AbstractAction {

		protected CalendarSchedule calendarSchedule;

		public DayFocusTransferAction(final CalendarSchedule schedule) {
			calendarSchedule = schedule;
		}
	}

	private static class UpFocusTransferAction extends DayFocusTransferAction {

		public UpFocusTransferAction(CalendarSchedule schedule) {
			super(schedule);
		}

		/**
		 * @param i
		 * @return index above i or -1 if impossible go up
		 */
		private int getUp(final int i) {
			int result = i - calendarSchedule.getUI().getViewer().getModel().getColumns();
			if (result < 0) {
				result = -1;
			}
			return result;
		}

		public void actionPerformed(ActionEvent ae) {
			JTable table = (JTable) ae.getSource();
			Integer ind = (Integer) table.getClientProperty(DayUI.INDEX);
			int up = getUp(ind.intValue());
			if (up != -1) {
				DayUI upDayUI = calendarSchedule.getUI().getViewer().getUI().getDayUIs()[up];
				upDayUI.setFocused();
			}
		}
	}

	private static class DownFocusTransferAction extends DayFocusTransferAction {

		public DownFocusTransferAction(CalendarSchedule schedule) {
			super(schedule);
		}

		/**
		 * @param i
		 * @return index under i or -1 if impossible go under
		 */
		private int getDown(final int i) {
			int cols = calendarSchedule.getUI().getViewer().getModel().getColumns();
			int rows = calendarSchedule.getUI().getViewer().getModel().getRows();
			int result = i + cols;
			if (result >= rows * cols) {
				result = -1;
			}
			return result;
		}

		public void actionPerformed(ActionEvent ae) {
			JTable table = (JTable) ae.getSource();
			Integer ind = (Integer) table.getClientProperty(DayUI.INDEX);
			int down = getDown(ind.intValue());
			if (down != -1) {
				DayUI downDayUI = calendarSchedule.getUI().getViewer().getUI().getDayUIs()[down];
				downDayUI.setFocused();
			}
		}
	}

	private static class RightFocusTransferAction extends DayFocusTransferAction {

		public RightFocusTransferAction(CalendarSchedule schedule) {
			super(schedule);
		}

		/**
		 * @param i
		 * @return index to the right of i or -1 if impossible go right
		 */
		int getRight(final int i) {
			int result = i + 1;
			int cols = calendarSchedule.getUI().getViewer().getModel().getColumns();
			int row = i / cols;
			int rowAfter = result / cols;
			if (row != rowAfter) {
				result = -1;
			}
			return result;
		}

		public void actionPerformed(ActionEvent ae) {
			JTable table = (JTable) ae.getSource();
			Integer ind = (Integer) table.getClientProperty(DayUI.INDEX);
			int right = getRight(ind.intValue());
			if (right != -1) {
				DayUI rightDayUI = calendarSchedule.getUI().getViewer().getUI().getDayUIs()[right];
				rightDayUI.setFocused();
			}
		}

	}

	private static class LeftFocusTransferAction extends DayFocusTransferAction {

		public LeftFocusTransferAction(CalendarSchedule schedule) {
			super(schedule);
		}

		/**
		 * @param i
		 * @return index to the left of i or -1 if impossible go left
		 */
		int getLeft(final int i) {
			int result = i - 1;
			int cols = calendarSchedule.getUI().getViewer().getModel().getColumns();
			int row = i / cols;
			int rowAfter = result / cols;
			if (row != rowAfter) {
				result = -1;
			}
			return result;
		}

		public void actionPerformed(ActionEvent ae) {
			JTable table = (JTable) ae.getSource();
			Integer ind = (Integer) table.getClientProperty(DayUI.INDEX);
			int left = getLeft(ind.intValue());
			if (left != -1) {
				DayUI leftDayUI = calendarSchedule.getUI().getViewer().getUI().getDayUIs()[left];
				leftDayUI.setFocused();
			}
		}

	}

}