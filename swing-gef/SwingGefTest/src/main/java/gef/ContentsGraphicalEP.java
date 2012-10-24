package gef;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.id.swingeditor.d2.LineBorder;
import com.id.swingeditor.d2.XFigure;

/**
 * @author idanilov
 *
 */
public class ContentsGraphicalEP extends BaseGraphicalEp {

	@Override
	protected XFigure createFigure() {
		return new ContentsFigure();
	}

	private static class ContentsFigure extends NodeFigure {

		public ContentsFigure() {
			super();
			setBorder(new LineBorder(2, Color.RED));
			setOpaque(true);
			setCursor(null);
			setBackgroundColor(Color.LIGHT_GRAY);
		}

		@Override
		protected void paintFigure(Graphics2D graphics) {
			super.paintFigure(graphics);
			Rectangle2D b = getBounds();
			//			graphics.setColor(Color.RED);
			//			graphics.drawString("Contents", b.getBounds().width / 2, b.getBounds().height / 2);
		}

	}

}
