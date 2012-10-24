import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;

import clipboard.AppTransferHandler;
import deferredcopypaste.TransferNotifier;

/**
 * @author idanilov
 * 
 */
public class ListTransferHandler extends AppTransferHandler {

	public ListTransferHandler(final ClipboardOwner clipboardOwner) {
		super(clipboardOwner);
	}

	protected Transferable createTransferable(JComponent c) {
		ListItemSelection result = null;
		JList list = (JList) c;
		if (list.getSelectedIndex() >= 0) {
			result = new ListItemSelection(list);
		}
		return result;
	}

	public void exportToClipboard(JComponent comp, Clipboard clip, int action) {
		setClipboardOwner(new ListClipboardOwner((JList) comp));
		super.exportToClipboard(comp, clip, action);
	}

	public int getSourceActions(JComponent c) {
		return MOVE;
	}

	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		for (int i = 0; i < transferFlavors.length; i++) {
			if (transferFlavors[i].equals(ItemTransferable.ITEM_FLAVOR)) {
				return true;
			}
		}
		return false;
	}

	public static boolean canImport(final Object data) {
		return (data instanceof ItemTransferData);
	}

	public boolean importData(JComponent comp, Transferable t) {
		try {
			Object data = t.getTransferData(ItemTransferable.ITEM_FLAVOR);
			if (canImport(data)) {
				JList list = (JList) comp;
				DefaultListModel dlm = (DefaultListModel) list.getModel();
				//add item to the end.
				ItemTransferData itemData = (ItemTransferData) data;
				dlm.addElement(itemData.getString());
				list.setSelectedIndex(dlm.getSize() - 1);
				acceptOrReject(t, true);
				return true;
			}
			return false;
		} catch (Exception e) {
			acceptOrReject(t, false);
			return false;
		}
	}

	private void acceptOrReject(Transferable t, boolean accept) {
		try {
			if (t.isDataFlavorSupported(TransferNotifier.NOTIFICATION_FLAVOR)) {
				TransferNotifier notifier = (TransferNotifier) t
						.getTransferData(TransferNotifier.NOTIFICATION_FLAVOR);
				if (accept) {
					notifier.transferAccepted();
				} else {
					notifier.transferRejected();
				}
			}
		} catch (UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
