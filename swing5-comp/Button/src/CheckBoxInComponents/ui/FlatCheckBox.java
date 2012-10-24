package CheckBoxInComponents.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.sun.java.swing.plaf.windows.WindowsCheckBoxUI;

/**
 * @author idanilov
 *
 */
public class FlatCheckBox extends JCheckBox {

	public FlatCheckBox() {
		super();
		setBorderPaintedFlat(true);
        setBorder(null);
	}

	public void updateUI() {
        setUI(FlatCheckBoxUI.createUI(this));
	}

    /**
     * Draws a flat black-bordered check icon.
     * @author idanilov
     *
     */
    public static class FlatCheckBoxUI extends WindowsCheckBoxUI {

    	private final static FlatCheckBoxUI flatCheckBoxUI = new FlatCheckBoxUI();
        
    	public static ComponentUI createUI(final JComponent c) {
    		return flatCheckBoxUI;
        }
    	
        public void installDefaults(final AbstractButton ab) {
            super.installDefaults(ab);            
            icon = new FlatCheckBoxIcon();
        }
        
        protected void paintFocus(Graphics g, Rectangle textRect, Dimension size) {
        	super.paintFocus(g, textRect, size);
        }
    }	
}
