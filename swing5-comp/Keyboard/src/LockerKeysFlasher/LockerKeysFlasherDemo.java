package LockerKeysFlasher;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Shows ability to set Caps Lock, Scroll Lock an Num Lock programmatically.
 * @author idanilov
 * @jdk 1.5
 */
public class LockerKeysFlasherDemo extends JFrame {

	public LockerKeysFlasherDemo() {
		super(LockerKeysFlasherDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		KeyboardFlasher flasher = new KeyboardFlasher();
		Toolkit tk = Toolkit.getDefaultToolkit();
		tk.addAWTEventListener(flasher, AWTEvent.KEY_EVENT_MASK);
		JTextField tf = new JTextField("some control");
		result.add(tf);
		return result;
	}
	
	public static void main(String[] args) {
        JFrame frame = new LockerKeysFlasherDemo();
        frame.pack();
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

	private static class KeyboardFlasher 
			implements AWTEventListener {

		public void eventDispatched(AWTEvent event) {
			if (event instanceof KeyEvent) {
				KeyEvent kevt = (KeyEvent)event;
				if (kevt.getID( ) == KeyEvent.KEY_PRESSED) {
					System.out.println("key event: " + event);
					if (kevt.getKeyCode( ) != KeyEvent.VK_SCROLL_LOCK) {
						flipScrollLock();
					}
				}
			}			
		}
		
		private void flipScrollLock() {
			Toolkit tk = Toolkit.getDefaultToolkit( );
			boolean state = tk.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
			tk.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK,!state);
		}
		
	}
	
}
