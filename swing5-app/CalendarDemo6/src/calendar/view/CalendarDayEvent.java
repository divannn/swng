package calendar.view;

import java.util.EventObject;

/**
 * @author idanilov
 *
 */
public class CalendarDayEvent extends EventObject {

	public CalendarDayEvent(final Object source) {
		super(source);
	}

	public ICalendarDay getSource() {
		return (ICalendarDay) super.getSource();
	}
}
