package ColoredToolTip;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class ColoredToolTipDemo extends JFrame {

	public ColoredToolTipDemo() {
		super(ColoredToolTipDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();
		JTextField tf = new JTextField("text");
		tf.setToolTipText("Colored tool tip");
	    result.add(tf);
		return result;
	}

	public static void main(String[] args) {
		//override the ToolTip's colors in Swing's defaults table.
		UIManager.put("ToolTip.foreground", Color.WHITE);
		UIManager.put("ToolTip.background", Color.BLUE);		
		JFrame f = new ColoredToolTipDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}