package WaterMark.custompaint;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;

/**
 * An extension of <code>WatermarkPainter</code> that
 * tiles an image to fill the entire component.
 */
public class FillPainter extends WatermarkPainter {

    /** The image to paint in the background */
    private Image bgImage;

    public FillPainter() {
        bgImage = getImage(FillPainter.class.getResource("beach.jpg"));
    }

    public String[] getCommands() {
        return new String[] {"beach.jpg",
                             "rocks_waves.jpg",
                             "green_swirl.jpg",
                             "fire_ring.jpg"};
    }

    public void paint(Graphics g) {
        // if a background image exists, paint it
        if (bgImage != null) {
            int width = getComponent().getWidth();
            int height = getComponent().getHeight();
            int imageW = bgImage.getWidth(null);
            int imageH = bgImage.getHeight(null);

            // we'll tile the image to fill our area
            for (int x = 0; x < width; x += imageW) {
                for (int y = 0; y < height; y += imageH) {
                    g.drawImage(bgImage, x, y, getComponent());
                }
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        bgImage = getImage(getClass().getResource(ae.getActionCommand()));
        getComponent().repaint();
    }

}
