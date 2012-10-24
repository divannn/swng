package PopupInvoker;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

/**
 * Simple way how to show popup in place of current editing (can be used in code sense).
 * @author idanilov
 * @jdk 1.5
 * 
 */
public class PopupInvokerDemo extends JFrame {

	public PopupInvokerDemo() {
		super(PopupInvokerDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		final JTextField textField = new JTextField();
		result.add(textField, BorderLayout.NORTH);

		final JPopupMenu popup = createPopup();
		ActionListener actionListener = new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int dotPosition = textField.getCaretPosition();
					Rectangle popupLocation = textField.modelToView(dotPosition);
					popup.show(textField, popupLocation.x, popupLocation.y);
				} catch (BadLocationException ble) {
				}
			}
		};
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK,
				false);
		textField.registerKeyboardAction(actionListener, keystroke, JComponent.WHEN_FOCUSED);

		return result;
	}

	private JPopupMenu createPopup() {
		JPopupMenu result = new JPopupMenu();
		JMenuItem menuItem1 = new JMenuItem("item 1");
		result.add(menuItem1);

		JMenuItem menuItem2 = new JMenuItem("item 2");
		result.add(menuItem2);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new PopupInvokerDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
