package com.id.swingeditor.gef;

import com.id.swingeditor.gef.tool.XDragTracker;


/**
 * @author idanilov
 *
 */
public interface XHandle {

	/**
	 * Returns the DragTracker for dragging this Handle.
	 * @return <code>null</code> or a <code>DragTracker</code>
	 */
	XDragTracker getDragTracker();

	/**
	 * Returns an optional accessibility Point.  This returned point is in absolute
	 * coordinates.
	 * @return <code>null</code> or the absolute location
	 */
	//[ID]Point getAccessibleLocation();

}
