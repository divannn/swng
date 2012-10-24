package WaterMark.layeredpane;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class JWatermark extends JComponent {

    private String text = "";
    private Font font = UIManager.getFont("Label.font");

    private static final float OPACITY = 0.2f;
    private static final double ROTATION = -(Math.PI/4);
    private static Integer WATER_MARK_LAYER = new Integer(JLayeredPane.FRAME_CONTENT_LAYER.intValue() + 1);

    private JWatermark(String text) {
        this.text = text;
    }

    public void paintComponent(Graphics g) {
        Window window = SwingUtilities.getWindowAncestor(this);
        Rectangle viewRect = window.getBounds();
        int halfWidth = viewRect.width / 2;
        int halfHeight = viewRect.height / 2;
        Graphics2D graphics2D = (Graphics2D) g;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, OPACITY);
        graphics2D.setComposite(ac);
        final int minSide = Math.min(viewRect.width, viewRect.height);
        font = font.deriveFont((float)(minSide/10));
        graphics2D.setFont(font);
        Rectangle textRect = new Rectangle();
        graphics2D.rotate(ROTATION, halfWidth, halfHeight);
        SwingUtilities.layoutCompoundLabel(this, graphics2D.getFontMetrics(), text, null,
                SwingConstants.CENTER, SwingConstants.CENTER,
                SwingConstants.CENTER, SwingConstants.CENTER,
                viewRect, new Rectangle(), textRect,
                0);
        graphics2D.setColor(getForeground());
        int x = halfWidth - textRect.width/2;
        int y = halfHeight + textRect.height/5;
        graphics2D.drawString(text, x, y);
    }

    public static void createWatermark(final JFrame frame, final String text) {
        JWatermark watermark = new JWatermark(text);
        watermark.setOpaque(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        watermark.setBounds(0, 0,
                (int)screenSize.getWidth(),
                (int)screenSize.getHeight());
        watermark.setVisible(true);
        watermark.setForeground(Color.RED);
        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(watermark, WATER_MARK_LAYER, 0);
    }

}
