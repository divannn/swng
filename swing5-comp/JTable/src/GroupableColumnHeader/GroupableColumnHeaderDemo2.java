package GroupableColumnHeader;

/* 
 * |-----------------------------------------------------|
 * |        |       Name      |         Language         |
 * |        |-----------------|--------------------------|
 * |  SNo.  |        |        |        |      Others     |
 * |        |   1    |    2   | Native |-----------------|
 * |        |        |        |        |   2    |   3    |  
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
 * @author unknown
 * @author idanilov
 * @jdk 1.5
 */
public class GroupableColumnHeaderDemo2 extends JFrame {

	public GroupableColumnHeaderDemo2() {
		super(GroupableColumnHeaderDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] {
				{"11","12","13","14"},
				{"21","22","23","24"}
	  	}, new Object[] {
				"ñ1","ñ2","ñ3","ñ4"
		});
		JTable table = new JTable( dm ) {
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};
		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g1 = new ColumnGroup("Group1");
		g1.add(cm.getColumn(0));
		ColumnGroup g2 = new ColumnGroup("Group2");
		g2.add(cm.getColumn(3));
		ColumnGroup g3 = new ColumnGroup("Group3");
		g3.add(cm.getColumn(1));
		g3.add(cm.getColumn(2));
		g2.add(g3);
		GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
		header.addColumnGroup(g1);
		header.addColumnGroup(g2);
		header.addColumnGroup(g3);
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
		JFrame f = new GroupableColumnHeaderDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
