package SplitterLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author idanilov
 *
 */
class SplitterBarMouseMotionListener extends MouseMotionAdapter {

	private JSplitterBar s;

	public SplitterBarMouseMotionListener(JSplitterBar s) {
		this.s = s;
	}

	public void mouseDragged(MouseEvent e) {
		s.mouseDrag(e);
	}
}