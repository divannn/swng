package HyperlinkButton._1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class HyperlinkButtonDemo1 extends JFrame {

	private static final Color LINK_COLOR = Color.BLACK;
	private static final Color HOVER_COLOR = Color.BLUE;
	private static final Border LINK_BORDER = BorderFactory.createEmptyBorder(0, 0, 1, 0);
	private static final Border HOVER_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 0,
			HOVER_COLOR);

	public HyperlinkButtonDemo1() {
		super(HyperlinkButtonDemo1.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();
		JButton linkButton = new JButton("Like a hyperlink...");
		makeLink(linkButton);
		result.add(linkButton);
		return result;
	}

	private static JButton makeLink(JButton result) {
		result.setBorder(LINK_BORDER);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setFocusPainted(false);
		result.setRequestFocusEnabled(false);
		result.setContentAreaFilled(false);
		result.addMouseListener(new LinkMouseListener());
		return result;
	}

	private static class LinkMouseListener extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {
			((JComponent) e.getComponent()).setBorder(HOVER_BORDER);
			((JComponent) e.getComponent()).setForeground(HOVER_COLOR);
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
			((JComponent) e.getComponent()).setBorder(LINK_BORDER);
			((JComponent) e.getComponent()).setForeground(LINK_COLOR);
		}

	}

	public static void main(String[] args) {
		JFrame f = new HyperlinkButtonDemo1();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
