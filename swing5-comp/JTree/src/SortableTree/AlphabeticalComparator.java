package SortableTree;

import java.util.Comparator;

/**
 * @author idanilov
 *
 */
public class AlphabeticalComparator
		implements Comparator {

	public static final AlphabeticalComparator DIRECT_CASE_INSENSITIVE = new AlphabeticalComparator(
			true);

	public static final AlphabeticalComparator INVERSE_CASE_INSENSITIVE = new AlphabeticalComparator(
			false);

	private byte koef;

	private AlphabeticalComparator(boolean direct) {
		koef = (byte) (direct ? 1 : -1);
	}

	public boolean isDirectOrder() {
		return koef == 1;
	}

	public int compare(Object o1, Object o2) {
		return koef * String.CASE_INSENSITIVE_ORDER.compare(o1.toString(), o2.toString());
	}

}
