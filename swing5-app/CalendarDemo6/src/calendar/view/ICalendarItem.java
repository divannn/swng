package calendar.view;

import calendar.model.CalendarTask;

/**
 * @author idanilov
 *
 */
public interface ICalendarItem {

	CalendarTask getParentTask();

	ICalendarDay getParentDay();

}
