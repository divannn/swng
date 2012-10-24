package TableColumnsWidthToFit.columnfitter;

import java.awt.event.ActionEvent;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * Fits widths for all columns.
 * @author idanilov
 *
 */
public class FitTableColumnsAction extends AbstractAction {

	public FitTableColumnsAction() {
		super("FitTableColumnsAction");
	}

	public void actionPerformed(ActionEvent ae) {
		JTable table = (JTable) ae.getSource();
		int rowCount = table.getRowCount();
		JTableHeader header = table.getTableHeader();
		Enumeration columns = table.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn nextColumn = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(nextColumn.getIdentifier());
			int width = (int) table.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(table,nextColumn.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col)
						.getTableCellRendererComponent(table,
								table.getValueAt(row, col), false, false,
								row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(nextColumn); // !!!this line is very important.
			nextColumn.setWidth(width + table.getIntercellSpacing().width);
		}
	}
}