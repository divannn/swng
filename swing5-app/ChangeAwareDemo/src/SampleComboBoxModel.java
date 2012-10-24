import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import changeaware.ChangeAwareComboBox;

/**
 * Supports deep cloning only for selected item of combobox model.
 * @author idanilov
 *
 */
public class SampleComboBoxModel extends DefaultComboBoxModel
		implements ChangeAwareComboBox.ClonableComboBoxModel {

	public SampleComboBoxModel() {
		super();
	}

	public SampleComboBoxModel(final Object [] items) {
		super(items);
	}

	public boolean isWholeModelCloneSupported() {
		return false;
	}

	public ComboBoxModel cloneComboBoxModel() {
		final int size = getSize();
		Object[] data = new Object[size];
		for (int i = 0; i < size; i++) {
			data[i] = getElementAt(i);
		}
		SampleComboBoxModel result = new SampleComboBoxModel(data);
		//clone selected item here.
		String clonedSelectedItem = new String(getSelectedItem().toString());
		result.setSelectedItem(clonedSelectedItem);
		return result;
	}

}
