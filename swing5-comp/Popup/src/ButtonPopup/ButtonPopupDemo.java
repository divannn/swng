package ButtonPopup;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class ButtonPopupDemo extends JFrame {

    public ButtonPopupDemo() {
        super(ButtonPopupDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton start = new JButton("Pick Me for Popup");
        ActionListener actionListener = new ShowPopupActionListener(this);
        start.addActionListener(actionListener);
        getContentPane().add(start);
    }

    public static void main(final String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new ButtonPopupDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

class ShowPopupActionListener
        implements ActionListener {

    private Component component;

    ShowPopupActionListener(Component component) {
        this.component = component;
    }

    public synchronized void actionPerformed(ActionEvent actionEvent) {
        JButton button = new JButton("Hello, World");
        PopupFactory factory = PopupFactory.getSharedInstance();
        Random random = new Random();
        int x = random.nextInt(600);
        int y = random.nextInt(600);
        final Popup popup = factory.getPopup(component, button, x, y);
        popup.show();
        //Component p = button.getParent();
        Component p = SwingUtilities.getWindowAncestor(button);
        System.err.println(">>> " + (p instanceof JWindow));
        ActionListener hider = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                popup.hide();
            }
        };
        //hide popup in 3 seconds.
        Timer timer = new Timer(3000, hider);
        timer.start();
    }
}
