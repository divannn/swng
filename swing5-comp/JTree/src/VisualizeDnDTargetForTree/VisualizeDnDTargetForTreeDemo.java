package VisualizeDnDTargetForTree;

import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * @author santosh
 * @jdk 1.5
 */
public class VisualizeDnDTargetForTreeDemo extends JFrame {

	public VisualizeDnDTargetForTreeDemo() {
		super(VisualizeDnDTargetForTreeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(1, 2));
		JTree sourceTree = new JTree();
		sourceTree.setDragEnabled(true);
		result.add(new JScrollPane(sourceTree));
		JTree targetTree = new JTree();
		new DropTarget(targetTree, new SampleDropListener());
		result.add(new JScrollPane(targetTree));
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new VisualizeDnDTargetForTreeDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class SampleDropListener extends TreeDropListener {

		public void drop(DropTargetDropEvent dtde) {
			super.drop(dtde);
			if (treePath == null) {
				dtde.rejectDrop();
				return;
			}
			JTree tree = (JTree) dtde.getDropTargetContext().getComponent();
			DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
			try {
				String data = (String) dtde.getTransferable().getTransferData(
						DataFlavor.stringFlavor);
				DefaultMutableTreeNode dragNode = new DefaultMutableTreeNode(data);
				DefaultMutableTreeNode dropNode = (DefaultMutableTreeNode) treePath
						.getLastPathComponent();
				DefaultMutableTreeNode dropParent = (DefaultMutableTreeNode) dropNode.getParent();
				if (dropParent == null) {
					dtde.rejectDrop();
					return;
				}
				int dropIndex = treeModel.getIndexOfChild(dropParent, dropNode);
				if (before == null) {
					treeModel.removeNodeFromParent(dropNode);
					treeModel.insertNodeInto(dragNode, dropParent, dropIndex);
				} else if (before.equals(Boolean.TRUE)) {
					treeModel.insertNodeInto(dragNode, dropParent, dropIndex);
				} else {
					if (dropIndex < dropParent.getChildCount()) {
						treeModel.insertNodeInto(dragNode, dropParent, dropIndex + 1);
					} else {
						dropParent.add(dragNode);
						treeModel.nodesWereInserted(dropParent, new int[dropIndex + 1]);
					}
				}
				tree.setSelectionPath(new TreePath(dragNode.getPath()));
				dtde.acceptDrop(DnDConstants.ACTION_COPY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}