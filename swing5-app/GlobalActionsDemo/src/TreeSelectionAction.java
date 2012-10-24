import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * @author idanilov
 * 
 */
public abstract class TreeSelectionAction extends AbstractAction
		implements TreeSelectionListener {

	protected JTree tree;
	protected TreePath[] selection;

	public TreeSelectionAction(final JTree t) {
		tree = t;
		tree.getSelectionModel().addTreeSelectionListener(this);
	}

	public void valueChanged(TreeSelectionEvent tse) {
		selection = tree.getSelectionPaths();
		if (selection == null) {
			selection = new TreePath[0];
		}
		update();
	}

	protected void update() {
		setEnabled(canDo());
	}

	protected boolean canDo() {
		return false;
	}

}
