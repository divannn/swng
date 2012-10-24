package com.id.swingeditor.d2;

/**
 * @author idanilov
 *
 */
public abstract class AbstractLayoutManager
		implements ILayoutManager {

	public Object getConstraint(XFigure figure) {
		return null;
	}

	public void setConstraint(XFigure child, Object constraint) {
		//[ID]child.invalidate();
	}

	public void remove(XFigure child) {
		//[ID]invalidate();
	}

}
