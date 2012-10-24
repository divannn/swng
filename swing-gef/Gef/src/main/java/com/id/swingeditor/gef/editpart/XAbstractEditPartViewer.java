package com.id.swingeditor.gef.editpart;

import java.awt.Point;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.gef.EditDomain;

/**
 * @author idanilov
 *
 */
public abstract class XAbstractEditPartViewer
		implements XEditPartViewer {

	private XRootEditPart rootEditPart;
	private EditDomain domain;
	private XEditPartFactory factory;
	private Map<Object, XEditPart> mapIDToEditPart = new HashMap<Object, XEditPart>();
	private Map<XFigure, XEditPart> mapVisualToEditPart = new HashMap<XFigure, XEditPart>();

	public XRootEditPart getRootEditPart() {
		return rootEditPart;
	}

	public void setRootEditPart(XRootEditPart editpart) {
		if (rootEditPart != null) {
			//[ID]			
			//			if (rootEditPart.isActive())
			//				rootEditPart.deactivate();
			rootEditPart.setViewer(null);
		}
		rootEditPart = editpart;
		rootEditPart.setViewer(this);
		//[ID]		
		//		if (getControl() != null)
		//			rootEditPart.activate();
	}

	public EditDomain getEditDomain() {
		return domain;
	}

	public void setEditDomain(EditDomain editdomain) {
		this.domain = editdomain;
	}

	public XEditPartFactory getEditPartFactory() {
		return factory;
	}

	public void setEditPartFactory(XEditPartFactory factory) {
		this.factory = factory;
	}

	public XEditPart getContents() {
		return getRootEditPart().getContents();
	}

	public void setContents(XEditPart editpart) {
		getRootEditPart().setContents(editpart);
	}

	public Map<Object, XEditPart> getEditPartRegistry() {
		return mapIDToEditPart;
	}

	public Map<XFigure, XEditPart> getVisualPartMap() {
		return mapVisualToEditPart;
	}

	public final XEditPart findObjectAt(Point pt) {
		return findObjectAtExcluding(pt, Collections.EMPTY_SET);
	}

	public final XEditPart findObjectAtExcluding(Point pt, Collection<XEditPart> exclude) {
		return findObjectAtExcluding(pt, exclude, null);
	}

}