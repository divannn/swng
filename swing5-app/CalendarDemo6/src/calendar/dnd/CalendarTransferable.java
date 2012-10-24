package calendar.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;

import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public class CalendarTransferable
		implements Transferable {

	private CalendarTransferData data;

	public CalendarTransferable(final JTable t, final Date sourceDate,
			final List<ICalendarItem> selectedItems) {
		data = new CalendarTransferData(t, sourceDate, selectedItems);
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { CalendarFlavor.CALENDAR_FLAVOR, DataFlavor.stringFlavor };
	}

	public boolean isDataFlavorSupported(final DataFlavor flavor) {
		return CalendarFlavor.CALENDAR_FLAVOR.equals(flavor)
				|| DataFlavor.stringFlavor.equals(flavor);
	}

	public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException,
			IOException {
		if (CalendarFlavor.CALENDAR_FLAVOR.equals(flavor)) {
			return data;
		} else if (DataFlavor.stringFlavor.equals(flavor)) {
			return getStringPresentation();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	private String getStringPresentation() {
		StringBuffer result = new StringBuffer();
		List<ICalendarItem> tasks = data.getSelectedItems();
		Iterator<ICalendarItem> it = tasks.iterator();
		while (it.hasNext()) {
			ICalendarItem nextItem = it.next();
			result.append(nextItem.toString());
			if (it.hasNext()) {
				result.append(",");
			}
		}
		return result.toString();
	}

}