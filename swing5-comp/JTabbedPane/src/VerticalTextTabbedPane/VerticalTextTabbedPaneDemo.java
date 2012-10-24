package VerticalTextTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class VerticalTextTabbedPaneDemo extends JFrame {

	public VerticalTextTabbedPaneDemo() {
		super(VerticalTextTabbedPaneDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane1 = createLeftOrientedTabbedPane();
		result.add(tabbedPane1, BorderLayout.WEST);
		JTabbedPane tabbedPane2 = createRightOrientedTabbedPane();
		result.add(tabbedPane2, BorderLayout.EAST);
		return result;
	}

	private JTabbedPane createLeftOrientedTabbedPane() {
		JTabbedPane result = TabbedPaneWithVerticalTabs.create(SwingConstants.LEFT);
		JTree tree = new JTree();
		JScrollPane scrollPane1 = new JScrollPane(tree);
		result.addTab("Tab1", scrollPane1);
		JTextArea textArea = new JTextArea("Some text text text text text");
		JScrollPane scrollPane2 = new JScrollPane(textArea);
		result.addTab("Tab2", scrollPane2);
		return result;
	}

	private JTabbedPane createRightOrientedTabbedPane() {
		JTabbedPane result = TabbedPaneWithVerticalTabs.create(SwingConstants.RIGHT);
		String[] colNames = { "col1", "col2" };
		String[][] data = { { "1", "2" }, { "3", "4" } };
		JTable table = new JTable(data, colNames);
		JScrollPane scrollPane1 = new JScrollPane(table);
		result.addTab("Tab1", scrollPane1);
		JPanel panel = new JPanel();
		JScrollPane scrollPane2 = new JScrollPane(panel);
		result.addTab("Tab2", scrollPane2);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new VerticalTextTabbedPaneDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}