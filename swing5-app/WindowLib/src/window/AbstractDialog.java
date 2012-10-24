package window;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

/**
 * AbstractDialog is an abstract class that allows to implements dialog boxes.
 * Concrete classes must redefine the <code>createClientArea<code> method.
 * 
 * @author idanilov
 */
public abstract class AbstractDialog extends JDialog {

	private HashMap<Integer, JButton> buttons;
	private JComponent contents, clientArea, buttonsBar;

	protected int returnCode;

	private WindowListener windowListener;

	private static final String CLOSE_ACTION_KEY = "DEFAUL_CANCEL_ACTION_KEY";
	/**
	 * Button id property.
	 */
	private static final String BUTTON_ID = "button_id";

	/**
	 * Create an AbstractDialog
	 * @param title The title
	 * @param butPos The position of the button
	 * @param tokHelp
	 */
	public AbstractDialog(final Frame parent, final String title) {
		super(parent);
		configure(title);
	}

	public AbstractDialog(final Frame parent) {
		this(parent, null);
	}

	public AbstractDialog(final Dialog parent, final String title) {
		super(parent);
		configure(title);
	}

	public AbstractDialog(final Dialog parent) {
		this(parent, null);
	}

	private void configure(final String title) {
		setTitle(title);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setDefaultCloseAction();
		windowListener = createWindowListener();
		addWindowListener(windowListener);
	}

	/**
	 * Call this in consructor or in code that shows dialog.
	 */
	protected void create() {
		buttons = new HashMap<Integer, JButton>();
		contents = createContents();
		if (contents != null) {
			setContentPane(contents);
			initDialog();
		}
	}

	protected JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		clientArea = createClientArea();
		if (clientArea != null) {
			clientArea.setBorder(InsetConstant.DIALOG_CONTENT_BORDER);
			result.add(clientArea, BorderLayout.CENTER);
		}
		buttonsBar = createButtonsBar();
		if (buttonsBar != null) {
			result.add(buttonsBar, BorderLayout.SOUTH);
		}
		return result;
	}

	/**
	 * Override to create content of dialog.
	 */
	protected JComponent createClientArea() {
		JPanel result = new JPanel(new BorderLayout(InsetConstant.BORDER_MARGIN,
				InsetConstant.BORDER_MARGIN));
		return result;
	}

	/**
	 * Override to create cusomized button bar.
	 */
	protected JComponent createButtonsBar() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JSeparator(), BorderLayout.NORTH);
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 0, InsetConstant.BETWEEN_INSET, 0));
		createButtonsForButtonBar(buttonsPanel);
		JPanel rightAlinedPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		rightAlinedPanel.add(buttonsPanel);
		result.add(rightAlinedPanel, BorderLayout.SOUTH);
		return result;
	}

	/**
	 * Override to create custom buttons.
	 */
	protected void createButtonsForButtonBar(final JComponent buttonBar) {
		createButton(buttonBar, DialogConstant.OK_ID, DialogConstant.OK_LABEL);
		createButton(buttonBar, DialogConstant.CANCEL_ID, DialogConstant.CANCEL_LABEL);
		setDefaultButton(DialogConstant.OK_ID);
	}

	protected JButton createButton(final JComponent buttonBar, final int id, final String label) {
		JButton result = new JButton(label);
		result.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				Integer buttonId = (Integer) button.getClientProperty(BUTTON_ID);
				buttonPressed(buttonId.intValue());
			}

		});
		Integer buttonId = new Integer(id);
		result.putClientProperty(BUTTON_ID, buttonId);
		buttons.put(buttonId, result);
		buttonBar.add(result);
		return result;
	}

	protected JButton getButton(final int id) {
		return buttons.get(new Integer(id));
	}

	protected void buttonPressed(final int buttonId) {
		if (DialogConstant.OK_ID == buttonId) {
			clickOnOk();
		} else if (DialogConstant.CANCEL_ID == buttonId) {
			clickOnCancel();
		}
	}

	/**
	 * Call after a click on the ok button
	 *
	 */
	protected void clickOnOk() {
		setReturnCode(DialogConstant.OK_ID);
		close();
	}

	/**
	 * Call after a click on the cancel button
	 */
	protected void clickOnCancel() {
		setReturnCode(DialogConstant.CANCEL_ID);
		close();
	}

	/**
	 * Shows the dialog box. Must be used instead of <code>setVisible()</code>
	 * @param pack
	 * @return The exit code
	 */
	public int open() {
		setVisible(true);
		return returnCode;
	}

	public void close() {
		setVisible(false);
		dispose();
	}

	/**
	 * Override if you need place for disposal of some listeners, resources etc.
	 */
	public void dispose() {
		removeWindowListener(windowListener);
		buttons = null;
		contents = null;
		super.dispose();
	}

	/**
	 * Initialize the dialog box.
	 */
	protected void initDialog() {
	}

	/**
	 * Called when system menu or 'X' button pressed (Windows).
	 * Default behaviour is close dialog.
	 * Override if you need to do something before window will be closed.
	 */
	protected void handleDefaultClose() {
		setReturnCode(DialogConstant.DEFAULT_CLOSE);
		close();
	}

	protected void setReturnCode(final int val) {
		returnCode = val;
	}

	/**
	 * The return code is returned by <code>open</code> if dialog is modal. 
	 * For non-modal dialogs, the return code needs to be retrieved using
	 * <code>getReturnCode</code>.
	 */
	public int getReturnCode() {
		return returnCode;
	}

	protected void setDefaultButton(final int id) {
		JButton button = getButton(id);
		if (button != null) {
			getRootPane().setDefaultButton(button);
		}
	}

	protected WindowListener createWindowListener() {
		return new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				handleDefaultClose();
			}
		};
	}

	/**
	 * Used JLayeredPane (not JRootPane) to avoid some problems with popup menus.
	 */
	private void setDefaultCloseAction() {
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		JComponent lPane = getLayeredPane();
		InputMap inputMap = lPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(escapeKey, CLOSE_ACTION_KEY);
		lPane.getActionMap().put(CLOSE_ACTION_KEY, new DefaultCloseAction());
	}

	private class DefaultCloseAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			handleDefaultClose();
		}

	}

}
