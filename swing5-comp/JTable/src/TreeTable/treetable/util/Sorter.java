package TreeTable.treetable.util;

import java.util.Vector;
import java.util.Date;

/**
 * Utility class - performs sorting.
 * @author idanilov
 */
public class Sorter {

	public static void sort(int[] arr) {
		int[] from = arr.clone();
		int[] to = arr;
		int low = 0;
		int high = arr.length;
		sortProc(from, to, low, high);
	}

	public static void sort(Vector vec) {
		int size = vec.size();
		int[] idxs = new int[size];
		for (int i = 0; i < size; i++) {
			idxs[i] = i;
		}

		int[] from = idxs.clone();
		int[] to = idxs;
		int low = 0;
		int high = idxs.length;

		sortProc(vec, from, to, low, high);
		Vector vec2 = (Vector) vec.clone();
		for (int i = 0; i < size; i++) {
			vec.setElementAt(vec2.elementAt(idxs[i]), i);
		}
	}

	private static void sortProc(int[] from, int[] to, int low, int high) {
		if (high - low < 2) {
			return;
		}

		int middle = (low + high) / 2;
		sortProc(to, from, low, middle);
		sortProc(to, from, middle, high);

		int p = low;
		int q = middle;

		if (high - low >= 4 && from[middle - 1] <= from[middle]) {
			for (int i = low; i < high; i++) {
				to[i] = from[i];
			}
			return;
		}

		for (int i = low; i < high; i++) {
			if (q >= high || (p < middle && from[p] <= from[q])) {
				to[i] = from[p++];
			} else {
				to[i] = from[q++];
			}
		}
	}

	private static void sortProc(Vector vec, int[] from, int[] to, int low, int high) {
		if (high - low < 2) {
			return;
		}

		int middle = (low + high) / 2;

		sortProc(vec, to, from, low, middle);
		sortProc(vec, to, from, middle, high);

		if (high - low >= 4
				&& compare(vec.elementAt(from[middle - 1]), vec.elementAt(from[middle])) <= 0) {
			for (int i = low; i < high; i++) {
				to[i] = from[i];
			}
			return;
		}

		int p = low;
		int q = middle;
		for (int i = low; i < high; i++) {
			if (q >= high
					|| (p < middle && compare(vec.elementAt(from[p]), vec.elementAt(from[q])) <= 0)) {
				to[i] = from[p++];
			} else {
				to[i] = from[q++];
			}
		}
	}

	public static int compare(Object o1, Object o2) {
		return compare(o1, o2, false);
	}

	public static int compare(Object o1, Object o2, boolean ignoreCase) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null) {
			return -1; // define null less than everything
		} else if (o2 == null) {
			return 1;
		}

		if (o1 instanceof Number) {
			Number n1 = (Number) o1;
			double d1 = n1.doubleValue();
			Number n2 = (Number) o2;
			double d2 = n2.doubleValue();

			if (d1 < d2) {
				return -1;
			} else if (d1 > d2) {
				return 1;
			} else {
				return 0;
			}
		} else if (o1 instanceof Date) {
			Date d1 = (Date) o1;
			long n1 = d1.getTime();
			Date d2 = (Date) o2;
			long n2 = d2.getTime();

			if (n1 < n2) {
				return -1;
			} else if (n1 > n2) {
				return 1;
			} else {
				return 0;
			}
		} else if (o1 instanceof String) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			int result = ignoreCase ? s1.compareToIgnoreCase(s2) : s1.compareTo(s2);

			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (o1 instanceof Boolean) {
			Boolean bool1 = (Boolean) o1;
			boolean b1 = bool1.booleanValue();
			Boolean bool2 = (Boolean) o2;
			boolean b2 = bool2.booleanValue();

			if (b1 == b2) {
				return 0;
			} else if (b1) {
				return 1;
			} else {
				return -1;
			}
		} else {
			String s1 = o1.toString();
			String s2 = o2.toString();
			int result = ignoreCase ? s1.compareToIgnoreCase(s2) : s1.compareTo(s2);

			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}