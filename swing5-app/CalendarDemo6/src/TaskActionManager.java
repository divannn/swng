import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

import calendar.CalendarSchedule;
import calendar.action.AbstractCalendarAction;
import calendar.action.CalendarActionManager;
import calendar.action.GoToDateAction;
import calendar.action.SpecialDayAction;
import calendar.model.CalendarModel;
import calendar.view.ICalendarDay;

/**
 * @author idanilov
 *
 */
public class TaskActionManager extends CalendarActionManager {

	private NewTaskAction newTaskAction;
	private EditTaskAction editTaskAction;
	private GoToDateAction goToDateAction;
	private DeleteTaskAction deleteTaskAction;
	private SpecialDayAction specialDayAction;

	private AbstractCalendarAction cutItemAction;
	private AbstractCalendarAction copyItemAction;
	private AbstractCalendarAction pasteItemAction;

	private JCheckBoxMenuItem specialDayMenuItem;

	public TaskActionManager(final CalendarSchedule calendarSchedule) {
		super(calendarSchedule);
		specialDayMenuItem = new JCheckBoxMenuItem(specialDayAction);
	}

	protected void createActions() {
		newTaskAction = new NewTaskAction(calendarSchedule);
		add(newTaskAction);
		editTaskAction = new EditTaskAction(calendarSchedule);
		add(editTaskAction);
		goToDateAction = new GoToDateAction(calendarSchedule);
		add(goToDateAction);
		deleteTaskAction = new DeleteTaskAction(calendarSchedule);
		add(deleteTaskAction);
		specialDayAction = new SpecialDayAction(calendarSchedule);
		add(specialDayAction);
		cutItemAction = new CutItemAction(calendarSchedule);
		add(cutItemAction);
		copyItemAction = new CopyItemAction(calendarSchedule);
		add(copyItemAction);
		pasteItemAction = new PasteItemAction(calendarSchedule);
		add(pasteItemAction);
	}

	public void init(final ICalendarDay day, final int[] selectedItems) {
		super.init(day, selectedItems);
		CalendarModel cm = calendarSchedule.getModel();
		specialDayMenuItem.setSelected(cm.isSpecial(day.getDate()));
	}

	public Action getDefaultAction(final int[] selection) {
		if (selection != null && selection.length > 0) {
			return editTaskAction;
		}
		return newTaskAction;
	}

	//XXX : maybe use popup listenter for dynamic menu fill.
	protected JPopupMenu createPopup() {
		JPopupMenu result = new JPopupMenu();
		result.add(newTaskAction);
		result.add(editTaskAction);
		result.addSeparator();
		result.add(goToDateAction);
		result.addSeparator();
		result.add(cutItemAction);
		result.add(copyItemAction);
		result.add(pasteItemAction);
		result.add(deleteTaskAction);
		result.addSeparator();
		result.add(specialDayMenuItem);
		return result;
	}

}
