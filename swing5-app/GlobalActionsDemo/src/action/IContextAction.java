package action;

import javax.swing.JComponent;

/**
 * Used to indicate actions that are context sensitive.
 * Used as redirector for some existing action that should be used with different invoker.
 * F.e. in Popup menus.
 * @author idanilov
 *
 */
public interface IContextAction {

	JComponent getContext();
}
