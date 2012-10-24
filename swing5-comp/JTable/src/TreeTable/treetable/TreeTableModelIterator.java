package TreeTable.treetable;

import java.util.Iterator;
import java.util.Stack;

/**
 * Iterates through TreeTableModel
 * next() method returns objct corresponding to tree-table rowþ
 * @author idanilov
 */
class TreeTableModelIterator
		implements Iterator {

	/** determines whether to expand all nodes or iterate them 'as is' */
	protected boolean expand;

	/** model to iterate through */
	protected TreeTableModel model;

	/** next item parent's path */
	protected Stack<PathNode> path;

	/** holds next object (NB: next, not current! ) */
	protected Object next;

	/** finds next node */
	protected void findNext() {
		if (next == null) {
			// there's no more nodes
			return;
		}
		if (model.getChildCount(next) > 0 && (expand || model.getExpansionModel().isExpanded(next))) {
			// next is the first child
			path.push(new PathNode(next));
			next = model.getChildAt(next, 0);
		} else {
			// we're going up along path (parent, granpa, etc.),
			// checking whether each node has next child
			while (!path.isEmpty()) {
				PathNode parent = path.peek();
				int childCnt = model.getChildCount(parent.node);
				if (parent.curChildIdx + 1 < childCnt) {
					// our parent has next child
					parent.curChildIdx++;
					next = model.getChildAt(parent.node, parent.curChildIdx);
					return;
				}
				path.pop(); // this branch is exhausted - get rid of it
			}
			next = null;
		}
	}

	/** creates iterator, which will iterate
	 * entire model, starting from root
	 * @param model model to iterate
	 * @param expand specifies whether iterator should expand copllapsed nodes
	 * (i.e. go through collapsed nodes children) or not
	 */
	public TreeTableModelIterator(TreeTableModel model, boolean expand) {
		this.model = model;
		this.expand = expand;
		path = new Stack<PathNode>();
		//path.push(new PathNode(model.getRoot()));
		next = model.getRoot();
		if (!model.isRootVisible()) {
			next = model.getRoot();
			findNext();
		}
	}

	/** creates iterator, which will iterate through
	 * given node and all its children
	 * @param model model to iterate
	 * @param expand specifies whether iterator should expand copllapsed nodes
	 * @param next object, whose children should be iterated
	 * (i.e. go through collapsed nodes children) or not
	 */
	public TreeTableModelIterator(TreeTableModel model, boolean expand, Object next) {
		this.model = model;
		this.expand = expand;
		this.next = next;
		path = new Stack<PathNode>();
	}

	public boolean hasNext() {
		return (next != null);
	}

	public Object next() {
		Object ret = next;
		findNext();
		return ret;
	}

	public void remove() {
		throw new java.lang.UnsupportedOperationException("Method remove() is unsupported.");
	}

	/** just a holder, wrapping a pair of values - node object and it's current child's index */
	protected class PathNode {

		public Object node;
		public int curChildIdx;

		protected PathNode(Object node) {
			this.node = node;
			this.curChildIdx = 0;
		}
		
	}
	
}