package changeaware;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.ComponentUI;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;

/**
 * Supports selected item change and model change.
 * @author idanilov
 */
public class ChangeAwareComboBox extends JComboBox
		implements IChangeAware {

	private boolean isChanged;
	private boolean isValid;
	private Object stateValue;
	private ListDataListener dataChangeListener;
	private DocumentListener documentListener;
	private PropertyChangeListener modelChangeListener;
	private static final Color NORMAL_COLOR = new JComboBox().getBackground();

	public ChangeAwareComboBox(final ComboBoxModel model) {
		super(model);
		setRenderer(new StateTrackCellRenderer());
		dataChangeListener = new ListDataListener() {

			public void intervalAdded(ListDataEvent e) {
				computeState();
			}

			public void intervalRemoved(ListDataEvent e) {
				computeState();
			}

			public void contentsChanged(ListDataEvent e) {
				computeState(); //called on item selection change.
			}

		};
		getModel().addListDataListener(dataChangeListener);
		documentListener = new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				computeState();
			}

			public void removeUpdate(DocumentEvent e) {
				computeState();
			}

			public void changedUpdate(DocumentEvent e) {
			}
		};
		JTextField textField = (JTextField) getEditor().getEditorComponent();
		textField.getDocument().addDocumentListener(documentListener);
		modelChangeListener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent pce) {
				if ("model".equals(pce.getPropertyName())) {
					computeState();
				}
			}
		};
		addPropertyChangeListener(modelChangeListener);
		putClientProperty(IChangeAware.SHOW_CHANGE, Boolean.TRUE);
		putClientProperty(IChangeAware.SHOW_INCORRECTNESS, Boolean.TRUE);
	}

	public void setModel(final ComboBoxModel dataModel) {
		if (!(dataModel instanceof ClonableComboBoxModel)) {
			throw new IllegalArgumentException("Combo model must be "
					+ ClonableComboBoxModel.class.getName());
		}
		ComboBoxModel oldModel = getModel();
		if (oldModel != null) {
			oldModel.removeListDataListener(dataChangeListener);
		}
		super.setModel(dataModel);
		getModel().addListDataListener(dataChangeListener);
	}

	public void updateUI() {
		setUI(StateTrackComboBoxUI.createUI(this));
	}

	public boolean isStateChanged() {
		return isChanged;
	}

	public boolean isStateValid() {
		return isValid;
	}

	public void markState() {
		ClonableComboBoxModel currentModel = (ClonableComboBoxModel) getModel();
		stateValue = currentModel.cloneComboBoxModel();
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
			setModel((ComboBoxModel) stateValue);
		}
	}

	/**
	 * Override to provide custom validation.
	 * @return true if current component value is valid
	 */
	protected boolean checkValidity() {
		return true;
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

	protected void fireStateChangeEvent(final StateChangeEvent sce) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStateChangeListener.class) {
				IStateChangeListener nextListener = (IStateChangeListener) (listeners[i + 1]);
				nextListener.stateChanged(sce);
			}
		}
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
		//change color in combo editor.
		if (isEditable()) {
			getEditor().getEditorComponent().setBackground(getBackground());
		}
		repaint();
	}

	private void computeState() {
		if (stateValue != null) {
			ComboBoxModel storedModel = (ComboBoxModel) stateValue;
			Object storedSelected = storedModel.getSelectedItem();
			Object curSelected = null;
			if (isEditable()) {
				curSelected = getEditor().getItem();
				//System.err.println("==== EDITABLE:");
			} else {
				curSelected = getModel().getSelectedItem();
				//System.err.println("==== NON EDITABLE:");
			}
			isChanged = !compareSelectedItems(curSelected, storedSelected);
			if (((ClonableComboBoxModel) storedModel).isWholeModelCloneSupported()) {
				if (!isChanged) { //already changed.
					isChanged |= !compareComboModels();
				}
			}
			isValid = checkValidity();
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	/**
	 * @return true if selected items are equal
	 */
	private boolean compareSelectedItems(final Object curSelected, final Object storedSelected) {
		return ((curSelected == null && storedSelected == null) || (curSelected != null && curSelected
				.equals(storedSelected)));
	}

	/**
	 * Compares all models contents except selected items.
	 * @return true if models are equal
	 */
	private boolean compareComboModels() {
		ComboBoxModel curModel = getModel();
		ComboBoxModel storedModel = (ComboBoxModel) stateValue;
		ArrayList current = model2List(curModel);
		ArrayList stored = model2List(storedModel);
		return current.equals(stored);
	}

	private static ArrayList model2List(final ListModel listModel) {
		ArrayList<Object> result = new ArrayList<Object>(listModel.getSize());
		for (int i = 0; i < listModel.getSize(); i++) {
			result.add(listModel.getElementAt(i));
		}
		return result;
	}

	private class StateTrackCellRenderer extends DefaultListCellRenderer {

		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel result = (JLabel) super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);
			if (!isSelected) {
				((JComponent) result).setOpaque(true);
				result.setBackground(ChangeAwareComboBox.this.getBackground());
			}
			return result;
		}
	}

	private static class StateTrackComboBoxUI extends WindowsComboBoxUI {

		public static ComponentUI createUI(JComponent c) {
			//Create UI per each combo - the same as in ancestors.
			return new StateTrackComboBoxUI();
		}

		public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean unused) {
			if (comboBox.isEnabled()) {
				Color old = g.getColor();
				g.setColor(comboBox.getBackground());
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
				g.setColor(old);
			} else {
				super.paintCurrentValueBackground(g, bounds, unused);
			}
		}

	}

	/**
	 * @author idanilov
	 */
	public interface ClonableComboBoxModel {

		/**
		 * For most case should return false. That means that only selected item should be cloned in <code>cloneComboBoxModel()</code>. 
		 * This a contract that if this method returns false - only selected item will be check for equality.
		 * @return true if model needs cloning all contents.
		 */
		boolean isWholeModelCloneSupported();

		/**
		 * Must return the deep copy of each list model element.
		 * Each object must be Cloneable.   	 
		 */
		ComboBoxModel cloneComboBoxModel();

	}

}