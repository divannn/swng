package com.id.swingeditor.gef.tool;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.id.swingeditor.gef.EditDomain;

/**
 * @author idanilov
 *
 */
public interface XTool {

    void setEditDomain(EditDomain domain);

    void activate();

    void deactivate();

    Point getStartLocation();

    MouseEvent getMouse();

    void mouseEntered(MouseEvent me);

    void mouseExited(MouseEvent me);

    void mousePressed(MouseEvent me);

    void mouseReleased(MouseEvent me);

    void mouseClicked(MouseEvent me);

    void mouseMoved(MouseEvent me);

    void mouseDragged(MouseEvent me);
}
