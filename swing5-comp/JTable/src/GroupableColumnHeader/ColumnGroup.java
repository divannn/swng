package GroupableColumnHeader;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

 
/**
 * <strong>Notes</strong>
 * <br>
 * 1. Columns in group must be adjacent.
 * <br>
 * Impossible to add, f.i. col0 and col2, because it's unclear how to display col1.
 * <br>
 * 2. Each <code>TableColumn</code> can be added only in one <code>ColumnGroup</code>.
 * <br>
 * 3. Each <code>ColumnGroup</code> can be added only in one <code>ColumnGroup</code>.
 * <br>
 * 4. Do not updates on table column model add/remove events.
 * Can be easily added.
 * @author idanilov
 *
 */
public class ColumnGroup {
	
	private TableCellRenderer renderer;
	private ArrayList elements;
	private String headerValue;

	public ColumnGroup(String text) {
		this(null,text);
	}

	public ColumnGroup(final TableCellRenderer renderer,final String headerValue) {
		if (renderer == null) {
			this.renderer = new GroupHeaderRenderer();
		} else {
			this.renderer = renderer;
		}
		this.headerValue = headerValue;
		elements = new ArrayList();
	}
  
	/**
	 * @param obj TableColumn or ColumnGroup
	 */
	public void add(Object obj) {
    	if (obj == null) { 
    		throw new IllegalArgumentException("Null argument"); 
    	}
    	if (obj == this) {
    		throw new IllegalArgumentException("Cannot add group to itself.");
    	}
    	elements.add(obj);
	}
  
	/**
	 * @param col
	 * @return All column groups that sit above this <code>col<code>.
	 */
	public List<ColumnGroup> getColumnGroups(final TableColumn col) {
		ArrayList<ColumnGroup> result = new ArrayList<ColumnGroup>();
		if (col != null) {
			collectColumnGroups(this,col,result);	
		}
		return result;
	}
	
	private void collectColumnGroups(final ColumnGroup group,final TableColumn col,final ArrayList<ColumnGroup> storage) {
		List<TableColumn> columns = group.getColumns();
		if (columns.contains(col)) {
			storage.add(group);	
		}
		for (Object nextObj : group.elements) {
			if (nextObj instanceof ColumnGroup) {
				ColumnGroup nextGroup = (ColumnGroup)nextObj;
				nextGroup.collectColumnGroups(nextGroup,col,storage);
			}
		}
	}
    
	/**
	 * @return All table columns in this group including sub-groups.
	 */
	private List<TableColumn> getColumns() {
		List<TableColumn> result = new ArrayList<TableColumn>();
		collectColumns(result);
		return result;
	}
	
	private void collectColumns(final List<TableColumn> storage) {
		for (Object nextObj : elements) {
			if (nextObj instanceof ColumnGroup) {
				((ColumnGroup)nextObj).collectColumns(storage);
			} else {
				storage.add((TableColumn)nextObj);
			} 
		}
	}
	
	public TableCellRenderer getHeaderRenderer() {

		return renderer;
	}
    
	public Object getHeaderValue() {
		return headerValue;
	}
  
	/**
	 * @param table
	 * @return size of group rect.
	 */
	public Dimension getSize(final JTable table) {
		//-1 for row means column header.
		Component comp = renderer.getTableCellRendererComponent(
		    table, getHeaderValue(), false, false,-1, -1);
		int height = comp.getPreferredSize().height; 
		int width  = 0;
		for (Object nextObj : elements) {
			if (nextObj instanceof TableColumn) {
				TableColumn nextColumn = (TableColumn)nextObj;
				width += nextColumn.getWidth();
			} else {
				width += ((ColumnGroup)nextObj).getSize(table).width;
			}
		}
		return new Dimension(width, height);
	}

	public String toString() {
		String result = "{ val = " + headerValue + ", children = ";
		Iterator it = elements.iterator();
		while (it.hasNext()) {
			Object nextObj = it.next(); 
			result += (nextObj instanceof ColumnGroup ? "<" + ((ColumnGroup)nextObj).getHeaderValue() + ">": ((TableColumn)nextObj).getHeaderValue());
			if (it.hasNext()) {
				result += ",";
			}
		}
		result += "}";
		return result;
	}
	
	private static class GroupHeaderRenderer extends DefaultTableCellRenderer {
		
		public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
			JTableHeader header = table.getTableHeader();
			if (header != null) {
				setForeground(header.getForeground());
				setBackground(header.getBackground());
				setFont(header.getFont());
			}
			setHorizontalAlignment(SwingConstants.CENTER);
			setText((value == null) ? "" : value.toString());
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			return this;
		}
		
	}
	
}