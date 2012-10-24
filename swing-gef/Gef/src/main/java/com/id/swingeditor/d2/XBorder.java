package com.id.swingeditor.d2;

import java.awt.Graphics2D;
import java.awt.Insets;

/**
 * @author idanilov
 *
 */
public interface XBorder {

	void paint(XFigure f, Graphics2D g);

	Insets getInsets(XFigure figure);

	boolean isOpaque();

}
