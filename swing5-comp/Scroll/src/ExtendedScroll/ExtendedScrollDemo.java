package ExtendedScroll;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * Usage of middle mouse button to automatically scroll contents af any
 * JScrollPane. The same as in MS Internet Explorer. 
 * <br>
 * <br>
 * When user clicks on any component inside scrollpane with middle button, a
 * scroll icon appears at that location When the user moves the mouse to any
 * side of the scroll icon, the scrolling starting in that direction. The speed
 * of scrolling depends on how much distance the mouse is from the scroll icon.
 * @author santosh
 * @jdk 1.5
 */
public class ExtendedScrollDemo extends JFrame {

	private static final String END_LINE = System.getProperty("line.separator");
	private static final String PATTERN = "Press middle mouse buttonon to use extended scrolling feature."
			+ END_LINE;

	public ExtendedScrollDemo() {
		super(ExtendedScrollDemo.class.getSimpleName());
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
				return new Dimension(200, 200);
			}
		};
		result.add(new JScrollPane(textArea), BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ExtendedScrollDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		ScrollGestureRecognizer.getInstance();
		f.setVisible(true);
	}

}
