import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JList;

import action.IBuiltinAction;

/**
 * @author idanilov
 * 
 */
public class ListClearAllAction extends ListSelectionAction
		implements IBuiltinAction {

	private Action delegate;
	private static final String CLEAR_ALL_ACTION_NAME = "clearSelection";

	public ListClearAllAction(final JList l) {
		super(l);
		delegate = list.getActionMap().get(CLEAR_ALL_ACTION_NAME);
		list.getActionMap().put(CLEAR_ALL_ACTION_NAME, this); //replace default action.
		putValue(Action.NAME, "Clear All");
		//putValue(Action.ACTION_COMMAND_KEY, CLEAR_ALL_ACTION_NAME);
		setEnabled(false);
	}

	protected boolean canDo() {
		return selection.length != 0;
	}

	public void actionPerformed(ActionEvent ae) {
		delegate.actionPerformed(new ActionEvent(list, ae.getID(), ae.getActionCommand(), ae
				.getWhen(), ae.getModifiers()));
	}

	public Action getBuiltinAction() {
		return delegate;
	}

	public String getBuiltinActionName() {
		return CLEAR_ALL_ACTION_NAME;
	}

}
