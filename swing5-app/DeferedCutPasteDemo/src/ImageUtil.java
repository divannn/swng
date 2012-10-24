import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

/**
 * @author idanilov
 * 
 */
public abstract class ImageUtil {

	private static JLabel imgObserver = new JLabel();

	private ImageUtil() {
	}

	/**
	 * @param img
	 * @return dimmed image from source image
	 */
	public static Image createGhostImage(final Image img) {
		BufferedImage result = new BufferedImage(img.getWidth(imgObserver), img
				.getHeight(imgObserver), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g2 = result.createGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f));
		g2.drawImage(img, 0, 0, result.getWidth(), result.getHeight(), imgObserver);
		g2.dispose();
		return result;
	}

}
