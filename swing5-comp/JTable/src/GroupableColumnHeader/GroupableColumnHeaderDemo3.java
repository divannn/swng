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
public class GroupableColumnHeaderDemo3 extends JFrame {

	public GroupableColumnHeaderDemo3() {
		super(GroupableColumnHeaderDemo3.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] {
				{"119","foo","bar","ja","ko","zh","ru"},
				{"911","bar","foo","en","fr","pt","pl"}
	  	}, new Object[] {
				"#","first","last","Native","1","2","3"
		});
		JTable table = new JTable( dm ) {
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};
		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("Name");
		g_name.add(cm.getColumn(1));
		g_name.add(cm.getColumn(2));
		ColumnGroup g_lang = new ColumnGroup("Language");
		g_lang.add(cm.getColumn(3));
		ColumnGroup g_other = new ColumnGroup("Others");
		g_other.add(cm.getColumn(4));
		g_other.add(cm.getColumn(5));
		g_other.add(cm.getColumn(6));
		g_lang.add(g_other);
		GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
		header.addColumnGroup(g_name);
		header.addColumnGroup(g_lang);
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
		JFrame f = new GroupableColumnHeaderDemo3();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
