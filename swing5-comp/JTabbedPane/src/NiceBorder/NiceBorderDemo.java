package NiceBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 * When JTabbedPane is placed inside JSplitPane, it gives a very ugly looks 
 * because of JTabbedPane borders. Here border of tabbed pane is corrected and looks better.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class NiceBorderDemo extends JFrame {

	public NiceBorderDemo() {
		super(NiceBorderDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(2, 1));
		result.add(createDefault());
		result.add(createWithNiceBorders());
		return result;
	}

	private JComponent createDefault() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Default:"),BorderLayout.NORTH);
		
		JSplitPane splitPane = new JSplitPane();
		JTabbedPane tabPane = new JTabbedPane();
		JTree tree = new JTree();
		tabPane.addTab("Tree", new JScrollPane(tree));
		JTable table = new JTable(5, 3);
		table.setPreferredScrollableViewportSize(new Dimension(200, 100));
		tabPane.addTab("Table", new JScrollPane(table));
		splitPane.setLeftComponent(tabPane);
		result.add(splitPane,BorderLayout.CENTER);
		return result;
	}

	private JComponent createWithNiceBorders() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Nice borders:"),BorderLayout.NORTH);

		Insets oldInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		JTabbedPane tabPane = new JTabbedPane();
		UIManager.put("TabbedPane.contentBorderInsets", oldInsets);

		JTree tree = new JTree();
		tabPane.addTab("Tree", new JScrollPane(tree));
		JTable table = new JTable(5, 3);
		table.setPreferredScrollableViewportSize(new Dimension(200, 100));
		tabPane.addTab("Table", new JScrollPane(table));
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(tabPane);
		result.add(splitPane,BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame f = new NiceBorderDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
