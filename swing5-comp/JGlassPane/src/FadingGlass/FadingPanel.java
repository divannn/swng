package FadingGlass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * @author idanilov
 *
 */
public class FadingPanel extends JComponent 
		implements ActionListener {
	
    private Timer ticker;
    private int alpha;
    private int step;

    public FadingPanel() {
		super();
		setOpaque(false);
		setVisible(false);
	}
    
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            if (ticker != null) {
                ticker.stop();
            }
            alpha = 0;
            step = 25;
            ticker = new Timer(50, this);
            ticker.start();
        } else {
            if (ticker != null) {
                ticker.stop();
                ticker = null;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 255, 255, alpha));
        Rectangle clip = g.getClipBounds();
        g.fillRect(clip.x, clip.y, clip.width, clip.height);
    }

    public void actionPerformed(ActionEvent e) {
        alpha += step;
        if (alpha >= 255) {
            alpha = 255;
            step = -step;
        } else if (alpha <= 0) {
            alpha = 0;
            step = -step;
        }
        repaint();
    }
    
}
