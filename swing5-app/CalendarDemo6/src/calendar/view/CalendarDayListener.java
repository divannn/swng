package calendar.view;

import java.util.EventListener;

/**
 * @author idanilov
 *
 */
public interface CalendarDayListener
		extends EventListener {

	void dayContentsChanged(CalendarDayEvent cde);

}
