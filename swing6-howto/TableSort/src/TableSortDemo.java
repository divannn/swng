import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Shows new feature - ability to sort JTable rows.
 * @jdk 1.6
 */
public class TableSortDemo extends JFrame {

	private Object[] columnNames = { "col1", "col2" };
	private Object[][] data = { { "4", 2.0 }, { "6", 1.2 }, { "1", 7.11 } };

	public TableSortDemo() {
		super(TableSortDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTableModel tm = new DefaultTableModel(data, columnNames) {
			public Class<?> getColumnClass(int column) {
				Class returnValue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnValue = getValueAt(0, column).getClass();
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}
		};
		JTable table = new JTable(tm);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tm);
		table.setRowSorter(sorter);

		result.add(new JScrollPane(table), BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new TableSortDemo();
		f.pack();
		f.setMinimumSize(f.getPreferredSize());
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
