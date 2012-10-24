package calendar.model;

import java.util.EventObject;

/**
 * @author idanilov
 *
 */
public class CalendarModelEvent extends EventObject {

	public CalendarModelEvent(final Object source) {
		super(source);
	}

	public CalendarModel getSource() {
		return (CalendarModel) super.getSource();
	}
}
