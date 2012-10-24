package com.id.swingeditor.d2;

/**
 * @author idanilov
 *
 */
public interface TreeSearch {

	/**
	 * Returns <code>true</code> if the given figure is accepted by the search.
	 * @param figure the current figure in the traversal
	 * @return  <code>true</code> if the figure is accepted
	 */
	boolean accept(XFigure figure);

	/**
	 * Returns <code>true</code> if the figure and all of its contained figures should be 
	 * pruned from the search.
	 * @param figure the current figure in the traversal
	 * @return  <code>true</code> if the subgraph should be pruned
	 */
	boolean prune(XFigure figure);

}
