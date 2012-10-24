package TextFieldAutoCompletion;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import TextFieldAutoCompletion.textcompleter.ListAutoCompleter;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class ListAutoCompletionDemo extends JFrame {

	public ListAutoCompletionDemo() {
		super(ListAutoCompletionDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTextField tf = new JTextField();
		tf.setColumns(50);
		List availableChoices = Arrays.asList(new String[] { "JFC", "Swing", "JSF", "JSP", "JAXP",
				"JDBC", "J2EE" });
		new ListAutoCompleter(tf, availableChoices, true);
		result.add(tf, BorderLayout.NORTH);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ListAutoCompletionDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
