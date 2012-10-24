import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import changeaware.ChangeAwareList;

/**
 * @author idanilov
 *
 */
public class SampleListModel extends DefaultListModel
		implements ChangeAwareList.ClonableListModel {

	public SampleListModel(final int size) {
		super();
		setSize(size);
	}

	public SampleListModel(final Object[] data) {
		super();
		ensureCapacity(data.length);
		//fill data.
		for (int i = 0; i < data.length; i++) {
			addElement(data[i]);
		}
	}

	public ListModel cloneListModel() {
		final int size = getSize();
		SampleListModel result = new SampleListModel(size);
		for (int i = 0; i < size; i++) {
			Object nextObject = getElementAt(i);
			//clone each object here.
			Object nextClonedObject = new String(nextObject.toString());
			result.set(i, nextClonedObject);
		}
		return result;
	}
}
