package ShortcutButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * @author idanilov
 *
 */
public class ShortcutButton extends /*JCheckBox*/JToggleButton {

    public static final Icon DEFAULT_ROLLOVER_ICON = new ImageIcon(ShortcutButton.class
            .getResource("icon_addshortcut_regular_HL.png"));
    public static final Icon DEFAULT_ICON = new ImageIcon(ShortcutButton.class
            .getResource("icon_addshortcut_regular.png"));
    public static final Icon SELECTED_ROLLOVER_ICON = new ImageIcon(ShortcutButton.class
            .getResource("icon_addshortcut_checked_HL.png"));
    public static final Icon SELECTED_ICON = new ImageIcon(ShortcutButton.class
            .getResource("icon_addshortcut_checked.png"));

    public ShortcutButton() {
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setSelectedIcon(SELECTED_ICON);
        setRolloverSelectedIcon(SELECTED_ROLLOVER_ICON);
        setIcon(DEFAULT_ICON);
        setRolloverIcon(DEFAULT_ROLLOVER_ICON);
        setRolloverEnabled(true);
        setContentAreaFilled(false);
        setFocusPainted(false);
        //        setBorderPainted(false);
    }
}
