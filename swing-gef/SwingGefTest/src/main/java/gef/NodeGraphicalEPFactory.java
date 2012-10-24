package gef;

import com.dob.ve.abstractmodel.DobXmlElement;
import com.id.swingeditor.gef.editpart.XEditPart;
import com.id.swingeditor.gef.editpart.XEditPartFactory;

/**
 * @author idanilov
 *
 */
public class NodeGraphicalEPFactory
		implements XEditPartFactory {

	public XEditPart createEditPart(XEditPart context, Object model) {
		XEditPart result = null;
		if (model instanceof DobXmlElement) {
			NodeGraphicalEP ep = new NodeGraphicalEP();
			ep.setModel(model);
			result = ep;
		}
		return result;
	}

}
