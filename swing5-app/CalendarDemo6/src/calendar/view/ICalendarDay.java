package calendar.view;

import java.util.Date;
import java.util.List;

/**
 * @author idanilov
 *
 */
public interface ICalendarDay {

	/**
	 * @return Date of the day.
	 */
	Date getDate();

	/**
	 * @return Items in this day.
	 */
	List<ICalendarItem> getItems();

	ICalendarItem getItem(final int ind);

	void addCalendarDayListener(final CalendarDayListener cml);

	void removeCalendarDayListener(final CalendarDayListener cml);

}
