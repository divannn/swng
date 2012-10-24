package SortableTree;

import java.util.Comparator;

import javax.swing.tree.DefaultTreeModel;

/**
 * @author idanilov
 *
 */
public class SortableTreeModel extends DefaultTreeModel {

	protected Comparator comparator;

	public SortableTreeModel(final SortableNode root) {
		super(root);
	}

	public void sort() {
		if (comparator != null) {
			SortableNode r = (SortableNode) this.getRoot();
			r.sort(comparator);
			reload();
		}
	}

	public void setComparator(Comparator comp) {
		comparator = comp;
	}

	public Comparator getComparator() {
		return comparator;
	}

}
