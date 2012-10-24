import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;

import action.IBuiltinAction;

/**
 * @author idanilov
 * 
 */
public class TreeSelectAllAction extends TreeSelectionAction
		implements IBuiltinAction {

	private Action delegate;
	private static final String SELECT_ALL_ACTION_NAME = "selectAll";

	public TreeSelectAllAction(final JTree t) {
		super(t);
		delegate = tree.getActionMap().get(SELECT_ALL_ACTION_NAME);
		tree.getActionMap().put(SELECT_ALL_ACTION_NAME, this); //replace default action.
		putValue(Action.NAME, "Select All");
		//putValue(Action.ACTION_COMMAND_KEY, SELECT_ALL_ACTION_NAME);
		setEnabled(false);
	}

	protected boolean canDo() {
		return (selection.length != tree.getRowCount());
	}

	public void actionPerformed(ActionEvent ae) {
		delegate.actionPerformed(new ActionEvent(tree, ae.getID(), ae.getActionCommand(), ae
				.getWhen(), ae.getModifiers()));
	}

	public Action getBuiltinAction() {
		return delegate;
	}

	public String getBuiltinActionName() {
		return SELECT_ALL_ACTION_NAME;
	}

}
