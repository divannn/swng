package CustomPopupFactory;

import java.awt.Component;

import javax.swing.Popup;
import javax.swing.PopupFactory;

/**
 * @author idanilov
 *
 */
public class TranslucentPopupFactory extends PopupFactory {

    @Override
    public Popup getPopup(Component owner, Component contents, int x, int y)
            throws IllegalArgumentException {
        // A more complete implementation would cache and reuse popups.
        return new TranslucentPopup(owner, contents, x, y);
    }
}
