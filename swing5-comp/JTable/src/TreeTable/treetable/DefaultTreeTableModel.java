package TreeTable.treetable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class DefaultTreeTableModel extends AbstractTreeTableModel {

	protected DefaultTreeModel model;

	public DefaultTreeTableModel() {
		model = new DefaultTreeModel(new DefaultMutableTreeNode());
	}

	public DefaultTreeTableModel(DefaultTreeModel m) {
		model = m;
	}

	public DefaultTreeTableModel(TreeNode root) {
		model = new DefaultTreeModel(root);
	}

	public void setRoot(TreeNode root) {
		model.setRoot(root);
	}

	/*
	 * Overriden abstract methods of TreeTableModel
	 */

	public Object getValueAt(Object row, int columnIndex) {
		return row.toString();
	}

	public Object getRoot() {
		return model.getRoot();
	}

	public Object getChildAt(Object node, int index) {
		return model.getChild(node, index);
	}

	public Object getParent(Object node) {
		return ((TreeNode) node).getParent();
	}

	public int getChildCount(Object node) {
		return model.getChildCount(node);
	}

	public boolean isLeaf(Object node) {
		return model.isLeaf(node);
	}

	/*
	 * Overriden other abstract methods of TreeTable
	 */

	public int getColumnCount() {
		return 1;
	}

}
