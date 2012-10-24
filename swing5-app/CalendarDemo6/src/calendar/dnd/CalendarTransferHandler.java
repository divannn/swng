package calendar.dnd;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import calendar.CalendarSchedule;
import calendar.DayUI;
import calendar.model.CalendarTask;
import calendar.util.CalendarLog;
import calendar.view.ICalendarDay;
import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public abstract class CalendarTransferHandler extends TransferHandler {

	//Used to forbid drop to itself during DnD only - not during clipboard transfer.
	private JTable source;
	private CalendarTransferable transferable;
	protected CalendarSchedule schedule;

	private Vector<ClipboardListener> listeners;

	public CalendarTransferHandler(final CalendarSchedule schedule) {
		this.schedule = schedule;
		listeners = new Vector<ClipboardListener>();
	}

	protected Transferable createTransferable(final JComponent sourceComp) {
		source = (JTable) sourceComp;
		CalendarTransferable result = null;
		int[] indices = source.getSelectedRows();
		ICalendarDay sourceDay = (ICalendarDay) source.getClientProperty(DayUI.DAY);
		if (indices != null) {
			List<ICalendarItem> itemsToTransfer = new ArrayList<ICalendarItem>();
			for (int nextSelected : indices) {
				ICalendarItem nextSelectedItem = (ICalendarItem) source.getModel().getValueAt(
						nextSelected, 0);
				itemsToTransfer.add(nextSelectedItem);
			}
			CalendarLog.TRANSFER_LOGGER.fine("[transfer] create transferable: "
					+ Arrays.toString(indices));
			result = new CalendarTransferable(source, sourceDay.getDate(), itemsToTransfer);
			transferable = result;
		}
		return result;
	}

	//XXX : used only during drag. See super implementation. 
	public boolean canImport(final JComponent targetComp, final DataFlavor[] flavors) {
		JTable targetTable = (JTable) targetComp;
		ICalendarDay targetDay = (ICalendarDay) targetTable.getClientProperty(DayUI.DAY);
		return hasItemFlavor(flavors) && !isMyself(targetComp) && canPaste(targetDay, transferable);
	}

	private boolean isMyself(final JComponent targetComp) {
		return (source == targetComp);
	}

	protected boolean hasItemFlavor(final DataFlavor[] flavors) {
		for (int i = 0; i < flavors.length; i++) {
			if (CalendarFlavor.CALENDAR_FLAVOR.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}

	public int getSourceActions(final JComponent c) {
		return COPY_OR_MOVE;
	}

	public boolean importData(final JComponent targetComp, final Transferable t) {
		boolean result = false;
		JTable targetTable = (JTable) targetComp;
		ICalendarDay targetDay = (ICalendarDay) targetTable.getClientProperty(DayUI.DAY);
		if (hasItemFlavor(t.getTransferDataFlavors())) {
			result = paste(targetDay, t);
		}
		CalendarLog.TRANSFER_LOGGER.fine("[transfer] import. Result: " + result);
		return result;
	}

	protected abstract boolean canPaste(final ICalendarDay targetDay, final Transferable t);

	protected abstract boolean paste(final ICalendarDay targetDay, final Transferable t);

	protected void exportDone(final JComponent sourceComp, final Transferable t, final int action) {
		if (action == MOVE) {
			CalendarTransferData data = CalendarTransferUtil.getCalendarData(t);
			if (data != null) {
				List<CalendarTask> tasksToRemove = new ArrayList<CalendarTask>();
				List<ICalendarItem> items = data.getSelectedItems();
				for (ICalendarItem nextItem : items) {
					CalendarTask task = nextItem.getParentTask();
					tasksToRemove.add(task);
				}
				schedule.getModel().removeTasks(tasksToRemove);
			}
		}
		source = null;
		transferable = null;
		CalendarLog.TRANSFER_LOGGER
				.fine("[transfer] export done. Action: " + action2String(action));
	}

	private static String action2String(final int action) {
		String result = "UNKNOWN";
		switch (action) {
			case MOVE:
				result = "MOVE";
				break;
			case COPY:
				result = "COPY";
				break;
			case COPY_OR_MOVE:
				result = "COPY_OR_MOVE";
				break;
			case NONE:
				result = "NONE";
				break;
			default:
				break;
		}
		return result;
	}

	public void exportToClipboard(final JComponent comp, final Clipboard clipboard, final int action) {
		super.exportToClipboard(comp, clipboard, action);
		fireCliboardContentsChanged(clipboard);
	}

	public void addClipboardListener(final ClipboardListener cl) {
		listeners.addElement(cl);
	}

	public void removeClipboardListener(final ClipboardListener cl) {
		listeners.removeElement(cl);
	}

	protected void fireCliboardContentsChanged(final Clipboard clipboard) {
		if (listeners.size() > 0) {
			ClipboardEvent e = new ClipboardEvent(clipboard);
			Vector copyListeners = (Vector) listeners.clone();
			for (int i = 0; i < copyListeners.size(); i++) {
				((ClipboardListener) copyListeners.get(i)).contentChanged(e);
			}
		}
	}

}