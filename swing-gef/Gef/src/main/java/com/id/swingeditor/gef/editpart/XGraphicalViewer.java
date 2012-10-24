package com.id.swingeditor.gef.editpart;

import java.awt.Point;

import com.id.swingeditor.gef.XHandle;

/**
 * @author idanilov
 *
 */
public interface XGraphicalViewer
		extends XEditPartViewer {

	/**
	 * Returns the <code>Handle</code> at the specified Point. Returns <code>null</code> if no
	 * handle exists at the given Point. The specified point should be relative to the
	 * {@link org.eclipse.swt.widgets.Scrollable#getClientArea() client area} for this
	 * Viewer's <code>Control</code>.
	 * @param p the location relative to the Control's client area
	 * @return Handle <code>null</code> or a Handle
	 */
	//TODO: add handle.
	XHandle findHandleAt(Point p);
}
