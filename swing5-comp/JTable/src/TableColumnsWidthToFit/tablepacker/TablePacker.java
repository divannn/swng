package TableColumnsWidthToFit.tablepacker;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * @author idanilov
 *
 */
public class TablePacker {
	
	/**
	 * Defines which rows participates in calculation of preffered size.
	 * @author idanilov
	 *
	 */
	public enum CalculatinType {
		/**
		 * Only visible rows and header columns participate in calculation.
		 */
		VISIBLE_ROWS,
		/**
		 * All rows and header columns participate in calculation.
		 */
		ALL_ROWS,
		/**
		 * Only header columns participate in calculation.
		 */
		NO_ROWS,
	}
	
	private CalculatinType rowsIncluded = CalculatinType.VISIBLE_ROWS;
	private boolean distributeExtraArea = true;
	   
	public TablePacker(CalculatinType rowsIncluded, boolean distributeExtraArea){
		this.rowsIncluded = rowsIncluded;
		this.distributeExtraArea = distributeExtraArea;
	}
	
	private int preferredWidth(final JTable table, final int col) {
		TableColumn tableColumn = table.getColumnModel().getColumn(col);
		int width = (int)table.getTableHeader().getDefaultRenderer()
			.getTableCellRendererComponent(table, tableColumn.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
		if (table.getRowCount() != 0) {
			int startRow = 0, rowCount = 0;
			if (rowsIncluded == CalculatinType.VISIBLE_ROWS) {
				Rectangle rect = table.getVisibleRect();
				startRow = table.rowAtPoint(rect.getLocation());
				rowCount = table.rowAtPoint(new Point((int)rect.getMaxX() - 1, (int)rect.getMaxY() - 1));
				rowCount++;
			} else if (rowsIncluded == CalculatinType.ALL_ROWS) {
				startRow = 0;
				rowCount = table.getRowCount();
			}
			for(int row = startRow; row < rowCount; row++) {
				int preferedWidth = (int)table.getCellRenderer(row, col).getTableCellRendererComponent(table,
					table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
		}
		return width + table.getIntercellSpacing().width;
	}
	
	public void pack(final JTable table) {
		if (!table.isShowing()) {
			throw new IllegalStateException("table must be showing to pack");
		}
		if (table.getColumnCount() != 0) {
			int width[] = new int[table.getColumnCount()];
			int total = 0;
			for(int col = 0; col < width.length; col++) {
				width[col] = preferredWidth(table, col);
				total += width[col];
			}
			int extra = table.getVisibleRect().width - total;
			if (extra > 0) {
				if (distributeExtraArea) {
					int bonus = extra/table.getColumnCount();
					for(int i = 0; i<width.length; i++) {
						width[i] += bonus;
					}
					extra -= bonus*table.getColumnCount();
				}
				width[width.length-1] += extra;
			}
			TableColumnModel columnModel = table.getColumnModel();
			for(int col = 0; col < width.length; col++) {
				TableColumn tableColumn = columnModel.getColumn(col);
				table.getTableHeader().setResizingColumn(tableColumn);
				tableColumn.setWidth(width[col]);
			}
		}
	}
	
} 