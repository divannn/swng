import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author idanilov
 *
 */
public class ItemTransferable
		implements Transferable {

	/*	public static DataFlavor ITEM_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
	 ";class=\"" + ItemTransferData.class.getName() + "\"",ItemTransferData.class.getSimpleName());*/
	public static final DataFlavor ITEM_FLAVOR = new DataFlavor(ItemTransferData.class,
			ItemTransferData.class.getSimpleName());

	private static final DataFlavor[] FLAVOURS = { ITEM_FLAVOR, DataFlavor.stringFlavor };
	private ItemTransferData data;

	public ItemTransferable(final ItemTransferData data) {
		this.data = data;
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (ITEM_FLAVOR.equals(flavor)) {
			return data;
		} else if (DataFlavor.stringFlavor.equals(flavor)) {
			return data.getString();
		} else {
			throw new UnsupportedFlavorException(flavor);
		}

	}

	public DataFlavor[] getTransferDataFlavors() {
		return FLAVOURS;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return ITEM_FLAVOR.equals(flavor) || DataFlavor.stringFlavor.equals(flavor);
	}

}
