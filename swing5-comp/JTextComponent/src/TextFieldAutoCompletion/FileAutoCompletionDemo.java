package TextFieldAutoCompletion;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import TextFieldAutoCompletion.textcompleter.FileAutoCompleter;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class FileAutoCompletionDemo extends JFrame {

	public FileAutoCompletionDemo() {
		super(FileAutoCompletionDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTextField tf = new JTextField();
		tf.setColumns(50);
		new FileAutoCompleter(tf);
		result.add(tf,BorderLayout.NORTH);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new FileAutoCompletionDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}