package WaterMark.custompaint;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * An extension of <code>WatermarkPainter</code> that
 * animates a waving Duke in the bottom right corner
 * of the component.
 */
public class AnimPainter extends WatermarkPainter {
    
    /** A single image containing frames of Duke */
    private static final Image animImage
        = getImage(AnimPainter.class.getResource("duke_anim.gif"));
    
    /** The number of frames in the image */
    private static final int numFrames = 10;
    
    /** The width of each frame */
    private static final int animW = animImage.getWidth(null) / numFrames;
    
    /** The height of each frame */
    private static final int animH = animImage.getHeight(null);
    
    /** The amount of time to wait between each frame */
    private static final int animDelay = 100;

    /** The current frame number */
    private int currentFrame = 0;

    /**
     * Listens for events on the timer. Increments the frame
     * number and repaints the component.
     */
    private ActionListener timerListener = new ActionListener() {
	public void actionPerformed(ActionEvent ae) {
	    currentFrame = (currentFrame + 1) % numFrames;

	    int rpX = getComponent().getWidth() - animW - 5;
	    int rpY = getComponent().getHeight() - animH - 5;
	    getComponent().paintImmediately(rpX, rpY, animW, animH);
	}
    };
    
    /** The timer that controls the animation */
    private Timer timer = new Timer(animDelay, timerListener);

    public void paint(Graphics g) {
	// if the animation image exists, paint a frame of animation
	if (animImage != null) {
	    // calculate x and y positions to paint at
	    int xloc = getComponent().getWidth() - animW - 5;
	    int yloc = getComponent().getHeight() - animH - 5;

            // calculate the x position in the image where
            // the current frame starts
	    int imageX = currentFrame * animW;

            // paint the current frame by specifying the target
            // co-ordinates to paint at and the source co-ordinates
            // representing the portion of the image to paint
            // (ie. the co-ordinates representing the current frame)
            g.drawImage(animImage,
                        xloc, yloc, xloc + animW, yloc + animH,
                        imageX, 0, imageX + animW, animH,
                        getComponent());
	}
    }

    public void start() {
	if (animImage != null) {
	    timer.start();
	}
    }
    
    public void stop() {
	if (animImage != null) {
	    timer.stop();
	}
    }

}
