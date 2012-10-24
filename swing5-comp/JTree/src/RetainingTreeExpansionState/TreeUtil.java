package RetainingTreeExpansionState;

import java.util.StringTokenizer;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * @author idanilov
 * 
 */
public abstract class TreeUtil {

	private static String DELIMETER = ",";
	
	// is path1 descendant of path2.
	public static boolean isDescendant(TreePath path1, final TreePath path2) {
		int count1 = path1.getPathCount();
		int count2 = path2.getPathCount();
		if (count1 <= count2) {
			return false;
		}
		while (count1 != count2) {
			path1 = path1.getParentPath();
			count1--;
		}
		return path1.equals(path2);
	}

	public static String getExpansionState(final JTree tree, final int row) {
		TreePath rowPath = tree.getPathForRow(row);
		StringBuffer buf = new StringBuffer();
		int rowCount = tree.getRowCount();
		for (int i = row; i < rowCount; i++) {
			TreePath path = tree.getPathForRow(i);
			if (i == row || isDescendant(path, rowPath)) {
				if (tree.isExpanded(path)) {
					//System.err.println("save: " + String.valueOf(i - row));
					buf.append(DELIMETER + String.valueOf(i - row));
				}
			} else {
				break;
			}
		}
		return buf.toString();
	}

	public static void restoreExpanstionState(final JTree tree, final int row,
			final String expansionState) {
		StringTokenizer stok = new StringTokenizer(expansionState, DELIMETER);
		while (stok.hasMoreTokens()) {
			int token = row + Integer.parseInt(stok.nextToken());
			//System.err.println("restore: " + token);
			tree.expandRow(token);
		}
	}

}
