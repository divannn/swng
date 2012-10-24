package com.id.swingeditor.gef.editpart;


/**
 * @author idanilov
 *
 */
public interface XRootEditPart
		extends XEditPart {

	/**
	 * Returns the <i>contents</i> EditPart. A RootEditPart only has a single child, called its
	 * <i>contents</i>.
	 * @return the contents.
	 */
	XEditPart getContents();

	/**
	 * Sets the <i>contents</i> EditPart. A RootEditPart only has a single child, called its
	 * <i>contents</i>.
	 * @param editpart the contents
	 */
	void setContents(XEditPart editpart);

	/**
	 * Returns the root's EditPartViewer.
	 * @return The <code>XEditPartViewer</code>
	 */
	XEditPartViewer getViewer();

	/**
	 * Sets the root's EditPartViewer.
	 * @param viewer the EditPartViewer
	 */
	void setViewer(XEditPartViewer viewer);
}
