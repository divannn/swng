package TreeTable.treetable.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * Paints icon with the help of icons provided by UIManager
 * ( UIManager.get("Tree.collapsedIcon") and UIManager.get("Tree.expandedIcon")).
 *
 * But suggests one useful workaround:
 * in Windows LAF default icons are trandparent while default table selected row color is black,
 * so in Windows LAF these icons look dreadful when used in table.
 * This class sets component's  background color to null, then paints icon and then restores the background
 * @author idanilov
 **/
public class PlusMinusIcon
		implements Icon {

	public final static int PLUS = 1;
	public final static int MINUS = -1;

	private Icon icon;
	private boolean transparent;
	private int sign;
	private boolean windows;

	public PlusMinusIcon(int sign, boolean transparent) {
		this.sign = sign;
		this.transparent = transparent;
		setIcons();
	}

	private void setIcons() {
		if (sign == PLUS) {
			icon = (Icon) UIManager.get("Tree.collapsedIcon");
		} else if (sign == MINUS) {
			icon = (Icon) UIManager.get("Tree.expandedIcon");
		}
		String lafID = UIManager.getLookAndFeel().getID();
		windows = lafID.equals("Windows");
	}

	public int getIconWidth() {
		return icon.getIconWidth();
	}

	public int getIconHeight() {
		return icon.getIconHeight();
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (!transparent && windows) {
			Color oldBkColor = c.getBackground();
			c.setBackground(null);
			icon.paintIcon(c, g, x, y);
			c.setBackground(oldBkColor);
		} else {
			icon.paintIcon(c, g, x, y);
		}
	}

}
