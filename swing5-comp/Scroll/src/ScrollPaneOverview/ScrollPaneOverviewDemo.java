package ScrollPaneOverview;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

/**
 * @author chicago
 * @author idanilov
 * @jdk 1.5
 */
public class ScrollPaneOverviewDemo extends JFrame {

	public ScrollPaneOverviewDemo() {
		super(ScrollPaneOverviewDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(createView());
		ScrollPaneOverviewer.install(scrollPane);
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}

	private JComponent createView() {
		JPanel result = new JPanel(new GridLayout(3,1));
		ImageIcon icon1 = new ImageIcon(ScrollPaneOverviewDemo.class.getResource("beach.jpg"));
		result.add(new JLabel(icon1));
		result.add(createControlsPanel());
		ImageIcon icon2 = new ImageIcon(ScrollPaneOverviewDemo.class.getResource("rocks_waves.jpg"));
		result.add(new JLabel(icon2));
		return result;
	}
	
	private JComponent createControlsPanel() {
		JPanel result = new JPanel(new GridLayout(1,2));
		JTree tree = new JTree();
		tree.expandRow(3);
		tree.expandRow(2);
		tree.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		result.add(tree);
		JTable table = new JTable(10,3);
		table.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		result.add(table);
		return result;
	}

	public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 
		JFrame f = new ScrollPaneOverviewDemo();
		f.setSize(400,400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}