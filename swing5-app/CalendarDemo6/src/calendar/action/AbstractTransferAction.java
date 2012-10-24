package calendar.action;

import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JComponent;

import calendar.CalendarSchedule;
import calendar.dnd.CalendarTransferHandler;

/**
 * @author idanilov
 *
 */
public abstract class AbstractTransferAction extends AbstractCalendarAction
		implements PropertyChangeListener {

	protected JComponent focusOwner;

	public AbstractTransferAction(final CalendarSchedule calSched, final String name) {
		super(calSched, name);
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addPropertyChangeListener("permanentFocusOwner", this);
	}

	protected CalendarTransferHandler getTransferHandler() {
		return calendarSchedule.getCalendarTransferHandler();
	}

	protected boolean isTransferEnabled() {
		return getTransferHandler() != null;
	}

	protected Clipboard getClipboard() {
		Clipboard result = Toolkit.getDefaultToolkit().getSystemClipboard();
		return result;
	}

	/** 
	 * Redirects invokation to cut/copy/paste actions in TransferHandler.
	 */
	public void actionPerformed(ActionEvent e) {
		if (focusOwner == null) {
			return;
		}
		String action = e.getActionCommand();
		Action a = focusOwner.getActionMap().get(action);
		if (a != null) {
			a.actionPerformed(new ActionEvent(focusOwner, ActionEvent.ACTION_PERFORMED, null));
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		Object o = e.getNewValue();
		if (o instanceof JComponent) {
			focusOwner = (JComponent) o;
		} else {
			focusOwner = null;
		}
	}

	public void dispose() {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.removePropertyChangeListener("permanentFocusOwner", this);
		super.dispose();
	}
}
