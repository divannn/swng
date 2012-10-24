package swingtest;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.ComponentPeer;
import java.awt.peer.LightweightPeer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;

import sun.awt.NullComponentPeer;

/**
 * Shows that:
 * <br>
 * 1. After JComponent creation its peer is not created.
 * <br>
 * 2. After UI is realized - peers are created in {@link Component#addNotify()}.
 * <br>
 * 3. After JComponent is removed from parent - peer eliminated (null) {@link Component#removeNotify()}.
 * <br>
 * 4. For all {@link JComponent} peer is {@link NullComponentPeer}. It implements {@link LightweightPeer}.
 * Pay attention that lightweight peers for button and text field are the same instance. 
 * <br>
 * 5.JFrame's peer is real peer.
 * @author idanilov
 * @jdk 1.5
 */
public class SwingPeerTest extends JFrame {

	private JButton button;
	private JTextField textField;
	private JTree tree;
	
	public SwingPeerTest() {
		super(SwingPeerTest.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
		System.err.println("--- after creation ---");
		printPeer(button);
		printPeer(textField);
		printPeer(tree);
		printPeer(this);
		System.err.println();
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
		System.err.println("=== after UI realization ===");
		printPeer(button);
		printPeer(textField);
		printPeer(tree);
		printPeer(this);
		System.err.println();
	}
	
	private JPanel createContents() {
		JPanel result = new JPanel();
		button = new JButton("JButton");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				System.err.println("*** after JComponent removal ***");
				Container parent = button.getParent(); 
				parent.remove(button);
				parent.validate();
				parent.repaint();
				printPeer(button);
			}
			
		});
		result.add(button);
		textField = new JTextField("JTextField");
		result.add(textField);
		tree = new JTree();
		result.add(tree);
		return result;
	}

	private static void printPeer(final Component comp) {
		ComponentPeer peer = comp.getPeer();
		if (peer == null) {
			System.err.println("comp = " + comp.getClass().getSimpleName() + " peer = " + peer);
		} else {
			System.err.println("comp = " + comp.getClass().getSimpleName() + " peer = " + peer);
		}
	}
	
	public static void main(String[] args) {
		JFrame f = new SwingPeerTest();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
