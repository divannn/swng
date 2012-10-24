package LineHighlight._1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 * @author santosh
 * @author idanilov
 *
 */
public class CurrentLineHighlighter1 {

	private static final String LINE = "line";
	private static final String PREVIOUS_CARET = "previousCaret";
	private static final Color COLOR = new Color(255, 255, 204);

	private static Highlighter.HighlightPainter painter = new Highlighter.HighlightPainter() {

		public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent tc) {
			try {
				Rectangle r = tc.modelToView(tc.getCaretPosition());
				g.setColor(COLOR);
				g.fillRect(0, r.y, tc.getWidth(), r.height);
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	};

	private static CaretListener caretListener = new CaretListener() {

		public void caretUpdate(CaretEvent ce) {
			CurrentLineHighlighter1.higlightUpdate((JTextComponent) ce.getSource());
		}

	};

	private static MouseListener mouseListener = new MouseAdapter() {

		public void mousePressed(MouseEvent me) {
			JTextComponent tc = (JTextComponent) me.getSource();
			CurrentLineHighlighter1.higlightUpdate(tc);
		}

	};

	//it seems text component is not repainted during drag - so repaint explicitly. 
	private static MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {

		public void mouseDragged(MouseEvent me) {
			JTextComponent tc = (JTextComponent) me.getSource();
			CurrentLineHighlighter1.higlightUpdate(tc);
		}

	};

	private static void higlightUpdate(final JTextComponent tc) {
		try {
			int prevCaret = ((Integer) tc.getClientProperty(PREVIOUS_CARET)).intValue();
			Rectangle prev = tc.modelToView(prevCaret);
			Rectangle cur = tc.modelToView(tc.getCaretPosition());
			if (prev.y != cur.y) {
				tc.repaint(0, prev.y, tc.getWidth(), cur.height);
				tc.repaint(0, cur.y, tc.getWidth(), cur.height);
			}
		} catch (BadLocationException ble) {
			//igore.
		}
		tc.putClientProperty(PREVIOUS_CARET, new Integer(tc.getCaretPosition()));
	}

	public static void install(final JTextComponent tc) {
		try {
			Object obj = tc.getHighlighter().addHighlight(0, 0, painter);
			tc.putClientProperty(LINE, obj);
			tc.putClientProperty(PREVIOUS_CARET, new Integer(tc.getCaretPosition()));
			tc.addCaretListener(caretListener);
			tc.addMouseListener(mouseListener);
			tc.addMouseMotionListener(mouseMotionListener);
		} catch (BadLocationException ble) {
			ble.printStackTrace();
		}
	}

	public static void uninstall(final JTextComponent tc) {
		tc.putClientProperty(PREVIOUS_CARET, null);
		tc.putClientProperty(LINE, null);
		tc.removeCaretListener(caretListener);
		tc.removeMouseListener(mouseListener);
		tc.removeMouseMotionListener(mouseMotionListener);
	}

}
