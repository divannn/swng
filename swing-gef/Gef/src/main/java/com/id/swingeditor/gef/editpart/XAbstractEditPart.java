package com.id.swingeditor.gef.editpart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author idanilov
 *
 */
public abstract class XAbstractEditPart
		implements XEditPart {

	private XEditPart parent;
	private List<XEditPart> children = new ArrayList<XEditPart>(5);
	private Object model;

	public XRootEditPart getRoot() {
		return getParent().getRoot();
	}

	public XEditPartViewer getViewer() {
		return getRoot().getViewer();
	}

	public XEditPart getParent() {
		return parent;
	}

	public void setParent(XEditPart parent) {
		this.parent = parent;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public List<XEditPart> getChildren() {
		return children;
	}

	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	protected void refreshVisuals() {
	}

	protected void refreshChildren() {
		int i;
		XEditPart editPart;
		Object model;

		Map modelToXEditPart = new HashMap();
		List<XEditPart> children = getChildren();

		for (i = 0; i < children.size(); i++) {
			editPart = children.get(i);
			modelToXEditPart.put(editPart.getModel(), editPart);
		}

		List modelObjects = getModelChildren();

		for (i = 0; i < modelObjects.size(); i++) {
			model = modelObjects.get(i);

			//Do a quick check to see if XEditPart[i] == model[i]
			if (i < children.size() && children.get(i).getModel() == model)
				continue;

			//Look to see if the XEditPart is already around but in the wrong location
			editPart = (XEditPart) modelToXEditPart.get(model);

			if (editPart != null)
				reorderChild(editPart, i);
			else {
				//An XEditPart for this model doesn't exist yet.  Create and insert one.
				editPart = createChild(model);
				addChild(editPart, i);
			}
		}
		List trash = new ArrayList();
		for (; i < children.size(); i++)
			trash.add(children.get(i));
		for (i = 0; i < trash.size(); i++) {
			XEditPart ep = (XEditPart) trash.get(i);
			removeChild(ep);
		}
	}

	protected void addChild(XEditPart child, int index) {
		if (child == null) {
			throw new IllegalArgumentException("Cannot ad null child");
		}
		if (index == -1)
			index = getChildren().size();
		children.add(index, child);
		child.setParent(this);
		addChildVisual(child, index);
		child.addNotify();
		//[ID]
		//if (isActive())
		//	child.activate();
		//fireChildAdded(child, index);
	}

	protected void removeChild(XEditPart child) {
		int index = getChildren().indexOf(child);
		if (index < 0)
			return;
		//[ID]
		//fireRemovingChild(child, index);
		//		if (isActive())
		//			child.deactivate();
		child.removeNotify();
		removeChildVisual(child);
		child.setParent(null);
		getChildren().remove(child);
	}

	protected void reorderChild(XEditPart editpart, int index) {
		removeChildVisual(editpart);
		children.remove(editpart);
		children.add(index, editpart);
		addChildVisual(editpart, index);
	}

	protected XEditPart createChild(Object model) {
		return getViewer().getEditPartFactory().createEditPart(this, model);
	}

	protected abstract void addChildVisual(XEditPart child, int index);

	protected abstract void removeChildVisual(XEditPart child);

	/**
	 * Registers itself in the viewer's various registries. If your EditPart has a 1-to-1
	 * relationship with a visual object and a 1-to-1 relationship with a model object, the
	 * default implementation should be sufficent.
	 *
	 * @see #unregister()
	 * @see EditPartViewer#getVisualPartMap()
	 * @see EditPartViewer#getEditPartRegistry()
	 */
	protected void register() {
		registerModel();
		registerVisuals();
		//[ID]registerAccessibility();
	}

	protected void unregister() {
		//[ID]unregisterAccessibility();
		unregisterVisuals();
		unregisterModel();
	}

	protected void registerModel() {
		getViewer().getEditPartRegistry().put(getModel(), this);
	}

	protected void unregisterModel() {
		getViewer().getEditPartRegistry().remove(getModel());
	}

	protected void registerVisuals() {
	}

	protected void unregisterVisuals() {
	}

	public void addNotify() {
		register();
		//[ID]createEditPolicies();
		for (int i = 0; i < children.size(); i++)
			children.get(i).addNotify();
		refresh();
	}

	public void removeNotify() {
		/*[ID]		if (getSelected() != SELECTED_NONE)
					getViewer().deselect(this);
				if (hasFocus())
					getViewer().setFocus(null);
		*/
		for (int i = 0; i < children.size(); i++)
			children.get(i).removeNotify();
		unregister();
	}

	public void refresh() {
		refreshVisuals();
		refreshChildren();
	}

}
