package TreeTable.treetable;

import javax.swing.event.TableModelListener;

/**
 * @author idanilov
 *
 */
public interface RowMapper
		extends TableModelListener {

	/** maps given row number to object */
	public Object mapRowToObject(int row);

	/** maps given object to row number */
	public int mapObjectToRow(Object rowObj);

	/** returns number of rows in table */
	public int getRowCount();
}