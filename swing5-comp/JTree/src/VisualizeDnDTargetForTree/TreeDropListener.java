package VisualizeDnDTargetForTree;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 * @author idanilov
 *
 */
public class TreeDropListener 
		implements DropTargetListener {
	
	private Component oldGlassPane;
	private Point from, to;

	//glasspane on which visual clues are drawn.
	private JPanel glassPane = new DropGlassPane();

	//size of hotspot used to find the whether user wants to insert element.
	private int hotspot = 5;

	//droppath - subclasses can access this in to accept/reject drop.
	protected TreePath treePath;

	// null means replace node at treePath
	// true means insert node before treePath
	// false means insert node after treePath
	// subclasses can access this in drop
	protected Boolean before;

	public void dragEnter(DropTargetDragEvent dtde) {
		JTree tree = (JTree) dtde.getDropTargetContext().getComponent();
		Point location = dtde.getLocation();
		JRootPane rootPane = tree.getRootPane();
		oldGlassPane = rootPane.getGlassPane();
		rootPane.setGlassPane(glassPane);
		glassPane.setVisible(true);
		updateLine(tree, location);
	}

	public void dragOver(DropTargetDragEvent dtde) {
		JTree tree = (JTree) dtde.getDropTargetContext().getComponent();
		Point location = dtde.getLocation();
		updateLine(tree, location);
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	public void dragExit(DropTargetEvent dte) {
		resetGlassPane(dte);
	}

	public void drop(DropTargetDropEvent dtde) {
		resetGlassPane(dtde);
	}

	private void resetGlassPane(DropTargetEvent dte) {
		JTree tree = (JTree) dte.getDropTargetContext().getComponent();
		JRootPane rootPane = tree.getRootPane();
		rootPane.setGlassPane(oldGlassPane);
		oldGlassPane.setVisible(false);
		rootPane.repaint();
	}
	
	private void updateLine(JTree tree, Point pt) {
		treePath = tree.getPathForLocation(pt.x, pt.y);
		if (treePath == null) {
			from = to = null;
			before = null;
			tree.clearSelection();
		} else {
			Rectangle bounds = tree.getPathBounds(treePath);
			if (pt.y <= bounds.y + hotspot) {
				from = bounds.getLocation();
				to = new Point(from.x + bounds.width, from.y);
				before = Boolean.TRUE;
			} else if (pt.y >= bounds.y + bounds.height - hotspot) {
				from = new Point(bounds.x, bounds.y + bounds.height);
				to = new Point(from.x + bounds.width, from.y);
				before = Boolean.FALSE;
			} else {
				from = to = null;
				before = null;
			}
			if (from != null && to != null) {
				from = SwingUtilities.convertPoint(tree, from, glassPane);
				to = SwingUtilities.convertPoint(tree, to, glassPane);
				tree.clearSelection();
			} else {
				tree.setSelectionPath(treePath);
			}
		}
		glassPane.getRootPane().repaint();
	}
	
	private class DropGlassPane extends JPanel {
		
		public DropGlassPane() {
			super();
			setOpaque(false);
		}

		public void paint(Graphics g) {
			g.setColor(Color.red);
			if (from == null || to == null) {
				return;
			}
			int x1 = from.x;
			int x2 = to.x;
			int y1 = from.y;
			//line.
			g.drawLine(x1 + 2, y1, x2 - 2, y1);
			g.drawLine(x1 + 2, y1 + 1, x2 - 2, y1 + 1);
			//right.
			g.drawLine(x1, y1 - 2, x1, y1 + 3);
			g.drawLine(x1 + 1, y1 - 1, x1 + 1, y1 + 2);
			//left.
			g.drawLine(x2, y1 - 2, x2, y1 + 3);
			g.drawLine(x2 - 1, y1 - 1, x2 - 1, y1 + 2);
		}
		
	}
	
}