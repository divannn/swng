package com.id.swingeditor.gef;

import java.awt.event.MouseEvent;

import com.id.swingeditor.gef.tool.XTool;

/**
 * @author idanilov
 *
 */
public class EditDomain {

    private XTool activeTool;

    public XTool getActiveTool() {
        return activeTool;
    }

    public void setActiveTool(XTool tool) {
        if (activeTool != null)
            activeTool.deactivate();
        activeTool = tool;
        if (activeTool != null) {
            activeTool.setEditDomain(this);
            activeTool.activate();
        }
    }

    public void mouseEntered(MouseEvent me) {
        XTool tool = getActiveTool();
        if (tool != null)
            tool.mouseEntered(me);
    }

    public void mouseExited(MouseEvent me) {
        XTool tool = getActiveTool();
        if (tool != null)
            tool.mouseExited(me);
    }

    public void mousePressed(MouseEvent me) {
        XTool tool = getActiveTool();
        if (tool != null)
            tool.mousePressed(me);
    }

    public void mouseReleased(MouseEvent me) {
        XTool tool = getActiveTool();
        if (tool != null)
            tool.mouseReleased(me);
    }

    public void mouseClicked(MouseEvent me) {
        XTool tool = getActiveTool();
        if (tool != null)
            tool.mouseClicked(me);
    }

    public void mouseMoved(MouseEvent me) {
        XTool tool = getActiveTool();
        if (tool != null)
            tool.mouseMoved(me);
    }

    public void mouseDragged(MouseEvent me) {
        XTool tool = getActiveTool();
        if (tool != null)
            tool.mouseDragged(me);
    }
}
