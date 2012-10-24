package calendar.model;

import java.util.EventListener;

/**
 * @author idanilov
 *
 */
public interface CalendarModelListener
		extends EventListener {

	void calendarModelChanged(CalendarModelEvent e);

}
