package FixedColumns._1;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * Makes first column fixed. Row header view used - so first column in out of table's scroll pane. 
 * Fixed columns count can be any but they all will be at the left side of table.
 * @author Sun
 * @author idanilov
 * @jdk 1.5
 */
public class FixedColumnsDemo extends JFrame {

	private JTable fixedTable, table;
	private final static int FIXED_COLUMN_COUNT  = 1;
	
	public FixedColumnsDemo() {
		super(FixedColumnsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());

		final Object[][] data = new Object[][] { 
				{ "1", "A", "", "", ""},
				{ "2", "", "B", "", "" }, 
				{ "3", "", "", "C", "" },
				{ "4", "", "", "", "D" }, 
				{ "5", "", "", "", "" },
			};
		final Object[] columns = new Object[] { "fixed", "a", "b", "c", "d"};

		AbstractTableModel fixedModel = new AbstractTableModel() {

			public int getColumnCount() {
				return FIXED_COLUMN_COUNT;
			}

			public int getRowCount() {
				return data.length;
			}

			public String getColumnName(int col) {
				return (String) columns[col];
			}

			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
		};

		AbstractTableModel model = new AbstractTableModel() {

			public int getColumnCount() {
				return columns.length - FIXED_COLUMN_COUNT;
			}

			public int getRowCount() {
				return data.length;
			}

			public String getColumnName(int col) {
				return (String) columns[col + FIXED_COLUMN_COUNT];
			}

			public Object getValueAt(int row, int col) {
				return data[row][col + FIXED_COLUMN_COUNT];
			}

			public void setValueAt(Object obj, int row, int col) {
				data[row][col + FIXED_COLUMN_COUNT] = obj;
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}
		};

		fixedTable = new JTable(fixedModel);
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel lsm = fixedTable.getSelectionModel();
		fixedTable.getTableHeader().setResizingAllowed(false);
		lsm.addListSelectionListener(new SelectionListener(true));

		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(100,250));
		lsm = table.getSelectionModel();
		lsm.addListSelectionListener(new SelectionListener(false));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);
		JViewport viewport = new JViewport();
		viewport.setView(fixedTable);
		viewport.setPreferredSize(fixedTable.getPreferredSize());
		scrollPane.setRowHeaderView(viewport);
		scrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, fixedTable.getTableHeader());

		result.add(scrollPane, BorderLayout.CENTER);
		return result;
	}

	public static void main(final String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        }	
		JFrame f = new FixedColumnsDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private class SelectionListener
			implements ListSelectionListener {

		private boolean isFixedTable = true;

		public SelectionListener(boolean isFixedTable) {
			this.isFixedTable = isFixedTable;
		}

		public void valueChanged(ListSelectionEvent e) {

			if (isFixedTable) {
				int fixedSelectedIndex = fixedTable.getSelectedRow();
				table.setRowSelectionInterval(fixedSelectedIndex, fixedSelectedIndex);
			} else {
				int selectedIndex = table.getSelectedRow();
				fixedTable.setRowSelectionInterval(selectedIndex, selectedIndex);
			}
		}
	}

}
