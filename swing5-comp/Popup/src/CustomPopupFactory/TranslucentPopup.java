package CustomPopupFactory;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JWindow;
import javax.swing.Popup;

/**
 * @author idanilov
 *
 */
public class TranslucentPopup extends Popup {

    JWindow popupWindow;

    TranslucentPopup(Component owner, Component contents, int ownerX, int ownerY) {
        // create a new heavyweight window
        this.popupWindow = new JWindow();
        // mark the popup with partial opacity
        //[ID]        
        //        com.sun.awt.AWTUtilities.setWindowOpacity(popupWindow,
        //                (contents instanceof JToolTip) ? 0.8f : 0.95f);
        // determine the popup location
        popupWindow.setLocation(ownerX, ownerY);
        // add the contents to the popup
        popupWindow.getContentPane().add(contents, BorderLayout.CENTER);
        contents.invalidate();
        //[ID]
        //JComponent parent = (JComponent) contents.getParent();
        // set the shadow border
        //parent.setBorder(new ShadowPopupBorder());
    }

    @Override
    public void show() {
        this.popupWindow.setVisible(true);
        this.popupWindow.pack();
        // mark the window as non-opaque, so that the
        // shadow border pixels take on the per-pixel
        // translucency
        //[ID]
        //com.sun.awt.AWTUtilities.setWindowOpaque(this.popupWindow, false);
    }

    @Override
    public void hide() {
        this.popupWindow.setVisible(false);
        this.popupWindow.removeAll();
        this.popupWindow.dispose();
    }
}
