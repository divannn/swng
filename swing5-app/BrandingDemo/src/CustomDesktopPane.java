import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 * @author idanilov
 *
 */
public class CustomDesktopPane extends JDesktopPane {
	
	private final static ImageIcon ALCATEL_LOGO = new ImageIcon(BrandingFrame.class.getResource("about.gif")); 
	private final static int LINE_BOTTOM_INSET = ALCATEL_LOGO != null ? ALCATEL_LOGO.getIconHeight() : 50;
	private final static Color ALCATEL_ORANGE = new Color(255,128,64);
	private final static int LINE_WIDTH = 2;
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color oldColor = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(),getHeight());
		g.setColor(ALCATEL_ORANGE);
		g.fillRect(0, getHeight() - LINE_BOTTOM_INSET, getWidth(),LINE_WIDTH);
		g.setColor(oldColor);
		ALCATEL_LOGO.paintIcon(this,g,0,getHeight() - LINE_BOTTOM_INSET + LINE_WIDTH);
	}
	
}