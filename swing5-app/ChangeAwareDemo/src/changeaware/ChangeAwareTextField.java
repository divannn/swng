package changeaware;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * @author idanilov
 */
public class ChangeAwareTextField extends JTextField
		implements IChangeAware {

	private boolean isChanged;
	private boolean isValid;
	private Object stateValue;
	private DocumentListener dataChangeListener;
	private PropertyChangeListener modelChangeListener;
	private static final Color NORMAL_COLOR = new JTextField().getBackground();

	public ChangeAwareTextField() {
		super();
		dataChangeListener = new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				computeState();
			}

			public void removeUpdate(DocumentEvent e) {
				computeState();
			}

			public void changedUpdate(DocumentEvent e) {
			}
		};
		getDocument().addDocumentListener(dataChangeListener);
		modelChangeListener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent pce) {
				if ("document".equals(pce.getPropertyName())) {
					computeState();
				}
			}
		};
		addPropertyChangeListener(modelChangeListener);
		putClientProperty(IChangeAware.SHOW_CHANGE, Boolean.TRUE);
		putClientProperty(IChangeAware.SHOW_INCORRECTNESS, Boolean.TRUE);
	}

	public void setDocument(final Document doc) {
		Document oldModel = getDocument();
		if (oldModel != null) {
			oldModel.removeDocumentListener(dataChangeListener);
		}
		super.setDocument(doc);
		getDocument().addDocumentListener(dataChangeListener);
	}

	public boolean isStateChanged() {
		return isChanged;
	}

	public boolean isStateValid() {
		return isValid;
	}

	public void markState() {
		stateValue = getText();
		computeState();
	}

	public void clearState() {
		stateValue = null;
		isChanged = false;
		isValid = true;
		refreshVisuals();
	}

	public void revertState() {
		if (isChanged) {
			setText(stateValue.toString());
		}
	}

	public void addStateChangeListener(final IStateChangeListener scl) {
		if (scl != null) {
			listenerList.add(IStateChangeListener.class, scl);
		}
	}

	public void removeStateChangeListener(final IStateChangeListener scl) {
		if (scl != null) {
			listenerList.remove(IStateChangeListener.class, scl);
		}
	}

	private void computeState() {
		if (stateValue != null) {
			isChanged = !stateValue.equals(getText());
			isValid = checkValidity();
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	/**
	 * Override to provide custom validation.
	 * @return true if current component value is valid
	 */
	protected boolean checkValidity() {
		return true;
	}

	private void refreshVisuals() {
		boolean isShowChanges = getClientProperty(IChangeAware.SHOW_CHANGE) != null;
		boolean isShowInvalidness = getClientProperty(IChangeAware.SHOW_INCORRECTNESS) != null;
		if (isValid) {
			if (isShowChanges) {
				setBackground(isChanged ? IChangeAware.CHANGED_COLOR : NORMAL_COLOR);
			} else {
				if (isShowInvalidness) {
					setBackground(NORMAL_COLOR);
				}
			}
		} else {
			if (isShowInvalidness) {
				setBackground(INVALID_COLOR);
			}
		}
	}

	protected void fireStateChangeEvent(final StateChangeEvent sce) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStateChangeListener.class) {
				IStateChangeListener nextListener = (IStateChangeListener) (listeners[i + 1]);
				nextListener.stateChanged(sce);
			}
		}
	}

}
