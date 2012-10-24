package calendar.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author idanilov
 *
 */
public abstract class CalendarUtil {

	public static final DateFormatSymbols DATE_FORMAT_SYMBOLS = new DateFormatSymbols();

	public static final DateFormat TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.SHORT,
			Locale.getDefault());
	public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT,
			Locale.getDefault());

	private CalendarUtil() {
	}

	public static String getDayOfWeekName(final int dayNum) {
		String[] dayOfWeekNames = DATE_FORMAT_SYMBOLS.getWeekdays();
		return dayOfWeekNames[dayNum];
	}

	public static String getShortDayOfWeekName(final int dayNum) {
		String[] dayOfWeekNames = DATE_FORMAT_SYMBOLS.getShortWeekdays();
		return dayOfWeekNames[dayNum];
	}

	public static String getMonthName(final int monthNum) {
		String[] monthNames = DATE_FORMAT_SYMBOLS.getMonths();
		return monthNames[monthNum];
	}

	public static boolean equalDays(final Calendar cal1, final Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			return false;
		}
		return ((cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) && (cal1
				.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)));
	}

	public static boolean equalDays(final Date date1, final Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return equalDays(cal1, cal2);
	}

	public static boolean equalDaysInWeek(final Date date1, final Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return equalDaysInWeek(cal1, cal2);
	}

	public static boolean equalDaysInWeek(final Calendar cal1, final Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			return false;
		}
		return (cal1.get(Calendar.DAY_OF_WEEK) == cal2.get(Calendar.DAY_OF_WEEK));
	}

	public static boolean equalTime(final Date date1, final Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return equalTime(cal1, cal2);
	}

	public static boolean equalTime(final Calendar cal1, final Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			return false;
		}
		return ((cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)) && (cal1
				.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)));
	}

	public static Date normalizeToDay(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date normalizeToTime(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar resultCal = Calendar.getInstance();
		resultCal.setTimeInMillis(0);
		resultCal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		resultCal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		return resultCal.getTime();
	}

	//debug utils.
	public static String toShortTime(final Date d) {
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(d);
	}

	public static String toMediumTime(final Date d) {
		return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(d);
	}

	public static String toLongTime(final Date d) {
		return DateFormat.getTimeInstance(DateFormat.LONG).format(d);
	}

	public static String toShortDate(final Date d) {
		return DateFormat.getDateInstance(DateFormat.SHORT).format(d);
	}

	public static String toMediumDate(final Date d) {
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
	}

	public static String toLongDate(final Date d) {
		return DateFormat.getDateInstance(DateFormat.LONG).format(d);
	}

}