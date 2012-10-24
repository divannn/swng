package calendar.model;

import java.util.Date;

import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
public class OneDayRule extends CalendarRule {

	private Date date;

	public OneDayRule() {
		super();
		setDate(new Date());
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date d) {
		this.date = d;
	}

	public CalendarRule derive(final Date d) {
		OneDayRule result = new OneDayRule();
		result.setTime(getTime());
		result.setDate(d);
		return result;
	}

	public boolean isMappedTo(final Date d) {
		return CalendarUtil.equalDays(date, d);
	}

}
