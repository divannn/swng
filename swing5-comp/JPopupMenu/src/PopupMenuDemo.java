import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class PopupMenuDemo extends JFrame {

    public PopupMenuDemo() {
        super(PopupMenuDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPopupMenu jpm = new JPopupMenu();
        final JMenuItem jmi = new JMenuItem();
        jpm.add(jmi);

        class MouseHandler extends MouseAdapter
                implements MouseMotionListener {

            public void mouseDragged(MouseEvent me) {
                showPopup(me);
            }

            public void mouseMoved(MouseEvent me) {

            }

            public void mousePressed(MouseEvent me) {
                showPopup(me);
            }

            public void mouseReleased(MouseEvent me) {
                jpm.setVisible(false);
            }

            private void showPopup(MouseEvent me) {
                jmi.setText("" + me.getX() + "," + me.getY());
                jpm.show(getContentPane(), me.getX(), me.getY());
            }

        }

        MouseHandler mh = new MouseHandler();
        getContentPane().addMouseMotionListener(mh);
        getContentPane().addMouseListener(mh);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new PopupMenuDemo();
        f.setSize(300, 300);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }
}