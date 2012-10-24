import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 * Minimum size respected during resizing by mouse on Windows.
 * But this is platform dependent. 
 * See {@link Window#setMinimumSize(java.awt.Dimension)} documentation.
 *  
 * @author idanilov
 * @jdk 1.6
 */
public class WindowMinimumSizeDemo extends JFrame {

	public WindowMinimumSizeDemo() {
		super(WindowMinimumSizeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTree tree = new JTree();
		result.add(new JScrollPane(tree),BorderLayout.CENTER);
		JButton b = new JButton("Show dialog");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JDialog d = new JDialog(WindowMinimumSizeDemo.this,"Dialog with restricted minimum size");
				JTextArea ta = new JTextArea("text\n\ttext\n\t\ttext");
				d.getContentPane().add(new JScrollPane(ta));
				d.pack();
				d.setMinimumSize(d.getPreferredSize());
				d.setLocationRelativeTo(WindowMinimumSizeDemo.this);
				d.setVisible(true);
			}
		});
		result.add(b,BorderLayout.NORTH);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new WindowMinimumSizeDemo();
		f.pack();
		f.setMinimumSize(f.getPreferredSize());
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
