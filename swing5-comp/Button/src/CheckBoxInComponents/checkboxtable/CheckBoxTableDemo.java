package CheckBoxInComponents.checkboxtable;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class CheckBoxTableDemo extends JFrame {

	public CheckBoxTableDemo() {
		super(CheckBoxTableDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		CheckBoxTable cbTable = new CheckBoxTable(5,3);
		//check some items.
		cbTable.setChecked(0,true);
		cbTable.setChecked(3,true);
		cbTable.setDelegateRendererForCheckableColumn(new CustomRenderer());
		result.add(new JScrollPane(cbTable),BorderLayout.CENTER);
		return result;
	}
    
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
		}       		
		JFrame frame = new CheckBoxTableDemo();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static class CustomRenderer extends DefaultTableCellRenderer {
		
		private static Icon ICON = new ImageIcon(CustomRenderer.class.getResource("dots3.png")); 
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel result = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
					row, column);
			result.setIcon(ICON);
			return result;
		}
		
	}
	
}
