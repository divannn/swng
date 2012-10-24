package calendar.dnd;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author idanilov
 *
 */
public abstract class CalendarTransferUtil {

	private CalendarTransferUtil() {

	}

	public static Transferable extractTransferable(final Clipboard c) {
		Transferable result = null;
		if (c != null) {
			try {
				result = c.getContents(null);
			} catch (IllegalStateException ise) {
				//ise.printStackTrace();
			}
		}
		return result;
	}

	public static CalendarTransferData getCalendarData(final Transferable t) {
		CalendarTransferData result = null;
		if (t != null && t.isDataFlavorSupported(CalendarFlavor.CALENDAR_FLAVOR)) {
			try {
				result = (CalendarTransferData) t.getTransferData(CalendarFlavor.CALENDAR_FLAVOR);
			} catch (UnsupportedFlavorException ufe) {
				ufe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return result;
	}

}
