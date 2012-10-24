import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import action.IGlobalActionProvider;

/**
 * @author idanilov
 *
 */
class TextAreaWithGlobalActions extends JTextArea
		implements IGlobalActionProvider {

	private Action selectAllContextAction;
	private Action clearAllContextAction;

	public TextAreaWithGlobalActions(final String text) {
		super(text);
		createActions();
		setComponentPopupMenu(createPopup());
	}

	public Action getAction(final String actionName) {
		Action result = null;
		if (IGlobalActionProvider.SELECT_ALL_ACTION_NAME.equals(actionName)) {
			result = selectAllContextAction;
		} else if (IGlobalActionProvider.CLEAR_ALL_ACTION_NAME.equals(actionName)) {
			result = clearAllContextAction;
		}
		return result;
	}

	private void createActions() {
		selectAllContextAction = new TextSelectAllAction(this);
		clearAllContextAction = new TextClearAllAction(this);
	}

	private JPopupMenu createPopup() {
		JPopupMenu result = new JPopupMenu();
		JMenuItem selectAllItem = new JMenuItem(selectAllContextAction);
		result.add(selectAllItem);
		JMenuItem clearAllItem = new JMenuItem(clearAllContextAction);
		result.add(clearAllItem);
		return result;
	}

}