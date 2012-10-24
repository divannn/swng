package calendar.action;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import window.DialogConstant;
import window.MessageDialog;
import window.MessageDialog.ICON_TYPE;
import calendar.CalendarSchedule;
import calendar.model.CalendarModel;
import calendar.model.CalendarTask;
import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public class SpecialDayAction extends AbstractCalendarAction {

	public SpecialDayAction(final CalendarSchedule calSchedule) {
		super(calSchedule, "Special Day");
	}

	public void actionPerformed(ActionEvent ae) {
		CalendarModel cm = calendarSchedule.getModel();
		if (cm.isSpecial(day.getDate())) {
			//delete special tasks.
			List<ICalendarItem> items = day.getItems();
			if (items.size() > 0) {
				int ret = getConfirmDialog(calendarSchedule.getParentWindow(), "Comfirm",
						"All items in this special day will be removed.Continue?",
						MessageDialog.ICON_TYPE.ERROR).open();
				if (ret == DialogConstant.YES_ID) {
					List<CalendarTask> tasksToRemove = new ArrayList<CalendarTask>();
					for (ICalendarItem nextItem : items) {
						CalendarTask nextTask = nextItem.getParentTask();
						tasksToRemove.add(nextTask);
					}
					cm.removeTasks(tasksToRemove);
					calendarSchedule.getModel().removeSpecialDay(day.getDate());
				}
			} else {
				calendarSchedule.getModel().removeSpecialDay(day.getDate());
			}
		} else {
			calendarSchedule.getModel().addSpecialDay(day.getDate());
		}
	}

	private static MessageDialog getConfirmDialog(final Window parent, final String title,
			final String msg, final ICON_TYPE type) {
		MessageDialog r = null;
		if (parent instanceof Frame) {
			r = MessageDialog.createYesNoDialog((Frame) parent, title, msg, type);
		} else if (parent instanceof Dialog) {
			r = MessageDialog.createYesNoDialog((Dialog) parent, title, msg, type);
		} else {
			r = MessageDialog.createYesNoDialog((Frame) null, title, msg, type);
		}
		return r;
	}
}
