package com.id.swingeditor.gef;

import java.awt.event.MouseEvent;

import com.id.swingeditor.d2.DefaultEventDispatcher;
import com.id.swingeditor.gef.editpart.XEditPartViewer;

/**
 * @author idanilov
 *
 */
public class DomainEventDispatcher extends DefaultEventDispatcher {

	private EditDomain domain;
	protected XEditPartViewer viewer;//todo: pass viewer to tool (see GEF).

	public DomainEventDispatcher(EditDomain ed, XEditPartViewer v) {
		domain = ed;
		viewer = v;
	}

	public void dispatchMouseEntered(MouseEvent me) {
		super.dispatchMouseEntered(me);
		domain.mouseEntered(me);
	}

	public void dispatchMouseExited(MouseEvent me) {
		super.dispatchMouseExited(me);
		domain.mouseExited(me);
		//root.repaint();
	}

	public void dispatchMouseDragged(MouseEvent me) {
		super.dispatchMouseDragged(me);
		domain.mouseDragged(me);
		//root.repaint();
	}

	public void dispatchMouseMoved(MouseEvent me) {
		super.dispatchMouseMoved(me);
		domain.mouseMoved(me);
		//root.repaint();
	}

	public void dispatchMousePressed(MouseEvent me) {
		super.dispatchMousePressed(me);
		domain.mousePressed(me);
	}

	public void dispatchMouseReleased(MouseEvent me) {
		super.dispatchMouseReleased(me);
		domain.mouseReleased(me);
	}

	public void dispatchMouseClicked(MouseEvent me) {
		super.dispatchMouseClicked(me);
		domain.mouseClicked(me);
	}

}
