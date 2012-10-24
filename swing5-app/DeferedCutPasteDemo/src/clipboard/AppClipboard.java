package clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Vector;

/**
 * Application clipboard. Supports contents notification.
 * Intended to be used as clipboard for application instead 
 * of system clipboard (Toolkit.getDefaultToolkit().getSystemClipboard()).
 * Cannot be used if clipboard operation base on <code>javax.swing.TransferHandler</code>>. 
 * @author idanilov
 * @deprecated
 */
public final class AppClipboard {

	private static AppClipboard instance;
	private Clipboard systemClipboard;
	private Vector<ClipboardListener> listeners;

	private AppClipboard() {
		systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	public static AppClipboard getInstance() {
		if (instance == null) {
			instance = new AppClipboard();
		}
		return instance;
	}

	public void setContents(Transferable contents, ClipboardOwner owner) {
		systemClipboard.setContents(contents, owner);
		fireCliboardContentsChanged(systemClipboard);
	}

	public Transferable getContents(final Object requestor) {
		return systemClipboard.getContents(requestor);
	}

	public Object getData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return systemClipboard.getData(flavor);
	}

	public boolean isDataFlavorAvailable(DataFlavor flavor) {
		return systemClipboard.isDataFlavorAvailable(flavor);
	}

	public DataFlavor[] getAvailableDataFlavors() {
		return systemClipboard.getAvailableDataFlavors();
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
			for (int i = 0; i < copyListeners.size(); i++) {
				((ClipboardListener) copyListeners.get(i)).contentChanged(e);
			}
		}
	}

}
