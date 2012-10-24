package LineHighlight._2;

import java.awt.Color;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

/**
 * Highlight is performed till the end of the current line. 
 * @author idanilov
 *
 */
public class CurrentLineHighlighter2
		implements ChangeListener {

	static final Color DEFAULT_COLOR = new Color(0, 0, 0, 15);

	private Highlighter.HighlightPainter painter;
	private Object highlight;
	private JTextComponent textComp;

	public CurrentLineHighlighter2() {
		this(null);
	}

	public CurrentLineHighlighter2(Color highlightColor) {
		Color c = highlightColor != null ? highlightColor : DEFAULT_COLOR;
		painter = new DefaultHighlighter.DefaultHighlightPainter(c);
	}

	public void install(JTextComponent tc) {
		textComp = tc;
		textComp.getCaret().addChangeListener(this);
	}

	public void stateChanged(ChangeEvent evt) {
		if (highlight != null) {
			textComp.getHighlighter().removeHighlight(highlight);
			highlight = null;
		}
		int pos = textComp.getCaretPosition();
		try {
			int start = Utilities.getRowStart(textComp,pos);
			int end = Utilities.getRowEnd(textComp,pos);
			highlight = textComp.getHighlighter().addHighlight(start, end, painter);
		} catch (BadLocationException ble) {
			ble.printStackTrace();
		}

	}
}
