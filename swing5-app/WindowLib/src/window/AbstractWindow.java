package window;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * @author idanilov
 *
 */
public abstract class AbstractWindow extends JWindow {

	private JComponent contents;

	public AbstractWindow() {
		super();
	}

	public AbstractWindow(final Window owner) {
		super(owner);
	}

	/**
	 * Call this in consructor or in place where you show frame.
	 */
	protected void create() {
		contents = createContents();
		if (contents != null) {
			setContentPane(contents);
			initWindow();
		}
	}

	protected void initWindow() {
	}

	protected JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		return result;
	}

	/**
	 * Shows the window. Must be used instead of <code>setVisible()</code>
	 * @param isModal
	 * @return The exit code
	 */
	/*public void open(final boolean pack) {
	 if (contents == null) {
	 create();
	 }
	 if (pack) {
	 pack();	
	 }
	 setLocationRelativeTo(getParent());//???
	 setVisible(true);
	 }*/

	public void close() {
		setVisible(false);
		dispose();
	}

	/**
	 * Override if you need place for disposal of some listeners, resources etc.
	 */
	public void dispose() {
		contents = null;
		super.dispose();
	}

}