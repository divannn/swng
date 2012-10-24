package com.id.swingeditor.gef.editpart;


/**
 * @author idanilov
 *
 */
public interface XEditPartFactory {

	XEditPart createEditPart(XEditPart context, Object model);
}
