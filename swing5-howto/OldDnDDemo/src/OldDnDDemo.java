import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreeNode;

/**
 * Old way to provide DnD functionality.
 * Note: Neither {@link TransferHandler} nor {@link JTree#setDragEnabled(boolean)} are not used.
 * @author idanilov
 * @jdk 1.5
 */
public class OldDnDDemo extends JFrame {

	public OldDnDDemo() {
		super(OldDnDDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createDragSourcePanel(), BorderLayout.CENTER);
		result.add(createDropPanel(), BorderLayout.SOUTH);
		return result;
	}

	private JComponent createDragSourcePanel() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Drag source:"), BorderLayout.NORTH);
		JTree tree = new DragTree();
		result.add(new JScrollPane(tree), BorderLayout.CENTER);
		return result;
	}
	
	private JComponent createDropPanel() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Drop target:"), BorderLayout.NORTH);
		result.add(new DropTextField(), BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new OldDnDDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class DragTree extends JTree
			implements DragGestureListener, DragSourceListener {

		public DragTree() {
			DragSource ds = DragSource.getDefaultDragSource();
			ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}

		public void dragGestureRecognized(DragGestureEvent dge) {
			TreeNode firstSelected = (TreeNode)getLastSelectedPathComponent();
			Transferable t = new StringSelection(firstSelected.toString());
			dge.startDrag(DragSource.DefaultCopyDrop, t, this);
		}

		public void dragDropEnd(DragSourceDropEvent dsde) {
		}

		public void dragEnter(DragSourceDragEvent dsde) {
		}

		public void dragExit(DragSourceEvent dse) {
		}

		public void dragOver(DragSourceDragEvent dsde) {
		}

		public void dropActionChanged(DragSourceDragEvent dsde) {
		}

	}

	private class DropTextField extends JTextField
			implements DropTargetListener {

		
		public DropTextField() {
			new DropTarget(this,DnDConstants.ACTION_COPY_OR_MOVE,this);
		}
		
		public void dragEnter(DropTargetDragEvent dtde) {
		}

		public void dragExit(DropTargetEvent dte) {
		}

		public void dragOver(DropTargetDragEvent dtde) {
		}

		public void drop(DropTargetDropEvent dtde) {
			try {
				DataFlavor stringFlavor = DataFlavor.stringFlavor;
				Transferable t = dtde.getTransferable();
				if (dtde.isDataFlavorSupported(stringFlavor)) {
					String text = (String)t.getTransferData(stringFlavor);
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					setText(text);
					dtde.dropComplete(true);
				} else {
					dtde.rejectDrop();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (UnsupportedFlavorException ufe) {
				ufe.printStackTrace();
			} 
		}

		public void dropActionChanged(DropTargetDragEvent dtde) {
			
		}

	}
}
