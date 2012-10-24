package changeaware;

import java.awt.Color;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author idanilov
 * <p>
 * <Strong>Important</Strong>
 * <p>
 * Model change is not processed. Because of new spinner editor is created 
 * as soon as new spinner model is set. And new JFormattedTextFiled is created also.
 * As result all format customization is lost.*
 */
public class ChangeAwareSpinner extends JSpinner
		implements IChangeAware {

	private boolean isChanged;
	private Object stateValue;
	private ChangeListener dataChangeListener;

	private static final Color NORMAL_COLOR = new JTextField().getBackground();

	public ChangeAwareSpinner() {
		this(new SpinnerNumberModel());
	}

	public ChangeAwareSpinner(final SpinnerModel spinnerModel) {
		super(spinnerModel);
		dataChangeListener = new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				computeState();
			}
		};
		getModel().addChangeListener(dataChangeListener);
	}

	public void setModel(final SpinnerModel dataModel) {
		SpinnerModel oldModel = getModel();
		if (oldModel != null) {
			oldModel.removeChangeListener(dataChangeListener);
		}
		super.setModel(dataModel);
		getModel().addChangeListener(dataChangeListener);
	}

	public boolean isStateChanged() {
		return isChanged;
	}

	public boolean isStateValid() {
		return true;
	}

	/**
	 * XXX : model value used to store. May be store the whole SpinnerModel?
	 */
	public void markState() {
		stateValue = getValue();
		isChanged = false;
	}

	public void clearState() {
		stateValue = null;
		isChanged = false;
		refreshVisuals();
	}

	public void revertState() {
		if (isChanged) {
			setValue(stateValue);
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
			//System.err.println("*** compute - saved: " + stateValue + " cur: " + getValue());
			isChanged = !stateValue.equals(getValue());
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	private void refreshVisuals() {
		JFormattedTextField ftf = ((DefaultEditor) getEditor()).getTextField();
		ftf.setBackground(isChanged ? IChangeAware.CHANGED_COLOR : NORMAL_COLOR);
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
