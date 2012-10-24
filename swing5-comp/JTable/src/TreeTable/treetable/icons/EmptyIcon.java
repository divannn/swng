package TreeTable.treetable.icons;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * @author idanilov
 * Does nothing but remember width and height passed via constructor parameters.
 **/
public class EmptyIcon
		implements Icon {

	private int width;
	private int height;

	public EmptyIcon(int width, int height) {
		this.width = width;
		this.height = height;
	}

	//does nothing since the icon is empty.
	public void paintIcon(Component c, Graphics g, int x, int y) {
	}

	public int getIconWidth() {
		return width;
	}

	public int getIconHeight() {
		return height;
	}

}
//$VPJ_CGRP Icons
