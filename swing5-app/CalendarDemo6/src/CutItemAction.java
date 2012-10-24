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
public class CutItemAction extends AbstractTransferAction {

	public CutItemAction(final CalendarSchedule calSched) {
		super(calSched, "Cut");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		putValue(FAKE_ACCELERATOR, Boolean.TRUE);
		putValue(Action.ACTION_COMMAND_KEY, TransferHandler.getCutAction().getValue(Action.NAME));
		update();
	}

	public void itemSelectionChanged(final CalendarSelectionEvent cse) {
		super.itemSelectionChanged(cse);
		update();
	}

	private void update() {
		boolean canCut = calendarSchedule.isEditable() && isTransferEnabled() && canCut();
		setEnabled(canCut);
	}

	private boolean canCut() {
		return (selectedItemIndices != null && selectedItemIndices.length > 0);
	}

	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue);
		TransferHandler.getCutAction().setEnabled(newValue);//XXX : update built-in table shortcut. 
	}

}