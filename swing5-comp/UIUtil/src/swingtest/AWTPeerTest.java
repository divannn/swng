package swingtest;

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.peer.ComponentPeer;

/**
 * Shows that:
 * <br>
 * 1. After AWT component creation its peer is not created.
 * <br>
 * 2. After UI is realized - peers are created in {@link Component#addNotify()}.
 * <br>
 * @author idanilov
 * @jdk 1.5
 */
public class AWTPeerTest extends Frame {

	private Button button;
	private TextField textField;
	private List list;
	
	public AWTPeerTest() {
		super(AWTPeerTest.class.getSimpleName());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		add(createContents());
		System.err.println("--- after creation ---");
		printPeer(button);
		printPeer(textField);
		printPeer(list);
		printPeer(this);
		System.err.println();
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
		System.err.println("=== after UI realization ===");
		printPeer(button);
		printPeer(textField);
		printPeer(list);
		printPeer(this);
		System.err.println();
	}
	
	private Panel createContents() {
		Panel result = new Panel();
		button = new Button("AWT Button");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				System.err.println("*** after Component removal ***");
				Container parent = button.getParent(); 
				parent.remove(button);
				parent.validate();
				parent.repaint();
				printPeer(button);
			}
			
		});
		result.add(button);
		textField = new TextField("AWT TextField");
		result.add(textField);
		list = new List();
		list.add("AWT list");
		result.add(list);
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
		Frame f = new AWTPeerTest();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
