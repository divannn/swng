package AutoIndent;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author idanilov
 * 
 */
public class AutoIndentAction extends AbstractAction {

	public void actionPerformed(ActionEvent ae) {
		JTextArea comp = (JTextArea) ae.getSource();
		Document doc = comp.getDocument();
		if (comp.isEditable()) {
			try {
				int line = comp.getLineOfOffset(comp.getCaretPosition());
				int start = comp.getLineStartOffset(line);
				int end = comp.getLineEndOffset(line);
				String str = doc.getText(start, end - start - 1);
				String whiteSpace = getLeadingWhiteSpace(str);
				doc.insertString(comp.getCaretPosition(), '\n' + whiteSpace, null);
			} catch (BadLocationException ex) {
				try {
					doc.insertString(comp.getCaretPosition(), "\n", null);
				} catch (BadLocationException ble) {
				}
			}
		}
	}

	/**
	 * Returns leading white space characters in the specified string.
	 */
	private String getLeadingWhiteSpace(final String str) {
		return str.substring(0, getLeadingWhiteSpaceWidth(str));
	}

	/**
	 * Returns the number of leading white space characters in the specified
	 * string.
	 */
	private int getLeadingWhiteSpaceWidth(final String str) {
		int whitespace = 0;
		while (whitespace < str.length()) {
			char ch = str.charAt(whitespace);
			if (ch == ' ' || ch == '\t') {
				whitespace++;
			} else {
				break;
			}
		}
		return whitespace;
	}
	
}
