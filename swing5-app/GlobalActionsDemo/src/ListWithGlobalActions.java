import javax.swing.Action;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import action.IGlobalActionProvider;

/**
 * @author idanilov
 *
 */
class ListWithGlobalActions extends JList
		implements IGlobalActionProvider {

	private Action selectAllContextAction;
	private Action clearAllContextAction;

	public ListWithGlobalActions(final Object[] data) {
		super(data);
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
		selectAllContextAction = new ListSelectAllAction(this);
		clearAllContextAction = new ListClearAllAction(this);
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