package TreeTable.treetable;

import java.util.Iterator;

import javax.swing.table.TableModel;

/**
 * @author idanilov
 *
 */
public interface TreeTableModel
		extends TableModel {

	TreeExpansionModel getExpansionModel();

	/**
	 * Gets iterator to iterate through table row objects
	 * @param expand determines whether item and its children 
	 */
	Iterator getIterator(boolean expand);

	/**
	 * Gets iterator, which will iterate through
	 * given node and its children.
	 * @param expand determines whether item and its children 
	 * should be expanded or not
	 * @param next object whose children should be iterated
	 */
	Iterator getIterator(boolean expand, Object next);

	/**
	 * Gets iterator to iterate through a set of table row objects -
	 * as a rule, used for iterating through selection.
	 * @param expand determines whether item and its children
	 * @param rows rows to iterate through
	 * or through the entire table
	 */
	Iterator getIterator(boolean expand, Object[] rows);

	public RowMapper getRowMapper();

	/*----------------------------------------------------------------
	 * Methods analogous to those of TableModel with one difference:
	 * they are using Object (instead of int row index) to identify row
	 */
	Object getValueAt(Object row, int columnIndex);

	boolean isCellEditable(Object row, int columnIndex);

	void setValueAt(Object aValue, Object row, int columnIndex);

	/*-----------------------------------------------------------------
	 * Methods similar to those of TreeModel
	 */
	Object getRoot();

	Object getChildAt(Object node, int index);

	Object getParent(Object node);

	int getChildCount(Object node);

	boolean isLeaf(Object node);

	int getNodeLevel(Object node);

	boolean isRootVisible();

}