import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import changeaware.ClonableTableModel;

/**
 * @author idanilov
 *
 */
public class SampleTableModel extends DefaultTableModel
		implements ClonableTableModel {

	public SampleTableModel(final int i, final int j) {
		super(i, j);
	}

	public SampleTableModel(final Object[][] data, final Object[] columnNames) {
		super(data, columnNames);
	}

	public TableModel cloneTableModel() {
		final int rowCount = getRowCount();
		final int colCount = getColumnCount();
		Object[] columnNames = new Object[colCount];
		SampleTableModel result = new SampleTableModel(rowCount, colCount);
		//copy data.		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (i == 0) {
					columnNames[j] = getColumnName(j);
				}
				Object nextObject = getValueAt(i, j);
				//clone each object here.
				Object nextClonedObject = new String(nextObject.toString());
				result.setValueAt(nextClonedObject, i, j);
			}
		}
		//copy column names.
		result.setColumnIdentifiers(columnNames);
		return result;
	}
}
