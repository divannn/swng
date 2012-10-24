package changeaware;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 * Differs from {@link ChangeAwareTable} in that :
 * <br>
 * 1. Each cell value change ñan be higlighted.
 * <br>
 * 2. Each cell value can be checked for invalidness and ñan be higlighted.
 * <br>
 * <strong>Restrictions:</strong>
 * <br>
 * 1. Rows and columns selection is disabled because selection background prevents eeing changed cells.
 * <br>
 * 2. Rows addition/removal and columns addition/removal are not supported. Only cells changes are processed. 
 * @author idanilov
 */
public class ChangeAwareTable2 extends JTable
		implements IChangeAware {

	private Object stateValue;
	private TableModelListener dataChangeListener;
	private PropertyChangeListener modelChangeListener;

	/**
	 * Stores (row,column) object for cells that are changed.
	 */
	private HashSet<Pair> changedCellsCache;

	/**
	 * Stores (row,column) object for cells that are invaliâ.
	 */
	private HashSet<Pair> invalidCellsCache;

	public ChangeAwareTable2(final TableModel model) {
		super(model);
		changedCellsCache = new HashSet<Pair>();
		invalidCellsCache = new HashSet<Pair>();
		dataChangeListener = new TableModelListener() {

			public void tableChanged(TableModelEvent tme) {
				//System.err.println("=== table changed");
				computeState();
			}
		};
		modelChangeListener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent pce) {
				if ("model".equals(pce.getPropertyName())) {
					//System.err.println("*** table model changed");
					computeState();
				}
			}
		};
		addPropertyChangeListener(modelChangeListener);
		setDefaultRenderer(Object.class, new ChangeAwareTableRenderer());
		setModel(model);
		putClientProperty(IChangeAware.SHOW_CHANGE, Boolean.TRUE);
		putClientProperty(IChangeAware.SHOW_INCORRECTNESS, Boolean.TRUE);

		//disable row selection (column selection is disabled by default).
		setRowSelectionAllowed(false);
		//do not start editor automatically.
		putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
		//terminate editor when table loses focus.
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}

	public void setModel(final TableModel dataModel) {
		if (!(dataModel instanceof ClonableTableModel)) {
			throw new IllegalArgumentException("Table model must be "
					+ ClonableTableModel.class.getName());
		}
		TableModel oldModel = getModel();
		if (oldModel != null) {
			oldModel.removeTableModelListener(dataChangeListener);
		}
		super.setModel(dataModel);
		getModel().addTableModelListener(dataChangeListener);
	}

	public boolean isStateChanged() {
		return !changedCellsCache.isEmpty();
	}

	public boolean isStateChanged(final int r, final int c) {
		Pair p = new Pair(r, c);
		return changedCellsCache.contains(p);
	}

	public boolean isStateValid(final int r, final int c) {
		Pair p = new Pair(r, c);
		return !invalidCellsCache.contains(p);
	}

	public boolean isStateValid() {
		return invalidCellsCache.isEmpty();
	}

	public void markState() {
		//System.err.println("--- MARK");
		ClonableTableModel currentModel = (ClonableTableModel) getModel();
		stateValue = currentModel.cloneTableModel();
		changedCellsCache.clear();
	}

	public void clearState() {
		//System.err.println("--- CLEAR");
		stateValue = null;
		changedCellsCache.clear();
		invalidCellsCache.clear();
		refreshVisuals();
	}

	public void revertState() {
		if (isStateChanged()) {
			//System.err.println("--- REVERT");
			setModel((TableModel) stateValue);
		}
	}

	private void refreshVisuals() {
		//renderer will do the job.
		repaint();
	}

	/**
	 * Should be called from TableCellRenderer to higlight changed/invalid cells.
	 */
	protected void refreshCellVisuals(final Component rendererComp, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (!isSelected) {
			boolean isShowChanges = this.getClientProperty(IChangeAware.SHOW_CHANGE) != null;
			boolean isShowInvalidness = this.getClientProperty(IChangeAware.SHOW_INCORRECTNESS) != null;

			Color normalColor = this.getBackground();
			if (this.isStateValid(row, column)) {
				if (isShowChanges) {
					rendererComp
							.setBackground(this.isStateChanged(row, column) ? IChangeAware.CHANGED_COLOR
									: normalColor);
				} else {
					if (isShowInvalidness) {
						rendererComp.setBackground(normalColor);
					}
				}
			} else {
				if (isShowInvalidness) {
					rendererComp.setBackground(IChangeAware.INVALID_COLOR);
				}
			}
		}
	}

	public void addStateChangeListener(final IStateChangeListener scl) {
		if (scl != null) {
			listenerList.add(IStateChangeListener.class, scl);
		}
	}

	public void removeStateChangeListener(final IStateChangeListener scl) {
		if (scl != null) {
			listenerList.remove(IStateChangeListener.class, scl);
		}
	}

	private void computeState() {
		if (stateValue != null) {
			compareTableModel();
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	/**
	 * Override to check cell.
	 * @param row
	 * @param col
	 * @return
	 */
	protected boolean checkValidity(final int row, final int col) {
		return true;
	}

	private void compareTableModel() {
		TableModel currentModel = getModel();
		TableModel savedModel = (TableModel) stateValue;
		final int rowCount = savedModel.getRowCount();
		final int colCount = savedModel.getColumnCount();
		if ((currentModel.getRowCount() == rowCount) && (currentModel.getColumnCount() == colCount)) {
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					Object nextO1 = savedModel.getValueAt(i, j);
					Object nextO2 = currentModel.getValueAt(i, j);
					Pair nextPair = new Pair(i, j);
					//change.
					if ((nextO1 == null && nextO2 != null)
							|| (nextO1 != null && !nextO1.equals(nextO2))) {
						changedCellsCache.add(nextPair);
					} else {
						changedCellsCache.remove(nextPair);
					}
					//validity.
					if (checkValidity(i, j)) {
						invalidCellsCache.remove(nextPair);
					} else {
						invalidCellsCache.add(nextPair);
					}
				}
			}
		} else {
			//unsupported change - rows added/removed.
			//TODO: what to do here???
		}
	}

	protected void fireStateChangeEvent(final StateChangeEvent sce) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStateChangeListener.class) {
				IStateChangeListener nextListener = (IStateChangeListener) (listeners[i + 1]);
				nextListener.stateChanged(sce);
			}
		}
	}

	/**
	 * Default editor.
	 * @author idanilov
	 *
	 */
	public static class ChangeAwareTableRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			Component result = super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			ChangeAwareTable2 cat2 = (ChangeAwareTable2) table;
			cat2.refreshCellVisuals(result, value, isSelected, hasFocus, row, column);
			return result;
		}

	}

	private static class Pair {

		private int row;
		private int col;

		Pair(int r, int c) {
			this.row = r;
			this.col = c;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair other = (Pair) obj;
				return (other.row == row && other.col == col);
			}
			return false;
		}

		public int hashCode() {
			return row * col;
		}

	}

}
