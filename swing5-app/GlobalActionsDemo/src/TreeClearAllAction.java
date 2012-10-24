import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTree;

import action.IBuiltinAction;

/**
 * @author idanilov
 * 
 */
public class TreeClearAllAction extends TreeSelectionAction
		implements IBuiltinAction {

	private Action delegate;
	private static final String CLEAR_ALL_ACTION_NAME = "clearSelection";

	public TreeClearAllAction(final JTree t) {
		super(t);
		delegate = tree.getActionMap().get(CLEAR_ALL_ACTION_NAME);
		tree.getActionMap().put(CLEAR_ALL_ACTION_NAME, this); //replace default action.
		putValue(Action.NAME, "Clear All");
		//putValue(Action.ACTION_COMMAND_KEY, CLEAR_ALL_ACTION_NAME);
		setEnabled(false);
	}

	protected boolean canDo() {
		return selection.length != 0;
	}

	public void actionPerformed(ActionEvent ae) {
		delegate.actionPerformed(new ActionEvent(tree, ae.getID(), ae.getActionCommand(), ae
				.getWhen(), ae.getModifiers()));
	}

	public Action getBuiltinAction() {
		return delegate;
	}

	public String getBuiltinActionName() {
		return CLEAR_ALL_ACTION_NAME;
	}

}
