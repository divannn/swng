package TreeTable.treetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import org.omg.CORBA.IntHolder;

import TreeTable.treetable.util.ArrayIterator;


/**
 * @author idanilov
 *
 */
public abstract class AbstractTreeTableModel extends AbstractTableModel
		implements TreeTableModel {

	/*
	 * Data
	 */
	TreeExpansionModel xmodel = null;

	// NB: NEVER assign to this variable!!!
	// use setRowMapper method instead
	private RowMapper rowMapper;
	
	public AbstractTreeTableModel() {
		setRowMapper(new DefaultRowMapper());
		xmodel = new DefaultTreeExpansionModel(this);
	}

	public AbstractTreeTableModel(TreeExpansionModel xmodel) {
		this();
		this.xmodel = xmodel;
	}

	public TreeExpansionModel getExpansionModel() {
		return xmodel;
	}

	public void setExpansionModel(TreeExpansionModel xmodel) {
		this.xmodel = xmodel;
	}

	/**
	 * Gets iterator to iterate through table row objects
	 * @param expand determines whether item and its children 
	 */
	public Iterator getIterator(boolean expand) {
		return new TreeTableModelIterator(this, expand);
	}

	/**
	 * Gets iterator, which will iterate through
	 * given node and its children.
	 * @param expand determines whether item and its children 
	 * should be expanded or not
	 * @param next object whose children should be iterated
	 */
	public Iterator getIterator(boolean expand, Object next) {
		return new TreeTableModelIterator(this, expand, next);
	}

	/**
	 * Gets iterator to iterate through a set of table row objects -
	 * as a rule, used for iterating through selection.
	 * @param expand determines whether item and its children
	 * @param rows rows to iterate through
	 * or through the entire table
	 */
	public Iterator getIterator(boolean expand, Object[] rows) {
		if (expand) {
			ArrayList selectedList = new ArrayList();
			HashSet selectedSet = new HashSet();
			for (int i = 0; i < rows.length; i++) {
				if (!selectedSet.contains(rows[i])) {
					selectedList.add(rows[i]);
					selectedSet.add(rows[i]);
				}
				Iterator it = new TreeTableModelIterator(this, expand, rows[i]);
				while (it.hasNext()) {
					Object o = it.next();
					if (!selectedSet.contains(o)) {
						selectedList.add(o);
						selectedSet.add(o);
					}
				}
			}
			return selectedList.iterator();
		}
		return new ArrayIterator(rows);
	}

	/*
	 * Methods analogous to those of TableModel with one difference:
	 * they are using Object (instead of int row index) to identify row
	 */
	public abstract Object getValueAt(Object row, int columnIndex);

	public boolean isCellEditable(Object row, int columnIndex) {
		return false;
	}

	public void setValueAt(Object aValue, Object row, int columnIndex) {
	}

	/*
	 * Methods similar to those of TreeModel
	 */
	abstract public Object getRoot();

	abstract public Object getChildAt(Object node, int index);

	abstract public Object getParent(Object node);

	abstract public int getChildCount(Object node);

	abstract public boolean isLeaf(Object node);

	public int getNodeLevel(Object node) {
		int level = 0;
		Object parent = getParent(node);
		while (parent != null) {
			level++;
			parent = getParent(parent);
		}
		return level;
	}

	protected boolean rootVisible = true;

	public boolean isRootVisible() {
		return rootVisible;
	}

	public void setRootVisible(boolean visible) {
		rootVisible = visible;
		fireTableChanged();
	}

	/*
	 * Methods mapping TableModel calls (row-number-based in row-object-based ones)
	 */
	public int getRowCount() {
		return rowMapper.getRowCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return getValueAt(rowMapper.mapRowToObject(rowIndex), columnIndex);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		setValueAt(aValue, rowMapper.mapRowToObject(rowIndex), columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return isCellEditable(rowMapper.mapRowToObject(rowIndex), columnIndex);
	}

	public RowMapper getRowMapper() {
		return rowMapper;
	}

	public void setRowMapper(RowMapper newRowMapper) {
		if (rowMapper != null) {
			removeTableModelListener(rowMapper);
		}
		rowMapper = newRowMapper;
		addTableModelListener(rowMapper);
		fireTableChanged();
	}

	public void fireTableChanged() {
		fireTableChanged(new TableModelEvent(this));
	}

	/*
	 * Just for convenience
	 */
	public Object getRowObject(int rowIdx) {
		return getRowMapper().mapRowToObject(rowIdx);
	}

	public void expandRoot() {
		getExpansionModel().expand(getRoot());
		//fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
	}

	/** dump - for debugging purposes only */
	public void dump(String title) {
		dump(getRoot(), true);
	}

	/** dump - for debugging purposes only */
	private void dump(Object node, boolean isRoot) {
		//Diagnostic.trace("" + node + (isRoot ? " visible=" + isRootVisible() : ""));
		//Diagnostic.indent();
		for (int i = 0; i < getChildCount(node); i++) {
			dump(getChildAt(node, i), false);
		}
		//Diagnostic.unindent();
	}

	private class DefaultRowMapper
			implements RowMapper {

		// lastRow, rowCount and lastObject are just cache allowing us
		// not to dig through the tree every time
		private int lastRow = -1;
		private Object lastObject = null;
		private int rowCount = -1;

		private long mapRowCacheHits = 0;
		private long mapRowTotalCalls = 0;
		private long rowCountCacheHits = 0;
		private long rowCountTotalCalls = 0;

		private final boolean useCache = true;

		public DefaultRowMapper() {
			//TreeTableModel.this.addTableModelListener(this);
		}

		public void tableChanged(TableModelEvent ev) {
			// cleaning cache
			lastRow = -1;
			rowCount = -1;
			lastObject = null;
		}

		public Object mapRowToObject(int row) {

			mapRowTotalCalls++;
			/*if( mapRowTotalCalls % 1000 == 0 ) {
			 Diagnostic.trace("mapRow cache hits=" + mapRowCacheHits + " total calls=" + mapRowTotalCalls
			 + " ratio=" + (mapRowCacheHits * 100 / mapRowTotalCalls) + "%");
			 mapRowTotalCalls = mapRowCacheHits = 0;
			 }*/

			if (useCache) {
				if (lastRow == row) {
					mapRowCacheHits++;
					return lastObject; // checking lastRow >= 0 is unnecessary
				}
			}

			Object rowObj;

			Object root = getRoot();
			if (row == 0 && isRootVisible()) {
				rowObj = root;
			} else {
				IntHolder current = new IntHolder(isRootVisible() ? 1 : 0);
				rowObj = findInVisibleChildren(root, row, current);
			}

			// fill cache
			lastRow = row;
			lastObject = rowObj;

			return rowObj;
		}

		protected Object findInVisibleChildren(Object node, int row, IntHolder current) {

			// here current has the value of the NEXT row
			if (getExpansionModel().isExpanded(node)) {
				int cnt = getChildCount(node);
				for (int i = 0; i < cnt; i++) {
					Object child = getChildAt(node, i);
					if (current.value == row) {
						return child;
					} else {
						current.value++;
						Object obj = findInVisibleChildren(child, row, current);
						if (obj != null) {
							return obj;
						}
					}
				}
			}
			return null;
		}

		public int getRowCount() {

			rowCountTotalCalls++;
			/*if( rowCountTotalCalls % 1000 == 0 ) {
			 Diagnostic.trace("rowCount cache hits=" + rowCountCacheHits + " total calls=" + rowCountTotalCalls
			 + " ratio=" + (rowCountCacheHits * 100 / rowCountTotalCalls) + "%");
			 rowCountCacheHits = 0;
			 rowCountTotalCalls = 0;
			 }*/

			if (useCache) {
				if (rowCount >= 0) {
					rowCountCacheHits++;
					return rowCount;
				}
			}
			int cnt = isRootVisible() ? 1 : 0;
			cnt += countVisibleChildren(getRoot());
			rowCount = cnt; // fill cache
			return cnt;
		}

		protected int countVisibleChildren(Object node) {
			if (getExpansionModel().isExpanded(node)) {
				int cnt = getChildCount(node);
				int res = cnt;
				for (int i = 0; i < cnt; i++) {
					res += countVisibleChildren(getChildAt(node, i));
				}
				return res;
			}
			return 0;
		}

		public int mapObjectToRow(Object rowObj) {

			if (useCache) {
				if (lastObject == rowObj) {
					return lastRow;
				}
			}

			int row;

			Object root = getRoot();
			if (rowObj == root) {
				row = isRootVisible() ? 0 : -1;
			} else {
				IntHolder current = new IntHolder(isRootVisible() ? 1 : 0);
				if (findRowByObject(root, rowObj, current)) {
					row = current.value;
				} else {
					row = -1;
				}
			}

			// fill cache
			lastRow = row;
			lastObject = rowObj;

			return row;
		}

		private boolean findRowByObject(Object node, Object rowObjToFind, IntHolder current) {

			// here current has the value of the NEXT row
			if (getExpansionModel().isExpanded(node)) {
				int cnt = getChildCount(node);
				for (int i = 0; i < cnt; i++) {
					Object child = getChildAt(node, i);
					if (child == rowObjToFind) {
						return true;
					} else {
						current.value++;
						if (findRowByObject(child, rowObjToFind, current)) {
							return true;
						}
					}
				}
			}
			return false;
		}

	}
	
}