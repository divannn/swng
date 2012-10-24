package EditableList;

import javax.swing.DefaultListModel;

/**
 * @author idanilov
 *
 */
public class DefaultMutableListModel extends DefaultListModel
		implements MutableListModel {

	public boolean isCellEditable(int index) {
		return true;
	}

	public void setValueAt(Object value, int index) {
		super.setElementAt(value, index);
	}

}
