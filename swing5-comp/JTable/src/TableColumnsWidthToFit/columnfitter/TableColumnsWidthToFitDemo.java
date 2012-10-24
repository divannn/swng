package TableColumnsWidthToFit.columnfitter;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * The same as in Windows Explorer. Two abilities:
 * <br>
 * 1. Press "Ctrl+Num Pad Plus" to fit columns widths in their preferred size. 
 * <br>
 * 2. Dbl click on the vertical line dividing columns to fit single column width.
 * @author santosh
 * @jdk 1.5
 */
public class TableColumnsWidthToFitDemo extends JFrame {

	public TableColumnsWidthToFitDemo() {
		super(TableColumnsWidthToFitDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String[] columnNames = { "col1", "col2", "col3", "col4", "col5" };
		String[][] data = { 
				{ "1", "2", "3", "4", "5" },
				{ "1", "22", "3", "44", "5" }, 
				{ "1", "222", "3", "444", "5" },
				{ "1", "2222", "3", "4444", "5" },
				{ "1", "22222", "3", "44444", "5" }, 
		};
		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(dtm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		String toolTip = "Press Ctrl+NumPad+ to fit column's width"; 
		table.setToolTipText(toolTip);
		table.getTableHeader().setToolTipText(toolTip);
		table.registerKeyboardAction(new FitTableColumnsAction(), KeyStroke
				.getKeyStroke(KeyEvent.VK_ADD, InputEvent.CTRL_MASK),
				JComponent.WHEN_FOCUSED);
		table.getTableHeader().addMouseListener(new ColumnFitAdapter());		
		JScrollPane scrollPane = new JScrollPane(table);
		result.add(scrollPane, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		TableColumnsWidthToFitDemo f = new TableColumnsWidthToFitDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}