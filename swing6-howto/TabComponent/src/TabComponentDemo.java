import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 * Shows new feature - ability to set any component in the tabbed pane tab.
 * Here an example of simple closable tabs.
 * @jdk 1.6
 */
public class TabComponentDemo extends JFrame {

	public TabComponentDemo() {
		super(TabComponentDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTabbedPane tp = new JTabbedPane();
		for (int i = 0; i < 10; i++) {
			String tabTitle = "Tab" + i;
			tp.addTab(tabTitle, new JLabel(tabTitle));
			tp.setTabComponentAt(i, createTabComponent(tabTitle,tp));
		}
		result.add(tp, BorderLayout.CENTER);
		return result;
	}

	private JComponent createTabComponent(final String title,final JTabbedPane tabPane) {
		final JPanel result = new JPanel(new BorderLayout(10, 0));
		result.setOpaque(false);
		JLabel l = new JLabel(title);
		result.add(l, BorderLayout.CENTER);
		JButton b = new JButton("X");
		b.setFont(b.getFont().deriveFont(Font.BOLD));
		b.setFocusable(false);
		b.setContentAreaFilled(false);
		b.setBorder(null);
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				int i = tabPane.indexOfTabComponent(result);
				if (i != -1) {
					tabPane.remove(i);
				}
			}
		});
		result.add(b, BorderLayout.EAST);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new TabComponentDemo();
		f.pack();
		f.setMinimumSize(f.getPreferredSize());
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
