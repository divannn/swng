package MovablePopupWindow;
import javax.swing.JPanel;

/**
 * @author idanilov
 *
 */
public class PopupPanel extends JPanel {

    public void setPopupWindow(MoveablePopupWindow popup) {
    }

    public void popupWindowClosed() {
        System.err.println("Closed");
    }
}
