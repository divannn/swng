package com.id.swingeditor.d2;

import java.awt.Rectangle;
import java.util.List;

/**
 * @author idanilov
 *
 */
public class StackLayout extends AbstractLayoutManager {

    public void layout(XFigure figure) {
        Rectangle r = figure.getClientArea();
        List<XFigure> children = figure.getChildren();
        for (int i = 0; i < children.size(); i++) {
            XFigure child = children.get(i);
            child.setBounds(r);
        }
    }

}
