package RestrictTableToVisibleRowCount;


import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 * I have noticed than when I add JTable to container it occupies more space in height than it is needed.
 * JList has useful method setVisibleRowCount(). For me it is strange that JTable doesn't have the same functionality.
 * Here I make up this gap.
 * @author idanilov
 * @jdk 1.5
 */
public class RestrictTableToVisibleRowCountDemo extends JFrame {

	public RestrictTableToVisibleRowCountDemo() {
		super(RestrictTableToVisibleRowCountDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result,BoxLayout.Y_AXIS));
		JLabel normalTableLabel = new JLabel("Normal JTable:");
		result.add(normalTableLabel);
		String[] columnNames = { "col1", "col2", };
		String[][] data = { 
				{ "1", "http://www.rbc.ru/"},
				{ "2", "http://www.jroller.com/page/santhosh"},
				{ "3", "http://www.java.sun.com/jdk1.5"},
		};
		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		JTable normalTable = new JTable(dtm);
		JScrollPane scrollPane1 = new JScrollPane(normalTable);
		scrollPane1.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane1.setBorder(new LineBorder(Color.RED,1));//just to show table bounds.
		result.add(scrollPane1);
		
		JLabel restrictedTableLabel = new JLabel("JTable restricted to visible rows:");
		result.add(restrictedTableLabel);
		TableEx restrictedRowTable = new TableEx(dtm);
		restrictedRowTable.setVisibleRowCount(dtm.getRowCount());
		JScrollPane scrollPane2 = new JScrollPane(restrictedRowTable);
		scrollPane2.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane2.setBorder(new LineBorder(Color.GREEN,1));//just to show table bounds.
		result.add(scrollPane2);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		RestrictTableToVisibleRowCountDemo f = new RestrictTableToVisibleRowCountDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}