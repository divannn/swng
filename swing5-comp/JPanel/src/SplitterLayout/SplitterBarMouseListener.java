package SplitterLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author idanilov
 *
 */
class SplitterBarMouseListener extends MouseAdapter {

	private JSplitterBar s;

	public SplitterBarMouseListener(JSplitterBar s) {
		this.s = s;
	}

	public void mouseEntered(MouseEvent e) {
		s.mouseEnter(e);
	}

	public void mouseExited(MouseEvent e) {
		s.mouseExit(e);
	}

	public void mouseReleased(MouseEvent e) {
		s.mouseRelease(e);
	}
}