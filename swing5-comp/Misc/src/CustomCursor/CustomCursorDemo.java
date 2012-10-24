package CustomCursor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;


/**
 * When creating custom cursors, be sure to take advantage of the hints available 
 * from the {@link Toolkit#getBestCursorSize(int, int)} and 
 * {@link Toolkit#getMaximumCursorColors()} methods.
 * <br> In WinXP getBestCursorSize() returned as 32x32.
 * If cursor image is smaller - it will be stretched to getBestCursorSize().
 * @author idanilov
 * @jdk 1.5
 */
public class CustomCursorDemo extends JFrame {

	public CustomCursorDemo() {
		super(CustomCursorDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTable table = new JTable(5,3);
		table.setCursor(createForbidCursor());
		result.add(new JScrollPane(table),BorderLayout.CENTER);
		return result;
	}

	private static Cursor createForbidCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		ImageIcon icon = new ImageIcon(CustomCursorDemo.class.getResource("notAllowed.gif"));
		Image image = icon.getImage();
		Cursor result = toolkit.createCustomCursor(image, new Point(0, 0), "notAllowed");
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new CustomCursorDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
