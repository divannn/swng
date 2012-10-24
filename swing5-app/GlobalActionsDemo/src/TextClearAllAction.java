import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.text.JTextComponent;

import action.IBuiltinAction;

/**
 * @author idanilov
 * 
 */
public class TextClearAllAction extends TextSelectionAction
		implements IBuiltinAction {

	private Action delegate;
	private static final String CLEAR_ALL_ACTION_NAME = "unselect";

	public TextClearAllAction(final JTextComponent t) {
		super(t);
		delegate = text.getActionMap().get(CLEAR_ALL_ACTION_NAME);
		text.getActionMap().put(CLEAR_ALL_ACTION_NAME, this); //replace default action.
		putValue(Action.NAME, "Clear All");
		//putValue(Action.ACTION_COMMAND_KEY, CLEAR_ALL_ACTION_NAME);
		setEnabled(false);
	}

	protected boolean canDo() {
		return (selection != null && selection.length() != 0);
	}

	public void actionPerformed(ActionEvent ae) {
		delegate.actionPerformed(new ActionEvent(text, ae.getID(), ae.getActionCommand(), ae
				.getWhen(), ae.getModifiers()));
	}

	public Action getBuiltinAction() {
		return delegate;
	}

	public String getBuiltinActionName() {
		return CLEAR_ALL_ACTION_NAME;
	}

}
