package EditableTableHeader;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class EditableTableHeaderDemo extends JFrame {

	public EditableTableHeaderDemo() {
		super(EditableTableHeaderDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
        Object[][] data = {
                { "A", "1", "C" },
                { " ", "2", "D" },
                { "B", "3", " " },
                { "B", "4", "D" },
                { "A", "5", "C" },
                { " ", "6", "C" },
                { "A", "7", " " },
                { " ", "8", " " },
                { "B", "9", "D" },
                { "B", "10", "E" }
		};
		Object[] cols = { "ONE", "TWO", "THREE" };
		JTable table = new JTable(data, cols);
		table.getTableHeader().setToolTipText("Double click to start editing header value");
		new EditableHeaderSupport(table);
		result.add(new JScrollPane(table));
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        }		
		JFrame f = new EditableTableHeaderDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
