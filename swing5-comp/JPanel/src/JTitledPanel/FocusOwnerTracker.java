package JTitledPanel;

import java.applet.Applet;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;

/**
 * Actually this code is taken from JTable - this way it tracks keyboard focus state.
 * @author idanilov
 * 
 */
public abstract class FocusOwnerTracker
		implements PropertyChangeListener {

	private static final String PERMANENT_FOCUS_OWNER = "permanentFocusOwner";

	private KeyboardFocusManager focusManager = KeyboardFocusManager
			.getCurrentKeyboardFocusManager();
	private Component comp;
	private boolean inside;

	public FocusOwnerTracker(final Component comp) {
		this.comp = comp;
	}

	public boolean isFocusInside() {
		return isFocusInside(false);
	}

	private boolean isFocusInside(final boolean find) {
		if (!find) {
			return inside;
		}
		Component focusOwner = focusManager.getPermanentFocusOwner();
		while (focusOwner != null) {
			if (focusOwner == comp) {
				return true;
			} else if ((focusOwner instanceof Window) || (focusOwner instanceof Applet && focusOwner.getParent() == null)) {
				if (focusOwner == SwingUtilities.getRoot(comp)) {
					return false;
				}
				break;
			}
			focusOwner = focusOwner.getParent();
		}
		return false;
	}

	public void start() {
		focusManager.addPropertyChangeListener(PERMANENT_FOCUS_OWNER, this);
		inside = isFocusInside(true);
	}

	public void stop() {
		focusManager.removePropertyChangeListener(PERMANENT_FOCUS_OWNER, this);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		boolean isInside = isFocusInside(true);
		if (this.inside != isInside) {
			if (isInside) {
				focusGained();
			} else {
				focusLost();
			}
			this.inside = isInside;
		}
	}

	public abstract void focusLost();

	public abstract void focusGained();
	
}
