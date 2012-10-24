package com.id.swingeditor.d2;


/**
 * @author idanilov
 *
 */
public interface ILayoutManager {

    void layout(XFigure f);

    Object getConstraint(XFigure child);

    void setConstraint(XFigure child, Object constraint);

    void remove(XFigure child);
    
}
