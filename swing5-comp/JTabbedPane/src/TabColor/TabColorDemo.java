package TabColor;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;


/**
 * Shows how to higlight active selected tab background.
 * @author tomemasa
 * @author idanilov
 * @jdk 1.5
 */
public class TabColorDemo extends JFrame {

	private String[] titles = { "blue", "cyan", "green", "yellow", "orange", "pink", "red" };
	private Color[] colors = { Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK,
			Color.red };

	public TabColorDemo() {
		super(TabColorDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new ColoredTabbedPane();
		for (int i = 0; i < titles.length; i++) {
			tabbedPane.addTab(titles[i], createPane(colors[i]));
			tabbedPane.setBackgroundAt(i, colors[i].darker());
		}
		tabbedPane.setSelectedIndex(0);
		result.add(tabbedPane,BorderLayout.CENTER);
		return result;
	}

	private JPanel createPane(final Color color) {
		JPanel result = new JPanel();
		result.setBackground(color);
		return result;
	}

	private class ColoredTabbedPane extends JTabbedPane {

		public Color getBackgroundAt(int index) {
			if (index == getSelectedIndex()) {
				return colors[index];
			}
			return super.getBackgroundAt(index);
		}
		
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new TabColorDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
