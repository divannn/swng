package DblBuffered;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author idanilov
 * @jdk 1.5
 *
 */
public class DoubleBufferDemo extends JFrame {

    public DoubleBufferDemo() {
        super(DoubleBufferDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel(new BorderLayout());
        setContentPane(p);
        p.add(new DblBufferedPaintable(true), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        DoubleBufferDemo f = new DoubleBufferDemo();
        f.setLocationRelativeTo(null);
        f.setSize(300, 200);
        f.setVisible(true);
    }

}
