package ProcessUncaughtExceptionInEDT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class EDTExceptionHandlerDemo extends JFrame {

    public EDTExceptionHandlerDemo() {
        super(EDTExceptionHandlerDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JPanel result = new JPanel();
        JButton b = new JButton("Test");
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                throw new RuntimeException("Exception from EDT");
            }

        });
        result.add(b);
        return result;
    }

    public static void main(String[] args) {
        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
        JFrame f = new EDTExceptionHandlerDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * @see See the documentation of the private method handleException of java.awt.EventDispatchThread
     * @author idanilov
     *
     */
    public static class EDTExceptionHandler {

        static {
            System.err.println("EDTExceptionHandler.init()");
        }

        public void handle(Throwable thr) {
            //log to file or do whatever you want.
            System.err.println("Handled uncaught exception: " + thr.getMessage());
        }

    }

}
