package com.id.swingeditor.gef;

import javax.swing.JComponent;

import com.id.swingeditor.gef.editpart.XEditPartViewer;
import com.id.swingeditor.gef.editpart.XGraphicalViewerImpl;
import com.id.swingeditor.gef.tool.SelectionTool;

/**
 * @author idanilov
 *
 */
public class GefEditor {

	private EditDomain domain;
	private XEditPartViewer viewer;

	public GefEditor() {
		domain = createEditDomain();
		viewer = createGraphicalViewer();
		viewer.setEditDomain(domain);
	}

	protected EditDomain createEditDomain() {
		EditDomain result = new EditDomain();
		result.setActiveTool(new SelectionTool());
		return result;
	}

	void setEditDomain(EditDomain ed) {
		domain = ed;
	}

	public EditDomain getEditDomain() {
		return domain;
	}

	public XEditPartViewer getViewer() {
		return viewer;
	}

	public JComponent getControl() {
		return viewer.getControl();
	}

	protected XEditPartViewer createGraphicalViewer() {
		XGraphicalViewerImpl result = new XGraphicalViewerImpl();
		//[ID]
		//result.createControl(parent);
		return result;
	}

}
