package action;

import javax.swing.Action;

/**
 * Used to indicate actions that are build in for some component.
 * F.e. "selectAll" action in JList or JTable.
 * Used by wrappers in order to not to duplicate code.
 * @author idanilov
 *
 */
public interface IBuiltinAction {

	String getBuiltinActionName();

	Action getBuiltinAction();
}
