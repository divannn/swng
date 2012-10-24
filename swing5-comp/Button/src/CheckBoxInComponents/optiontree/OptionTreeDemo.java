package CheckBoxInComponents.optiontree;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import CheckBoxInComponents.optiontree.OptionTree.IRadioNode;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class OptionTreeDemo extends JFrame {

	private DefaultMutableTreeNode root, child1, child2, subChild11, subChild12, subChild21,
			subChild22, subChild23, leaf1, leaf2, leaf3;
	private OptionTree optionsTree;

	public OptionTreeDemo() {
		super(OptionTreeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setContentPane(createContents());
		init();
	}

	private void init() {
		//select some.
		TreePath leaf2Path = new TreePath(new Object[] { root, leaf2 });
		TreePath subChild22Path = new TreePath(new Object[] { root, child2, subChild22 });
		TreePath subChild23Path = new TreePath(new Object[] { root, child2, subChild23 });
		optionsTree.setChecked(leaf2Path, true);
		optionsTree.setChecked(subChild22Path, true);
		//disable some.
		optionsTree.setEnabled(leaf2Path, false);
		optionsTree.setEnabled(subChild23Path, false);
		//expand some.
		optionsTree.expandRow(3);
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		optionsTree = new OptionTree(new DefaultTreeModel(createNodes()));

		//for demo purpose - prints object checked that was checked.
		optionsTree.addCheckListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent tse) {
				if (tse.isAddedPath()) {
					System.out.println(Arrays.asList(tse.getPath()));
				}
			}

		});
		result.add(new JScrollPane(optionsTree), BorderLayout.CENTER);
		return result;
	}

	private TreeNode createNodes() {
		root = new DefaultMutableTreeNode("Root");
		leaf1 = new DefaultMutableTreeNode("leaf1", true);
		root.add(leaf1);

		child1 = new DefaultMutableTreeNode("1");
		subChild11 = new DefaultMutableTreeNode("1.1");
		subChild12 = new DefaultMutableTreeNode("1.2");
		child1.add(subChild11);
		child1.add(subChild12);
		root.add(child1);

		child2 = new DefaultMutableTreeNode("2");
		subChild21 = new RadioNode("2.1");
		subChild22 = new RadioNode("2.2");
		subChild23 = new RadioNode("2.3");
		child2.add(subChild21);
		child2.add(subChild22);
		child2.add(subChild23);
		root.add(child2);

		leaf2 = new DefaultMutableTreeNode("leaf2", false);
		leaf3 = new DefaultMutableTreeNode("leaf3", true);
		root.add(leaf2);
		root.add(leaf3);
		return root;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame frame = new OptionTreeDemo();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static class RadioNode extends DefaultMutableTreeNode
			implements IRadioNode {

		public RadioNode() {
			super();
		}

		public RadioNode(final Object o) {
			super(o);
		}
	}

}
