package TreeTable.treetable.util;

import java.util.Iterator;

/**
 * Iterator for iterating arrays.
 */
public class ArrayIterator
		implements Iterator {

	private int curr = 0;

	private Object[] array;

	public ArrayIterator(Object[] array) {
		this.array = array;
	}

	public boolean hasNext() {
		return curr < array.length;
	}

	public Object next() {
		return array[curr++];
	}

	public void remove() {
		throw new java.lang.UnsupportedOperationException("Method remove() is unsupported.");
	}

}
