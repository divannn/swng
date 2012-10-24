package calendar;

import java.util.Calendar;

import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
public class MonthViewer extends AbstractCalendarViewer {

	private static final int ROW = 6;
	private static final int COLUMN = 7;

	public MonthViewer(CalendarSchedule calendarSchedule) {
		super(calendarSchedule);
	}

	protected CalendarViewerModel createModel() {
		return new CalendarViewerModel(ROW, COLUMN) {

			public int getInterval() {
				return Calendar.MONTH;
			}

			public boolean isViewportFull() {
				return false;
			}

			protected int getCurrentDayIndex(final Calendar calendar) {
				return getDayInMonthIndex(calendar);
			}

			private int getFirstDayInMonthIndex(final Calendar calendar) {
				Calendar tmpCalendar = (Calendar) calendar.clone();
				int firstDayOfWeek = tmpCalendar.getFirstDayOfWeek();
				tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
				int result = tmpCalendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
				if (result < 0) {
					result += 7;
				}
				return result;
			}

			private int getDayInMonthIndex(final Calendar calendar) {
				int currentDayNum = calendar.get(Calendar.DAY_OF_MONTH);
				int result = getFirstDayInMonthIndex(calendar) + (currentDayNum - 1);
				return result;
			}

		};
	}

	public String getName() {
		return "MonthViewer";
	}

	public String getTitle() {
		Calendar calendar = getCalendarSchedule().getCalendar();
		String result = CalendarUtil.getMonthName(calendar.get(Calendar.MONTH)) + " - "
				+ calendar.get(Calendar.YEAR);
		return result;
	}

}
