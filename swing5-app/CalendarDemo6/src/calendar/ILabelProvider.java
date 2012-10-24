package calendar;

import javax.swing.Icon;

/**
 * @author idanilov
 *
 */
public interface ILabelProvider {

	String getText(CalendarItem item);

	Icon getIcon(CalendarItem item);
}
