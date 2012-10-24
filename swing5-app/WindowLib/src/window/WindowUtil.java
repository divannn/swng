package window;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * @author idanilov
 *
 */
public abstract class WindowUtil {

	private WindowUtil() {
	}

	public static void centerWindow(final Component parent, final Window window) {
		if (parent == null || window == null) {
			return;
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDim = toolkit.getScreenSize();
		Dimension dim = window.getSize();
		int x;
		int y;
		if (window.isVisible()) {
			Point locParent = parent.getLocation();
			Dimension dimParent = parent.getSize();
			x = Math.max(locParent.x + (dimParent.width - dim.width) / 2, 0);
			if (x + dim.width > screenDim.width) {
				x = screenDim.width - dim.width;
			}
			y = Math.max(locParent.y + (dimParent.height - dim.height) / 2, 0);
			if (y + dim.height > screenDim.height) {
				y = screenDim.height - dim.height;
			}
		} else {
			x = (screenDim.width - dim.width) / 2;
			y = (screenDim.height - dim.height) / 2;
		}
		window.setLocation(x, y);
	}

}
