package DocumentGuard;

import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;

/**
 * Allows only complete lines to be guarded, not a portion of line.
 * 
 * @author idanilov
 * 
 */
public class DocumentGuard {

	private JTextArea textComp;
	private Highlighter.HighlightPainter highlightPainter;
	private GuardFilter guardFilter;

	public DocumentGuard(final JTextArea textComp) {
		this.textComp = textComp;
		highlightPainter = new GuardBlockHighlightPainter();
		guardFilter = new GuardFilter();
	}

	public void addGuardedLines(final int fromLine, final int toLine) throws BadLocationException {
		int fromOffset = textComp.getLineStartOffset(fromLine);
		int toOffset = textComp.getLineEndOffset(toLine);
		addGuardedBlock(fromOffset, toOffset);
	}

	private void addGuardedBlock(final int fromOffset, final int toOffset)
			throws BadLocationException {
		AbstractDocument doc = (AbstractDocument) textComp.getDocument();
		guardFilter.addGuardedBlock(doc.createPosition(fromOffset), doc
				.createPosition(toOffset - 1));
		textComp.getHighlighter().addHighlight(fromOffset + 1, toOffset - 1, highlightPainter);
	}

	public void setGuardEnabled(final boolean enable) {
		AbstractDocument doc = (AbstractDocument) textComp.getDocument();
		doc.setDocumentFilter(enable ? guardFilter : null);
	}

}