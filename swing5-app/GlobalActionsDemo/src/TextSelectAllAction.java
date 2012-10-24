import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.text.JTextComponent;

import action.IBuiltinAction;

/**
 * @author idanilov
 * 
 */
public class TextSelectAllAction extends TextSelectionAction
		implements IBuiltinAction {

	private Action delegate;
	private static final String SELECT_ALL_ACTION_NAME = "select-all";

	public TextSelectAllAction(final JTextComponent t) {
		super(t);
		delegate = text.getActionMap().get(SELECT_ALL_ACTION_NAME);
		text.getActionMap().put(SELECT_ALL_ACTION_NAME, this); //replace default action.
		putValue(Action.NAME, "Select All");
		//putValue(Action.ACTION_COMMAND_KEY, SELECT_ALL_ACTION_NAME);
		setEnabled(false);
	}

	protected boolean canDo() {
		return (selection == null || selection.length() != text.getText().length());
	}

	public void actionPerformed(ActionEvent ae) {
		delegate.actionPerformed(new ActionEvent(text, ae.getID(), ae.getActionCommand(), ae
				.getWhen(), ae.getModifiers()));
	}

	public Action getBuiltinAction() {
		return delegate;
	}

	public String getBuiltinActionName() {
		return SELECT_ALL_ACTION_NAME;
	}

}
