package window;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * @author idanilov
 *
 */
public abstract class AbstractFrame extends JFrame {

	private JComponent contents;
	private WindowListener windowListener;

	public AbstractFrame(final String title) {
		super(title);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		windowListener = createWindowListener();
		addWindowListener(windowListener);
	}

	/**
	 * Call this in consructor or in place where you show frame.
	 */
	protected void create() {
		contents = createContents();
		if (contents != null) {
			setContentPane(contents);
			initFrame();
		}
	}

	protected void initFrame() {
	}

	protected JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		return result;
	}

	protected WindowListener createWindowListener() {
		return new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				handleDefaultClose();
			}
		};
	}

	/**
	 * Called when system menu or 'X' button pressed (Windows).
	 * Default behaviour is close frame.
	 * Override if you need to do something before window will be closed.
	 */
	protected void handleDefaultClose() {
		close();
	}

	/**
	 * Shows the frame. Must be used instead of <code>setVisible()</code>
	 * @param isModal
	 * @return The exit code
	 */
	/*    public void open(final boolean pack) {
	 if (contents == null) {
	 create();
	 }
	 if (pack) {
	 pack();	
	 }
	 setLocationRelativeTo(getParent());//???
	 setVisible(true);
	 }
	 */
	public void close() {
		setVisible(false);
		dispose();
	}

	/**
	 * Override if you need place for disposal of some listeners, resources etc.
	 */
	public void dispose() {
		removeWindowListener(windowListener);
		contents = null;
		super.dispose();
	}
}