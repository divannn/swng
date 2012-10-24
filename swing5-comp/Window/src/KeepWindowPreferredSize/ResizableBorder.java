package KeepWindowPreferredSize;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

/**
 * @author idanilov
 *
 */
public class ResizableBorder extends EmptyBorder {

	private static final Icon CORNER_ICON = new ImageIcon(ResizableBorder.class
			.getResource("ResizableCorner.gif"));

	public ResizableBorder(int top, int left, int bottom, int right) {
		super(top, left, bottom, right);
	}

	public ResizableBorder(Insets borderInsets) {
		super(borderInsets);
	}

	/**
	 * Draw icon which indicate that dialog is resizable at bottom-left corner
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (CORNER_ICON != null) {
			CORNER_ICON.paintIcon(c, g, width - CORNER_ICON.getIconWidth(), height
					- CORNER_ICON.getIconHeight());
		}
	}

}
