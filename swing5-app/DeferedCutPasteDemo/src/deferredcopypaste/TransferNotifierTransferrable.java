package deferredcopypaste;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author idanilov
 * 
 */
public class TransferNotifierTransferrable
		implements Transferable, TransferNotifier {

	private Transferable delegate;
	private static final Transferable NULL_TRANSFERABLE = new NullTransferable();

	public TransferNotifierTransferrable(final Transferable delegate) {
		this.delegate = delegate;
	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor [] delegateFlavors = delegate.getTransferDataFlavors();
		DataFlavor [] flavors = new DataFlavor[delegateFlavors.length + 1];
		System.arraycopy(delegateFlavors, 0, flavors, 0, delegateFlavors.length);
		flavors[flavors.length - 1] = NOTIFICATION_FLAVOR;
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(NOTIFICATION_FLAVOR) || delegate.isDataFlavorSupported(flavor);
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return flavor.equals(NOTIFICATION_FLAVOR) ? this : delegate.getTransferData(flavor);
	}

	protected void clearClipboard() {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(NULL_TRANSFERABLE, null);
	}

	public void transferAccepted() {
		clearClipboard();
	}

	public void transferRejected() {
	}

	/**
	 * Used to indicate that clibpboad is empty.
	 * Another way - introduce speical NULL flavour.
	 * @author idanilov
	 *
	 */
	private static class NullTransferable
			implements Transferable {

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
				IOException {
			return null;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[0];
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return false;
		}

	}

}
