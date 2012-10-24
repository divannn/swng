import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerDateModel;

/**
 * @author idanilov
 *
 */
public class GranulatedDateModel extends SpinnerDateModel {

	private int minuteGranularity;

	public GranulatedDateModel(final int granularity) {
		if (granularity < 0 || granularity > 60 || (60 % granularity != 0)) {
			throw new IllegalArgumentException("Invalid granularity: " + granularity);
		}
		minuteGranularity = granularity;
		DateTimeChooserFactory.getInstance().restrictTimeModel(this);
	}

	public int getGranularity() {
		return minuteGranularity;
	}

	public Object getNextValue() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate());
		cal.add(Calendar.MINUTE, minuteGranularity);
		Date next = cal.getTime();
		Object result = ((getEnd() == null) || (getEnd().compareTo(next) >= 0)) ? next : null;
		//System.err.println("NEXT: " + result);
		return result;
	}

	public Object getPreviousValue() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate());
		cal.add(Calendar.MINUTE, -minuteGranularity);
		Date prev = cal.getTime();
		Object result = ((getStart() == null) || (getStart().compareTo(prev) <= 0)) ? prev : null;
		//System.err.println("PREV: " + result);
		return result;
	}
}