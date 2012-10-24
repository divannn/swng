package com.id.swingeditor.d2;

import java.util.Collection;

/**
 * @author idanilov
 *
 */
public class ExclusionSearch
		implements TreeSearch {

	private final Collection c;

	/**
	 * Constructs an Exclusion search using the given collection.
	 * @param collection the exclusion set
	 */
	public ExclusionSearch(Collection collection) {
		this.c = collection;
	}

	public boolean accept(XFigure figure) {
		return true;
	}

	public boolean prune(XFigure f) {
		return c.contains(f);
	}

}
