package KeepWindowPreferredSize;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author idanilov
 *
 */
public class WindowResizer extends ComponentAdapter {

	private Window w;
	private Point oldLocation;
	private Dimension prefSize;

	public WindowResizer(final Window w) {
		this.w = w;
		w.addComponentListener(this);
	}

	public void uninstall() {
		w.removeComponentListener(this);
	}

	public void componentResized(ComponentEvent event) {
		if (prefSize != null) {
			if (!isPrefSizeValid()) {
				Dimension realSize = w.getSize();
				if (realSize.width < prefSize.width) {
					realSize.width = prefSize.width;
				}
				if (realSize.height < prefSize.height) {
					realSize.height = prefSize.height;
				}
				w.setSize(realSize);//restore size.
				Point realLocation = w.getLocation();
				if (!realLocation.equals(oldLocation)) {
					w.setLocation(oldLocation);//restore location.
				}
			}
		}
	}

	public void componentShown(ComponentEvent e) {
		oldLocation = w.getLocation();
		prefSize = w.getPreferredSize();
	}

	public void componentMoved(ComponentEvent e) {
		if (isPrefSizeValid()) {
			oldLocation = w.getLocation();
		}
	}

	private boolean isPrefSizeValid() {
		boolean result = false;
		if (prefSize != null) {
			Dimension realSize = w.getSize();
			result = realSize.width >= prefSize.width && realSize.height >= prefSize.height;
		}
		return result;
	}

}
