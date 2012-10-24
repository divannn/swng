package com.id.swingeditor.gef;

import java.awt.Cursor;

import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.d2.XFigureImpl;
import com.id.swingeditor.d2.XLocator;
import com.id.swingeditor.gef.editpart.XGraphicalEditPart;
import com.id.swingeditor.gef.tool.XDragTracker;

/**
 * @author idanilov
 *
 */
public abstract class XAbstractHandle extends XFigureImpl
		implements XHandle {

	private XGraphicalEditPart editpart;
	private XDragTracker dragTracker;
	private XLocator locator;

	public XAbstractHandle() {

	}

	public XAbstractHandle(XGraphicalEditPart owner, XLocator loc) {
		setOwner(owner);
		setLocator(loc);
	}

	public XAbstractHandle(XGraphicalEditPart owner, XLocator loc, Cursor c) {
		this(owner, loc);
		setCursor(c);
	}

	protected abstract XDragTracker createDragTracker();

	//[ID]
	//	/**
	//	 * Returns the cursor.  The cursor is displayed whenever the mouse is over the handle.
	//	 * @deprecated use getCursor()
	//	 * @return the cursor
	//	 */
	//	public Cursor getDragCursor() {
	//		return getCursor();
	//	}

	/**
	 * Returns the drag tracker to use when the user clicks on this handle.  If the drag
	 * tracker has not been set, it will be lazily created by calling {@link
	 * #createDragTracker()}.
	 * @return the drag tracker
	 */
	public XDragTracker getDragTracker() {
		if (dragTracker == null)
			dragTracker = createDragTracker();
		return dragTracker;
	}

	/**
	 * Returns the <code>Locator</code> used to position this handle.
	 * @return the locator
	 */
	public XLocator getLocator() {
		return locator;
	}

	/**
	 * Returns the <code>GraphicalEditPart</code> associated with this handle.
	 * @return the owner editpart
	 */
	protected XGraphicalEditPart getOwner() {
		return editpart;
	}

	/**
	 * Convenience method to return the owner's figure.
	 * @return the owner editpart's figure
	 */
	protected XFigure getOwnerFigure() {
		return getOwner().getFigure();
	}

	protected void setOwner(XGraphicalEditPart editpart) {
		this.editpart = editpart;
	}

	protected void setLocator(XLocator locator) {
		this.locator = locator;
	}

	public void setDragTracker(XDragTracker dragTracker) {
		this.dragTracker = dragTracker;
	}

	public void validate() {
		//[ID]		
		//		if (isValid())
		//			return;
		getLocator().relocate(this);
		super.validate();
	}
}
