package d2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.id.swingeditor.d2.LineBorder;
import com.id.swingeditor.d2.LwSystem;
import com.id.swingeditor.d2.StackLayout;
import com.id.swingeditor.d2.XBorder;
import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.d2.XFigureImpl;
import com.id.swingeditor.d2.XLayer;
import com.id.swingeditor.d2.XLayeredPane;
import com.id.swingeditor.d2.XYLayout;

/**
 * @author idanilov
 *
 */
public class D2LayersTest extends JFrame {

	private static final String LAYER1_ID = "layer1";
	private static final String LAYER2_ID = "layer2";

	private XLayeredPane layeredPane;

	public static void main(String[] args) {
		D2LayersTest f = new D2LayersTest();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setSize(500, 400);
		f.setVisible(true);
	}

	public D2LayersTest() {
		super(D2LayersTest.class.getSimpleName());
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		final JComponent editor = createEditor();
		result.add(editor, BorderLayout.CENTER);
		final JToggleButton hideShowLayer1Button = new JToggleButton("Hide layer 1");
		hideShowLayer1Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				XLayer layer2 = layeredPane.getLayer(LAYER1_ID);
				boolean selected = hideShowLayer1Button.isSelected();
				layer2.setVisible(!selected);
				if (selected) {
					hideShowLayer1Button.setText("Show layer 1");
				} else {
					hideShowLayer1Button.setText("Hide layer 1");
				}
			}
		});
		final JToggleButton hideShowLayer2Button = new JToggleButton("Hide layer 2");
		hideShowLayer2Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				XLayer layer2 = layeredPane.getLayer(LAYER2_ID);
				boolean selected = hideShowLayer2Button.isSelected();
				layer2.setVisible(!selected);
				if (selected) {
					hideShowLayer2Button.setText("Show layer 2");
				} else {
					hideShowLayer2Button.setText("Hide layer 2");
				}
			}
		});
		final JToggleButton enableDisableLayer1Button = new JToggleButton("Disable layer 1");
		enableDisableLayer1Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				XLayer layer1 = layeredPane.getLayer(LAYER1_ID);
				boolean selected = enableDisableLayer1Button.isSelected();
				layer1.setEnabled(!selected);
				if (selected) {
					enableDisableLayer1Button.setText("Enable layer 1");
				} else {
					enableDisableLayer1Button.setText("Disable layer 1");
				}
			}
		});
		final JToggleButton enableDisableLayer2Button = new JToggleButton("Disable layer 2");
		enableDisableLayer2Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				XLayer layer2 = layeredPane.getLayer(LAYER2_ID);
				boolean selected = enableDisableLayer2Button.isSelected();
				layer2.setEnabled(!selected);
				if (selected) {
					enableDisableLayer2Button.setText("Enable layer 2");
				} else {
					enableDisableLayer2Button.setText("Disable layer 2");
				}
			}
		});
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(hideShowLayer1Button);
		buttonsPanel.add(hideShowLayer2Button);
		buttonsPanel.add(new JSeparator(SwingConstants.VERTICAL));
		buttonsPanel.add(enableDisableLayer1Button);
		buttonsPanel.add(enableDisableLayer2Button);

		result.add(buttonsPanel, BorderLayout.NORTH);
		return result;
	}

	private JComponent createEditor() {
		LwSystem lws = new LwSystem();
		lws.setContents(createEditorContents(lws));
		return lws.getCanvas();
	}

	private XFigure createEditorContents(final LwSystem lws) {
		XFigure result = new XFigureImpl("content");
		result.setLayoutManager(new StackLayout());
		result.setOpaque(true);
		result.setBackgroundColor(Color.WHITE);
		XBorder b = new LineBorder(2, Color.RED);
		result.setBorder(b);

		layeredPane = new XLayeredPane("layered pane");
		XFigure layer0 = createLayer0();
		layeredPane.add(layer0, layer0.getName());
		XFigure layer1 = createLayer1();
		layeredPane.add(layer1, layer1.getName());
		XFigure layer2 = createLayer2();
		layeredPane.add(layer2, layer2.getName());

		result.add(layeredPane);
		return result;
	}

	private XFigure createLayer0() {
		final String name = "grid layer";
		XFigure result = new XLayer(name) {

			@Override
			protected void paintClientArea(Graphics2D graphics) {
				final int HSTEP = 20;
				final int VSTEP = 20;
				final int DOT_WITH = 1;
				Rectangle clientArea = getClientArea();
				int w = clientArea.width;
				int h = clientArea.height;
				int hPixStep = (int) Math.ceil((double) w / HSTEP);
				int vPixStep = (int) Math.ceil((double) h / VSTEP);
				for (int i = 0; i < hPixStep; i++) {
					for (int j = 0; j < vPixStep; j++) {
						graphics.setColor(Color.BLACK);
						graphics.fillRect(clientArea.x + i * HSTEP, clientArea.y + j * VSTEP,
								DOT_WITH, DOT_WITH);
					}
				}
				Rectangle strBounds = graphics.getFontMetrics().getStringBounds("grid layer",
						graphics).getBounds();
				graphics.drawString(name, clientArea.width / 2 - strBounds.width/2, clientArea.height / 2);
			}
		};
		return result;
	}

	private XFigure createLayer1() {
		String lName = LAYER1_ID;
		XFigure result = new XLayer(lName);
		result.setLayoutManager(new XYLayout());
		XFigure fig1 = new SampleFigure(lName + ":fig1");
		fig1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fig1.setBackgroundColor(Color.MAGENTA);
		XBorder b1 = new LineBorder(2, Color.BLACK);
		fig1.setBorder(b1);
		result.add(fig1, new Rectangle(1, 1, 40, 50));

		XFigure fig2 = new SampleFigure(lName + ":fig2");
		fig2.setBackgroundColor(Color.GREEN);
		fig2.setLayoutManager(new XYLayout());
		result.add(fig2, new Rectangle(120, 130, 40, 50));

		XFigure fig3 = new SampleFigure(lName + ":fig3");
		fig3.setBackgroundColor(Color.BLUE);
		fig2.add(fig3, new Rectangle(5, 5, 20, 30));

		XFigure fig4 = new SampleFigure(lName + ":fig4");
		fig4.setOpaque(false);
		fig4.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		XBorder b4 = new LineBorder(2, Color.YELLOW);
		fig4.setBorder(b4);
		result.add(fig4, new Rectangle(55, 65, 20, 60));
		return result;
	}

	private XFigure createLayer2() {
		String lName = LAYER2_ID;
		XFigure result = new XLayer(lName);
		result.setLayoutManager(new XYLayout());
		XFigure fig1 = new SampleFigure(lName + ":fig1");
		fig1.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		fig1.setBackgroundColor(Color.DARK_GRAY);
		XBorder b1 = new LineBorder(2, Color.BLACK);
		fig1.setBorder(b1);
		result.add(fig1, new Rectangle(30, 30, 40, 50));

		XFigure fig2 = new SampleFigure(lName + ":fig2");
		fig2.setBackgroundColor(Color.RED);
		fig2.setLayoutManager(new XYLayout());
		result.add(fig2, new Rectangle(70, 70, 40, 50));
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
