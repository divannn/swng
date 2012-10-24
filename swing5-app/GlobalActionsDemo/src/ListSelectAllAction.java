import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JList;

import action.IBuiltinAction;

/**
 * @author idanilov
 * 
 */
public class ListSelectAllAction extends ListSelectionAction
		implements IBuiltinAction {

	private Action delegate;
	private static final String SELECT_ALL_ACTION_NAME = "selectAll";

	public ListSelectAllAction(final JList l) {
		super(l);
		delegate = list.getActionMap().get(SELECT_ALL_ACTION_NAME);
		list.getActionMap().put(SELECT_ALL_ACTION_NAME, this); //replace default action.
		putValue(Action.NAME, "Select All");
		//putValue(Action.ACTION_COMMAND_KEY, SELECT_ALL_ACTION_NAME);
		setEnabled(false);
	}

	protected boolean canDo() {
		return (selection.length != list.getModel().getSize());
	}

	public void actionPerformed(ActionEvent ae) {
		delegate.actionPerformed(new ActionEvent(list, ae.getID(), ae.getActionCommand(), ae
				.getWhen(), ae.getModifiers()));
	}

	public Action getBuiltinAction() {
		return delegate;
	}

	public String getBuiltinActionName() {
		return SELECT_ALL_ACTION_NAME;
	}

}
