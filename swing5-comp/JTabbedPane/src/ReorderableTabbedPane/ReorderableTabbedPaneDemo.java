package ReorderableTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;


/**
 * Allows reordering tabs by DnD, and highlight tab when dragging over.
 * @author sun forums
 * @author idanilov
 * @jdk 1.5
 */
public class ReorderableTabbedPaneDemo extends JFrame {

	private String[] titles = { "blue", "cyan", "green", "yellow", "orange", "pink", "red" };

	public ReorderableTabbedPaneDemo() {
		super(ReorderableTabbedPaneDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new ReorderableTabbedPane();
		for (int i = 0; i < titles.length; i++) {
			tabbedPane.addTab(titles[i], new JLabel(titles[i]));
		}
		tabbedPane.setSelectedIndex(0);
		result.add(tabbedPane,BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ReorderableTabbedPaneDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
