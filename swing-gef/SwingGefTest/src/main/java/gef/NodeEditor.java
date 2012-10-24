package gef;

import java.awt.Rectangle;

import model.FooModelUtils;

import com.dob.ve.abstractmodel.DobXmlElement;
import com.dob.ve.abstractmodel.DobXmlModel;
import com.id.swingeditor.gef.GefEditor;
import com.id.swingeditor.gef.editpart.XEditPartViewer;

/**
 * @author idanilov
 *
 */
public class NodeEditor extends GefEditor {

	private DobXmlModel model;

	public NodeEditor() {
		super();
		initViewer();
	}

	private DobXmlModel createModel() {
		DobXmlModel result = new DobXmlModel();
		DobXmlElement n1 = new DobXmlElement();
		FooModelUtils.setId(n1, "node1");
		FooModelUtils.setBounds(n1, new Rectangle(10, 10, 20, 30));
		DobXmlElement n2 = new DobXmlElement();
		FooModelUtils.setId(n2, "node2");
		FooModelUtils.setBounds(n2, new Rectangle(50, 50, 40, 40));
		result.addChild(n1);
		result.addChild(n2);
		return result;
	}

	@Override
	protected XEditPartViewer createGraphicalViewer() {
		XEditPartViewer result = super.createGraphicalViewer();
		result.setEditPartFactory(new NodeGraphicalEPFactory());//? 2 init.
		return result;
	}

	private void initViewer() {
		model = createModel();
		ContentsGraphicalEP contents = new ContentsGraphicalEP();
		contents.setModel(model);
		getViewer().setContents(contents);
	}
}
