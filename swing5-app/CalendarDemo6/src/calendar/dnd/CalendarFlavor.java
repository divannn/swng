package calendar.dnd;

import java.awt.datatransfer.DataFlavor;

/**
 * @author idanilov
 *
 */
public class CalendarFlavor {

	public static final DataFlavor CALENDAR_FLAVOR = new DataFlavor(
			DataFlavor.javaJVMLocalObjectMimeType + ";class=\""
					+ CalendarTransferData.class.getName() + "\"", CalendarTransferData.class
					.getSimpleName());

}
