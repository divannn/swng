package MultilineTableCellEditor._1;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;

/**
 * Editing multiline cell as in in Excel.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class MultilineTableCellEditorDemo extends JFrame {

	private String[] columnNames = { "multiline editor", "col2", "col3", };

	private String[][] data = { { "text1\ntext1\ntext1", "2", "3", }, { "6", "text2", "8", },
			{ "11", "12", "13", }, { "16", "17", "18", }, { "21", "22", "23", }, };

	public MultilineTableCellEditorDemo() {
		super(MultilineTableCellEditorDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTable table = new TableEx(data, columnNames);
		TableColumn column0 = table.getColumnModel().getColumn(0);
		column0.setCellEditor(new MultiLineTableCellEditor());

		JScrollPane scrollPane = new JScrollPane(table);
		result.add(scrollPane);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MultilineTableCellEditorDemo f = new MultilineTableCellEditorDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}