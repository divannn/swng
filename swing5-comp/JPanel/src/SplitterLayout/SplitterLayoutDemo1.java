package SplitterLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author santosh
 * @jdk 1.5
 */
public class SplitterLayoutDemo1 extends JFrame {

	public SplitterLayoutDemo1() {
		super(SplitterLayoutDemo1.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	
	private JPanel createContents() {
		JPanel result = new JPanel(new SplitterLayout(SplitterLayout.HORIZONTAL));
		result.add("1",new JButton("A (1)"));
		result.add("2",new JButton("B (2)"));
		result.add("3",new JButton("C (3)"));
		result.add("4",new JButton("D (4)"));
		result.add("5",new JButton("E (5)"));
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new SplitterLayoutDemo1();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
