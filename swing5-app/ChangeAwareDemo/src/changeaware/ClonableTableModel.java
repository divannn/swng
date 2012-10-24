package changeaware;

import javax.swing.table.TableModel;

/**
 * XXX : Maybe just use Cloneable.  
 * @author idanilov
 */
public interface ClonableTableModel {

	/**
	 * Return the deep copy of each table model.
	 * Each object must be Cloneable.   	 
	 */
	TableModel cloneTableModel();
}