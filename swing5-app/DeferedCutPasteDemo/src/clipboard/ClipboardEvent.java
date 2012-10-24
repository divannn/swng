package clipboard;

import java.awt.datatransfer.Clipboard;
import java.util.EventObject;

/**
 * @author idanilov
 * 
 */
public class ClipboardEvent extends EventObject {

	public ClipboardEvent(Clipboard source) {
		super(source);
	}

	public Clipboard getClipboard() {
		return (Clipboard) getSource();
	}
}