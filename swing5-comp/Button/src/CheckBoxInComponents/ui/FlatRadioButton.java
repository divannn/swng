package CheckBoxInComponents.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.plaf.ComponentUI;

import com.sun.java.swing.plaf.windows.WindowsRadioButtonUI;

/**
 * @author idanilov
 *
 */
public class FlatRadioButton extends JRadioButton {

	public FlatRadioButton() {
		super();
        setBorder(null);
	}

	public void updateUI() {
        setUI(FlatRadioButtonUI.createUI(this));
	}
	
	/**
	 * Draws a flat black-bordered radio icon.
	 * @author idanilov
	 *
	 */
	public static class FlatRadioButtonUI extends WindowsRadioButtonUI {

		private final static FlatRadioButtonUI flatRadioButtonUI = new FlatRadioButtonUI();
	    
		public static ComponentUI createUI(final JComponent c) {
			return flatRadioButtonUI;
	    }
		
	    public void installDefaults(final AbstractButton ab) {
	        super.installDefaults(ab);            
	        icon = new FlatRadioButtonIcon();
	    }
	    
	    protected void paintFocus(Graphics g, Rectangle textRect, Dimension size) {
	    	super.paintFocus(g, textRect, size);
	    }
	}	
}
