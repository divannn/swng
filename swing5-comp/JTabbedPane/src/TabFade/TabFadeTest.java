package TabFade;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 * @author swinghacks
 * @jdk 1.5
 */
public class TabFadeTest extends JFrame {

    public TabFadeTest() {
        super(TabFadeTest.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new TabFadeTest();
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JComponent createContents() {
        JPanel result = new JPanel(new GridLayout(2, 1));
        JTabbedPane tab1 = new VenetianPane();
        tab1.addTab("t1", new JButton("Test Button 1"));
        tab1.addTab("t2", new JButton("Test Button 2"));
        result.add(tab1);
        JTabbedPane tab2 = new InOutPane();
        tab2.addTab("t1", new JButton("Test Button 1"));
        tab2.addTab("t2", new JButton("Test Button 2"));
        result.add(tab2);

        return result;
    }

}
