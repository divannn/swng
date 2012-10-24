package TreeTable;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Just allows to set all chidren.
 * @author idanilov
 *
 */
public class DefaultMutableTreeNodeEx extends DefaultMutableTreeNode {

	public DefaultMutableTreeNodeEx() {
		super();
	}

	public DefaultMutableTreeNodeEx(final Object userObject) {
		super(userObject);
	}

	public void setChildren(final Vector v) {
		children = v;
	}

}