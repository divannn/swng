package com.id.swingeditor.d2;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author idanilov
 *
 */
public class XYLayout extends AbstractLayoutManager {

	protected Map<XFigure, Object> constraints = new HashMap<XFigure, Object>();

	public Object getConstraint(XFigure figure) {
		return constraints.get(figure);
	}

	public void setConstraint(XFigure figure, Object newConstraint) {
		super.setConstraint(figure, newConstraint);
		if (newConstraint != null) {
			constraints.put(figure, newConstraint);
		}
	}

	public void remove(XFigure figure) {
		super.remove(figure);
		constraints.remove(figure);
	}

	public Point getOrigin(XFigure parent) {
		Rectangle ca = parent.getClientArea();
		return new Point(ca.x, ca.y);
	}

	//XXX: perform 2d calculations.
	//constraint rectangle is relative to parent coordinate system.
	//bounds for child set in absolute coordinate system.
	public void layout(XFigure parent) {
		Iterator<XFigure> children = parent.getChildren().iterator();
		Point o = getOrigin(parent);
		Point offset = new Point(o.x, o.y);
		XFigure f;
		while (children.hasNext()) {
			f = children.next();
			Rectangle bounds = (Rectangle) getConstraint(f);
			if (bounds == null)
				continue;
			//[ID] ignore pref. size.
			//            if (bounds.width == -1 || bounds.height == -1) {
			//                Dimension preferredSize = f.getPreferredSize(bounds.width, bounds.height);
			//                bounds = new Rectangle(bounds);
			//                if (bounds.width == -1)
			//                    bounds.width = preferredSize.width;
			//                if (bounds.height == -1)
			//                    bounds.height = preferredSize.height;
			//            }
			Rectangle r = new Rectangle(bounds);
			r.translate(offset.x, offset.y);
			f.setBounds(r);
		}
	}

}