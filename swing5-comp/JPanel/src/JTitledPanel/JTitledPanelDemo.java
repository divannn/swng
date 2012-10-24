package JTitledPanel;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class JTitledPanelDemo extends JFrame {

	public JTitledPanelDemo() {
		super(JTitledPanelDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTree tree1 = new JTree();
		JTitledPanel tp1 = new JTitledPanel("Panel1",new JScrollPane(tree1));
		result.add(tp1,BorderLayout.WEST);
		
		JTable table= new JTable(new String[][] { {"item1","item1"}},new String[] {"col1","col2"});
		JTitledPanel tp2 = new JTitledPanel("Panel2",new JScrollPane(table));
		result.add(tp2,BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.add(new JTextField("text"));
		panel.add(new JCheckBox("checkbox"));
		JTitledPanel tp3 = new JTitledPanel("Panel3",new JScrollPane(panel));
		result.add(tp3,BorderLayout.EAST);
		return result;
	}

	public static void main(String[] args) {
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch(Exception e){ 
			e.printStackTrace();
		}	  		
		JFrame f = new JTitledPanelDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
