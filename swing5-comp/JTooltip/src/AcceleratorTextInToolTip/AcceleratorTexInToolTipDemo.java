package AcceleratorTextInToolTip;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class AcceleratorTexInToolTipDemo extends JFrame {

	/**
	 * Delimeter between tooltip and accelerator.
	 */
	private static final String DELIMETER = "   ";
	
	public AcceleratorTexInToolTipDemo() {
		super(AcceleratorTexInToolTipDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JToolBar toolBar = new JToolBar();
		Action a1 = new AbstractAction() {

			public void actionPerformed(ActionEvent ae) {
			}
			
		};
		Icon icon = new ImageIcon(AcceleratorTexInToolTipDemo.class.getResource("image.gif"));
		a1.putValue(Action.SMALL_ICON,icon);
		a1.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F1,InputEvent.CTRL_DOWN_MASK));
		a1.putValue(Action.SHORT_DESCRIPTION,"Tooltip");
		enableAcceleratedTooltips(a1);
		toolBar.add(a1);
		result.add(toolBar, BorderLayout.NORTH);
		return result;
	}

	public static Action enableAcceleratedTooltips(final Action action) { 
	    String acceleratorText = getAcceleratorText((KeyStroke)action.getValue(Action.ACCELERATOR_KEY)); 
	    String oldToolTip = (String)action.getValue(Action.SHORT_DESCRIPTION);
	    action.putValue(Action.SHORT_DESCRIPTION, oldToolTip + DELIMETER + acceleratorText); 
	    return action; 
	}
	
	public static String getAcceleratorText(final KeyStroke accelerator) {
		String acceleratorDelimiter = UIManager.getString("MenuItem.acceleratorDelimiter");
		if (acceleratorDelimiter == null) {
			acceleratorDelimiter = "+";
		}
		String result = "";
		if (accelerator != null) {
			int modifiers = accelerator.getModifiers();
			if (modifiers > 0) {
				result = KeyEvent.getKeyModifiersText(modifiers);
				result += acceleratorDelimiter;
			}
			int keyCode = accelerator.getKeyCode();
			if (keyCode != 0) {
				result += KeyEvent.getKeyText(keyCode);
			} else {
				result += accelerator.getKeyChar();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
		JFrame f = new AcceleratorTexInToolTipDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}