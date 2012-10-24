package clipboard;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * Supports notification about contents change.
 * 
 * @author idanilov
 * 
 */
public class AppTransferHandler extends TransferHandler {

	private ClipboardOwner clipboardOwner;
	private Vector<ClipboardListener> listeners;

	public AppTransferHandler(final ClipboardOwner clipboardOwner) {
		setClipboardOwner(clipboardOwner);
		listeners = new Vector<ClipboardListener>();
	}

	public void setClipboardOwner(final ClipboardOwner clipboardOwner) {
		this.clipboardOwner = clipboardOwner;
	}

	public void exportToClipboard(JComponent comp, Clipboard clip, int action) {
		int clipboardAction = getSourceActions(comp) & action;
		if (clipboardAction != NONE) {
			Transferable t = createTransferable(comp);
			if (t != null) {
				try {
					clip.setContents(t, clipboardOwner);
					fireCliboardContentsChanged(clip); //fire event if transfer suceesful.
					exportDone(comp, t, clipboardAction);
					return;
				} catch (IllegalStateException ise) {
					exportDone(comp, t, NONE);
					throw ise;
				}
			}
		}
		exportDone(comp, null, NONE);
	}

	public void addClipboardListener(final ClipboardListener cl) {
		listeners.addElement(cl);
	}

	public void removeClipboardListener(final ClipboardListener cl) {
		listeners.removeElement(cl);
	}

	protected void fireCliboardContentsChanged(final Clipboard clipboard) {
		if (listeners.size() > 0) {
			ClipboardEvent e = new ClipboardEvent(clipboard);
			Vector copyListeners = (Vector) listeners.clone();
			for (Object next : copyListeners) {
				((ClipboardListener) next).contentChanged(e);
			}
		}
	}

}