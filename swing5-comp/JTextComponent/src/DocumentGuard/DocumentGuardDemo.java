package DocumentGuard;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;


/**
 * @author santosh
 *
 */
public class DocumentGuardDemo extends JFrame {

	private static final String END_LINE = System.getProperty("line.separator");
	private static final String PATTERN = "Some parts of this text cannot be edited."
			+ END_LINE;
	
	public DocumentGuardDemo() {
		super(DocumentGuardDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
		
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String text = "";
		for (int i = 0; i < 30; i++) {
			text += PATTERN;
		}
		JTextArea textArea = new JTextArea(text) {

			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(200, 400);
			}
		};
		DocumentGuard guard = new DocumentGuard(textArea);
		try {
			guard.addGuardedLines(0, 0);
			guard.addGuardedLines(2, 5); 
			guard.addGuardedLines(11, 11); 
			guard.addGuardedLines(15, 15); 
		} catch (BadLocationException ble) {
			ble.printStackTrace();
		} 
		guard.setGuardEnabled(true);		
		result.add(new JScrollPane(textArea), BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new DocumentGuardDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
