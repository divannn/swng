package GradientPanel;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Paint;

import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class GradientPanel extends JPanel {

	public enum Direction {
		HOR,
		VER,
		DIAGONAL
	}
	
	private Direction direction;
	
	public GradientPanel(final Direction gir) {
		super();
		this.direction = gir;
	}
	
	public GradientPanel(final LayoutManager lm, final Direction dir) {
		super(lm);
		this.direction = dir;
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isOpaque()) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        Color controlColor = UIManager.getColor("control");
        Paint oldPaint = g2.getPaint();
        int x1, y1 , x2, y2;
        switch (direction) {
			case HOR:
				x1 = 0;
				y1 = 0;
				x2 = width;
				y2 = 0;
				break;
			case VER:
				x1 = 0;
				y1 = 0;
				x2 = 0;
				y2 = height;				
				break;
			case DIAGONAL:
			default:
				x1 = 0;
				y1 = 0;
				x2 = width;
				y2 = height;				
				break;
		}
        g2.setPaint(new GradientPaint(x1, y1, Color.WHITE, x2, y2, controlColor));
        g2.fillRect(0, 0, width, height);
        g2.setPaint(oldPaint);
    }
    
}