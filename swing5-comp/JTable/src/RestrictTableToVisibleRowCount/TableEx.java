package RestrictTableToVisibleRowCount;


import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * @author santosh
 * @author idanilov
 *
 */
public class TableEx extends JTable {

	private int visibleRowCount;
	
	public TableEx() {
		super();
	}

	public TableEx(final TableModel m) {
		super(m);
	}
	
	public int getVisibleRowCount() {
		return visibleRowCount;
	}
	
	//not exactly the same as in JList but PreferredScrollableViewportSize is affected similarly.
	public void setVisibleRowCount(final int rowCount) {
		visibleRowCount = rowCount;
        int height = 0; 
        for(int row = 0; row < rowCount; row++) { 
            height += getRowHeight(row);
        }
        setPreferredScrollableViewportSize(
        		new Dimension(getPreferredScrollableViewportSize().width,height)); 
    }

} 