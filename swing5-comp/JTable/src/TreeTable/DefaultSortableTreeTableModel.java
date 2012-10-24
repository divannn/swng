package TreeTable;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import TreeTable.treetable.sort.SortableTreeTableModel;

/**
 * @author idanilov
 *
 */
public class DefaultSortableTreeTableModel extends SortableTreeTableModel {

	protected DefaultTreeModel model;

	public DefaultSortableTreeTableModel() {
		model = new DefaultTreeModel(new DefaultMutableTreeNodeEx());
	}

	public DefaultSortableTreeTableModel(DefaultTreeModel m) {
		model = m;
	}

	@Override
	protected void setChildArray(Object parent, Object[] children) {
		DefaultMutableTreeNodeEx nodeEx = (DefaultMutableTreeNodeEx) parent;
		nodeEx.setChildren(new Vector(Arrays.asList(children)));
	}

	@Override
	public Object getChildAt(Object node, int index) {
		return model.getChild(node, index);
	}

	@Override
	public int getChildCount(Object node) {
		return model.getChildCount(node);
	}

	@Override
	public Object getParent(Object node) {
		return ((TreeNode) node).getParent();
	}

	@Override
	public Object getRoot() {
		return model.getRoot();
	}

	@Override
	public Object getValueAt(Object row, int columnIndex) {
		return row.toString();
	}

	@Override
	public boolean isLeaf(Object node) {
		return model.isLeaf(node);
	}

	public int getColumnCount() {
		return 1;
	}

}