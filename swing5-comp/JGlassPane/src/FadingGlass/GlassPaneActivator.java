package FadingGlass;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * @author idanilov
 *
 */
public class GlassPaneActivator 
		implements AWTEventListener {
	
    private Component glass, oldGlass;
    private int keyCode;

    public GlassPaneActivator(final Component newGlassPane, final Component oldGlassPane,final int keyCode) {
    	if (newGlassPane == oldGlassPane) {
    		throw new IllegalArgumentException("Provide different glass panes");
    	}
        this.glass = newGlassPane;
        this.oldGlass = oldGlassPane;
        this.keyCode = keyCode;
    }

    public void install() {
    	Toolkit.getDefaultToolkit().addAWTEventListener(this,AWTEvent.KEY_EVENT_MASK);
	}

    public void deinstall() {
    	Toolkit.getDefaultToolkit().removeAWTEventListener(this);
	}
    
    public void eventDispatched(AWTEvent event) {
        if (event.getID() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getKeyCode() == keyCode) {
                Window windowAncestor = SwingUtilities.getWindowAncestor((Component) event.getSource());
                if (windowAncestor instanceof JFrame) {
                	boolean toggle = !glass.isVisible();
                	if (toggle) {
                    	((JFrame)windowAncestor).setGlassPane(glass);
                    	glass.setVisible(true);
                	} else {
                		glass.setVisible(false);
                		((JFrame)windowAncestor).setGlassPane(oldGlass);
                	}
                }
            }
        }
    }

}