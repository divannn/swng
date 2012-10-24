package GroupableColumnHeader;

/*
 * |-----------------------------------------------------|
 * |   1st  |      2nd        |          3rd             |
 * |-----------------------------------------------------|
 * |        |        |        |        |        |        |
 */

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * Multi column header.
 * @author unknown
 * @author idanilov
 * @jdk 1.5
 */
public class GroupableColumnHeaderDemo1 extends JFrame {

	public GroupableColumnHeaderDemo1() {
		super(GroupableColumnHeaderDemo1.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
	    DefaultTableModel dtm = new DefaultTableModel();
	    dtm.setDataVector(new Object[][] {
				{"a","b","c","d","e","f"},
				{"A","B","C","D","E","F"} 
		}, new Object[] {
    			"1","2","3","4","5","6"
		});
	    JTable table = new JTable(dtm) {
			protected JTableHeader createDefaultTableHeader() {
				GroupableTableHeader r = new GroupableTableHeader(columnModel);
				r.setPaintColumns(false);
				return r;
			}
		};
	    TableColumnModel tcm = table.getColumnModel();
		ColumnGroup colGroup1st = new ColumnGroup("1st");
		colGroup1st.add(tcm.getColumn(0));
	    ColumnGroup colGroup2nd = new ColumnGroup("2nd");
	    colGroup2nd.add(tcm.getColumn(1));
	    colGroup2nd.add(tcm.getColumn(2));
	    ColumnGroup colGroup3d = new ColumnGroup("3d");
	    colGroup3d.add(tcm.getColumn(3));
	    colGroup3d.add(tcm.getColumn(4));
	    colGroup3d.add(tcm.getColumn(5));
	    GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
		header.addColumnGroup(colGroup1st);
	    header.addColumnGroup(colGroup2nd);
	    header.addColumnGroup(colGroup3d);
	    header.revalidate(); 		
	    JScrollPane scroll = new JScrollPane(table);
	    result.add(scroll,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new GroupableColumnHeaderDemo1();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
