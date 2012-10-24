package TreeTable.treetable.util;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Window;

import javax.swing.SwingUtilities;

/**
 * Miscellaneous UI-related static utility functions
 */
public class UiUtils {

	public static void setWaitCursor(Component comp, boolean wait) {
		Window w = SwingUtilities.getWindowAncestor(comp);
		if (w != null) {
			w.setCursor(wait ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor
					.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}
	}

}