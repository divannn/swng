package calendar.model;

import java.util.Date;
import java.util.EventObject;

/**
 * @author idanilov
 *
 */
public class CalendarSelectionEvent extends EventObject {

	private Date oldDay;
	private int[] oldItems;

	public CalendarSelectionEvent(final Object source, final Date oldDay, final int[] oldItems) {
		super(source);
		this.oldDay = oldDay;
		this.oldItems = oldItems;
	}

	public Date getOldDay() {
		return oldDay;
	}

	public int[] getOldItems() {
		return oldItems;
	}

}