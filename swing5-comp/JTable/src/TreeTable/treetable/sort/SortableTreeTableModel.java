package TreeTable.treetable.sort;

import java.util.Comparator;
import java.util.Vector;

import TreeTable.treetable.AbstractTreeTableModel;
import TreeTable.treetable.TreeExpansionModel;
import TreeTable.treetable.util.Sorter;

/**
 * @author idanilov
 *
 */
public abstract class SortableTreeTableModel extends AbstractTreeTableModel
		implements Comparator {

	private Vector sortingColumns = new Vector();
	private Vector sortingAscending = new Vector();
	private Vector sortingNames = new Vector();

	private boolean ignoreCase;

	public SortableTreeTableModel() {
		super();
	}

	public SortableTreeTableModel(TreeExpansionModel xmodel) {
		super(xmodel);
	}

	public void setIgnoreCase(boolean ignore) {
		ignoreCase = ignore;
	}

	public boolean getIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * Clear sorting model
	 **/
	public void removeAllColumns() {
		sortingColumns.clear();
		sortingAscending.clear();
		sortingNames.clear();
	}

	/**
	 * Is sorting condition by Ascending
	 **/
	public boolean isAscending(int idx) {
		if (sortingAscending.size() > idx) {
			return ((Boolean) sortingAscending.elementAt(idx)).booleanValue();
		} else {
			return true;
		}
	}

	/**
	 * Get sorting condition column number
	 **/
	public int getSortingColumn(int idx) {
		if (getSortingColumnsCount() <= idx) {
			return -1;
		}
		Integer obj = (Integer) sortingColumns.elementAt(idx);
		return obj.intValue();
	}

	/**
	 * Get sorting condition column name
	 **/
	public String getSortingColumnName(int idx) {
		if (getSortingColumnsCount() <= idx) {
			return null;
		}
		return (String) sortingNames.elementAt(idx);
	}

	/**
	 * Number of sorting conditions
	 * @deprecated use getSortingColumnsCount() instead
	 **/
	public int numSortingColumns() {
		return sortingColumns.size();
	}

	/**
	 * Number of sorting conditions
	 **/
	public int getSortingColumnsCount() {
		return sortingColumns.size();
	}

	/**
	 * Add next sorting cidition
	 **/
	public void addSortCondition(int column, boolean asc) {
		sortingColumns.add(new Integer(column));
		sortingAscending.add(new Boolean(asc));
		sortingNames.add(getColumnName(column));
	}

	/**
	 * Create dummy tree table sorter model
	 * Used only for keeping sorting model
	 **/
	public static SortableTreeTableModel createDummySorterModel() {
		return new SortableTreeTableModel() {

			protected Object[] getChildArray(Object node) {
				return null;
			}

			protected void setChildArray(Object node, Object[] children) {
			}

			public Object getValueAt(Object row, int columnIndex) {
				return null;
			}

			public Object getRoot() {
				return null;
			}

			public Object getChildAt(Object node, int index) {
				return null;
			}

			public Object getParent(Object node) {
				return null;
			}

			public int getChildCount(Object node) {
				return 0;
			}

			public boolean isLeaf(Object node) {
				return false;
			}

			public int getColumnCount() {
				return 0;
			}
		};
	}

	/**
	 * Copy sorting conditions from old sorting model
	 **/
	public void copySortCondition(SortableTreeTableModel old) {
		removeAllColumns();
		setIgnoreCase(old.getIgnoreCase());
		int size = old.numSortingColumns();
		if (getColumnCount() == 0) {
			// This is dummy sorter TreeTableModel
			for (int i = 0; i < size; i++) {
				sortingColumns.add(new Integer(old.getSortingColumn(i)));
				sortingAscending.add(new Boolean(old.isAscending(i)));
				sortingNames.add(old.getSortingColumnName(i));
			}
			return;
		}
		for (int i = 0; i < size; i++) {
			int col = old.getSortingColumn(i);
			String name = old.getSortingColumnName(i);
			if (col >= 0) {
				if (getColumnCount() > col && name.equals(getColumnName(col))) {
					addSortCondition(col, old.isAscending(i));
				} else {
					int sizeModel = getColumnCount();
					for (int j = 0; j < sizeModel; j++) {
						if (name.equals(getColumnName(j))) {
							addSortCondition(j, old.isAscending(i));
							break;
						}
					}
				}
			}
		}
	}

	public int compare(Object rowObj1, Object rowObj2) {
		//dbg compares++;
		for (int level = 0; level < sortingColumns.size(); level++) {
			Integer column = (Integer) sortingColumns.elementAt(level);
			Boolean asc = (Boolean) sortingAscending.elementAt(level);
			int result = compareRowsByColumn(rowObj1, rowObj2, column.intValue());
			if (result != 0) {
				return asc.booleanValue() ? result : -result;
			}
		}
		return 0;
	}

	public int compareRowsByColumn(Object rowObj1, Object rowObj2, int column) {
		Object o1 = getValueAt(rowObj1, column);
		Object o2 = getValueAt(rowObj2, column);
		return Sorter.compare(o1, o2, ignoreCase);
	}

	public void sortByColumn(int column) {
		sortByColumn(column, true);
	}

	public void sortByColumn(int column, boolean asc) {
		sortingColumns.clear();
		sortingAscending.clear();
		sortingNames.clear();
		sortingColumns.add(new Integer(column));
		sortingAscending.add(new Boolean(asc));
		sortingNames.add(getColumnName(column));
		sort();
	}

	public void sort() {
		sort(getRoot());
	}

	public void sort(Object node) {
		if (isLeaf(node)) {
			return;
		}
		Object[] children = getChildArray(node);
		shuttleSort((Object[]) children.clone(), children, 0, children.length);

		for (int i = 0; i < children.length; i++) {
			sort(children[i]);
		}
		setChildArray(node, children);
	}

	public void resort() {
		if (sortingColumns.size() == 0) {
			return;
		}
		sort();
	}

	/** gets array of given node's children -
	 *  need for sorting purposes */
	protected Object[] getChildArray(Object parent) {
		Object[] children = new Object[getChildCount(parent)];
		for (int i = 0; i < children.length; i++) {
			children[i] = getChildAt(parent, i);
		}
		return children;

	}

	/** sets given parent's children;
	 * it is guaranteed that there is just sorted children array,
	 * returned by previously called getChildArray().
	 *
	 * It's up to descendant to decide, whether to
	 * clear entire children list and create it again
	 * or replace children one by one
	 */
	protected abstract void setChildArray(Object parent, Object[] children);

	private void shuttleSort(Object[] from, Object[] to, int low, int high) {
		if (high - low < 2) {
			return;
		}

		int middle = (low + high) / 2;
		shuttleSort(to, from, low, middle);
		shuttleSort(to, from, middle, high);

		int p = low;
		int q = middle;

		/* This is an optional short-cut; at each recursive call,
		 check to see if the elements in this subset are already
		 ordered.  If so, no further comparisons are needed; the
		 sub-array can just be copied.  The array must be copied rather
		 than assigned otherwise sister calls in the recursion might
		 get out of sinc.  When the number of elements is three they
		 are partitioned so that the first set, [low, mid), has one
		 element and and the second, [mid, high), has two. We skip the
		 optimisation when the number of elements is three or less as
		 the first compare in the normal merge will produce the same
		 sequence of steps. This optimisation seems to be worthwhile
		 for partially ordered lists but some analysis is needed to
		 find out how the performance drops to Nlog(N) as the initial
		 order diminishes - it may drop very quickly. */

		if (high - low >= 4 && compare(from[middle - 1], from[middle]) <= 0) {
			for (int i = low; i < high; i++) {
				to[i] = from[i];
			}
			return;
		}

		// A normal merge.
		for (int i = low; i < high; i++) {
			if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
				to[i] = from[p++];
			} else {
				to[i] = from[q++];
			}
		}
	}

	/**
	 * Process click conditions
	 **/
	void rotateConditions(int column, boolean isShift) {
		int size = numSortingColumns();
		if (size == 0) {
			addSortCondition(column, true);
		} else {
			int from = -1;
			for (int i = 0; i < size; i++) {
				if (getSortingColumn(i) == column) {
					from = i;
					break;
				}
			}
			if (from == -1) {
				if (isShift) {
					// VK 11 jan 2002: let it be as much sorting columns as user wants,
					// regardless of the fact, that sorting dialog can't display them all
					// otherwise we refer from MODEL to some DIALOG 8-()
					//if (size < DlgSorting.CONDITIONS){
					addSortCondition(column, true);
					//}
				} else {
					removeAllColumns();
					addSortCondition(column, true);
				}
			} else {
				boolean ad = isAscending(from);
				if (isShift) {
					sortingAscending.set(from, new Boolean(!ad));
				} else {
					removeAllColumns();
					if (size == 1) {
						addSortCondition(column, !ad);
					} else {
						addSortCondition(column, ad);
					}
				}
			}
		}
	}
	
}
