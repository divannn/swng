package BubblePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author idanilov
 *
 */
public class JBubblePanel extends JPanel {

    private static final Color YELLOW = new Color(255, 255, 204);
    private static final Color BUBBLE_BORDER = Color.GRAY;
    private final int ARC_WIDTH = 8;
    private final int ARC_HEIGHT = 8;
    protected static final int X_MARGIN = 4;
    protected static final int Y_MARGIN = 2;    

    public JBubblePanel() {
        super();
        init();
    }

    public JBubblePanel(LayoutManager layout) {
        super(layout);
        init();
    }

    protected void init() {
        this.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Insets insets = getRealInsets();
        Color savedColor = g2d.getColor();

        int rectX = insets.left;
        int rectY = insets.top;
        int rectWidth = getWidth() - insets.left - insets.right;
        int rectHeight = getHeight() - insets.top - insets.bottom;

        //paint the yellow interior.
        g2d.setColor(YELLOW);
        g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, ARC_WIDTH, ARC_HEIGHT);

        //draw the gray border.
        g2d.setColor(BUBBLE_BORDER);
        g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, ARC_WIDTH, ARC_HEIGHT);

        g2d.setColor(savedColor);
    }

    protected Insets getRealInsets() {
        return super.getInsets();
    }

    public Insets getInsets() {
        Insets realInsets = getRealInsets();
        Insets fakeInsets = new Insets(realInsets.top + Y_MARGIN, realInsets.left + X_MARGIN,
                realInsets.bottom + Y_MARGIN, realInsets.right + X_MARGIN);
        return fakeInsets;
    }

}
