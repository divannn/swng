package DblBuffered;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * @author idanilov
 *
 */
public class DblBufferedPaintable extends JComponent {

    private Image offscreen;
    private boolean dblBuffered;
    private Image img;

    public DblBufferedPaintable(boolean dblBuffered) {
        try {
            img = ImageIO.read(DblBufferedPaintable.class.getResourceAsStream("sunset.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dblBuffered = dblBuffered;
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long s = System.currentTimeMillis();
        Graphics2D g2 = (Graphics2D) g;
        if (dblBuffered) {
            if (offscreen == null || sizeChanged()) {
                if (offscreen != null) {
                    offscreen.flush();
                }
                offscreen = createOffscreen();
                Graphics2D offG2 = (Graphics2D) offscreen.getGraphics();
                try {
                    draw(offG2);
                } finally {
                    offG2.dispose();
                }
            }
            g2.drawImage(offscreen, 0, 0, null);//paint prepared image.
        } else {
            draw(g2);//usual paint.
        }
        long e = System.currentTimeMillis();
        System.err.println("paint time: " + (e - s));
    }

    private void draw(Graphics2D g2) {
        for (int i = 0; i < 1000; i++) {//pretend really difficult paint.
            g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);//scale image for example (time comsuming operation).
        }
    }

    private Image createOffscreen() {
        return new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
    }

    private boolean sizeChanged() {
        return offscreen != null
                && (offscreen.getHeight(null) != getHeight() || offscreen.getWidth(null) != getWidth());
    }

}