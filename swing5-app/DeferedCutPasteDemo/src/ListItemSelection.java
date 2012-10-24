
import javax.swing.DefaultListModel;
import javax.swing.JList;

import deferredcopypaste.TransferNotifierTransferrable;

/**
 * @author idanilov
 * 
 */
public class ListItemSelection extends TransferNotifierTransferrable {

	private JList list;
	private int index;

	public ListItemSelection(final JList list) {
		super(new ItemTransferable(new ItemTransferData((String) list.getSelectedValue())));
		this.list = list;
		index = list.getSelectedIndex();
	}

	public void transferAccepted() {
		DefaultListModel model = (DefaultListModel) list.getModel();
		model.removeElementAt(index);
		super.transferAccepted();
	}

}