package d2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.id.swingeditor.d2.LineBorder;
import com.id.swingeditor.d2.LwSystem;
import com.id.swingeditor.d2.XBorder;
import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.d2.XFigureImpl;

/**
 * Use absolute positioning of figures (no layou managers).
 * @author idanilov
 *
 */
public class D2MoveResizeFigureTest extends JFrame {

	private XFigure contents;

	private XFigure parent1;
	private XFigure parent2;
	private XFigure parent3;

	private static final Point P11 = new Point(20, 20);
	private static final Point P12 = new Point(120, 60);
	private static final Dimension D11 = new Dimension(50, 50);
	//private static final Dimension D12 = new Dimension(30, 40);
	private static final Rectangle B11 = new Rectangle(P11.x, P11.y, D11.width, D11.height);
	//private static final Rectangle B12 = new Rectangle(P12.x, P12.y, D12.width, D12.height);

	private static final Point P21 = new Point(80, 90);
	//private static final Point P22 = new Point(140, 80);
	private static final Dimension D21 = new Dimension(60, 40);
	private static final Dimension D22 = new Dimension(20, 60);
	private static final Rectangle B21 = new Rectangle(P21.x, P21.y, D21.width, D21.height);
	//private static final Rectangle B22 = new Rectangle(P22.x, P22.y, D22.width, D22.height);

	private static final Point P31 = new Point(220, 120);
	private static final Point P32 = new Point(240, 180);
	private static final Dimension D31 = new Dimension(90, 100);
	private static final Dimension D32 = new Dimension(70, 120);
	private static final Rectangle B31 = new Rectangle(P31.x, P31.y, D31.width, D31.height);
	private static final Rectangle B32 = new Rectangle(P32.x, P32.y, D32.width, D32.height);

	public static void main(String[] args) {
		D2MoveResizeFigureTest f = new D2MoveResizeFigureTest();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setSize(500, 400);
		f.setVisible(true);
	}

	public D2MoveResizeFigureTest() {
		super(D2MoveResizeFigureTest.class.getSimpleName());
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		final JComponent editor = createEditor();
		result.add(editor, BorderLayout.CENTER);

		final JToggleButton moveButton = new JToggleButton("Move parent1");
		moveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean selected = moveButton.isSelected();
				if (selected) {
					moveButton.setText("Move parent1 back");
					parent1.setLocation(P12.x, P12.y);
				} else {
					moveButton.setText("Move parent1");
					parent1.setLocation(P11.x, P11.y);
				}
			}
		});
		final JToggleButton resizeButton = new JToggleButton("Resize parent2");
		resizeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean selected = resizeButton.isSelected();
				if (selected) {
					resizeButton.setText("Resize parent2 back");
					parent2.setSize(D22.width, D22.height);
				} else {
					resizeButton.setText("Resize parent2");
					parent2.setSize(D21.width, D21.height);
				}
			}
		});
		final JToggleButton setBoundsButton = new JToggleButton("Set bounds parent3");
		setBoundsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean selected = setBoundsButton.isSelected();
				if (selected) {
					setBoundsButton.setText("Set bounds parent3 back");
					parent3.setBounds(B32);
				} else {
					setBoundsButton.setText("Set bounds parent3");
					parent3.setBounds(B31);
				}
			}
		});
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(moveButton);
		buttonsPanel.add(resizeButton);
		buttonsPanel.add(setBoundsButton);

		result.add(buttonsPanel, BorderLayout.NORTH);
		return result;
	}

	private JComponent createEditor() {
		LwSystem lws = new LwSystem();
		contents = createEditorContents(lws);
		lws.setContents(contents);
		return lws.getCanvas();
	}

	private XFigure createEditorContents(final LwSystem lws) {
		XFigure result = new XFigureImpl("content");
		result.setOpaque(true);
		result.setBackgroundColor(Color.WHITE);
		XBorder b = new LineBorder(2, Color.RED);
		result.setBorder(b);

		parent1 = createParent1();
		result.add(parent1);

		parent2 = createParent2();
		result.add(parent2);

		parent3 = createParent3();
		result.add(parent3);
		return result;
	}

	private XFigure createParent1() {
		XFigure result = new SampleFigure("parent1");
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setBackgroundColor(Color.MAGENTA);
		XBorder b1 = new LineBorder(2, Color.BLACK);
		result.setBorder(b1);
		result.setBounds(B11);
		result.add(createChild());
		return result;
	}

	private XFigure createParent2() {
		XFigure result = new SampleFigure("parent2");
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setBackgroundColor(Color.blue);
		XBorder b1 = new LineBorder(1, Color.RED);
		result.setBorder(b1);
		result.setBounds(B21);
		result.add(createChild());
		return result;
	}

	private XFigure createParent3() {
		XFigure result = new SampleFigure("parent3");
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setBackgroundColor(Color.GRAY);
		XBorder b1 = new LineBorder(2, Color.BLUE);
		result.setBorder(b1);
		result.setBounds(B31);
		result.add(createChild());
		return result;
	}

	private XFigure createChild() {
		XFigure result = new SampleFigure("child");
		result.setOpaque(false);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		XBorder b = new LineBorder(1, Color.YELLOW);
		result.setBorder(b);
		result.setBounds(new Rectangle(15, 15, 20, 30));
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
