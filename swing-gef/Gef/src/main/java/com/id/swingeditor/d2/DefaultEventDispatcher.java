package com.id.swingeditor.d2;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * @author idanilov
 *
 */
public class DefaultEventDispatcher
		implements IEventDispatcher {

	private MEvent currentEvent;
	protected JComponent control;
	protected XFigure root;
	private XFigure mouseTarget;
	private XFigure cursorTarget;
	private Cursor cursor;
	private String tooltip;

	public DefaultEventDispatcher() {
	}

	public void setControl(JComponent c) {
		control = c;
	}

	public void setRoot(XFigure figure) {
		root = figure;
	}

	public void updateCursor() {
		Cursor newCursor = null;
		if (cursorTarget != null)
			newCursor = cursorTarget.getCursor();
		setCursor(newCursor);
	}

	protected void setCursor(Cursor cur) {
		if (cur == null && cursor == null) {
			return;
		} else if ((cur != cursor) || (!cur.equals(cursor))) {
			cursor = cur;
			if (control != null /*[ID]&& !control.isDisposed()*/) {
				control.setCursor(cur);
			}
		}
	}

	//[ID]new.
	public void updateTooltip() {
		String newTooltip = null;
		if (cursorTarget != null)
			newTooltip = cursorTarget.getTooltip();
		setTooltip(newTooltip);
	}

	//[ID]new.
	protected void setTooltip(String tip) {
		if (tip == null && tooltip == null) {
			return;
		} else if ((tip != tooltip) || (!tip.equals(tooltip))) {
			tooltip = tip;
			if (control != null /*[ID]&& !control.isDisposed()*/) {
				control.setToolTipText(tip);
			}
		}
	}

	protected void setFigureUnderCursor(XFigure f) {
		if (cursorTarget == f)
			return;
		cursorTarget = f;
		updateCursor();
		updateTooltip();
	}

	/**
	 * Updates the figure under the cursor, unless the mouse is captured, in which case all 
	 * mouse events will be routed to the figure that captured the mouse.
	 * @param me the mouse event
	 */
	protected void updateFigureUnderCursor(MouseEvent me) {
		//[ID]if (!captured) {
		XFigure f = root.findFigureAt(me.getX(), me.getY());
		setFigureUnderCursor(f);
		//[ID]		
		//		if (cursorTarget != hoverSource)
		//			updateHoverSource(me);
		//}
	}

	public MEvent getCurrentEvent() {
		return currentEvent;
	}

	protected XFigure getCursorTarget() {
		return cursorTarget;
	}

	public void dispatchMouseEntered(MouseEvent me) {
		//System.err.println("ENTER " + me);
		receive(me);
	}

	public void dispatchMouseExited(MouseEvent me) {
		//System.err.println("EXIT " + me);
		if (mouseTarget != null) {
			currentEvent = new MEvent(me, this, mouseTarget);
			mouseTarget.handleMouseExited(currentEvent);
			//[ID]
			//releaseCapture();
			setMouseTarget(null);
		}
		//root.repaint();//for crosspainting on top of all.
	}

	public void dispatchMouseDragged(MouseEvent me) {
		receive(me);
		if (mouseTarget != null) {
			mouseTarget.handleMouseDragged(currentEvent);
		}
		//root.repaint();//for crosspainting on top of all.
	}

	public void dispatchMouseMoved(MouseEvent me) {
		//System.err.println("MOVED " + me);
		receive(me);
		if (mouseTarget != null) {
			mouseTarget.handleMouseMoved(currentEvent);
		}
		//root.repaint();//for crosspainting on top of all.
	}

	public void dispatchMousePressed(MouseEvent me) {
		//System.err.println("PRESSED " + me);
		receive(me);
		if (mouseTarget != null) {
			mouseTarget.handleMousePressed(currentEvent);
			/*[ID]			if (currentEvent.isConsumed()) {
							setCapture(mouseTarget);
						}*/
		}
	}

	public void dispatchMouseReleased(MouseEvent me) {
		//		System.err.println("RELEASED " + me);
		receive(me);
		if (mouseTarget != null) {
			mouseTarget.handleMouseReleased(currentEvent);
		}
		//[ID]		
		//		releaseCapture();
		//		receive(me);
	}

	public void dispatchMouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	protected XFigure getMouseTarget() {
		return mouseTarget;
	}

	protected void setMouseTarget(XFigure figure) {
		mouseTarget = figure;
	}

	private void receive(MouseEvent me) {
		currentEvent = null;
		updateFigureUnderCursor(me);
		MEvent ev = new MEvent(me, this, mouseTarget);
		//[ID]
		//		if (captured) {
		//			if (mouseTarget != null)
		//				currentEvent = ev;
		//		} 
		//		else {
		XFigure f = root.findMouseEventTargetAt(me.getX(), me.getY());
		System.err.println("fig: " + f + " mouse: " + me.getPoint());
		if (f == mouseTarget) {
			if (mouseTarget != null) {
				currentEvent = ev;
			}
			return;
		}
		if (mouseTarget != null) {
			currentEvent = ev;
			mouseTarget.handleMouseExited(currentEvent);
		}
		setMouseTarget(f);
		if (mouseTarget != null) {
			currentEvent = ev;
			mouseTarget.handleMouseEntered(currentEvent);
		}
	}

}
