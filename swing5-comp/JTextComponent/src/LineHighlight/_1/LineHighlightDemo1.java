package LineHighlight._1;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * @author idanilov
 * @jdk 1.5
 */
public class LineHighlightDemo1 extends JFrame {

    public LineHighlightDemo1() {
        super(LineHighlightDemo1.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }
    
    private JComponent createContents() {
        JPanel result = new JPanel(new BorderLayout());
        JTextArea ta = new JTextArea("text\n\ttext\n\t\ttext\n\t\t\ttext");
        CurrentLineHighlighter1.install(ta);
        result.add(new JScrollPane(ta),BorderLayout.CENTER);
        return result;
    }
    
    public static void main(String[] args) {
        JFrame f = new LineHighlightDemo1();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
}
