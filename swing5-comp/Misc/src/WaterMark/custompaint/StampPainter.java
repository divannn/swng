package WaterMark.custompaint;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;

/**
 * An extension of <code>WatermarkPainter</code> that
 * paints a translucent image in the bottom right
 * corner of the component.
 *
 */
public class StampPainter extends WatermarkPainter {

    /** The image to paint in the foreground */
    private Image fgImage;
   
    public StampPainter() {
        fgImage = getImage(getClass().getResource("duke_wave.png"));
    }
    
    public String[] getCommands() {
        return new String[] {"duke_wave.png",
                             "duke_hips.png"};
    }
    
    public void paint(Graphics g) {
        // if a foreground image exists, paint it
        if (fgImage != null) {
            int imageW = fgImage.getWidth(null);
            int imageH = fgImage.getHeight(null);
            
            // we need to cast to Graphics2D for this operation
            Graphics2D g2d = (Graphics2D)g;
            
            // create the composite to use for the translucency
            AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            
            // save the old composite
            Composite oldComp = g2d.getComposite();

            // set the translucent composite
            g2d.setComposite(comp);
            
            // calculate the x and y positions to paint at
            int xloc = getComponent().getWidth() - imageW - 5;
            int yloc = getComponent().getHeight() - imageH - 5;

            // paint the image using the new composite
            g2d.drawImage(fgImage, xloc, yloc, getComponent());

            // restore the original composite
            g2d.setComposite(oldComp);
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        fgImage = getImage(getClass().getResource(ae.getActionCommand()));
        getComponent().repaint();
    }

}
