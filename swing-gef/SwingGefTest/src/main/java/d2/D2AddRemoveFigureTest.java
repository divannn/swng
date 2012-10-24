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
import com.id.swingeditor.d2.XYLayout;

/**
 * Use absolute positioning of figures (no layou managers).
 * @author idanilov
 *
 */
public class D2AddRemoveFigureTest extends JFrame {

	private XFigure contents;
	private XFigure parent;
	private XFigure child;

	public static void main(String[] args) {
		D2AddRemoveFigureTest f = new D2AddRemoveFigureTest();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setSize(500, 400);
		f.setVisible(true);
	}

	public D2AddRemoveFigureTest() {
		super(D2AddRemoveFigureTest.class.getSimpleName());
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		final JComponent editor = createEditor();
		result.add(editor, BorderLayout.CENTER);

		final JToggleButton removeAddParentButton = new JToggleButton("Remove parent");
		removeAddParentButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean selected = removeAddParentButton.isSelected();
				if (selected) {
					removeAddParentButton.setText("Add parent back");
					contents.remove(parent);
				} else {
					removeAddParentButton.setText("Remove  parent");
					contents.add(parent);
				}
			}
		});

		final JToggleButton removeAddChildButton = new JToggleButton("Remove child");
		removeAddChildButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean selected = removeAddChildButton.isSelected();
				if (selected) {
					removeAddChildButton.setText("Add child back");
					parent.remove(child);
				} else {
					removeAddChildButton.setText("Remove  child");
					parent.add(child);
				}
			}
		});
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(removeAddParentButton);
		buttonsPanel.add(removeAddChildButton);

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
		result.setLayoutManager(new XYLayout());
		result.setOpaque(true);
		result.setBackgroundColor(Color.WHITE);
		XBorder b = new LineBorder(2, Color.RED);
		result.setBorder(b);
		parent = createParent();
		result.add(parent, new Rectangle(20, 30, 80, 120));
		return result;
	}

	private XFigure createParent() {
		XFigure result = new SampleFigure("parent");
		result.setLayoutManager(new XYLayout());
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setBackgroundColor(Color.MAGENTA);
		XBorder b1 = new LineBorder(2, Color.BLACK);
		result.setBorder(b1);
		child = createChild();
		result.add(child, new Rectangle(15, 15, 20, 30));
		return result;
	}

	private XFigure createChild() {
		XFigure result = new SampleFigure("child");
		result.setOpaque(false);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		XBorder b = new LineBorder(1, Color.YELLOW);
		result.setBorder(b);
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
