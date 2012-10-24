import java.awt.Event;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;

import calendar.CalendarSchedule;
import calendar.action.AbstractTransferAction;
import calendar.dnd.CalendarTransferData;
import calendar.dnd.CalendarTransferHandler;
import calendar.dnd.CalendarTransferUtil;
import calendar.dnd.ClipboardEvent;
import calendar.dnd.ClipboardListener;
import calendar.model.CalendarSelectionEvent;

/**
 * @author idanilov
 *
 */
public class PasteItemAction extends AbstractTransferAction
		implements ClipboardListener, FlavorListener {

	public PasteItemAction(final CalendarSchedule calSched) {
		super(calSched, "Paste");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
		putValue(FAKE_ACCELERATOR, Boolean.TRUE);
		putValue(Action.ACTION_COMMAND_KEY, TransferHandler.getPasteAction().getValue(Action.NAME));
		CalendarTransferHandler cth = getTransferHandler();
		if (cth != null) {
			cth.addClipboardListener(this);
		}
		Clipboard c = getClipboard();
		c.addFlavorListener(this);
		update();
	}

	public void daySelectionChanged(CalendarSelectionEvent cse) {
		super.daySelectionChanged(cse);
		update();
	}

	public void contentChanged(final ClipboardEvent ce) {
		update();
	}

	public void flavorsChanged(final FlavorEvent fe) {
		update();
	}

	private void update() {
		boolean canPaste = calendarSchedule.isEditable() && isTransferEnabled() && canPaste();
		setEnabled(canPaste);
	}

	private boolean canPaste() {
		boolean result = false;
		if (day != null) {
			Transferable t = CalendarTransferUtil.extractTransferable(getClipboard());
			CalendarTransferData data = CalendarTransferUtil.getCalendarData(t);
			result = TaskTransferHandler.canPaste(data, day);
		}
		return result;
	}

	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue);
		TransferHandler.getPasteAction().setEnabled(newValue);//XXX : update built-in table shortcut. 
	}

	public void dispose() {
		Clipboard c = getClipboard();
		c.removeFlavorListener(this);
		CalendarTransferHandler cth = getTransferHandler();
		if (cth != null) {
			cth.removeClipboardListener(this);
		}
		super.dispose();
	}

}