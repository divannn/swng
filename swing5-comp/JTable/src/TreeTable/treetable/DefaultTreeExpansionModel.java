package TreeTable.treetable;

import java.util.HashSet;

/**
 * Default implementation of TreeExpansionModel.
 * Uses HashSet to hold expanded nodes.
 * TODO: make it part of TreeTableModel.
 * @author idanilov
 */
public class DefaultTreeExpansionModel
		implements TreeExpansionModel {

	private AbstractTreeTableModel model;
	private HashSet expandedNodes;

	public DefaultTreeExpansionModel(AbstractTreeTableModel model) {
		this.model = model;
		expandedNodes = new HashSet();
	}

	public boolean isExpanded(Object node) {
		return expandedNodes.contains(node);
	}

	public void expand(Object node) {
		expandedNodes.add(node);
		model.fireTableChanged();
	}

	public void collapse(Object node) {
		expandedNodes.remove(node);
		model.fireTableChanged();
	}

}