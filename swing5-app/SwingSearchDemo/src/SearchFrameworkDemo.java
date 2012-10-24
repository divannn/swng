import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

import swingsearch.ComboFindAction;
import swingsearch.ListFindAction;
import swingsearch.TableFindAction;
import swingsearch.TextComponentFindAction;
import swingsearch.TreeFindAction;

/**
 * CTRL+I              = Case Sensitive Search
 * CTRL+SHIFT+I    = Case Insensitive Search
 * @author santosh
 * @jdk 1.5
 */
public class SearchFrameworkDemo extends JFrame {

	public SearchFrameworkDemo() {
		super(SearchFrameworkDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTabbedPane tp = new JTabbedPane();

		JTree tree = new JTree();
		new TreeFindAction().install(tree);
		tp.addTab("JTree", new JScrollPane(tree));

		String[] colNames = { "col1", "col2" };
		String[][] data = { { "12", "77" }, { "21", "67" } };
		JTable table = new JTable(data, colNames);
		table.setPreferredScrollableViewportSize(new Dimension(200, 200));
		new TableFindAction().install(table);
		tp.addTab("JTable", new JScrollPane(table));

		JList list = new JList(new String[] { "item1", "item2", "item3" });
		new ListFindAction().install(list);
		tp.addTab("JList", new JScrollPane(list));

		JComboBox combobox = new JComboBox(new String[] { "SMS", "eMail", "Phone" });
		JPanel comboPanel = new JPanel();
		comboPanel.add(combobox);
		new ComboFindAction().install(combobox);
		tp.addTab("JComboBox", comboPanel);

		JTextField textField = new JTextField("some line of text...");
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.add(textField);
		new TextComponentFindAction().install(textField);
		tp.addTab("JTextField", textFieldPanel);

		result.add(tp, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new SearchFrameworkDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
