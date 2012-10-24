import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import action.IContextAction;

/**
 * @author idanilov
 * 
 */
public abstract class ListSelectionAction extends AbstractAction
		implements ListSelectionListener, IContextAction {

	protected JList list;
	protected int[] selection;

	public ListSelectionAction(final JList l) {
		list = l;
		list.getSelectionModel().addListSelectionListener(this);
	}

	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			selection = list.getSelectedIndices();
			update();
		}
	}

	protected void update() {
		setEnabled(canDo());
	}

	protected boolean canDo() {
		return false;
	}

	public JComponent getContext() {
		return list;
	}

}
