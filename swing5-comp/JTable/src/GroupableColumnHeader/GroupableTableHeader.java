package GroupableColumnHeader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
 
/**
 * @author idanilov
 *
 */
public class GroupableTableHeader extends JTableHeader {
	
	private static final String uiClassID = "GroupableTableHeaderUI";
	
	private ArrayList<ColumnGroup> columnGroups;
	private boolean isShowColumns;
    
	public GroupableTableHeader(TableColumnModel model) {
	    super(model);
	    setReorderingAllowed(false);
	    columnGroups = new ArrayList<ColumnGroup>();
	    isShowColumns = true;
	}

	/**
	 * @return true to paint column headers
	 */
	public boolean isShowColumns() {
		return isShowColumns;
	}
	
	public void setPaintColumns(boolean isPaintColumns) {
		this.isShowColumns = isPaintColumns;
	}
	
	public void updateUI() {
		setUI(GroupableTableHeaderUI.createUI(this));
	}
	
	public String getUIClassID() {
		return uiClassID;
	}
	
	public void addColumnGroup(ColumnGroup columnGroup) {
		columnGroups.add(columnGroup);
	}

	/**
	 * Searches among top-level column groups.
	 * @param col
	 * @return null if this column is not included in any group
	 */
	public List<ColumnGroup> getColumnGroups(final TableColumn col) {
		for (ColumnGroup nextGroup : columnGroups) {
			List<ColumnGroup> nextGroupList = nextGroup.getColumnGroups(col);
			if (nextGroupList.size() > 0) {
				//stop if found first top level group that contains this column.
				return nextGroupList;
			}	
		}
		return null;
	}
  
}