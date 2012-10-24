package calendar;

import java.util.Calendar;
import java.util.Date;

import calendar.util.CalendarUtil;
import calendar.view.ICalendarDay;
import calendar.view.ICalendarViewerModel;

/**
 * @author idanilov
 *
 */
abstract class CalendarViewerModel
		implements ICalendarViewerModel {

	private CalendarDay[] days;
	private int rows;
	private int columns;

	public CalendarViewerModel(final int rows, final int columns) {
		this.rows = rows;
		this.columns = columns;

		days = new CalendarDay[rows * columns];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				int index = x + (columns * y);
				days[index] = new CalendarDay();
			}
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public ICalendarDay[] getDays() {
		return days;
	}

	/**
	 * @param date
	 * @return CalendarDay object for passed date
	 */
	public ICalendarDay getDay(final Date date) {
		CalendarDay result = null;
		for (CalendarDay nextDay : days) {
			if (CalendarUtil.equalDays(nextDay.getDate(), date)) {
				result = nextDay;
			}
		}
		return result;
	}

	/**
	 * Sets date for all days in current viewer model.
	 * @param calendar
	 */
	void updateInterval(final Calendar calendar) {
		int currentDayIndex = getCurrentDayIndex(calendar);
		//before today
		Calendar tmpCalendar = (Calendar) calendar.clone();
		for (int i = currentDayIndex - 1; i >= 0; i--) {
			tmpCalendar.add(Calendar.DAY_OF_MONTH, -1);
			days[i].setDate(tmpCalendar.getTime());
		}
		//today
		days[currentDayIndex].setDate(calendar.getTime());
		//after today
		tmpCalendar = (Calendar) calendar.clone();
		for (int i = currentDayIndex + 1; i < getRows() * getColumns(); i++) {
			tmpCalendar.add(Calendar.DAY_OF_MONTH, 1);
			days[i].setDate(tmpCalendar.getTime());
		}
	}

	protected abstract boolean isViewportFull();

	protected abstract int getCurrentDayIndex(final Calendar calendar);

	/*boolean contains(final Calendar calendar) {
	 Calendar lower = Calendar.getInstance();
	 lower.setTime(days[0].getDate());
	 Calendar upper = Calendar.getInstance();
	 upper.setTime(days[days.length - 1].getDate());
	 return (lower.before(calendar) && upper.after(calendar));
	 }*/
}
