package swingtest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 * Shows that:
 * <br> Disposed frame can be made visible again.
 * @author idanilov
 * @jdk 1.5
 */
public class DisposeTest extends JFrame {

	public DisposeTest() {
		super(DisposeTest.class.getSimpleName());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JButton disposeButton = new JButton("Dispose");
		disposeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				instance.setVisible(false);
				instance.dispose();
				System.err.println("---frame disposed: " + System.identityHashCode(instance));
				instance.setVisible(true);
				System.err.println("+++frame recreated: " + System.identityHashCode(instance));
			}
		});
		result.add(disposeButton, BorderLayout.NORTH);
		JTable t = new JTable(3,2);
		t.setPreferredScrollableViewportSize(new Dimension(100,100));
		result.add(new JScrollPane(t), BorderLayout.CENTER);
		return result;
	}

	
	public static void main(String[] args) {
		instance = new DisposeTest();
		instance.pack();
		instance.setLocationRelativeTo(null);
		instance.setVisible(true);
	}

	private static JFrame instance;
	
}
