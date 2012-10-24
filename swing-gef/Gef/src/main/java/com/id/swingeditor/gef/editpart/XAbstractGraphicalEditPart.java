package com.id.swingeditor.gef.editpart;

import com.id.swingeditor.d2.XFigure;

/**
 * @author idanilov
 *
 */
public abstract class XAbstractGraphicalEditPart extends XAbstractEditPart
		implements XGraphicalEditPart {

	protected XFigure figure;

	public XFigure getContentPane() {
		return getFigure();
	}

	public XFigure getFigure() {
		if (figure == null)
			setFigure(createFigure());
		return figure;
	}

	protected void setFigure(XFigure figure) {
		this.figure = figure;
	}

	protected abstract XFigure createFigure();

	@Override
	protected void addChildVisual(XEditPart childEditPart, int index) {
		XFigure child = ((XGraphicalEditPart) childEditPart).getFigure();
		getContentPane().add(child, index);
	}

	@Override
	protected void removeChildVisual(XEditPart childEditPart) {
		XFigure child = ((XGraphicalEditPart) childEditPart).getFigure();
		getContentPane().remove(child);
	}

	protected XFigure getLayer(Object layerKey) {
		XLayerManager manager = (XLayerManager) getViewer().getEditPartRegistry().get(
				XLayerManager.ID);
		return manager.getLayer(layerKey);
	}

	protected void registerVisuals() {
		getViewer().getVisualPartMap().put(getFigure(), this);
	}

	protected void unregisterVisuals() {
		getViewer().getVisualPartMap().remove(getFigure());
	}

}
