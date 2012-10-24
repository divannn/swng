package action;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

/**
 * @author idanilov
 * 
 */
public class GloballyContextSensitiveAction extends AbstractAction {

	private String actionName;
	private Action delegate;
	private JComponent source;

	private PropertyChangeListener focusOwnerListener = new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {
			if ("permanentFocusOwner".equals(evt.getPropertyName())) {
				Object newFocusOwner = evt.getNewValue();
				if (newFocusOwner instanceof JComponent) {
					JComponent comp = (JComponent) newFocusOwner;
					if (comp instanceof IGlobalActionProvider) {
						//Action action = comp.getActionMap().get(actionName);
						Action action = ((IGlobalActionProvider) comp).getAction(actionName);
						if (action != null) {
							changeDelegate(comp, action);
						} else {
							//disable if no global action found.
							clearDelegate();
						}
					} else {
						clearDelegate();
					}
				} else {
					clearDelegate();
				}
			}
		}
	};

	//listen to delegate change events and reset state to inform UI component.
	private PropertyChangeListener delegateListener = new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {
			if ("enabled".equals(evt.getPropertyName())) {
				//System.err.println("-- delegate state change: " + evt.getNewValue());
				setEnabled(Boolean.valueOf(evt.getNewValue().toString()).booleanValue());
			}
		}
	};

	public GloballyContextSensitiveAction(final String actionName, final String displayName) {
		this.actionName = actionName;
		putValue(Action.NAME, displayName); //override name.
		setEnabled(false); //disable by default.
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(
				focusOwnerListener);
	}

	private void clearDelegate() {
		source = null;
		delegate = null;
		setEnabled(false);
	}

	private void changeDelegate(final JComponent comp, final Action action) {
		if (delegate != null) {
			delegate.removePropertyChangeListener(delegateListener);
		}
		source = comp;
		delegate = action;
		delegate.addPropertyChangeListener(delegateListener);
		//re-set state according to delegate.
		setEnabled(delegate.isEnabled());
	}

	public void actionPerformed(final ActionEvent ae) {
		if (delegate != null) {
			delegate.actionPerformed(new ActionEvent(source, ae.getID(), ae.getActionCommand(), ae
					.getWhen(), ae.getModifiers()));
		}
	}

}
