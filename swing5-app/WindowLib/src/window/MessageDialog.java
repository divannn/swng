package window;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class MessageDialog extends AbstractDialog {

	public enum ICON_TYPE {
		NONE, INFO, QUESTION, WARNING, ERROR
	}

	protected Icon image;
	protected String message;

	protected JLabel label;

	public MessageDialog(final Dialog parent) {
		this(parent, null);
	}

	public MessageDialog(final Dialog parent, final String title) {
		this(parent, title, "", ICON_TYPE.INFO);
	}

	public MessageDialog(final Dialog parent, final String title, final String msg,
			final ICON_TYPE type) {
		super(parent, title);
		configure(msg, type);
	}

	public MessageDialog(final Frame parent) {
		this(parent, null);
	}

	public MessageDialog(final Frame parent, final String title) {
		this(parent, title, "", ICON_TYPE.INFO);
	}

	public MessageDialog(final Frame parent, final String title, final String msg,
			final ICON_TYPE type) {
		super(parent, title);
		configure(msg, type);
	}

	//Always pack message and center relative its parent dialog.
	public int open() {
		pack();
		setLocationRelativeTo(getOwner());
		return super.open();
	}

	private void configure(final String msg, final ICON_TYPE type) {
		setModal(true);
		setResizable(false);
		message = msg;
		image = getImage(type);
		create();
	}

	protected JComponent createClientArea() {
		JComponent result = super.createClientArea();
		label = new JLabel(message, image, SwingConstants.LEFT);
		result.add(label);
		return result;
	}

	protected JComponent createButtonsBar() {
		JComponent result = super.createButtonsBar();
		result.remove(0); //remove separator.
		return result;
	}

	protected void buttonPressed(final int buttonId) {
		setReturnCode(buttonId);
		close();
	}

	private Icon getImage(final ICON_TYPE type) {
		Icon result = null;
		String propertyName = null;
		switch (type) {
			case INFO:
				propertyName = "OptionPane.informationIcon";
				break;
			case QUESTION:
				propertyName = "OptionPane.questionIcon";
				break;
			case WARNING:
				propertyName = "OptionPane.warningIcon";
				break;
			case ERROR:
				propertyName = "OptionPane.errorIcon";
				break;
			default:
		}
		if (propertyName != null) {
			result = UIManager.getIcon(propertyName);
		}
		return result;
	}

	/**
	 * Default dialog.
	 * @return MessageDialog with OK and Cancel buttons
	 */
	public static MessageDialog createOkCancelDialog(final Dialog parent, final String title,
			final String msg, final ICON_TYPE type) {
		return new MessageDialog(parent, title, msg, type);
	}

	public static MessageDialog createOkCancelDialog(final Frame parent, final String title,
			final String msg, final ICON_TYPE type) {
		return new MessageDialog(parent, title, msg, type);
	}

	/**
	 * @return MessageDialog with OK button
	 */
	public static MessageDialog createOkDialog(final Dialog parent, final String title,
			final String msg, final ICON_TYPE type) {
		MessageDialog result = new MessageDialog(parent, title, msg, type) {

			protected void createButtonsForButtonBar(JComponent buttonBar) {
				createButton(buttonBar, DialogConstant.OK_ID, DialogConstant.OK_LABEL);
				setDefaultButton(DialogConstant.OK_ID);
			}
		};
		return result;
	}

	public static MessageDialog createOkDialog(final Frame parent, final String title,
			final String msg, final ICON_TYPE type) {
		MessageDialog result = new MessageDialog(parent, title, msg, type) {

			protected void createButtonsForButtonBar(JComponent buttonBar) {
				createButton(buttonBar, DialogConstant.OK_ID, DialogConstant.OK_LABEL);
				setDefaultButton(DialogConstant.OK_ID);
			}
		};
		return result;
	}

	/**
	 * @return MessageDialog with Yes and No buttons
	 */
	public static MessageDialog createYesNoDialog(final Dialog parent, final String title,
			final String msg, final ICON_TYPE type) {
		MessageDialog result = new MessageDialog(parent, title, msg, type) {

			protected void createButtonsForButtonBar(JComponent buttonBar) {
				createButton(buttonBar, DialogConstant.YES_ID, DialogConstant.YES_LABEL);
				createButton(buttonBar, DialogConstant.NO_ID, DialogConstant.NO_LABEL);
				setDefaultButton(DialogConstant.YES_ID);
			}
		};
		return result;
	}

	public static MessageDialog createYesNoDialog(final Frame parent, final String title,
			final String msg, final ICON_TYPE type) {
		MessageDialog result = new MessageDialog(parent, title, msg, type) {

			protected void createButtonsForButtonBar(JComponent buttonBar) {
				createButton(buttonBar, DialogConstant.YES_ID, DialogConstant.YES_LABEL);
				createButton(buttonBar, DialogConstant.NO_ID, DialogConstant.NO_LABEL);
				setDefaultButton(DialogConstant.YES_ID);
			}
		};
		return result;
	}

	/**
	 * @return MessageDialog with Yes,No and Cancel buttons
	 */
	public static MessageDialog createYesNoCancelDialog(final Dialog parent, final String title,
			final String msg, final ICON_TYPE type) {
		MessageDialog result = new MessageDialog(parent, title, msg, type) {

			protected void createButtonsForButtonBar(JComponent buttonBar) {
				createButton(buttonBar, DialogConstant.YES_ID, DialogConstant.YES_LABEL);
				createButton(buttonBar, DialogConstant.NO_ID, DialogConstant.NO_LABEL);
				createButton(buttonBar, DialogConstant.CANCEL_ID, DialogConstant.CANCEL_LABEL);
				setDefaultButton(DialogConstant.YES_ID);
			}
		};
		return result;
	}

	public static MessageDialog createYesNoCancelDialog(final Frame parent, final String title,
			final String msg, final ICON_TYPE type) {
		MessageDialog result = new MessageDialog(parent, title, msg, type) {

			protected void createButtonsForButtonBar(JComponent buttonBar) {
				createButton(buttonBar, DialogConstant.YES_ID, DialogConstant.YES_LABEL);
				createButton(buttonBar, DialogConstant.NO_ID, DialogConstant.NO_LABEL);
				createButton(buttonBar, DialogConstant.CANCEL_ID, DialogConstant.CANCEL_LABEL);
				setDefaultButton(DialogConstant.YES_ID);
			}
		};
		return result;
	}

}