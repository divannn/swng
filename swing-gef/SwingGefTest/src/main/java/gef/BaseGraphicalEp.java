package gef;

import com.dob.ve.abstractmodel.DobXmlElement;
import com.id.swingeditor.gef.editpart.XAbstractGraphicalEditPart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author idanilov
 */
public abstract class BaseGraphicalEp extends XAbstractGraphicalEditPart {

    @Override
    protected List getModelChildren() {
        List result = new ArrayList();
        DobXmlElement m = getModel();
        if (m != null) {
            result.addAll(m.getChildren());
        }
        return result;
    }

    @Override
    public DobXmlElement getModel() {
        return (DobXmlElement) super.getModel();
    }

    @Override
    public NodeFigure getFigure() {
        return (NodeFigure) super.getFigure();
    }
}
