package TableColumnsWidthToFit.tablepacker;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * Another packing strategy. Press F5 to pack.
 * Features:
 * <br>
 * 1. Fits all columns of the table.
 * <br>
 * 2. Remaining extra width distributed among the columns or left for last column.
 * @author santosh
 * @jdk 1.5
 */
public class TablePackerDemo extends JFrame {

	private static JTable packedTable;
	private static TablePacker tablePacker;
	
	public TablePackerDemo() {
		super(TablePackerDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
		tablePacker = new TablePacker(TablePacker.CalculatinType.VISIBLE_ROWS, true);
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(2,1));
		String[] columnNames = { "col1", "col2", };
		String[][] data = { 
				{ "1", "http://www.rbc.ru/"},
				{ "2", "http://www.jroller.com/page/santhosh"},
				{ "3", "http://www.java.sun.com/jdk1.5"},
		};
		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		JTable normalTable = new JTable(dtm);
		normalTable.setPreferredScrollableViewportSize(new Dimension(200,100));
		normalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		result.add(new JScrollPane(normalTable));
		packedTable = new JTable(dtm);
		String tooltip = "Press F5 to pack table";
		packedTable.setToolTipText(tooltip);
		packedTable.getTableHeader().setToolTipText(tooltip);
		packedTable.setPreferredScrollableViewportSize(new Dimension(200,100));
		packedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		packedTable.registerKeyboardAction(new PackAction(), KeyStroke
				.getKeyStroke(KeyEvent.VK_F5, 0), JComponent.WHEN_FOCUSED);
		result.add(new JScrollPane(packedTable));
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TablePackerDemo f = new TablePackerDemo();
				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				tablePacker.pack(packedTable);
			}
		});
	}

	private class PackAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			tablePacker.pack(packedTable);
		}
		
	}
	
}