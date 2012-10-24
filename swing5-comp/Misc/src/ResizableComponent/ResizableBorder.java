package ResizableComponent;

import java.awt.event.MouseEvent;

import javax.swing.border.Border;

/**
 * @author idanilov
 *
 */
public interface ResizableBorder
		extends Border {

	public int getResizeCursor(MouseEvent me);
}
