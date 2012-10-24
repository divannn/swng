package DocumentGuard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 * @author idanilov
 * 
 */
public class GuardBlockHighlightPainter
		implements Highlighter.HighlightPainter {

	private Color color;

	public GuardBlockHighlightPainter() {
		this(new Color(225, 236, 247));
	}
	
	public GuardBlockHighlightPainter(final Color color) {
		this.color = color;
	}

	public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
		paintImpl(g, p0 - 1, p1 + 1, bounds, c); // trick.
	}

	private void paintImpl(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
		Rectangle alloc = bounds.getBounds();
		try {
			TextUI mapper = c.getUI();
			Rectangle p0 = mapper.modelToView(c, offs0);
			Rectangle p1 = mapper.modelToView(c, offs1);
			g.setColor(color);
			if (p0.y == p1.y) {
				// same line, render a rectangle.
				Rectangle r = p0.union(p1);
				g.fillRect(r.x, r.y, r.width, r.height);
			} else {
				// different lines.
				int p0ToMarginWidth = alloc.x + alloc.width - p0.x;
				g.fillRect(p0.x, p0.y, p0ToMarginWidth, p0.height);
				if ((p0.y + p0.height) != p1.y) {
					g.fillRect(alloc.x, p0.y + p0.height, alloc.width, p1.y - (p0.y + p0.height));
				}
				g.fillRect(alloc.x, p1.y, (p1.x - alloc.x), p1.height);
			}
		} catch (BadLocationException ble) {
		}
	}
}
