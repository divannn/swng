package com.id.swingeditor.d2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 * @author idanilov
 *
 */
public class LineBorder extends AbstractBorder {

	private int width = 1;
	private Color color;

	public LineBorder(int w, Color c) {
		setWidth(w);
		setColor(c);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (width <= 0) {
			throw new IllegalArgumentException("provide positive width");
		}
		this.width = width;
	}

	public Insets getInsets(XFigure figure) {
		return D2Util.createInsets(width);
	}

	public boolean isOpaque() {
		return true;
	}

	public void paint(XFigure f, Graphics2D g) {
		if (color != null) {
			g.setColor(color);
		}
		BasicStroke s = new BasicStroke(width);
		g.setStroke(s);
		Rectangle r = new Rectangle(f.getBounds().getBounds());
		if (width % 2 == 1) {
			r.width--;
			r.height--;
		}
		r.grow(-width / 2, -width / 2);
		g.draw(r);
	}

}
