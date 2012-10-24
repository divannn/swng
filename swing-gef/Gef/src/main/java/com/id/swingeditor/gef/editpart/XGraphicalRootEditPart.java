package com.id.swingeditor.gef.editpart;

import com.id.swingeditor.d2.StackLayout;
import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.d2.XLayer;
import com.id.swingeditor.d2.XLayeredPane;

/**
 * @author idanilov
 *
 */
public class XGraphicalRootEditPart extends XAbstractGraphicalEditPart
		implements XRootEditPart, XLayerManager {

	protected XEditPart contents;
	protected XEditPartViewer viewer;

	private XLayeredPane innerLayers;
	private XLayeredPane printableLayers;

	public XEditPart getContents() {
		return contents;
	}

	public void setContents(XEditPart editpart) {
		if (contents != null)
			removeChild(contents);
		contents = editpart;
		if (contents != null)
			addChild(contents, 0);
	}

	protected XFigure createFigure() {
		innerLayers = new XLayeredPane();
		printableLayers = new XLayeredPane();

		XLayer layer = new XLayer();
		layer.setLayoutManager(new StackLayout());
		printableLayers.add(layer, PRIMARY_LAYER);

		//[ID]		
		//		layer = new ConnectionLayer();
		//		layer.setPreferredSize(new Dimension(5, 5));
		//		printableLayers.add(layer, CONNECTION_LAYER);

		innerLayers.add(printableLayers, PRINTABLE_LAYERS);

		layer = new XLayer();
		//[ID]layer.setPreferredSize(new Dimension(5, 5));
		innerLayers.add(layer, HANDLE_LAYER);

		layer = new FeedbackLayer();
		//[ID]layer.setPreferredSize(new Dimension(5, 5));
		innerLayers.add(layer, FEEDBACK_LAYER);

		//[ID] I don't want implement scrollbars in d2.		
		//		ScrollPane pane = new ScrollPane();
		//		pane.setViewport(new Viewport(true));
		//		pane.setContents(innerLayers);
		//		return pane;
		return innerLayers;
	}

	public XFigure getLayer(Object key) {
		if (innerLayers == null)
			return null;
		XFigure layer = printableLayers.getLayer(key);
		if (layer != null)
			return layer;
		return innerLayers.getLayer(key);
	}

	@Override
	public XRootEditPart getRoot() {
		return this;
	}

	public XFigure getContentPane() {
		return getLayer(PRIMARY_LAYER);
	}

	public Object getModel() {
		return XLayerManager.ID;
	}

	/**
	 * Overridden to do nothing since the child is explicitly set.
	 */
	protected void refreshChildren() {
	}

	public XEditPartViewer getViewer() {
		return viewer;
	}

	public void setViewer(XEditPartViewer v) {
		if (viewer == v)
			return;
		if (viewer != null)
			unregister();
		viewer = v;
		if (viewer != null)
			register();
	}
}

class FeedbackLayer extends XLayer {

	FeedbackLayer() {
		this(null);
	}

	FeedbackLayer(String name) {
		super(name);
		setEnabled(false);
	}
}
