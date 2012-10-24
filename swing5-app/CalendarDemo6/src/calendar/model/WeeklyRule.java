package calendar.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
public class WeeklyRule extends CalendarRule {

	private ArrayList<Integer> daysOfWeek;

	public WeeklyRule() {
		daysOfWeek = new ArrayList<Integer>(7);
	}

	public List<Integer> getDaysOfWeek() {
		return Collections.unmodifiableList(daysOfWeek);
	}

	public void addDay(final int dayNum) {
		if (dayNum < Calendar.SUNDAY || dayNum > Calendar.SATURDAY) {
			return;
		}
		Integer value = new Integer(dayNum);
		if (!daysOfWeek.contains(value)) {
			daysOfWeek.add(value);
		}
	}

	public WeeklyRule derive(final Date d) {
		WeeklyRule result = new WeeklyRule();
		result.setTime(getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		result.addDay(dayOfWeek);
		return result;
	}

	public boolean isMappedTo(final Date d) {
		boolean result = false;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		for (Integer nextDayOfWeek : daysOfWeek) {
			Calendar nextTmpCalendar = Calendar.getInstance();
			nextTmpCalendar.set(Calendar.DAY_OF_WEEK, nextDayOfWeek.intValue());
			if (CalendarUtil.equalDaysInWeek(nextTmpCalendar, c)) {
				result = true;
				break;
			}
		}
		return result;
	}

}