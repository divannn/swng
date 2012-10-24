package MovablePopupWindow;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

/**
 * @author TOS
 * @author idanilov
 * @jdk 1.5
 */
public class MoveablePopupWindowDemo extends JFrame {

    public MoveablePopupWindowDemo() {
        super(MoveablePopupWindowDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }

    private JComponent createContents() {
        final JPanel result = new JPanel();
        final PopupPanel popContents = new PopupPanel();
        JTree tree = new JTree();
        tree.setEnabled(false);
        popContents.add(tree);
        final JButton b = new JButton("Open popup");
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MoveablePopupWindow popupWindow = new MoveablePopupWindow(result, popContents,
                        "Title");
                Point p = new Point(10, 10);
                SwingUtilities.convertPointToScreen(p, b);
                popupWindow.popupAt(p);
            }
        });
        result.add(b);
        return result;
    }

    public static void main(String[] args) {
        //        try {
        //            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        JFrame f = new MoveablePopupWindowDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
