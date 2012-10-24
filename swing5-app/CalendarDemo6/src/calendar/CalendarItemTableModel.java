package calendar;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author idanilov
 *
 */
public class CalendarItemTableModel extends AbstractTableModel {

	private List data;

	public CalendarItemTableModel(final List data) {
		this.data = data;
	}

	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return 1;
	}

	public String getColumnName(final int column) {
		return "";
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return data.get(rowIndex);
	}

}
