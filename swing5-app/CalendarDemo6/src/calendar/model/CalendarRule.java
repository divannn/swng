package calendar.model;

import java.util.Date;

import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
public abstract class CalendarRule {

	private Date time;

	public CalendarRule() {
		setTime(new Date());
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = CalendarUtil.normalizeToTime(time);
	}

	/**
	 * Duplicates this rule for date <code>d</code>. 
	 */
	public abstract CalendarRule derive(final Date d);

	public abstract boolean isMappedTo(final Date d);

}
