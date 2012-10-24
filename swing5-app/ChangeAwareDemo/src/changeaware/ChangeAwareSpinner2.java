package changeaware;

import java.awt.Color;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;

/**
 * Differs from ChangeAwareSpinner in that this component
 * is track changes of text field value instead of spinner value.
 * The displayed value can be invalid unlike in ChangeAwareSpinner.
 * <p>
 * <Strong>Important</Strong>
 * <p>
 * Model change is not processed. Because of new spinner editor is created 
 * as soon as new spinner model is set. And new JFormattedTextFiled is created also.
 * As result all format customization is lost.  
 * @author idanilov
 */
public class ChangeAwareSpinner2 extends JSpinner
		implements IChangeAware {

	private boolean isChanged;
	private boolean isValid;
	private String stateValue; //XXX : may be us value of JFormattedTextField ?
	private DocumentListener dataChangeListener;
	private static final Color NORMAL_COLOR = new JTextField().getBackground();

	/**
	 * This is a workaround for problem when formatted text field value is synchronized with spinner value.
	 * JTextField#setText() causes replace call on Document. In turn calls remove() and insert() sequentially.
	 * As result there are extra document events (with "" text in text field) passed to <code>dataChangeListener</code>.
	 * <p>
	 * <strong>Other way</strong>
	 * <p>
	 * The described problem can be easily workarounded by removing <code>dataChangeListener</code> on focus lost 
	 * and restoring it back on focus gain of <code>ChangeAwareSpinner2</code>.
	 *  
	 */
	private static final String REPLACE_FLAG = "replaceFlag";

	public ChangeAwareSpinner2() {
		this(new SpinnerNumberModel());
	}

	public ChangeAwareSpinner2(final SpinnerModel spinnerModel) {
		super(spinnerModel);
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
		prepareTextField();
		putClientProperty(IChangeAware.SHOW_CHANGE, Boolean.TRUE);
		putClientProperty(IChangeAware.SHOW_INCORRECTNESS, Boolean.TRUE);
	}

	public void setModel(final SpinnerModel model) {
		JFormattedTextField ftf = getTextField();
		ftf.getDocument().removeDocumentListener(dataChangeListener);
		super.setModel(model);
		prepareTextField();
	}

	private void prepareTextField() {
		JFormattedTextField ftf = getTextField();
		ftf.setFocusLostBehavior(JFormattedTextField.COMMIT); //XXX:very important.
		ftf.getDocument().addDocumentListener(dataChangeListener);
	}

	public JFormattedTextField getTextField() {
		return ((DefaultEditor) getEditor()).getTextField();
	}

	public boolean isStateChanged() {
		return isChanged;
	}

	public boolean isStateValid() {
		return isValid;
	}

	public void markState() {
		stateValue = getTextField().getText();
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
			if (!"".equals(getTextField().getText())) { //ignore case when current text is empty.
				getTextField().putClientProperty(REPLACE_FLAG, Boolean.TRUE);
			}
			getTextField().setText(stateValue);
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
			Object replaceFlag = getTextField().getClientProperty(REPLACE_FLAG);
			if (replaceFlag != null) {
				getTextField().putClientProperty(REPLACE_FLAG, null);
				isValid = true;
				return;
			}
			isChanged = !stateValue.equals(getTextField().getText());
			isValid = checkValidity();
			//System.err.println("---- " + isChanged + " | " + isValid);	
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	/**
	 * Override to provide custom validation.
	 * @return true if current component value is valid
	 */
	protected boolean checkValidity() {
		boolean result = false;
		JFormattedTextField ftf = getTextField();
		try {
			/*ftf.commitEdit();//XXX: not use commitEdit - it results to JFormattedTextField#setValue(...) and to restore format pattern.
			 boolean isValid = ftf.isEditValid();*/
			Object res = ftf.getFormatter().stringToValue(ftf.getText());
			result = (res != null);
		} catch (ParseException pe) {
			result = false;
		}
		return result;
	}

	private void refreshVisuals() {
		JFormattedTextField ftf = getTextField();
		boolean isShowChanges = getClientProperty(IChangeAware.SHOW_CHANGE) != null;
		boolean isShowInvalidness = getClientProperty(IChangeAware.SHOW_INCORRECTNESS) != null;
		if (isValid) {
			if (isShowChanges) {
				ftf.setBackground(isChanged ? IChangeAware.CHANGED_COLOR : NORMAL_COLOR);
			} else {
				if (isShowInvalidness) {
					ftf.setBackground(NORMAL_COLOR);
				}
			}
		} else {
			if (isShowInvalidness) {
				ftf.setBackground(INVALID_COLOR);
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

	/**
	 * This class should be used instead of DateFormatter.
	 * @author idanilov
	 */
	public static class DateFormatterEx extends DateFormatter {

		public DateFormatterEx() {
			super();
		}

		public DateFormatterEx(final DateFormat format) {
			super(format);
		}

		public void install(JFormattedTextField ftf) {
			ftf.putClientProperty(REPLACE_FLAG, Boolean.TRUE);
			super.install(ftf);
		}
	}

	/**
	 * This class should be used instead of NumberFormatter.
	 * @author idanilov
	 */
	public static class NumberFormatterEx extends NumberFormatter {

		public NumberFormatterEx() {
			super();
		}

		public NumberFormatterEx(final NumberFormat format) {
			super(format);
		}

		public void install(JFormattedTextField ftf) {
			ftf.putClientProperty(REPLACE_FLAG, Boolean.TRUE);
			super.install(ftf);
		}
	}

}