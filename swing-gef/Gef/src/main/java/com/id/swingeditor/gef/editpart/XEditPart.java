package com.id.swingeditor.gef.editpart;

import java.util.List;


/**
 * @author idanilov
 *
 */
public interface XEditPart {

	XEditPart getParent();

	void setParent(XEditPart parent);

	Object getModel();

	List<XEditPart> getChildren();

	XRootEditPart getRoot();

	XEditPartViewer getViewer();

	/**
	 * Called <em>after</em> the EditPart has been added to its parent. This is used to
	 * indicate to the EditPart that it should refresh itself for the first time.
	 */
	void addNotify();

	/**
	 * Called when the EditPart is being permanently removed from its {@link EditPartViewer}.
	 * This indicates that the EditPart will no longer be in the Viewer, and therefore should
	 * remove itself from the Viewer.  This method is <EM>not</EM> called when a Viewer is
	 * disposed. It is only called when the EditPart is removed from its parent. This method
	 * is the inverse of {@link #addNotify()}
	 */
	void removeNotify();

	void refresh();

}
