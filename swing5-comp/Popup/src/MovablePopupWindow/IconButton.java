package MovablePopupWindow;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;

public class IconButton extends JButton {

    public IconButton(String icon_name, String rollover_icon_name, ActionListener listener) {
        this(icon_name, rollover_icon_name, null, listener);
    }

    public IconButton(String icon_name, String rollover_icon_name, String disabled_icon_name,
            ActionListener listener) {
        this(nullableIcon(icon_name), nullableIcon(rollover_icon_name),
                nullableIcon(disabled_icon_name), listener);
    }

    public IconButton(Icon icon, Icon rollover_icon, Icon disabled_icon, ActionListener listener) {
        super(icon);
        if (rollover_icon != null)
            setRolloverIcon(rollover_icon);
        if (disabled_icon != null)
            setDisabledIcon(disabled_icon);
        if (listener != null)
            addActionListener(listener);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFocusable(false);
        setBorder(null);
    }

    private static Icon nullableIcon(String icon_name) {
        return icon_name != null ? UIManager.getIcon(icon_name) : null;
    }

}
