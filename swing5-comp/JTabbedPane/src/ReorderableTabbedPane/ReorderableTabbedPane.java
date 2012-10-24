package ReorderableTabbedPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.plaf.TabbedPaneUI;

/**
 * @author idanilov
 */

public class ReorderableTabbedPane extends JTabbedPane {

	public ReorderableTabbedPane() {
		this(TOP);
	}

	public ReorderableTabbedPane(int tabPlacement) {
		this(tabPlacement, WRAP_TAB_LAYOUT);
	}

	public ReorderableTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
		TabMoveHandler mouseHandler = new TabMoveHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}
	
	private class TabMoveHandler extends MouseAdapter
			implements MouseMotionListener {

		private Cursor defaultCursor;
		private Cursor handCursor;
		//private Cursor forbidCursor;

		private int startIndex;
		private int currentIndex;

		public TabMoveHandler() {
			startIndex = -1;
			currentIndex = -1;
			defaultCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
			handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		}

		public void mousePressed(MouseEvent e) {
			if (!e.isPopupTrigger()) {
				JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
				startIndex = tabbedPane.indexAtLocation(e.getX(), e.getY());
			}
			currentIndex = -1;
		}

		public void mouseReleased(MouseEvent e) {
			JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
			if (!e.isPopupTrigger()) {
				int endIndex = tabbedPane.indexAtLocation(e.getX(), e.getY());

				if (startIndex != -1 && endIndex != -1 && startIndex != endIndex) {
					moveTab(tabbedPane, startIndex, endIndex);
					tabbedPane.setSelectedIndex(endIndex);
				}
			}
			startIndex = -1;
			clearRectangle(tabbedPane);
			currentIndex = -1;
			setCursor(defaultCursor);
		}

		/**
		 * @param tabbedPane
		 * @param startIndex
		 * @param endIndex
		 */
		private void moveTab(JTabbedPane pane, int src, int dst) {
			// Get all the properties
			Component comp = pane.getComponentAt(src);
			String label = pane.getTitleAt(src);
			Icon icon = pane.getIconAt(src);
			Icon iconDis = pane.getDisabledIconAt(src);
			String tooltip = pane.getToolTipTextAt(src);
			boolean enabled = pane.isEnabledAt(src);
			int keycode = pane.getMnemonicAt(src);
			int mnemonicLoc = pane.getDisplayedMnemonicIndexAt(src);
			Color fg = pane.getForegroundAt(src);
			Color bg = pane.getBackgroundAt(src);

			// Remove the tab
			pane.remove(src);

			// Add a new tab
			pane.insertTab(label, icon, comp, tooltip, dst);

			// Restore all properties
			pane.setDisabledIconAt(dst, iconDis);
			pane.setEnabledAt(dst, enabled);
			pane.setMnemonicAt(dst, keycode);
			pane.setDisplayedMnemonicIndexAt(dst, mnemonicLoc);
			pane.setForegroundAt(dst, fg);
			pane.setBackgroundAt(dst, bg);
		}

		public void mouseDragged(MouseEvent e) {
			if (startIndex != -1) {
				JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
				int index = tabbedPane.indexAtLocation(e.getX(), e.getY());

				if (index != -1 && index != currentIndex) { // moved over another tab
					clearRectangle(tabbedPane);
					currentIndex = index;
				}
				drawRectangle(tabbedPane);
				setCursor(handCursor);
			}
		}

		private void clearRectangle(JTabbedPane tabbedPane) {
			if (currentIndex == -1) {
				return;
			}
			TabbedPaneUI tabUI = tabbedPane.getUI();
			Rectangle rect = tabUI.getTabBounds(tabbedPane, currentIndex);
			tabbedPane.repaint(rect);
		}

		private void drawRectangle(JTabbedPane tabbedPane) {
			TabbedPaneUI tabUI = tabbedPane.getUI();
			Rectangle rect = tabUI.getTabBounds(tabbedPane, currentIndex);
			Graphics graphics = tabbedPane.getGraphics();
			graphics.setColor(Color.BLACK);
			graphics.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
			clearRectangle((JTabbedPane) e.getSource());
			currentIndex = -1;
		}

	}

}
