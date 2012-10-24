package calendar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import calendar.util.CalendarUtil;
import calendar.view.CalendarDayEvent;
import calendar.view.CalendarDayListener;
import calendar.view.ICalendarDay;
import calendar.view.ICalendarItem;

/**
 * Package private.
 * @author idanilov
 *
 */
class CalendarDay
		implements ICalendarDay {

	private Date date;
	private ArrayList<CalendarItem> items;
	private Vector<CalendarDayListener> listenersList;
	private static final Comparator<CalendarItem> TIME_COMPARATOR = new TimeComparator();

	CalendarDay() {
		this(new Date());
	}

	CalendarDay(final Date date) {
		setDate(date);
		listenersList = new Vector<CalendarDayListener>();
		items = new ArrayList<CalendarItem>();
	}

	/**
	 * @return Date of the day.
	 */
	public Date getDate() {
		return date;
	}

	void setDate(final Date date) {
		this.date = CalendarUtil.normalizeToDay(date);
	}

	void removeItems() {
		items.clear();
		fireDayContentsChanged(new CalendarDayEvent(this));
	}

	/**
	 * @return Items in this day.
	 */
	public List<ICalendarItem> getItems() {
		List result = Collections.unmodifiableList(items);
		return result;
	}

	public ICalendarItem getItem(final int ind) {
		return items.get(ind);
	}

	void addItem(final CalendarItem item) {
		items.add(item);
		Collections.sort(items, TIME_COMPARATOR);
		fireDayContentsChanged(new CalendarDayEvent(this));
	}

	public void removeItem(final int ind) {
		items.remove(ind);
		Collections.sort(items, TIME_COMPARATOR);
		fireDayContentsChanged(new CalendarDayEvent(this));
	}

	public String toString() {
		return "CalendarDay = " + DateFormat.getDateInstance(DateFormat.SHORT).format(getDate());
	}

	public void addCalendarDayListener(final CalendarDayListener cml) {
		if (cml != null && !listenersList.contains(cml)) {
			listenersList.add(cml);
		}
	}

	public void removeCalendarDayListener(final CalendarDayListener cml) {
		listenersList.remove(cml);
	}

	protected void fireDayContentsChanged(final CalendarDayEvent cie) {
		for (CalendarDayListener nextListener : listenersList) {
			nextListener.dayContentsChanged(cie);
		}
	}

	private static class TimeComparator
			implements Comparator<CalendarItem> {

		public int compare(CalendarItem ci1, CalendarItem ci2) {
			Date time1 = ci1.getParentTask().getRecurrentRule().getTime();
			Date time2 = ci2.getParentTask().getRecurrentRule().getTime();
			return time1.compareTo(time2);
		}

	}

}
