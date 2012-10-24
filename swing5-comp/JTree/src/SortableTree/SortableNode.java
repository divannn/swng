/**
 * 
 */
package SortableTree;

import java.util.Collections;
import java.util.Comparator;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author idanilov
 *
 */
public class SortableNode extends DefaultMutableTreeNode {

	public SortableNode(Object object) {
		super(object);
	}

	public void sort(final Comparator comparator) {
		if (comparator != null) {
			if (children != null) {
				Collections.sort(this.children, comparator);
				for (Object child : children) {
					if (child instanceof SortableNode) {
						((SortableNode) child).sort(comparator);
					}
				}
			}
		}
	}

}