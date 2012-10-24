package MoveTableColumnsByKeyboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Move column demo. Ctrl+Left - move column left.
 * Ctrl+Right - move column right.
 * @author idanilov
 * @jdk 1.5
 */
public class MoveTableColumnsByKeyboardDemo extends JFrame {

	public MoveTableColumnsByKeyboardDemo() {
		super(MoveTableColumnsByKeyboardDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		TableModel dataModel = new TM();
		final JTable table = new JTable(dataModel);
		String ctrlLeftString = KeyEvent.getKeyModifiersText(InputEvent.CTRL_MASK)+ "+" +KeyEvent.getKeyText(KeyEvent.VK_LEFT);
		String ctrlRightString = KeyEvent.getKeyModifiersText(InputEvent.CTRL_MASK)+ "+" +KeyEvent.getKeyText(KeyEvent.VK_RIGHT);
		String tip = "Press " + ctrlLeftString + "/" + ctrlRightString;
		table.setToolTipText(tip);
		table.getTableHeader().setToolTipText(tip);
		table.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.getColumnModel().setColumnSelectionAllowed(true);

		//for test perpose only.
		table.getColumnModel().addColumnModelListener(new TableColumnModelListener() {

			public void columnAdded(TableColumnModelEvent e) {
			}

			public void columnRemoved(TableColumnModelEvent e) {
			}

			public void columnMoved(TableColumnModelEvent e) {
				if (e.getFromIndex() != e.getToIndex()) {
					TableColumnModel tcm = (TableColumnModel)e.getSource();
					System.err.println(">>> MOVED column " + tcm.getColumn(e.getToIndex()).getHeaderValue()
							+  " from : " + e.getFromIndex() + " to : " + e.getToIndex());
				}
			}

			public void columnMarginChanged(ChangeEvent e) {
			}

			public void columnSelectionChanged(ListSelectionEvent e) {
			}
			
		});
		
		table.setPreferredScrollableViewportSize(new Dimension(300, 200));
		table.setRowSelectionInterval(0, dataModel.getRowCount() - 1);
		table.setColumnSelectionInterval(0, 0);

		KeyStroke ksLeft = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,Event.CTRL_MASK);
		table.getInputMap().put(ksLeft, "MoveLeft");

		Action moveLeft = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedColumn();
				if (index != 0 && index != -1) {
					table.moveColumn(index, index - 1);
				}
			}
		};
		//Replace the table's default MoveLeft action.
		table.getActionMap().put("MoveLeft", moveLeft);
		KeyStroke ksRight = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,Event.CTRL_MASK);
		table.getInputMap().put(ksRight, "MoveRight");
		Action moveRight = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedColumn();
				if ((index != table.getColumnCount() - 1) && index != -1) {
					table.moveColumn(index, index + 1);
				}
			}
		};
		//Replace the table's default MoveRight action.
		table.getActionMap().put("MoveRight", moveRight);
		JScrollPane sp = new JScrollPane(table);
		result.add(sp,BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new MoveTableColumnsByKeyboardDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class TM extends AbstractTableModel {

		private final static int NROWS = 10;
		private final static int NCOLS = 5;

		private Object[][] rowData;

		public TM() {
			rowData = new Object[NROWS][NCOLS];
			int x = 0;
			for (int row = 0; row < NROWS; row++) {
				for (int col = 0; col < NCOLS; col++) {
					rowData[row][col] = new Integer(x++);
				}
			}
		}

		public int getColumnCount() {
			return NCOLS;
		}

		public int getRowCount() {
			return NROWS;
		}

		public Object getValueAt(int row, int col) {
			return rowData[row][col];
		}

		public boolean isCellEditable(int row, int col) {
			if (row == 0 || col == 0) {
				return true;
			}
			return false;
		}

		public void setValueAt(Object value, int row, int col) {
			rowData[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		
	}

}
