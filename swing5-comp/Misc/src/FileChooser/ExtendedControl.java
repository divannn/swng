package FileChooser;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


/**
 * @author idanilov
 *
 */
public class ExtendedControl extends JPanel {

	private JTextField textField;
	private JButton button;
	
	public ExtendedControl() {
		setLayout(new BorderLayout());
		setBorder(UIManager.getBorder("TextField.border")); 
		textField = new JTextField();
		Insets tfInsets = textField.getMargin(); 
		textField.setBorder(BorderFactory.createEmptyBorder(tfInsets.top, tfInsets.left, tfInsets.bottom, tfInsets.right));
		add(textField,BorderLayout.CENTER);
		button = new JButton("...");
		button.setDefaultCapable(false);
		button.setMargin(new Insets(0, 0, 0, 0)); 
		add(button,BorderLayout.EAST);
	}
	
	public JButton getButton() {
		return button;
	}
	
	public JTextField getTextField() {
		return textField;
	}
	
	public void setEnabled(final boolean enabled) {
		textField.setEnabled(enabled);
		button.setEnabled(enabled);
	}
	
}
