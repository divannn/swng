package com.id.swingeditor.gef.editpart;

import java.awt.Point;
import java.util.Collection;

import javax.swing.JComponent;

import com.id.swingeditor.d2.ExclusionSearch;
import com.id.swingeditor.d2.LwSystem;
import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.gef.DomainEventDispatcher;
import com.id.swingeditor.gef.EditDomain;
import com.id.swingeditor.gef.XHandle;

/**
 * @author idanilov
 *
 */
public class XGraphicalViewerImpl extends XAbstractEditPartViewer
		implements XGraphicalViewer {

	private final LwSystem lws;
	private DomainEventDispatcher eventDispatcher;

	public XGraphicalViewerImpl() {
		lws = createLightweightSystem();
		setRootEditPart(createDefaultRoot());
	}

	public JComponent getControl() {
		return lws.getCanvas();
	}

	//	public void setControl(JComponent control) {
	//		lws.setCanvas(control);
	//	}

	protected LwSystem createLightweightSystem() {
		return new LwSystem();
	}

	protected XRootEditPart createDefaultRoot() {
		return new XGraphicalRootEditPart();
	}

	protected LwSystem getLightweightSystem() {
		return lws;
	}

	protected DomainEventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	protected XLayerManager getLayerManager() {
		return (XLayerManager) getEditPartRegistry().get(XLayerManager.ID);
	}

	public void setEditDomain(EditDomain domain) {
		super.setEditDomain(domain);
		eventDispatcher = new DomainEventDispatcher(domain, this);
		getLightweightSystem().setEventDispatcher(eventDispatcher);
	}

	public void setRootEditPart(XRootEditPart editpart) {
		super.setRootEditPart(editpart);
		/*setRootFigure*/setContentsFigure(((XGraphicalEditPart) editpart).getFigure());
	}

	//[ID]set contents into LWS actually.
	//protected void setRootFigure(XFigure figure) {
	protected void setContentsFigure(XFigure figure) {
		getLightweightSystem().setContents(figure);
	}

	public XEditPart findObjectAtExcluding(Point pt, Collection<XEditPart> exclude,
			final Conditional condition) {

		class ConditionalTreeSearch extends ExclusionSearch {

			ConditionalTreeSearch(Collection coll) {
				super(coll);
			}

			public boolean accept(XFigure figure) {
				XEditPart editpart = null;
				while (editpart == null && figure != null) {
					editpart = getVisualPartMap().get(figure);
					figure = figure.getParent();
				}
				return editpart != null && (condition == null || condition.evaluate(editpart));
			}
		}
		XFigure figure = getLightweightSystem().getRootFigure().findFigureAt(pt.x, pt.y,
				new ConditionalTreeSearch(exclude));
		XEditPart part = null;
		while (part == null && figure != null) {
			part = getVisualPartMap().get(figure);
			figure = figure.getParent();
		}
		if (part == null)
			return getContents();
		return part;
	}

	public XHandle findHandleAt(Point p) {
		//TODO: find handle impl.
		return null;
	}

}
