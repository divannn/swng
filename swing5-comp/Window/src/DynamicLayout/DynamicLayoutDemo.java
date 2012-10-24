package DynamicLayout;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Playing with window decorations, minimum size of window and dynamic layout.
 * Demo for JDialog but the same is for JFrame.
 * @author idanilov
 * @jdk 1.5
 */
public class DynamicLayoutDemo extends JDialog {

	public DynamicLayoutDemo() {
		super((JFrame) null, DynamicLayoutDemo.class.getSimpleName());
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();
		JButton a = new JButton("aaaaaaa");
		result.add(a);
		JButton b = new JButton("bbbbbbb");
		result.add(b);
		JButton c = new JButton("ccccccc");
		result.add(c);
		return result;
	}

	public static void main(String[] args) {
		/*try {
		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 } catch (Exception e) {
		 e.printStackTrace();
		 }*/
		JDialog.setDefaultLookAndFeelDecorated(true);
		Toolkit.getDefaultToolkit().setDynamicLayout(true);
		System.setProperty("sun.awt.noerasebackground", "true");//needed to avoid ugly flicking

		DynamicLayoutDemo d = new DynamicLayoutDemo();
		//restrict window mimimum size - but this works only for Metal L&F =(.
		d.setMinimumSize(new Dimension(200, 200));
		d.setSize(new Dimension(300, 300));
		d.setModal(true);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		System.exit(0);
	}

}