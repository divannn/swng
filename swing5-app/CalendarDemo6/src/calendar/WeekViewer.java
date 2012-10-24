package calendar;

import java.util.Calendar;

import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
public class WeekViewer extends AbstractCalendarViewer {

	private static final int ROW = 1;
	private static final int COLUMN = 7;

	public WeekViewer(CalendarSchedule calendarSchedule) {
		super(calendarSchedule);
	}

	protected CalendarViewerModel createModel() {
		return new CalendarViewerModel(ROW, COLUMN) {

			public int getInterval() {
				return Calendar.WEEK_OF_YEAR;
			}

			protected int getCurrentDayIndex(final Calendar calendar) {
				return getDayInWeekIndex(calendar);
			}

			public boolean isViewportFull() {
				return true;
			}

		};
	}

	public String getName() {
		return "WeekViewer";
	}

	public String getTitle() {
		Calendar calendar = getCalendarSchedule().getCalendar();
		int ind = getDayInWeekIndex(calendar);
		Calendar tmpCalendar = (Calendar) calendar.clone();
		tmpCalendar.add(Calendar.DAY_OF_MONTH, -ind);

		int dayNum1 = tmpCalendar.get(Calendar.DAY_OF_MONTH);
		int monthNum1 = tmpCalendar.get(Calendar.MONTH);

		tmpCalendar.add(Calendar.DATE, 6);

		int dayNum2 = tmpCalendar.get(Calendar.DAY_OF_MONTH);
		int monthNum2 = tmpCalendar.get(Calendar.MONTH);
		String range = dayNum1 + " " + CalendarUtil.getMonthName(monthNum1) + " - " + dayNum2 + " "
				+ CalendarUtil.getMonthName(monthNum2);
		String result = calendar.get(Calendar.YEAR) + ", " + range + " ("
				+ tmpCalendar.get(Calendar.WEEK_OF_YEAR) + ")";
		return result;
	}

	private int getDayInWeekIndex(final Calendar calendar) {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		int result = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
		if (result < 0) {
			result += 7;
		}
		return result;
	}

}
