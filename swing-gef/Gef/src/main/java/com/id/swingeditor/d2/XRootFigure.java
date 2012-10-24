package com.id.swingeditor.d2;

import java.awt.Rectangle;

/**
 * @author idanilov
 *
 */
class XRootFigure extends XFigureImpl {

	private LwSystem lws;

	public XRootFigure(LwSystem lws) {
		this.lws = lws;
		setOpaque(true);
		setLayoutManager(new StackLayout());
	}

	@Override
	public void repaint(Rectangle rect) {
		if (rect == null)
			return;
		lws.getCanvas().repaint(rect.getBounds());
	}

	public IEventDispatcher internalGetEventDispatcher() {
		return lws.getEventDispatcher();
	}

}
