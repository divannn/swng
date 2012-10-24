package CheckBoxInComponents.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * Was taken from WindowsIconFactory#RadioButtonIcon.
 * @author idanilov
 *
 */
public class FlatRadioButtonIcon implements Icon {
    
    public final static int SIZE = 13;

    public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
    	AbstractButton b = (AbstractButton) c;
	    ButtonModel model = b.getModel();
	
		// fill interior
		if ((model.isPressed() && model.isArmed()) || !model.isEnabled()) {
		    g.setColor(UIManager.getColor("RadioButton.background"));
		} else {
		    g.setColor(UIManager.getColor("RadioButton.interiorBackground"));
		}
		g.fillRect(x+2, y+2, 8, 8);

	    // outer left arc
		//g.setColor(UIManager.getColor("RadioButton.shadow"));
		g.setColor(Color.BLACK);		
		g.drawLine(x+4, y+0, x+7, y+0);
		g.drawLine(x+2, y+1, x+3, y+1);
		g.drawLine(x+8, y+1, x+9, y+1);
		g.drawLine(x+1, y+2, x+1, y+3);
		g.drawLine(x+0, y+4, x+0, y+7);
		g.drawLine(x+1, y+8, x+1, y+9);

		// outer right arc
		//g.setColor(UIManager.getColor("RadioButton.highlight"));
		g.setColor(Color.BLACK);
		g.drawLine(x+2, y+10, x+3, y+10);
		g.drawLine(x+4, y+11, x+7, y+11);
		g.drawLine(x+8, y+10, x+9, y+10);
		g.drawLine(x+10, y+9, x+10, y+8);
		g.drawLine(x+11, y+7, x+11, y+4);
		g.drawLine(x+10, y+3, x+10, y+2);

		// inner left arc
		//g.setColor(UIManager.getColor("RadioButton.darkShadow"));
		g.setColor(Color.BLACK);
		g.drawLine(x+4, y+1, x+7, y+1);
		g.drawLine(x+2, y+2, x+3, y+2);
		g.drawLine(x+8, y+2, x+9, y+2);
		g.drawLine(x+2, y+3, x+2, y+3);
		g.drawLine(x+1, y+4, x+1, y+7);
		g.drawLine(x+2, y+8, x+2, y+8);

		// inner right arc
		//g.setColor(UIManager.getColor("RadioButton.light"));
		g.setColor(Color.BLACK);
		g.drawLine(x+2,  y+9,  x+3,  y+9);
		g.drawLine(x+4,  y+10, x+7,  y+10);
		g.drawLine(x+8,  y+9,  x+9,  y+9);
		g.drawLine(x+9,  y+8,  x+9,  y+8);
		g.drawLine(x+10, y+7,  x+10, y+4);
		g.drawLine(x+9,  y+3,  x+9,  y+3);

		// indicate whether selected or not
		if (model.isSelected()) {
		    g.setColor(UIManager.getColor("RadioButton.darkShadow"));
		    g.fillRect(x+4, y+5, 4, 2);
		    g.fillRect(x+5, y+4, 2, 4);
		} 
    }
    
    public int getIconWidth() {
        return SIZE;
    }
    
    public int getIconHeight() {
        return SIZE;
    }
}