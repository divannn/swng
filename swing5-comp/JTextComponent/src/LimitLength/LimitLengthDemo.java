package LimitLength;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Simple way restrict text length that can be entered.
 * @author idanilov
 * @jdk 1.5
 * 
 */
public class LimitLengthDemo extends JFrame {

	public LimitLengthDemo() {
		super(LimitLengthDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTextField textField = new JTextField(20);
		AbstractDocument doc = (AbstractDocument) textField.getDocument();
		doc.setDocumentFilter(new DocumentSizeFilter(5));
		result.add(textField, BorderLayout.NORTH);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new LimitLengthDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class DocumentSizeFilter extends DocumentFilter {

		private int maxCharacters;

		public DocumentSizeFilter(int maxChars) {
			maxCharacters = maxChars;
		}

		public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
				throws BadLocationException {

			//This rejects the entire insertion if it would make
			//the contents too long. Another option would be
			//to truncate the inserted string so the contents
			//would be exactly maxCharacters in length.
			if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
				super.insertString(fb, offs, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}

		public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
				throws BadLocationException {
			//This rejects the entire replacement if it would make
			//the contents too long. Another option would be
			//to truncate the replacement string so the contents
			//would be exactly maxCharacters in length.
			if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
				super.replace(fb, offs, length, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}

	}
}
