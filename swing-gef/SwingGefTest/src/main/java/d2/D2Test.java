package d2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.id.swingeditor.d2.LineBorder;
import com.id.swingeditor.d2.LwSystem;
import com.id.swingeditor.d2.XBorder;
import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.d2.XFigureImpl;
import com.id.swingeditor.d2.XYLayout;

/**
 * @author idanilov
 *
 */
public class D2Test extends JFrame {

	public static void main(String[] args) {
		D2Test f = new D2Test();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setSize(500, 400);
		f.setVisible(true);
	}

	public D2Test() {
		super(D2Test.class.getSimpleName());
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		final JComponent editor = createEditor();
		result.add(editor, BorderLayout.CENTER);
		JButton b = new JButton("Repaint Test");
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editor.repaint(10, 10, 10, 10);
				editor.repaint(100, 100, 10, 10);
			}
		});
		result.add(b, BorderLayout.NORTH);
		return result;
	}

	private JComponent createEditor() {
		LwSystem lws = new LwSystem();
		lws.setContents(createEditorContents(lws));
		return lws.getCanvas();
	}

	private XFigure createEditorContents(final LwSystem lws) {
		XFigure result = new XFigureImpl("content") {

			//			@Override
			//			protected void paintClientArea(Graphics2D g) {
			//				super.paintClientArea(g);
			//				g = (Graphics2D) g.create();
			//				try {
			//					paintCross(g);
			//				} finally {
			//					g.dispose();
			//				}
			//			}
			//			
			//			private void paintCross(Graphics2D g) {
			//				Rectangle r = getClientArea().getBounds();
			//				DefaultEventDispatcher evDisp = (DefaultEventDispatcher) lws.getEventDispatcher();
			//				MEvent d2MouseEvent = evDisp.getCurrentEvent();
			//				if (d2MouseEvent != null) {
			//					MouseEvent curMouse = d2MouseEvent.getAwtEvent();
			//					Point curPoint = curMouse.getPoint();
			//					if (curMouse.getID() == MouseEvent.MOUSE_EXITED) {
			//						curPoint = new Point(-1, -1);
			//					}
			//					if (r.contains(curPoint)) {
			//						g.setColor(Color.BLACK);
			//						g.drawLine(r.x, curPoint.y, r.width, curPoint.y);//hor line.
			//						g.drawLine(curPoint.x, r.y, curPoint.x, r.height);//vert line.    
			//					}
			//				}
			//			}
		};
		result.setOpaque(true);
		result.setBackgroundColor(Color.WHITE);
		result.setLayoutManager(new XYLayout());
		XBorder b = new LineBorder(2, Color.RED);
		result.setBorder(b);
		XFigure fig1 = new SampleFigure("fig1");
		fig1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fig1.setBackgroundColor(Color.MAGENTA);
		XBorder b1 = new LineBorder(2, Color.BLACK);
		fig1.setBorder(b1);
		result.add(fig1, new Rectangle(1, 1, 40, 50));

		XFigure fig2 = new SampleFigure("fig2");
		fig2.setBackgroundColor(Color.GREEN);
		fig2.setLayoutManager(new XYLayout());
		result.add(fig2, new Rectangle(120, 130, 40, 50));

		XFigure fig3 = new SampleFigure("fig3");
		fig3.setBackgroundColor(Color.BLUE);
		fig2.add(fig3, new Rectangle(5, 5, 20, 30));

		XFigure fig4 = new SampleFigure("fig4");
		fig4.setOpaque(false);
		fig4.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		XBorder b4 = new LineBorder(2, Color.YELLOW);
		fig4.setBorder(b4);
		result.add(fig4, new Rectangle(55, 65, 20, 60));
		return result;
	}

	private static class SampleFigure extends XFigureImpl {

		public SampleFigure(String name) {
			super(name);
			setTooltip(name);
			setOpaque(true);
		}

	}
}
