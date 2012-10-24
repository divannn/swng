package HighlightTreeNodeDescendants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * @author santosh
 * @author idanilov
 *
 */
public class HighlighterTree extends JTree {

	private TreePath highlightPath;
	private static final Color HIGHLIGHT_COLOR = new Color(255, 255, 204);

	public TreePath getHighlightPath() {
		return highlightPath;
	}

	public void setHighlightPath(TreePath highlightPath) {
		this.highlightPath = highlightPath;
		treeDidChange();
	}

	protected void paintComponent(Graphics g) {
		//paint background to self.
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		//paint the highlight if any.
		g.setColor(HIGHLIGHT_COLOR);
		int fromRow = getRowForPath(highlightPath);
		if (fromRow != -1) {
			int toRow = fromRow;
			int rowCount = getRowCount();
			while (toRow < rowCount) {
				TreePath nextPath = getPathForRow(toRow);
				if (highlightPath.isDescendant(nextPath)) {
					toRow++;
				} else {
					break;
				}
			}
			Rectangle fromBounds = getRowBounds(fromRow);
			Rectangle toBounds = getRowBounds(toRow - 1);
			g.fillRect(0, fromBounds.y, getWidth(), 
					toBounds.y - fromBounds.y + toBounds.height);
		}
		setOpaque(false); //not to paint background.
		super.paintComponent(g);
		setOpaque(true);
	}
	
	/**
	 * Trick to make renderer transparent when unselected.
	 * @see DefaultTreeCellRenderer
	 * @author idanilov
	 *
	 */
	public static class MyTreeCellRenderer extends DefaultTreeCellRenderer {

		public MyTreeCellRenderer() {
			setBackgroundNonSelectionColor(null);
		}

		public Color getBackground() {
			return null;
		}
		
	}	
	
}