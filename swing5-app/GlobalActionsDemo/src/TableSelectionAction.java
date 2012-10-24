import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import action.IContextAction;

/**
 * @author idanilov
 * 
 */
public abstract class TableSelectionAction extends AbstractAction
		implements ListSelectionListener, IContextAction {

	protected JTable table;
	protected int[] selection;

	public TableSelectionAction(final JTable t) {
		table = t;
		table.getSelectionModel().addListSelectionListener(this);
	}

	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			selection = table.getSelectedRows();
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
		return table;
	}

}
