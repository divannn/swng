package com.id.swingeditor.d2;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * Listens to various AWT events and dispatches these events to interested Draw2d objects.
 * @author idanilov
 *
 */
public interface IEventDispatcher {

	void setRoot(XFigure r);

	void setControl(JComponent c);

	void updateCursor();

	void updateTooltip();

	void dispatchMouseEntered(MouseEvent me);

	void dispatchMouseExited(MouseEvent me);

	void dispatchMouseMoved(MouseEvent me);

	void dispatchMouseDragged(MouseEvent me);

	void dispatchMousePressed(MouseEvent me);

	void dispatchMouseReleased(MouseEvent me);

	void dispatchMouseClicked(MouseEvent me);

}
