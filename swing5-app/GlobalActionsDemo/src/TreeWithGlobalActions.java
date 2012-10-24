import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import action.IGlobalActionProvider;

/**
 * @author idanilov
 *
 */
class TreeWithGlobalActions extends JTree
		implements IGlobalActionProvider {

	private Action selectAllContextAction;
	private Action clearAllContextAction;

	public TreeWithGlobalActions() {
		super();
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
		selectAllContextAction = new TreeSelectAllAction(this);
		clearAllContextAction = new TreeClearAllAction(this);
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