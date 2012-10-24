package ShortCutTrap;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author santosh
 * @jdk 1.5
 */
public class ShortCutTrapDemo extends JFrame {

	public ShortCutTrapDemo() {
		super(ShortCutTrapDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
        JTextField textField = new JTextField();
        textField.setColumns(20);
        //trap focus traversal keys also. 
        Set<AWTKeyStroke> emptySet = Collections.emptySet();
        textField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet); 
        textField.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, emptySet); 
        textField.addKeyListener(new KeyListener() { 
            int currentKeyCode = 0; 
            public void keyPressed(final KeyEvent ke) { 
                ke.consume(); 
                JTextField tf = (JTextField)ke.getSource(); 
                tf.setText(toString(ke)); 
            } 
 
            public void keyReleased(final KeyEvent ke) { 
                ke.consume(); 
                JTextField tf = (JTextField)ke.getSource(); 
                switch(currentKeyCode) { 
                    case KeyEvent.VK_ALT: 
                    case KeyEvent.VK_ALT_GRAPH: 
                    case KeyEvent.VK_CONTROL: 
                    case KeyEvent.VK_SHIFT: 
                        tf.setText(""); 
                        return; 
                } 
            } 
 
            public void keyTyped(final KeyEvent ke) { 
                ke.consume(); 
            }
            
            private String toString(final KeyEvent ke) { 
                int keyCode = currentKeyCode = ke.getKeyCode(); 
                int modifiers = ke.getModifiers(); 
                String modifText = KeyEvent.getKeyModifiersText(modifiers); 
                if ("".equals(modifText)) {//no modifiers. 
                    return KeyEvent.getKeyText(keyCode);
                }
                switch (keyCode) { 
                    case KeyEvent.VK_ALT: 
                    case KeyEvent.VK_ALT_GRAPH: 
                    case KeyEvent.VK_CONTROL: 
                    case KeyEvent.VK_SHIFT: 
                        return modifText+"+"; //middle of shortcut. 
                    default: 
                        return modifText+"+"+KeyEvent.getKeyText(keyCode); 
                } 
            } 
        }); 
        result.add(textField);
		return result;
	}
	
	public static void main(final String [] args) { 
		JFrame f = new ShortCutTrapDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	} 

}
