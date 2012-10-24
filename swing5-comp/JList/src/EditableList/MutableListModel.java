package EditableList;

import javax.swing.ListModel;

/**
 * @author idanilov
 *
 */
public interface MutableListModel
		extends ListModel {

	public boolean isCellEditable(int index);

	public void setValueAt(Object value, int index);

}
