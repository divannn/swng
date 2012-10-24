package DisabledTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author idanilov
 *
 */
public class DefaultDisabledNode extends DefaultMutableTreeNode
		implements DisabledNode {
	
	boolean isEnabled;
	
	public DefaultDisabledNode(final Object value) {
		super(value);
		this.isEnabled = true;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(final boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}