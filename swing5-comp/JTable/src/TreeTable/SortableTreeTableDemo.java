package TreeTable;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;

import TreeTable.treetable.TreeTable;
import TreeTable.treetable.sort.SortableTreeTable;

/**
 * Allows sort on table columns and even on tree-column.
 * Moreover selection is keeped! Really cool.
 * @author TG_QA
 * @author idanilov
 * @jdk 1.5
 */
public class SortableTreeTableDemo extends JFrame {

	public SortableTreeTableDemo() {
		super(SortableTreeTableDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultSortableTreeTableModel dttm = new DefaultSortableTreeTableModel(
				createDefaultTreeModelEx()) {

			public Object getValueAt(Object row, int columnIndex) {
				return row.toString() + (columnIndex + 1);
			}

			public int getColumnCount() {
				return 3;
			}
		};

		TreeTable sortableTreeTable = new SortableTreeTable(dttm);
		result.add(new JScrollPane(sortableTreeTable), BorderLayout.CENTER);
		return result;
	}

	private static DefaultTreeModel createDefaultTreeModelEx() {
		DefaultMutableTreeNodeEx root = new DefaultMutableTreeNodeEx("JTree");
		DefaultMutableTreeNodeEx parent;

		parent = new DefaultMutableTreeNodeEx("colors");
		root.add(parent);
		parent.add(new DefaultMutableTreeNodeEx("blue"));
		parent.add(new DefaultMutableTreeNodeEx("violet"));
		parent.add(new DefaultMutableTreeNodeEx("red"));
		parent.add(new DefaultMutableTreeNodeEx("yellow"));

		parent = new DefaultMutableTreeNodeEx("sports");
		root.add(parent);
		parent.add(new DefaultMutableTreeNodeEx("basketball"));
		parent.add(new DefaultMutableTreeNodeEx("soccer"));
		parent.add(new DefaultMutableTreeNodeEx("football"));
		parent.add(new DefaultMutableTreeNodeEx("hockey"));

		parent = new DefaultMutableTreeNodeEx("food");
		root.add(parent);
		parent.add(new DefaultMutableTreeNodeEx("hot dogs"));
		parent.add(new DefaultMutableTreeNodeEx("pizza"));
		parent.add(new DefaultMutableTreeNodeEx("ravioli"));
		parent.add(new DefaultMutableTreeNodeEx("bananas"));
		return new DefaultTreeModel(root);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new SortableTreeTableDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
