package TableSorter;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Usage of table sorter - shows how sorting on multiple columns maybe used.
 * Also theis abilty to exclude some columns from sorting. 
 * @author idanilov	
 * @jdk 1.5
 */
public class TableSorterDemo extends JFrame {
	
	private JTable table;
	
	public TableSorterDemo() {
		super(TableSorterDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String [] cols = { "c1","c2","c3" };
		Object [][] data = { 
				{ new Integer(4),"s1",new Boolean(false) },
				{ new Integer(2),"s2",new Boolean(true) }, 
				{ new Integer(2),"s3",new Boolean(true) }
			}; 
		DefaultTableModel dtm = new DefaultTableModel(data,cols);
		TableSorter tSorter = new TableSorter(dtm);
		tSorter.setProhibitedColumns(new int[] {0});//prohibit sorting for first column.
		table = new JTable(tSorter);
		tSorter.setTableHeader(table.getTableHeader());
		JScrollPane sp = new JScrollPane(table);
		result.add(sp,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(final String [] args) {
		JFrame f = new TableSorterDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
