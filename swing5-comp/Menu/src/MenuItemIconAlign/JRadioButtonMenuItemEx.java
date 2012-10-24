package MenuItemIconAlign;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.plaf.ComponentUI;

import com.sun.java.swing.plaf.windows.WindowsRadioButtonMenuItemUI;

/**
 * @author idanilov
 *
 */
class JRadioButtonMenuItemEx extends JRadioButtonMenuItem {
	
	public JRadioButtonMenuItemEx(String text, boolean selected) {
		super(text,selected);			
	}
	
	public void updateUI() {
		setUI(RadioButtonMenuItemExUI.createUI(this));
	}

	public static class RadioButtonMenuItemExUI extends WindowsRadioButtonMenuItemUI {
	    
		public static ComponentUI createUI(final JComponent c) {
			return new RadioButtonMenuItemExUI();
	    }
		
		protected void installDefaults() {
			super.installDefaults();
			checkIcon = new RadioButtonMenuItemIcon();
		}
	    
	}	
	/**
	 * Overriden from BasicIconFactory#RadioButtonMenuItemIcon to make size 9x9 - 
	 * the same as for BasicIconFactory#CheckBoxMenuItemIcon. This is needed for proper align radio menu items.
	 * @author idanilov
	 *
	 */
	private static class RadioButtonMenuItemIcon 
			implements Icon {
		
		public void paintIcon(Component c, Graphics g, int x, int y) {
		    AbstractButton b = (AbstractButton) c;
		    if (b.isSelected()) {
				g.fillOval(x+2, y+2, getIconWidth()-2, getIconHeight()-2);
		    }            
		}
		
	    public int getIconWidth() { 
	    	return 9; 
	    }
	    
		public int getIconHeight() { 
			return 9; 
		}
	
	}	
}
