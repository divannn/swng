package calendar.model;

import java.util.EventListener;

/**
 * @author idanilov
 *
 */
public interface CalendarSelectionListener
		extends EventListener {

	void daySelectionChanged(final CalendarSelectionEvent cse);

	void itemSelectionChanged(final CalendarSelectionEvent cse);

}