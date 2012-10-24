package BubblePanel;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * @author unknown
 * @author idanilov
 * @jdk 1.5
 */
public class BubblePanelDemo extends JFrame {
	
	public BubblePanelDemo() {
		super(BubblePanelDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JComponent result = new JPanel(new BorderLayout());
        JBubblePanel bubblePanel = new JBubblePanel(new BorderLayout());
        JTextPane textPane = new JTextPane();
        SimpleAttributeSet normal = new SimpleAttributeSet();
        SimpleAttributeSet bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, true);
        try {
            textPane.getDocument().insertString(
                textPane.getDocument().getLength(),
                "Your connection to ",
                normal);
            textPane.getDocument().insertString(
                textPane.getDocument().getLength(),
                "cvs.dev.java.net ",
                bold);
            textPane.getDocument().insertString(
                textPane.getDocument().getLength(),
                "failed. Here are a few possible reasons.\n\n",
                normal);
            textPane.getDocument().insertString(
                textPane.getDocument().getLength(),
                " Your computer is may not be connected to the network.\n"
                    + "* The CVS server name may be entered incorrectly.\n\n",
                normal);
            textPane.getDocument().insertString(
                textPane.getDocument().getLength(),
                "If you still can not connect, please contact support at ",
                normal);
            textPane.getDocument().insertString(
                textPane.getDocument().getLength(),
                "support@cvsclient.org",
                bold);
            textPane.getDocument().insertString(
                textPane.getDocument().getLength(),
                ".",
                normal);
        } catch (BadLocationException ex){
            ex.printStackTrace();
        }
        bubblePanel.add(textPane, BorderLayout.CENTER);
		result.add(bubblePanel,BorderLayout.CENTER);
		return result;
	}
	
    public static void main(String[] args) {
        JFrame f = new BubblePanelDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
