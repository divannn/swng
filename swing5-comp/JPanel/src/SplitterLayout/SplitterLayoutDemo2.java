package SplitterLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author santosh
 * @jdk 1.5
 */
public class SplitterLayoutDemo2 extends JFrame {

	public SplitterLayoutDemo2() {
		super(SplitterLayoutDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new SplitterLayout(SplitterLayout.HORIZONTAL));
		result.add("5", new JButton("A (1)"));
		result.add(new JSplitterBar());
		result.add("3", new JButton("B (2)"));
		result.add(new JSplitterBar());
		result.add("5", new JButton("C (4)"));
		result.add(new JSplitterBar());
		result.add("3", new JButton("D (8)"));
		result.add(new JSplitterBar());
		result.add("5", new JButton("E (3)"));
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new SplitterLayoutDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
