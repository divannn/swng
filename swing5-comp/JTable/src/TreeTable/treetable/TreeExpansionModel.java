package TreeTable.treetable;

/**
 * Holds information about expanded tree nodes.
 * Used by TreeTable
 * @author idanilov
 */
public interface TreeExpansionModel {

	/**
	 * checks whether given node is expanded or no.
	 * @param node Object representing node
	 * @returns <code>true</code> if the node is expanded otherwise <code>false</code>
	 */
	boolean isExpanded(Object node);

	/**
	 * expands given node
	 * @param node Object representing node
	 * NB: should fire table model listeners!
	 */
	void expand(Object node);

	/**
	 * collapses given node
	 * @param node Object representing node
	 * NB: should fire table model listeners!
	 */
	void collapse(Object node);
	
}
