import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;

import calendar.CalendarSchedule;
import calendar.action.AbstractTransferAction;
import calendar.model.CalendarSelectionEvent;

/**
 * @author idanilov
 * 
 */
public class CopyItemAction extends AbstractTransferAction {

	public CopyItemAction(final CalendarSchedule calSched) {
		super(calSched, "Copy");
		// in popup menu accelerators useless (if menu item is not visible).
		// so line below is used just as indication in menu item text.
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		putValue(FAKE_ACCELERATOR, Boolean.TRUE);
		putValue(Action.ACTION_COMMAND_KEY, TransferHandler.getCopyAction().getValue(Action.NAME));
		update();
	}

	private boolean canCopy() {
		return (selectedItemIndices != null && selectedItemIndices.length > 0);
	}

	public void itemSelectionChanged(final CalendarSelectionEvent cse) {
		super.itemSelectionChanged(cse);
		update();
	}

	private void update() {
		boolean canCopy = calendarSchedule.isEditable() && isTransferEnabled() && canCopy();
		setEnabled(canCopy);
	}

	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue);
		//XXX : update built-in table shortcut.
		TransferHandler.getCopyAction().setEnabled(newValue);
	}

}