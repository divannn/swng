package com.id.swingeditor.d2;

import java.awt.Insets;
import java.awt.Rectangle;

/**
 * @author idanilov
 *
 */
public abstract class AbstractBorder
		implements XBorder {

	public boolean isOpaque() {
		return false;
	}

	protected static Rectangle getPaintRectangle(XFigure figure, Insets insets) {
		Rectangle r = new Rectangle(figure.getBounds().getBounds());
		D2Util.shrink(r, insets);
		return r;
	}

}
