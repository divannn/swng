package AutoIndent;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * @author santosh
 * @jdk 1.5
 */
public class AutoIndentDemo extends JFrame {

	public AutoIndentDemo() {
		super(AutoIndentDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String text = 
			"public class AutoIndentDemo extends JFrame {\n"+
			"	public AutoIndentDemo() {\n"+
			"		super(AutoIndentDemo.class.getSimpleName());\n"+
			"		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n"+
			"		setContentPane(createContents());\n"+
			"	}\n"+
			"}";
		JTextArea textArea = new JTextArea(text);
		textArea.registerKeyboardAction(new AutoIndentAction(), KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
		result.add(new JScrollPane(textArea), BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new AutoIndentDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
