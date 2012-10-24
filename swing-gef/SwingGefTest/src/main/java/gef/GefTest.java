package gef;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author idanilov
 *
 */
public class GefTest extends JFrame {

	public static void main(String[] args) {
		GefTest f = new GefTest();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setSize(300, 200);
		f.setVisible(true);
	}

	public GefTest() {
		super(GefTest.class.getSimpleName());
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createEditor(), BorderLayout.CENTER);
		return result;
	}

	private JComponent createEditor() {
		NodeEditor editor = new NodeEditor();
		return editor.getControl();
	}

}
