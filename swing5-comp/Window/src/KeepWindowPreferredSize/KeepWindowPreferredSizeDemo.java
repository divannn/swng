package KeepWindowPreferredSize;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 * Restores window's preferred size and location if after resizing it's real size is smaller than preferred.
 * Demo is written for JFrame, the same applicable for JDialog.
 * <br>
 * <strong>Note</strong>: For JInternalFrame this demo can be applied also but no need for this because setMinimumSize(Deimesion d) works perferctly for JInternalFrame.
 * @author idanilov
 * @jdk 1.5
 */
public class KeepWindowPreferredSizeDemo extends JFrame {

	public KeepWindowPreferredSizeDemo() {
		super(KeepWindowPreferredSizeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent contents = createContents();
		setContentPane(contents);
		contents.setBorder(new ResizableBorder(5, 5, 5, 5));//set border with indication to resize.
		new WindowResizer(this);
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		JLabel l = new JLabel("Text:");
		topPanel.add(l);
		JTextField tf = new JTextField("text field");
		tf.setColumns(10);
		topPanel.add(tf);
		JButton b = new JButton("Button");
		topPanel.add(b);
		result.add(topPanel, BorderLayout.NORTH);
		JTree t = new JTree();
		JScrollPane scrollPane = new JScrollPane(t);
		result.add(scrollPane, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new KeepWindowPreferredSizeDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
