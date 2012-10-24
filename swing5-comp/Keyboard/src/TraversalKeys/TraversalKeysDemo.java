package TraversalKeys;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 * Default traversal keystroks for tabbing in JTable are Ctrl+Tab and Ctrl+Sfht+Tab. 
 * Adds Tab and Shft+Tab as traversal keystroks in JTable.
 * @author idanilov
 * @jdk 1.5
 */
public class TraversalKeysDemo extends JFrame  {

	public TraversalKeysDemo() {
		super(TraversalKeysDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(1,3));
		result.add(new JScrollPane(createTable()));
		result.add(new JScrollPane(createTable()));
		result.add(new JScrollPane(createTable()));
		return result;
	}
	
	private JTable createTable() {
		String [] cols = {"col1","col2"}; 
		String [][] data = {{"1","2"},{"3","4"}};
		JTable result = new JTable(data,cols);
		result.setPreferredScrollableViewportSize(new Dimension(100,100));
		setTraversalKeys(result);
		return result;
	}
	
	private static void setTraversalKeys(final JComponent c) {
		Set<AWTKeyStroke> keys = c.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
		keys = new HashSet<AWTKeyStroke>(keys);        
		keys.add(KeyStroke.getKeyStroke("TAB"));        
		c.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keys);        
		keys = c.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);        
		keys = new HashSet<AWTKeyStroke>(keys);        
		keys.add(KeyStroke.getKeyStroke("shift TAB"));        
		c.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, keys);		
	}
	
	public static void main(String[] args) {
		JFrame f = new TraversalKeysDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
