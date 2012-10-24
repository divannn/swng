package changeaware;

import java.awt.Color;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Higlights whole table and its viewport when somthing in table changed.
 * @author idanilov
 */
public class ChangeAwareTable extends JTable
		implements IChangeAware {

	private boolean isChanged;
	private Object stateValue;
	private TableModelListener dataChangeListener;
	private PropertyChangeListener modelChangeListener;

	private static final Color NORMAL_COLOR = new JTable().getBackground();

	public ChangeAwareTable(final TableModel model) {
		super(model);
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
		setModel(model);
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
		return isChanged;
	}

	public boolean isStateValid() {
		return true;
	}

	public void markState() {
		//System.err.println("--- MARK");
		ClonableTableModel currentModel = (ClonableTableModel) getModel();
		stateValue = currentModel.cloneTableModel();
		isChanged = false;
	}

	public void clearState() {
		//System.err.println("--- CLEAR");
		stateValue = null;
		isChanged = false;
		refreshVisuals();
	}

	public void revertState() {
		if (isChanged) {
			//System.err.println("--- REVERT");
			setModel((TableModel) stateValue);
		}
	}

	private void refreshVisuals() {
		Container parent = getParent();
		if (isChanged) {
			setBackground(IChangeAware.CHANGED_COLOR);
			//update viewport's background also - to visualize removed rows.
			if (parent instanceof JViewport) {
				parent.setBackground(IChangeAware.CHANGED_COLOR);
			}

		} else {
			setBackground(NORMAL_COLOR);
			//update viewport's background also - to visualize removed rows.
			if (parent instanceof JViewport) {
				parent.setBackground(NORMAL_COLOR);
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
			isChanged = !compareTableModel();
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	/**
	 * @return false if models are not equal
	 */
	private boolean compareTableModel() {
		TableModel currentModel = getModel();
		TableModel savedModel = (TableModel) stateValue;
		final int rowCount = savedModel.getRowCount();
		final int colCount = savedModel.getColumnCount();
		if ((currentModel.getRowCount() == rowCount) && (currentModel.getColumnCount() == colCount)) {
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					Object nextO1 = savedModel.getValueAt(i, j);
					Object nextO2 = currentModel.getValueAt(i, j);
					if ((nextO1 == null && nextO2 != null)
							|| (nextO1 != null && !nextO1.equals(nextO2))) {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return true;
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
}
