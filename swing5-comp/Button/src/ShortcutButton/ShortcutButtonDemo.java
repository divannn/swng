package ShortcutButton;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class ShortcutButtonDemo extends JFrame {

    protected Map<String, ShortcutButton> shortcuts = new HashMap<String, ShortcutButton>();

    public ShortcutButtonDemo() {
        super(ShortcutButtonDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JPanel result = new JPanel(new GridLayout(2, 1));
        JPanel row1 = new JPanel();
        JCheckBox cb1 = new JCheckBox(SettingInfo.SETTING1.getTitle());
        ShortcutButton sc1 = new ShortcutButton();
        sc1.setSelected(true);
        sc1.setToolTipText("Shortcut for " + SettingInfo.SETTING1.getTitle());
        shortcuts.put(SettingInfo.SETTING1.getId(), sc1);
        row1.add(cb1);
        row1.add(sc1);

        JPanel row2 = new JPanel();
        JCheckBox cb2 = new JCheckBox(SettingInfo.SETTING1.getTitle());
        ShortcutButton sc2 = new ShortcutButton();
        sc2.setToolTipText("Shortcut for " + SettingInfo.SETTING2.getTitle());
        shortcuts.put(SettingInfo.SETTING2.getId(), sc2);
        row2.add(cb2);
        row2.add(sc2);

        result.add(row1);
        result.add(row2);
        return result;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new ShortcutButtonDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
