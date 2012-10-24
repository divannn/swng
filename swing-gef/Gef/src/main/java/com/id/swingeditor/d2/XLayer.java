package com.id.swingeditor.d2;

import java.awt.Point;

/**
 * @author idanilov
 *
 */
public class XLayer extends XFigureImpl {

	public XLayer() {
		super();
	}

	public XLayer(String name) {
		super(name);
	}

	/**
	 * Overridden to implement transparent behavior.
	 * @see IFigure#containsPoint(int, int)
	 * 
	 */
	public boolean containsPoint(int x, int y) {
		if (isOpaque())
			return super.containsPoint(x, y);
		Point pt = new Point();
		pt.setLocation(x, y);
		//[ID ]translateFromParent(pt);
		x = pt.x;
		y = pt.y;
		for (int i = 0; i < getChildren().size(); i++) {
			XFigure child = getChildren().get(i);
			if (child.containsPoint(x, y))
				return true;
		}
		return false;
	}

	/**
	 * Overridden to implement transparency.
	 * @see IFigure#findFigureAt(int, int, TreeSearch)
	 */
	public XFigure findFigureAt(int x, int y, TreeSearch search) {
		if (!isEnabled())
			return null;
		if (isOpaque())
			return super.findFigureAt(x, y, search);
		XFigure f = super.findFigureAt(x, y, search);
		if (f == this)
			return null;
		return f;
	}

}
