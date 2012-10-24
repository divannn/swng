package ListSearch;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * @author sun
 * @author idanilov
 * @jdk 1.5
 */
public class ListSearchDemo extends JFrame {

	private static final String DATA[] = { "Partridge in a pear tree", "Turtle Doves",
			"French Hens", "Calling Birds", "Golden Rings", "Geese-a-laying", "Swans-a-swimming",
			"Maids-a-milking", "Ladies dancing", "Lords-a-leaping", "Pipers piping",
			"Drummers drumming", "Dasher", "Dancer", "Prancer", "Vixen", "Comet", "Cupid",
			"Donner", "Blitzen", "Rudolf", "Bakerloo", "Center", "Circle", "District",
			"East London", "Hammersmith and City", "Jubilee", "Metropolitan", "Northern",
			"Piccadilly Royal", "Victoria", "Waterloo and City", "Alpha", "Beta", "Gamma", "Delta",
			"Epsilon", "Zeta", "Eta", "Theta", "Iota", "Kapa", "Lamda", "Mu", "Nu", "Xi",
			"Omikron", "Pi", "Rho", "Sigma", "Tau", "Upsilon", "Phi", "Chi", "Psi", "Omega" };

	public ListSearchDemo() {
		super(ListSearchDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTextField text = new JTextField();
		result.add(text, BorderLayout.NORTH);
		FilteredList list = new FilteredList();
		JScrollPane scrollPane = new JScrollPane(list);
		result.add(scrollPane, BorderLayout.CENTER);
		for (String nextElement : DATA) {
			list.addElement(nextElement);
		}
		list.installTextField(text);
		return result;
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ListSearchDemo f = new ListSearchDemo();
		f.setSize(300, 200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
