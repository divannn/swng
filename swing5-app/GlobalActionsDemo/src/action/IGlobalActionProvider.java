package action;

import javax.swing.Action;

/**
 * @author idanilov
 *
 */
public interface IGlobalActionProvider {

	String SELECT_ALL_ACTION_NAME = "selectAll";
	String CLEAR_ALL_ACTION_NAME = "clearAll";

	/**
	 * @param actionName
	 * @return local action that supposed to be a global action
	 */
	Action getAction(String actionName);

}
