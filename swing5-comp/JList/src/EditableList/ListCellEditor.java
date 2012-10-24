package EditableList;

import java.awt.Component;

import javax.swing.CellEditor;
import javax.swing.JList;

/**
 * @author idanilov
 *
 */
public interface ListCellEditor
		extends CellEditor {

	Component getListCellEditorComponent(JList list, Object value, boolean isSelected, int index);

}
