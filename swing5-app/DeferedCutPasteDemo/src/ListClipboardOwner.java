
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import javax.swing.JList;

/**
 * @author idanilov
 */
public class ListClipboardOwner
		implements ClipboardOwner {

	// used as JList's client property.
	public static final String CLIP_BOARD_OWNER = "ClipBoardOwner";

	private JList list;
	private int index;

	public ListClipboardOwner(final JList list) {
		this.list = list;
		index = list.getSelectedIndex();
		list.putClientProperty(CLIP_BOARD_OWNER, this);
		Rectangle rect = list.getCellBounds(index, index);
		if (rect != null) {
			list.paintImmediately(list.getCellBounds(index, index));
		}
	}

	public int getIndex() {
		return index;
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		if (list.getClientProperty(CLIP_BOARD_OWNER) == this) {
			list.putClientProperty(CLIP_BOARD_OWNER, null);
		}
		Rectangle rect = list.getCellBounds(index, index);
		if (rect != null) {
			list.paintImmediately(list.getCellBounds(index, index));
		}
	}

}