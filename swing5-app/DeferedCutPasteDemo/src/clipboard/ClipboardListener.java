package clipboard;

import java.util.EventListener;

/**
 * @author idanilov
 *
 */
public interface ClipboardListener
		extends EventListener {

	/**
	 * Called after calendar data put into system clipboard.
	 * @param event
	 */
	void contentChanged(ClipboardEvent event);

}