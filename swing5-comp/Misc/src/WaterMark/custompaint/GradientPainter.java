package WaterMark.custompaint;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

/**
 * An extension of <code>WatermarkPainter</code> that
 * paints the entire component with a gradient.
 *
 */
public class GradientPainter extends WatermarkPainter {
    
    /** The first color to use in the gradient */
    private static final Color color1 = Color.WHITE;
    
    /** The second color to use in the gradient */
    private static final Color color2 = new Color(128, 128, 255);

    public void paint(Graphics g) {
        int width = getComponent().getWidth();
        int height = getComponent().getHeight();

        // Create the gradient paint        
        GradientPaint paint = new GradientPaint(0, 0, color1, width, height, color2, true);
        
        // we need to cast to Graphics2D for this operation
        Graphics2D g2d = (Graphics2D)g;
        
        // save the old paint
        Paint oldPaint = g2d.getPaint();
        
        // set the paint to use for this operation
        g2d.setPaint(paint);
        
        // fill the background using the paint
        g2d.fillRect(0, 0, width, height);
        
        // restore the original paint
        g2d.setPaint(oldPaint);
    }

}
