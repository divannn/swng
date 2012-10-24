package CheckBoxInComponents.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;

/**
 * Was taken from WindowsIconFactory#CheckBoxIcon.
 * @author idanilov
 *
 */
public class FlatCheckBoxIcon implements Icon {
    
    public final static int SIZE = 13;

    public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
    	JCheckBox cb = (JCheckBox)c;
	    ButtonModel model = cb.getModel();
		// outer bevel
		if(!cb.isBorderPaintedFlat()) {
		    // Outer top/left
		    g.setColor(UIManager.getColor("CheckBox.shadow"));
		    g.drawLine(x, y, x+11, y);
		    g.drawLine(x, y+1, x, y+11);

		    // Outer bottom/right
		    g.setColor(UIManager.getColor("CheckBox.highlight"));
		    g.drawLine(x+12, y, x+12, y+12);
		    g.drawLine(x, y+12, x+11, y+12);

		    // Inner top.left
		    g.setColor(UIManager.getColor("CheckBox.darkShadow"));
		    g.drawLine(x+1, y+1, x+10, y+1);
		    g.drawLine(x+1, y+2, x+1, y+10);

		    // Inner bottom/right
		    g.setColor(UIManager.getColor("CheckBox.light"));
		    g.drawLine(x+1, y+11, x+11, y+11);
		    g.drawLine(x+11, y+1, x+11, y+10);

		    // inside box
		    paintBackground(g,cb,x,y);
		} else {
			//XXX:draw flat black border.
		    //g.setColor(UIManager.getColor("CheckBox.shadow"));
		    g.setColor(Color.BLACK);
		    g.drawRect(x+1, y+1, SIZE-3, SIZE-3);
		    g.drawRect(x, y, SIZE-1, SIZE-1);//XXX: make border 2 pixel width.
		    paintBackground(g,cb,x,y);
		}

		if (model.isEnabled()) {
		    g.setColor(UIManager.getColor("CheckBox.darkShadow"));
		} else {
		    g.setColor(UIManager.getColor("CheckBox.shadow"));
		}

		// paint check
		if (model.isSelected()) {
		    g.drawLine(x+9, y+3, x+9, y+3);
		    g.drawLine(x+8, y+4, x+9, y+4);
		    g.drawLine(x+7, y+5, x+9, y+5);
		    g.drawLine(x+6, y+6, x+8, y+6);
		    g.drawLine(x+3, y+7, x+7, y+7);
		    g.drawLine(x+4, y+8, x+6, y+8);
		    g.drawLine(x+5, y+9, x+5, y+9);
		    g.drawLine(x+3, y+5, x+3, y+5);
		    g.drawLine(x+3, y+6, x+4, y+6);
		}
    }
    
    private void paintBackground(final Graphics g, final JCheckBox cb,final int x, final int y) {
	    ButtonModel model = cb.getModel();
	    if ((model.isPressed() && model.isArmed()) || !model.isEnabled()) {
			g.setColor(UIManager.getColor("CheckBox.background"));
	    } else {
			g.setColor(UIManager.getColor("CheckBox.interiorBackground"));
	    }
	    g.fillRect(x+2, y+2, SIZE-4, SIZE-4);
    }
    
    public int getIconWidth() {
        return SIZE;
    }
    
    public int getIconHeight() {
        return SIZE;
    }
}