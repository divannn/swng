import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import changeaware.ChangeAwareComboBox;

/**
 * Supports deep copy of the whole combobox model.
 * @author idanilov
 *
 */
public class SampleComboBoxModelWithFullCopy extends DefaultComboBoxModel
		implements ChangeAwareComboBox.ClonableComboBoxModel {

	public SampleComboBoxModelWithFullCopy() {
		super();
	}

	public SampleComboBoxModelWithFullCopy(final Object [] items) {
		super(items);
	}

	public boolean isWholeModelCloneSupported() {
		return true;
	}

	public ComboBoxModel cloneComboBoxModel() {
		final int size = getSize();
		Object[] data = new Object[size];
		for (int i = 0; i < size; i++) {
			Object nextObject = getElementAt(i);
			//clone each object here.
			Object nextClonedObject = null;
			if (nextObject != null) {
				nextClonedObject = new String(nextObject.toString());
			}
			data[i] = nextClonedObject;
		}
		SampleComboBoxModelWithFullCopy result = new SampleComboBoxModelWithFullCopy(data);
		//clone selected item here.
		String clonedSelectedItem = new String(getSelectedItem().toString());
		result.setSelectedItem(clonedSelectedItem);
		return result;
	}

}
