import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTable;

import action.IBuiltinAction;

/**
 * @author idanilov
 * 
 */
public class TableSelectAllAction extends TableSelectionAction
		implements IBuiltinAction {

	private Action delegate;
	private static final String SELECT_ALL_ACTION_NAME = "selectAll";

	public TableSelectAllAction(final JTable t) {
		super(t);
		delegate = table.getActionMap().get(SELECT_ALL_ACTION_NAME);
		table.getActionMap().put(SELECT_ALL_ACTION_NAME, this); //replace default action.
		putValue(Action.NAME, "Select All");
		//putValue(Action.ACTION_COMMAND_KEY, SELECT_ALL_ACTION_NAME);
		setEnabled(false);
	}

	protected boolean canDo() {
		return (selection.length != table.getModel().getRowCount());
	}

	public void actionPerformed(ActionEvent ae) {
		delegate.actionPerformed(new ActionEvent(table, ae.getID(), ae.getActionCommand(), ae
				.getWhen(), ae.getModifiers()));
	}

	public Action getBuiltinAction() {
		return delegate;
	}

	public String getBuiltinActionName() {
		return SELECT_ALL_ACTION_NAME;
	}

}
