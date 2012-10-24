package calendar.view;

import java.util.Calendar;
import java.util.Date;

/**
 * @author idanilov
 *
 */
public interface ICalendarViewerModel {

	int getRows();

	int getColumns();

	ICalendarDay[] getDays();

	/**
	 * @return type of viewer model
	 * <br>
	 * Can be: {@link Calendar#DAY_OF_YEAR},{@link Calendar#WEEK_OF_YEAR},{@link Calendar#MONTH}
	 */
	int getInterval();

	/**
	 * @param date
	 * @return CalendarDay object for passed date
	 */
	ICalendarDay getDay(final Date date);

}
