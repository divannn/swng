package gef;

import model.FooModelUtils;

import com.id.swingeditor.d2.XFigure;

/**
 * @author idanilov
 *
 */
public class NodeGraphicalEP extends BaseGraphicalEp {

	@Override
	protected XFigure createFigure() {
		XFigure result = new NodeFigure(FooModelUtils.getId(getModel()));
		return result;
	}

	@Override
	protected void refreshVisuals() {
		refreshBounds();
	}

	private void refreshBounds() {
		NodeFigure f = getFigure();
		f.setD2Constraint(FooModelUtils.getBounds(getModel()));
	}
}
