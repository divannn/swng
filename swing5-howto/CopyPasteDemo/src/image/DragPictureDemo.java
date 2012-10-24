package image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class DragPictureDemo extends JPanel {

	DTPicture pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8, pic9, pic10, pic11, pic12;
	static String mayaString = "Maya";
	static String anyaString = "Anya";
	static String laineString = "Laine";
	static String cosmoString = "Cosmo";
	static String adeleString = "Adele";
	static String alexiString = "Alexi";
	PictureTransferHandler picHandler;

	public DragPictureDemo() {
		super(new BorderLayout());
		picHandler = new PictureTransferHandler();

		JPanel mugshots = new JPanel(new GridLayout(4, 3));
		pic1 = new DTPicture(createImageIcon(mayaString + ".jpg", mayaString).getImage());
		pic1.setTransferHandler(picHandler);
		mugshots.add(pic1);
		pic2 = new DTPicture(createImageIcon(anyaString + ".jpg", anyaString).getImage());
		pic2.setTransferHandler(picHandler);
		mugshots.add(pic2);
		pic3 = new DTPicture(createImageIcon(laineString + ".jpg", laineString).getImage());
		pic3.setTransferHandler(picHandler);
		mugshots.add(pic3);
		pic4 = new DTPicture(createImageIcon(cosmoString + ".jpg", cosmoString).getImage());
		pic4.setTransferHandler(picHandler);
		mugshots.add(pic4);
		pic5 = new DTPicture(createImageIcon(adeleString + ".jpg", adeleString).getImage());
		pic5.setTransferHandler(picHandler);
		mugshots.add(pic5);
		pic6 = new DTPicture(createImageIcon(alexiString + ".jpg", alexiString).getImage());
		pic6.setTransferHandler(picHandler);
		mugshots.add(pic6);

		//These six components with no pictures provide handy
		//drop targets.
		pic7 = new DTPicture(null);
		pic7.setTransferHandler(picHandler);
		mugshots.add(pic7);
		pic8 = new DTPicture(null);
		pic8.setTransferHandler(picHandler);
		mugshots.add(pic8);
		pic9 = new DTPicture(null);
		pic9.setTransferHandler(picHandler);
		mugshots.add(pic9);
		pic10 = new DTPicture(null);
		pic10.setTransferHandler(picHandler);
		mugshots.add(pic10);
		pic11 = new DTPicture(null);
		pic11.setTransferHandler(picHandler);
		mugshots.add(pic11);
		pic12 = new DTPicture(null);
		pic12.setTransferHandler(picHandler);
		mugshots.add(pic12);

		setPreferredSize(new Dimension(450, 630));
		add(mugshots, BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imageURL = DragPictureDemo.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		}
		return new ImageIcon(imageURL, description);
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("DragPictureDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DragPictureDemo demo = new DragPictureDemo();
		demo.setOpaque(true); //content panes must be opaque
		frame.setContentPane(demo);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				createAndShowGUI();
			}
		});
	}
}
