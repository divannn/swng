package EnterKeyForToggleButton;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * 
 * I have found that plain JButton reacts on SPACE and ENTER key when focused. JToggleButton reacts only on SPACE.
 * I thought that JToggleButton simply doesn't have and ENTER key in its input maps. But the input maps (WHEN_FOCUSED at least) 
 * are the same for JButton and JToggleButton.   
 * I started investigation and found that ENTER key for JButton is processed via root pane's default button.
 * See BasicButtonListener#focusGained() and BasicButtonListener#focusLost(). 
 * 
 * <br> This is an example how to add toggling by ENTER key for JToggleButton.
 * @see WindowsLookAndFeel - "ToggleButton.focusInputMap" property,
 * 		BasicButtonListener
 * @author idanilov
 * @jdk 1.5
 */
public class EnterKeyForToggleButton extends JFrame {

	public EnterKeyForToggleButton() {
		super(EnterKeyForToggleButton.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		JToggleButton normalToggleButton = new JToggleButton("Normal");
		normalToggleButton.setToolTipText("Press SPACE to toggle");
		result.add(normalToggleButton);

		JToggleButton adjustedToggleButton = new JToggleButton("Adjusted");
		adjustedToggleButton.setToolTipText("Press SPACE or ENTER to toggle");
		//Keys strokes and action names are similarly taken from:
		//WindowsLookAndFeel, "ToggleButton.focusInputMap" property.
		//BasicButtonListener#Actions
		adjustedToggleButton.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "pressed");
		adjustedToggleButton.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("released ENTER"), "released");
		result.add(adjustedToggleButton);
		return result;
	}

	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 		
		JFrame f = new EnterKeyForToggleButton();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}