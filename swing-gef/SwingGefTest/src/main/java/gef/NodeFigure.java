package gef;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;

import com.id.swingeditor.d2.LineBorder;
import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.d2.XFigureImpl;
import com.id.swingeditor.d2.XYLayout;

/**
 * @author idanilov
 *
 */
public class NodeFigure extends XFigureImpl {

	public NodeFigure() {
		this(null);
	}

	public NodeFigure(String name) {
		super();
		setName(name);
		setTooltip(name);
		setLayoutManager(new XYLayout());
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		setBorder(new LineBorder(1, Color.BLACK));
	}

	public void setD2Constraint(Rectangle r) {
		XFigure parentFig = getParent();
		if (parentFig != null) {
			parentFig.setConstraint(this, r);
		}
	}

}
