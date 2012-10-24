package ScrollBarMenu;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * <strong>Note:</strong>
 * Default scroll actions can be used (from BasicScrollBarUI) for simplicity. 
 * @author idanilov			
 * @jdk 1.5
 */
public class ScrollBarMenuDemo extends JFrame {

	private static final String END_LINE = System.getProperty("line.separator");
	private static final String PATTERN = "Press right mouse on vertical or horizontal scrollbar."+END_LINE;

	private Point popupPosForVertical;
	private Point popupPosForHorizontal;
	
	public ScrollBarMenuDemo() {
		super(ScrollBarMenuDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTextArea textArea = new JTextArea();
		String text = "";
		for (int i = 0; i < 30; i++) {
			text += PATTERN;
		}
		textArea.setText(text);
		textArea.setWrapStyleWord(false);
		textArea.setLineWrap(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		final JScrollBar vScroll = scrollPane.getVerticalScrollBar();
		vScroll.setToolTipText("Right click to open popup menu");
		vScroll.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}
			
			public void mouseReleased(MouseEvent me) {
				showPopup(me);
			}
			
			private void showPopup(MouseEvent me) {
				if (me.isPopupTrigger()) {
					popupPosForVertical = me.getPoint();
					JPopupMenu vPopup = createVerticalPopupMenu(vScroll);
					vPopup.show(me.getComponent(),me.getX(),me.getY());
				}
			}
		});
		final JScrollBar hScroll = scrollPane.getHorizontalScrollBar();
		hScroll.setToolTipText("Right click to open popup menu");
		hScroll.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}
			
			public void mouseReleased(MouseEvent me) {
				showPopup(me);
			}
			
			private void showPopup(MouseEvent me) {
				if (me.isPopupTrigger()) {
					popupPosForHorizontal = me.getPoint();
					JPopupMenu hPopup = createHorizontalPopupMenu(hScroll);
					hPopup.show(me.getComponent(),me.getX(),me.getY());
				}
			}
		});
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}
	
	private JPopupMenu createVerticalPopupMenu(final JScrollBar scrollBar) {
		final BoundedRangeModel vModel = scrollBar.getModel();
		JPopupMenu result = new JPopupMenu();
		JMenuItem scrollVHere = new JMenuItem("Scroll Here");
		scrollVHere.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (popupPosForVertical != null) {//take last remembered point on popup invokation.
					int barHight = scrollBar.getHeight();
					System.err.println("bar hight = " + barHight + " pix");
					BoundedRangeModel model = scrollBar.getModel(); 
					System.err.println("max = " + model.getMaximum());
					int vUnit = model.getMaximum()/barHight;
					System.err.println("vUnit = " + vUnit);
					final int UP_ARROW_BUTTON_HIGHT = 16;//taken from BasicArrowButton#getPrefferedSize() - approx. variant.
					//better way - demand up button size from BasicScrollBarUI.
					int val = popupPosForVertical.y*vUnit - UP_ARROW_BUTTON_HIGHT*vUnit;
					System.err.println("val = " + val);
					model.setValue(val);				
				}
			}
			
		});
		JMenuItem up = new JMenuItem("Up");
		up.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.err.println("-=VERTICAL=-");
				System.err.println("value = " + vModel.getValue() + " min = " + vModel.getMinimum() + " max = " + vModel.getMaximum() + " ext = " + vModel.getExtent());
				System.err.println("unit incr = " + scrollBar.getUnitIncrement(-1) + " block incr = " + scrollBar.getBlockIncrement(-1));
				System.err.println("______________________________________________________________________________________________________________________");
				int val = vModel.getValue();
				vModel.setValue(val - scrollBar.getUnitIncrement(-1));
			}
			
		});
		JMenuItem down = new JMenuItem("Down");
		down.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int val = vModel.getValue();
				vModel.setValue(val + scrollBar.getUnitIncrement(1));
			}
			
		});
		JMenuItem pageUp = new JMenuItem("Page Up");
		pageUp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int val = vModel.getValue();
				vModel.setValue(val - scrollBar.getBlockIncrement(-1));
			}
			
		});
		JMenuItem pageDown = new JMenuItem("Page Down");
		pageDown.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int val = vModel.getValue();
				vModel.setValue(val + scrollBar.getBlockIncrement(1));
			}
			
		});
		JMenuItem top = new JMenuItem("Top");
		top.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				vModel.setValue(vModel.getMinimum());
			}
			
		});
		JMenuItem bottom = new JMenuItem("Bottom");
		bottom.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				vModel.setValue(vModel.getMaximum());
			}
			
		});
		result.add(scrollVHere);
		result.addSeparator();
		result.add(up);
		result.add(down);
		result.addSeparator();
		result.add(pageUp);
		result.add(pageDown);
		result.addSeparator();
		result.add(top);
		result.add(bottom);
		return result;
	}
	
	private JPopupMenu createHorizontalPopupMenu(final JScrollBar scrollBar) {
		final BoundedRangeModel hModel = scrollBar.getModel();
		JPopupMenu result = new JPopupMenu();
		JMenuItem scrollHHere = new JMenuItem("Scroll Here");
		scrollHHere.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (popupPosForHorizontal != null) {//take last remembered point on popup invokation.
					int barWidth = scrollBar.getWidth();
					System.err.println("bar wight = " + barWidth + " pix");
					BoundedRangeModel model = scrollBar.getModel(); 
					System.err.println("max = " + model.getMaximum());
					int hUnit = model.getMaximum()/barWidth;
					System.err.println("hUnit = " + hUnit);
					final int LEFT_ARROW_BUTTON_HIGHT = 16;//taken from BasicArrowButton#getPrefferedSize() - approx. variant.
					//better way - demand up button size from BasicScrollBarUI.
					int val = popupPosForHorizontal.x*hUnit - LEFT_ARROW_BUTTON_HIGHT*hUnit;
					System.err.println("val = " + val);
					model.setValue(val);				
				}
			}
			
		});		
		JMenuItem left = new JMenuItem("Left");
		left.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.err.println("-=HORIZONTAL=-");
				System.err.println("value = " + hModel.getValue() + " min = " + hModel.getMinimum() + " max = " + hModel.getMaximum() + " ext = " + hModel.getExtent());
				System.err.println("unit incr = " + scrollBar.getUnitIncrement(-1) + " block incr = " + scrollBar.getBlockIncrement(-1));
				System.err.println("______________________________________________________________________________________________________________________");
				int val = hModel.getValue();
				hModel.setValue(val - scrollBar.getUnitIncrement(-1));
			}
			
		});
		JMenuItem right = new JMenuItem("Right");
		right.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int val = hModel.getValue();
				hModel.setValue(val + scrollBar.getUnitIncrement(1));
			}
			
		});
		JMenuItem pageLeft = new JMenuItem("Page Left");
		pageLeft.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int val = hModel.getValue();
				hModel.setValue(val - scrollBar.getBlockIncrement(-1));
			}
			
		});
		JMenuItem pageRight = new JMenuItem("Page Right");
		pageRight.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int val = hModel.getValue();
				hModel.setValue(val + scrollBar.getBlockIncrement(1));
			}
			
		});
		JMenuItem leftEdge = new JMenuItem("Left Edge");
		leftEdge.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				hModel.setValue(hModel.getMinimum());
			}
			
		});
		JMenuItem rightEdge = new JMenuItem("Right Edge");
		rightEdge.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				hModel.setValue(hModel.getMaximum());
			}
			
		});
		result.add(scrollHHere);
		result.addSeparator();
		result.add(left);
		result.add(right);
		result.addSeparator();
		result.add(pageLeft);
		result.add(pageRight);
		result.addSeparator();
		result.add(leftEdge);
		result.add(rightEdge);
		
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		JFrame f = new ScrollBarMenuDemo();
		f.setSize(200,200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
