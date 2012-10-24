package com.id.swingeditor.gef.editpart;

import com.id.swingeditor.d2.XFigure;

/**
 * @author idanilov
 *
 */
public interface XGraphicalEditPart {

	//Primary Figure representing this GraphicalEditPart.
	XFigure getFigure();

	//Figure into which childrens' Figures will be added. 
	XFigure getContentPane();

}
