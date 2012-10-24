package changeaware;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author idanilov
 */
public class ChangeAwareList extends JList
		implements IChangeAware {

	private boolean isChanged;
	private Object stateValue;
	private ListDataListener dataChangeListener;
	private PropertyChangeListener modelChangeListener;

	private static final Color NORMAL_COLOR = new JList().getBackground();

	public ChangeAwareList() {
		super();
		dataChangeListener = new ListDataListener() {

			public void intervalAdded(ListDataEvent lde) {
				computeState();
			}

			public void intervalRemoved(ListDataEvent lde) {
				computeState();
			}

			public void contentsChanged(ListDataEvent lde) {
				computeState();
			}

		};
		getModel().addListDataListener(dataChangeListener);

		modelChangeListener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent pce) {
				if ("model".equals(pce.getPropertyName())) {
					//System.err.println("*** list model changed");
					computeState();
				}
			}
		};
		addPropertyChangeListener(modelChangeListener);
	}

	public void setModel(final ListModel dataModel) {
		if (!(dataModel instanceof ClonableListModel)) {
			throw new IllegalArgumentException("List model must be "
					+ ClonableListModel.class.getName());
		}
		ListModel oldModel = getModel();
		if (oldModel != null) {
			oldModel.removeListDataListener(dataChangeListener);
		}
		super.setModel(dataModel);
		getModel().addListDataListener(dataChangeListener);
	}

	public boolean isStateChanged() {
		return isChanged;
	}

	public boolean isStateValid() {
		return true;
	}

	public void markState() {
		ClonableListModel currentModel = (ClonableListModel) getModel();
		stateValue = currentModel.cloneListModel();
		isChanged = false;
	}

	public void clearState() {
		stateValue = null;
		isChanged = false;
		refreshVisuals();
	}

	public void revertState() {
		if (isChanged) {
			setModel((ListModel) stateValue);
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
			isChanged = !compareListModel();
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	/*	private boolean compareListModel() {
	 ListModel currentModel = getModel();
	 ListModel savedModel = (ListModel)stateValue;
	 final int I = savedModel.getSize();
	 if (currentModel.getSize() != I) {
	 return false;
	 }
	 for (int i = 0; i < I; i++) {
	 boolean result = false;				
	 Object nextO1 = savedModel.getElementAt(i);
	 Object nextO2 = currentModel.getElementAt(i);
	 if ((nextO1 == null && nextO2 == null)
	 || (nextO1 != null && nextO1.equals(nextO2))) {
	 result = true;
	 } 
	 if (!result) {
	 return false;
	 }
	 }
	 return true;
	 }*/

	private boolean compareListModel() {
		ArrayList current = model2List(getModel());
		ArrayList stored = model2List((ListModel) stateValue);
		return current.equals(stored);
	}

	private static ArrayList model2List(final ListModel listModel) {
		ArrayList<Object> result = new ArrayList<Object>(listModel.getSize());
		for (int i = 0; i < listModel.getSize(); i++) {
			result.add(listModel.getElementAt(i));
		}
		return result;
	}

	private void refreshVisuals() {
		if (isChanged) {
			setBackground(IChangeAware.CHANGED_COLOR);
		} else {
			setBackground(NORMAL_COLOR);
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
	 * XXX : Maybe just use Cloneable.  
	 * @author idanilov
	 */
	public interface ClonableListModel {

		/**
		 * Must return the deep copy of each list model element.
		 * Each object must be Cloneable.   	 
		 */
		ListModel cloneListModel();
	}
}
