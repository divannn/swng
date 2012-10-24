package deferredcopypaste;

import java.awt.datatransfer.DataFlavor;

/**
 * @author idanilov
 * 
 */
public interface TransferNotifier {

	DataFlavor NOTIFICATION_FLAVOR = new DataFlavor(TransferNotifier.class, TransferNotifier.class
			.getName());

	void transferAccepted();

	void transferRejected();

}
