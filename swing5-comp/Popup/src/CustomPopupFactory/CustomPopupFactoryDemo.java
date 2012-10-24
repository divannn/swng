package CustomPopupFactory;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.PopupFactory;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class CustomPopupFactoryDemo extends JFrame {

    public CustomPopupFactoryDemo() {
        super(CustomPopupFactoryDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
        JButton b = new JButton("Hover me!");
        b.setToolTipText("tiptiptiptip");
        getContentPane().add(b);
    }

    private JComponent createContents() {
        JPanel result = new JPanel();
        return result;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PopupFactory.setSharedInstance(new TranslucentPopupFactory());
        JFrame f = new CustomPopupFactoryDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
