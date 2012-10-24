package FixedColumns._2;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;


/**
 * Overriddis table header UI to achieve full column fix.
 * Serveral columns can be fixable.
 * @author idanilov
 * @jdk 1.5
 */
public class FixedColumnsDemo2 extends JFrame {

	private JTable table;

	public FixedColumnsDemo2() {
		super(FixedColumnsDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());

		final Object[][] data = new Object[][] { { "1", "A", "", "", "", "", "" },
				{ "2", "", "B", "", "", "", "" }, { "3", "", "", "C", "", "", "" },
				{ "4", "", "", "", "D", "", "" }, { "5", "", "", "", "", "E", "" },
				{ "6", "", "", "", "", "", "F" } };
		final Object[] columns = new Object[] { "fixed1", "a", "b", "c", "fixed2", "e", "f" };
		DefaultTableModel dtm = new DefaultTableModel(data, columns);
		table = new JTable(dtm);
		ColumnFixableTableHeader th = new ColumnFixableTableHeader(table.getColumnModel());
		th.setFixedColumnIndices(new int [] {0,4});
		table.setTableHeader(th);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);
		result.add(scrollPane, BorderLayout.CENTER);
		return result;
	}

	public static void main(final String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        }	
		JFrame f = new FixedColumnsDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}