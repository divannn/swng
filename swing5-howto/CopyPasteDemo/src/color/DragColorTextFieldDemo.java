package color;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.DefaultEditorKit;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class DragColorTextFieldDemo extends JPanel {

	JCheckBox toggleForeground;
	ColorAndTextTransferHandler colorHandler;

	public DragColorTextFieldDemo() {
		super(new BorderLayout());
		JTextField textField;

		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JColorChooser chooser = new JColorChooser();
		chooser.setDragEnabled(true);
		add(chooser, BorderLayout.PAGE_START);

		//Create the color transfer handler.
		colorHandler = new ColorAndTextTransferHandler();

		//Create some text fields.
		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		textField = new JTextField("I can accept color/text and drag text.");
		textField.setTransferHandler(colorHandler);
		textField.setDragEnabled(true);
		buttonPanel.add(textField);
		textField = new JTextField("Me too!");
		textField.setTransferHandler(colorHandler);
		textField.setDragEnabled(true);
		buttonPanel.add(textField);
		textField = new JTextField("Me three!");
		textField.setTransferHandler(colorHandler);
		textField.setDragEnabled(true);
		buttonPanel.add(textField);
		add(buttonPanel, BorderLayout.CENTER);
	}

	//Create an Edit menu to support cut/copy/paste.
	public JMenuBar createMenuBar() {
		JMenuItem menuItem = null;
		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = new JMenu("Edit");
		mainMenu.setMnemonic(KeyEvent.VK_E);

		menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
		menuItem.setText("Cut");
		menuItem.setMnemonic(KeyEvent.VK_T);
		mainMenu.add(menuItem);
		menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
		menuItem.setText("Copy");
		menuItem.setMnemonic(KeyEvent.VK_C);
		mainMenu.add(menuItem);
		menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
		menuItem.setText("Paste");
		menuItem.setMnemonic(KeyEvent.VK_P);
		mainMenu.add(menuItem);

		menuBar.add(mainMenu);
		return menuBar;
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("DragColorTextFieldDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DragColorTextFieldDemo demo = new DragColorTextFieldDemo();
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
}
