package SortableTree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Simple way to sort tree. Expansion state is retained.
 * @author idanilov
 * @jdk 1.5
 */
public class SortableTreeDemo extends JFrame {

	private JToggleButton sortButton;
	private JTree tree;
	private SortableTreeModel sortableTreeModel;
	private TreePath[] expandedPaths;

	public SortableTreeDemo() {
		super(SortableTreeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createToolbar(), BorderLayout.NORTH);

		sortableTreeModel = createTreeModel();
		tree = new JTree(sortableTreeModel);
		JScrollPane scrollPane = new JScrollPane(tree);
		result.add(scrollPane, BorderLayout.CENTER);

		sortButton.setSelected(true);//sort desc by default.
		return result;
	}

	private JToolBar createToolbar() {
		JToolBar result = new JToolBar();
		result.setRollover(true);
		final String sortAscTitle = "Asc";
		final String sortDescTitle = "Desc";

		sortButton = new JToggleButton("Ascending");

		sortButton.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent ie) {
				if (sortButton.isSelected()) {
					sortButton.setText(sortDescTitle);
					sortableTreeModel
							.setComparator(AlphabeticalComparator.INVERSE_CASE_INSENSITIVE);

				} else {
					sortButton.setText(sortAscTitle);
					sortableTreeModel.setComparator(AlphabeticalComparator.DIRECT_CASE_INSENSITIVE);

				}
				saveExpandedState();
				sortableTreeModel.sort();
				restoreExpandedState();
			}

		});
		result.add(sortButton);
		return result;
	}

	private void saveExpandedState() {
		ArrayList<TreePath> expandedPathList = new ArrayList<TreePath>();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		TreeNode root = (TreeNode) model.getRoot();
		for (Enumeration en = root.children(); en.hasMoreElements();) {
			TreePath path = new TreePath(new TreeNode[] { root, (TreeNode) en.nextElement() });
			Enumeration expandedEnum = tree.getExpandedDescendants(path);
			if (expandedEnum != null && expandedEnum.hasMoreElements()) {
				expandedPathList.addAll(Collections.list(expandedEnum));
			}
		}
		expandedPaths = expandedPathList.toArray(new TreePath[expandedPathList.size()]);
	}

	private void restoreExpandedState() {
		if (expandedPaths != null) {
			for (int i = 0; i < expandedPaths.length; i++) {
				tree.expandPath(expandedPaths[i]);
			}
		}
	}

	/**
	 * @return sortable model populated with sortable nodes
	 */
	private SortableTreeModel createTreeModel() {
		SortableNode root = new SortableNode("JTree");
		SortableNode parent;

		parent = new SortableNode("colors");
		root.add(parent);
		parent.add(new SortableNode("blue"));
		parent.add(new SortableNode("violet"));
		parent.add(new SortableNode("red"));
		parent.add(new SortableNode("yellow"));

		parent = new SortableNode("sports");
		root.add(parent);
		parent.add(new SortableNode("basketball"));
		parent.add(new SortableNode("soccer"));
		parent.add(new SortableNode("football"));
		parent.add(new SortableNode("hockey"));

		parent = new SortableNode("food");
		root.add(parent);
		parent.add(new SortableNode("hot dogs"));
		parent.add(new SortableNode("pizza"));
		parent.add(new SortableNode("ravioli"));
		parent.add(new SortableNode("bananas"));

		SortableTreeModel result = new SortableTreeModel(root);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new SortableTreeDemo();
		frame.pack();
		frame.setSize(new Dimension(300, 400));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
