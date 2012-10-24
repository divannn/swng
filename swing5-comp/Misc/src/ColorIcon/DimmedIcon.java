package ColorIcon;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class DimmedIcon
		implements Icon {

	private Icon delegate;
	private BufferedImage mask;
	private boolean colorPainted = true;

	public DimmedIcon(Icon delegate) {
		this(delegate, UIManager.getColor("textHighlight"), 0.5F);
	}

	public DimmedIcon(Icon delegate, Color color) {
		this(delegate, color, 0.5F);
	}

	public DimmedIcon(Icon delegate, Color color, float alpha) {
		this.delegate = delegate;
		createMask(color, alpha);
	}

	private void createMask(Color color, float alpha) {
		mask = new BufferedImage(delegate.getIconWidth(), delegate.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = (Graphics2D) mask.getGraphics();
		delegate.paintIcon(new JLabel(), gbi, 0, 0);
		gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
		gbi.setColor(color);
		gbi.fillRect(0, 0, mask.getWidth() - 1, mask.getHeight() - 1);
	}

	public boolean isColorPainted() {
		return colorPainted;
	}

	public void setColorPainted(boolean colorPainted) {
		this.colorPainted = colorPainted;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		delegate.paintIcon(c, g, x, y);
		if (colorPainted) {
			g.drawImage(mask, x, y, c);
		}
	}

	public int getIconWidth() {
		return delegate.getIconWidth();
	}

	public int getIconHeight() {
		return delegate.getIconHeight();
	}

}