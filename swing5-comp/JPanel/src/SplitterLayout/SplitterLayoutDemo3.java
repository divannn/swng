package SplitterLayout;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * @author santosh
 * @jdk 1.5
 */
public class SplitterLayoutDemo3 extends JFrame {

	public SplitterLayoutDemo3() {
		super(SplitterLayoutDemo3.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	
	private JPanel createContents() {
		JPanel result = new JPanel(new SplitterLayout(SplitterLayout.VERTICAL));
		result.add("1", new JButton("A (1)"));
		JSplitterBar b1 = new JSplitterBar();
		b1.setLayout(new GridLayout(1, 0));
		b1.add(new JSplitterSpace());
		b1.add(new JLabel("Status"));
		b1.add(new JTextField("Enter your name:"));
		b1.add(new JSplitterSpace());
		result.add(b1);
		result.add("1", new JButton("B (2)"));
		JSplitterBar b2 = new JSplitterBar();
		b2.setLayout(new SplitterLayout(SplitterLayout.HORIZONTAL));
		b2.add("5", new JSplitterSpace());
		b2.add(new JSplitterBar());
		b2.add("10", new JLabel("Status"));
		b2.add(new JSplitterBar());
		b2.add("20", new JCheckBox("Check me"));
		result.add(b2);
		result.add("1", new JButton("C (3)"));
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new SplitterLayoutDemo3();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
