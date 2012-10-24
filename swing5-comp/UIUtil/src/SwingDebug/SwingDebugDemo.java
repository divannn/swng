package SwingDebug;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;


/**
 * Useful to debug Swing models and listeners.
 * This technique can be used at any place (not just Swing).
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class SwingDebugDemo extends JFrame {
	
	public SwingDebugDemo() {
		super(SwingDebugDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTree tree = new JTree();
		//interested how component lisneners invoked.
		ComponentListener compListener = new ComponentAdapter() {
		};
		//create debug proxy instance.
		compListener = (ComponentListener)DebugHandler.newInstance(compListener, null);
		tree.addComponentListener(compListener);		
		JScrollPane scrollPane = new JScrollPane(tree);
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new SwingDebugDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
