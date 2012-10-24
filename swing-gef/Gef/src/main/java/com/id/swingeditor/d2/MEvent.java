package com.id.swingeditor.d2;

import java.awt.event.MouseEvent;


/**
 * @author idanilov
 *
 */
public class MEvent {

	private IEventDispatcher dispatcher;
	private MouseEvent awtEvent;
	private XFigure source;

	public MEvent(MouseEvent orig, IEventDispatcher d, XFigure s) {
		awtEvent = orig;
		dispatcher = d;
		source = s;
	}

	public MouseEvent getAwtEvent() {
		return awtEvent;
	}

	public XFigure getSource() {
		return source;
	}

}
