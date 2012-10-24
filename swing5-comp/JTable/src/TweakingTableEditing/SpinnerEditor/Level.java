/**
 * 
 */
package TweakingTableEditing.SpinnerEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows number [1-9] and empty values.
 * @author idanilov
 *
 */
public class Level {

	private String strLevel;

	public static final Level LEVEL_NONE = new Level("");
	public static final List<Level> ALL_LEVELS = new ArrayList<Level>();

	static {
		ALL_LEVELS.add(LEVEL_NONE);
		ALL_LEVELS.add(new Level("1"));
		ALL_LEVELS.add(new Level("2"));
		ALL_LEVELS.add(new Level("3"));
		ALL_LEVELS.add(new Level("4"));
		ALL_LEVELS.add(new Level("5"));
		ALL_LEVELS.add(new Level("6"));
		ALL_LEVELS.add(new Level("7"));
		ALL_LEVELS.add(new Level("8"));
		ALL_LEVELS.add(new Level("9"));
	}

	protected Level(final String str) {
		if (str == null) {
			throw new IllegalArgumentException("Specify non null string.");
		}
		strLevel = str.trim();
	}

	protected Level(final int l) {
		this("" + l);
	}

	public String getValue() {
		return strLevel;
	}

	public int toInt() {
		int result = -1;
		try {
			result = Integer.parseInt(strLevel);
		} catch (NumberFormatException nfe) {
			//no need to handle.
		}
		return result;
	}

	public boolean isValid() {
		return ALL_LEVELS.contains(this);
	}

	public String toString() {
		return strLevel;
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Level) {
			result = ((Level) o).strLevel.equals(strLevel);
		}
		return result;
	}

}