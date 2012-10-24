package calendar;

import calendar.model.CalendarTask;
import calendar.view.ICalendarDay;
import calendar.view.ICalendarItem;

/**
 * Package private.
 * @author idanilov
 *
 */
class CalendarItem
		implements ICalendarItem {

	private CalendarTask parentTask;
	private ICalendarDay parentDay;

	CalendarItem(final CalendarTask parentTask, final ICalendarDay parentDay) {
		this.parentTask = parentTask;
		this.parentDay = parentDay;
	}

	public CalendarTask getParentTask() {
		return parentTask;
	}

	public ICalendarDay getParentDay() {
		return parentDay;
	}

}
