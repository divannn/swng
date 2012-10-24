import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

import calendar.CalendarSchedule;
import calendar.dnd.CalendarTransferData;
import calendar.dnd.CalendarTransferHandler;
import calendar.dnd.CalendarTransferUtil;
import calendar.model.CalendarRule;
import calendar.model.CalendarTask;
import calendar.view.ICalendarDay;
import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public class TaskTransferHandler extends CalendarTransferHandler {

	public TaskTransferHandler(final CalendarSchedule schedule) {
		super(schedule);
	}

	protected boolean canPaste(final ICalendarDay targetDay, final Transferable t) {
		CalendarTransferData data = CalendarTransferUtil.getCalendarData(t);
		return canPaste(data, targetDay);
	}

	protected boolean paste(final ICalendarDay targetDay, final Transferable t) {
		CalendarTransferData data = CalendarTransferUtil.getCalendarData(t);
		if (data != null) {
			List<CalendarTask> tasksToAdd = new ArrayList<CalendarTask>();
			List<ICalendarItem> item = data.getSelectedItems();
			for (ICalendarItem nextItem : item) {
				CalendarTask nextTask = nextItem.getParentTask();
				CalendarRule nextRule = nextTask.getRecurrentRule();
				//clone task.
				CalendarTask newTask = new CalendarTask();
				CalendarRule newRule = nextRule.derive(targetDay.getDate());
				newTask.setRecurrentRule(newRule);
				newTask.setTarget(nextTask.getTarget());
				tasksToAdd.add(newTask);
			}
			schedule.getModel().addTasks(tasksToAdd);
			return true;
		}
		return false;
	}

	public static boolean canPaste(final CalendarTransferData data, final ICalendarDay targetDay) {
		boolean result = false;
		if (data != null && targetDay != null) {
			List<ICalendarItem> itemsToPaste = data.getSelectedItems();
			if (itemsToPaste != null && itemsToPaste.size() > 0) {
				for (ICalendarItem nextItem : itemsToPaste) {
					CalendarTask nextTask = nextItem.getParentTask();
					CalendarRule nextRule = nextTask.getRecurrentRule();
					result = !nextRule.isMappedTo(targetDay.getDate());//check that rule not mapped to target day.
				}
			}
		}
		return result;
	}

}
