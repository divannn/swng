package TreeTable.treetable.icons;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 * @author idanilov
 *
 */
public class CompoundIcon
		implements Icon {

	protected int gap = 4;
	protected Icon leftIcon;
	protected Icon rightIcon;

	public CompoundIcon() {
		this(null, null);
	}

	public CompoundIcon(Icon left, Icon right) {
		leftIcon = left;
		rightIcon = right;
	}

	public Icon getLeftIcon() {
		return leftIcon;
	}

	public void setLeftIcon(Icon icon) {
		leftIcon = icon;
	}

	public Icon getRightIcon() {
		return rightIcon;
	}

	public void setRightIcon(Icon icon) {
		rightIcon = icon;
	}

	/*
	 * Icon interface methods
	 */

	public void paintIcon(Component c, Graphics g, int x, int y) {
		int leftHeight = (leftIcon == null) ? 0 : leftIcon.getIconHeight();
		int rightHeight = (rightIcon == null) ? 0 : rightIcon.getIconHeight();
		int height = (leftHeight > rightHeight) ? leftHeight : rightHeight;

		int offset = 0;
		if (leftIcon != null) {
			int delta = (leftHeight < height) ? ((height - leftHeight) / 2) : 0;
			leftIcon.paintIcon(c, g, x, y + delta);
			offset = leftIcon.getIconWidth() + gap;
		}

		if (rightIcon != null) {
			rightIcon.paintIcon(c, g, x + offset, y);
		}
	}

	public int getIconWidth() {
		int width = 0;
		if (leftIcon != null) {
			width += leftIcon.getIconWidth();
		}
		if (rightIcon != null) {
			width += rightIcon.getIconWidth();
		}
		if (width != 0) {
			width += gap;
		}
		return width;
	}

	public int getIconHeight() {
		int heightL = (leftIcon == null) ? 0 : leftIcon.getIconHeight();
		int heightR = (rightIcon == null) ? 0 : rightIcon.getIconHeight();
		return (heightL > heightR) ? heightL : heightR;
	}

}
