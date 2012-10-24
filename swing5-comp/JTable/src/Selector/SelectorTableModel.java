package Selector;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author idanilov
 *
 */
public class SelectorTableModel extends AbstractTableModel {

	private List<SelectorItem> data;
	private int rowCount;
	private int columnCount;

	public SelectorTableModel(final List<SelectorItem> d, final int constriction,
			final boolean byRows) {
		data = d;
		if (byRows) {
			calculateByRows(constriction);
		} else {
			calculateByColumns(constriction);
		}
	}

	private void calculateByRows(final int rows) {
		rowCount = rows;
		columnCount = data.size() / rowCount;
		if (columnCount * rowCount < data.size()) {
			columnCount++;
		}
		if (columnCount * rowCount >= data.size() + columnCount) {
			rowCount = rowCount - (columnCount * rowCount - data.size()) / columnCount;
		}
	}

	private void calculateByColumns(final int columns) {
		columnCount = columns;
		rowCount = data.size() / columnCount;
		if (columnCount * rowCount < data.size()) {
			rowCount++;
		}
	}

	private int convertToCounter(int row, int col) {
		return row * getColumnCount() + col;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int count) {
		calculateByColumns(count);
		fireTableStructureChanged();
	}

	public String getColumnName(int column) {
		return null;
	}

	public Class<?> getColumnClass(int i) {
		return SelectorItem.class;
	}

	public Object getValueAt(int row, int col) {
		int index = convertToCounter(row, col);
		if (index >= 0 && index < data.size()) {
			return get(index);
		}
		return null;
	}

	public SelectorItem get(final int ind) {
		return data.get(ind);
	}

	public int size() {
		return data.size();
	}

	public int getRow(final int ind) {
		return ind / getColumnCount();
	}

	public int getColumn(final int ind) {
		return ind % getColumnCount();
	}

}
