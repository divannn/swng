package image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class DragPictureDemo2 extends JPanel {

	DTPicture pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8, pic9, pic10, pic11, pic12;
	static String mayaString = "Maya";
	static String anyaString = "Anya";
	static String laineString = "Laine";
	static String cosmoString = "Cosmo";
	static String adeleString = "Adele";
	static String alexiString = "Alexi";
	PictureTransferHandler picHandler;

	public DragPictureDemo2() {
		super(new BorderLayout());
		picHandler = new PictureTransferHandler();
		//Since we are using keyboard accelerators, we don't
		//need the component to install its own input map
		//bindings.
		DTPicture.setInstallInputMapBindings(false);

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

	//Create an Edit menu to support cut/copy/paste.
	public JMenuBar createMenuBar() {
		JMenuItem menuItem = null;
		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = new JMenu("Edit");
		mainMenu.setMnemonic(KeyEvent.VK_E);
		TransferActionListener actionListener = new TransferActionListener();

		menuItem = new JMenuItem("Cut");
		menuItem.setActionCommand((String) TransferHandler.getCutAction().getValue(Action.NAME));
		menuItem.addActionListener(actionListener);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_T);
		mainMenu.add(menuItem);
		menuItem = new JMenuItem("Copy");
		menuItem.setActionCommand((String) TransferHandler.getCopyAction().getValue(Action.NAME));
		menuItem.addActionListener(actionListener);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_C);
		mainMenu.add(menuItem);
		menuItem = new JMenuItem("Paste");
		menuItem.setActionCommand((String) TransferHandler.getPasteAction().getValue(Action.NAME));
		menuItem.addActionListener(actionListener);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_P);
		mainMenu.add(menuItem);

		menuBar.add(mainMenu);
		return menuBar;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imageURL = DragPictureDemo2.class.getResource(path);
		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		}
		return new ImageIcon(imageURL, description);
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("DragPictureDemo2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DragPictureDemo2 demo = new DragPictureDemo2();
		frame.setJMenuBar(demo.createMenuBar());
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

	/*
	 * A class that tracks the focused component.  This is necessary
	 * to delegate the menu cut/copy/paste commands to the right
	 * component.  An instance of this class is listening and
	 * when the user fires one of these commands, it calls the
	 * appropriate action on the currently focused component.
	 */
	public class TransferActionListener
			implements ActionListener, PropertyChangeListener {

		private JComponent focusOwner;

		public TransferActionListener() {
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.addPropertyChangeListener("permanentFocusOwner", this);
		}

		public void propertyChange(PropertyChangeEvent e) {
			Object o = e.getNewValue();
			if (o instanceof JComponent) {
				focusOwner = (JComponent) o;
			} else {
				focusOwner = null;
			}
		}

		public void actionPerformed(ActionEvent e) {
			if (focusOwner == null) {
				return;
			}
			String action = e.getActionCommand();
			Action a = focusOwner.getActionMap().get(action);
			if (a != null) {
				a.actionPerformed(new ActionEvent(focusOwner, ActionEvent.ACTION_PERFORMED, null));
			}
		}
	}

}
