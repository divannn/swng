package GroupableColumnHeader;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 * @author idanilov
 *
 */
public class GroupableTableHeaderUI extends BasicTableHeaderUI {
  
    public static ComponentUI createUI(JComponent header) {
        return new GroupableTableHeaderUI();
    }

	public void paint(Graphics g, JComponent c) {
	    if (header.getColumnModel() == null) {
	    	return;
	    }
	    Dimension size = header.getSize();
	    Rectangle currentCellRect  = new Rectangle(0, 0, size.width, size.height);
		GroupableTableHeader groupableHeader = (GroupableTableHeader)header;
	    HashMap<ColumnGroup,Rectangle> group2RectMap = new HashMap<ColumnGroup,Rectangle>();
	    //iterate over columns - not column groups.
	    TableColumnModel cm = header.getColumnModel();
	    for (int i = 0; i < cm.getColumnCount(); i++) {
			currentCellRect.y = 0;
			currentCellRect.height = size.height;
			TableColumn nextColumn = cm.getColumn(i);
			List<ColumnGroup> nextGroups = groupableHeader.getColumnGroups(nextColumn);
			if (nextGroups != null) {
//System.err.println(">>> groups for column: " + nextColumn.getHeaderValue() + " -> " + nextGroups);				
				for (ColumnGroup nextGroup : nextGroups) {
					//small optimization - remember already painted group rect in map.
					Rectangle nextGroupRect = group2RectMap.get(nextGroup);
					if (nextGroupRect == null) {
						nextGroupRect = new Rectangle(currentCellRect);
						Dimension nextGroupSize = nextGroup.getSize(header.getTable());
						nextGroupRect.width  = nextGroupSize.width;
						nextGroupRect.height = nextGroupSize.height;    
						paintGroupCell(g, nextGroupRect, nextGroup);
						group2RectMap.put(nextGroup, nextGroupRect);
					} 
					currentCellRect.y += nextGroupRect.height;
					currentCellRect.height = size.height - currentCellRect.y;
				}
			}
			currentCellRect.width = nextColumn.getWidth();
			if (groupableHeader.isShowColumns()) {
				paintColumnCell(g, currentCellRect, i);
			}
			currentCellRect.x += currentCellRect.width;
		}
	}

	/**
	 * Paints column header.
	 */
	private void paintColumnCell(final Graphics g, final Rectangle cellRect, final int columnIndex) {
		TableColumn column = header.getColumnModel().getColumn(columnIndex);
		TableCellRenderer colRenderer = getRenderer(column);
		Component component = colRenderer.getTableCellRendererComponent(
				header.getTable(), column.getHeaderValue(),false, false, -1, columnIndex);
		rendererPane.add(component);
		rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
				cellRect.width, cellRect.height, true);
	}

	/**
	 * Paints group header.
	 */
	private void paintGroupCell(final Graphics g, final Rectangle cellRect,final ColumnGroup cGroup) {
	    TableCellRenderer groupRenderer = cGroup.getHeaderRenderer();
	    Component component = groupRenderer.getTableCellRendererComponent(
				header.getTable(), cGroup.getHeaderValue(),false, false, -1, -1);
	    rendererPane.add(component);
	    rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
					cellRect.width, cellRect.height, true);
	}

	private TableCellRenderer getRenderer(final TableColumn col) {
		TableCellRenderer result = col.getHeaderRenderer();
		if (result == null) {
			result = header.getDefaultRenderer();
		}
		return result;
	}
	
	/**
	 * Just a copy of superclass - to be able to invoke <code>getHeaderHeight()</code>. 
	 */
	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn nextColumn = enumeration.nextElement();
			width += nextColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}
	
	/**
	 * Just a copy of superclass - to be able to invoke <code>getHeaderHeight()</code>. 
	 */
	private Dimension createHeaderSize(long width) {
        //TableColumnModel columnModel = header.getColumnModel();
        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }
        return new Dimension((int)width, getHeaderHeight());
    }	

	private int getHeaderHeight() {
		int result = 0;
		GroupableTableHeader groupableHeader = (GroupableTableHeader)header;
		TableColumnModel columnModel = header.getColumnModel();
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn nextColumn = columnModel.getColumn(i);
			TableCellRenderer nextRenderer = getRenderer(nextColumn);
			int nextHightForColumn = 0;
			if (groupableHeader.isShowColumns()) {
				Component nextColumnComp = nextRenderer.getTableCellRendererComponent(
					    header.getTable(), nextColumn.getHeaderValue(), false, false,-1, i);
				nextHightForColumn = nextColumnComp.getPreferredSize().height;
			}
			List<ColumnGroup> nextGroups = groupableHeader.getColumnGroups(nextColumn);
			if (nextGroups != null) {
				for (ColumnGroup nextGroup : nextGroups) {
					nextHightForColumn += nextGroup.getSize(header.getTable()).height;
				}
			}
			result = Math.max(result, nextHightForColumn);
		}
		return result;
	}
	
}