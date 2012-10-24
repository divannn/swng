package TreeTable;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import TreeTable.treetable.DefaultTreeTableModel;
import TreeTable.treetable.TreeTable;

/**
 * @author TG_QA
 * @author idanilov
 * 
 */
public class TreeTableDemo extends JFrame {

	public TreeTableDemo() {
		super(TreeTableDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	/**
	 * Copy from {@link JTree}.
	 * @return TreeModel
	 */
	public static DefaultTreeModel createDefaultTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("JTree");
		DefaultMutableTreeNode parent;

		parent = new DefaultMutableTreeNode("colors");
		root.add(parent);
		parent.add(new DefaultMutableTreeNode("blue"));
		parent.add(new DefaultMutableTreeNode("violet"));
		parent.add(new DefaultMutableTreeNode("red"));
		parent.add(new DefaultMutableTreeNode("yellow"));

		parent = new DefaultMutableTreeNode("sports");
		root.add(parent);
		parent.add(new DefaultMutableTreeNode("basketball"));
		parent.add(new DefaultMutableTreeNode("soccer"));
		parent.add(new DefaultMutableTreeNode("football"));
		parent.add(new DefaultMutableTreeNode("hockey"));

		parent = new DefaultMutableTreeNode("food");
		root.add(parent);
		parent.add(new DefaultMutableTreeNode("hot dogs"));
		parent.add(new DefaultMutableTreeNode("pizza"));
		parent.add(new DefaultMutableTreeNode("ravioli"));
		parent.add(new DefaultMutableTreeNode("bananas"));
		return new DefaultTreeModel(root);
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTreeTableModel dttm = new DefaultTreeTableModel(createDefaultTreeModel()) {

			public Object getValueAt(Object row, int columnIndex) {
				String res = null;
				if (columnIndex == 0) {
					res = row.toString();
				} else {
					res = "item " + (getRowMapper().mapObjectToRow(row) + 1) + "_"
							+ (columnIndex + 1);
				}
				return res;
			}

			public int getColumnCount() {
				return 3;
			}
		};

		TreeTable treeTable = new TreeTable(dttm);
		result.add(new JScrollPane(treeTable), BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new TreeTableDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
