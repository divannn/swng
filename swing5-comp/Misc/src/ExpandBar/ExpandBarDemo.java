package ExpandBar;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

import ExpandBar.expandbar.ExpandItem;
import ExpandBar.expandbar.JExpandBar;

/**
 * Simple realisation of ExpanBar. Traversing between expand items is via Tab. 
 * Spacebar or left mouse click toggles item to open/close.
 * Focusability is supported.
 * @author idanilov
 * @jdk 1.5
 */
public class ExpandBarDemo extends JFrame {

	public ExpandBarDemo() {
		super(ExpandBarDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultListModel m = new DefaultListModel();
		ImageIcon icon = new ImageIcon(ExpandBarDemo.class.getResource("image.gif"));
		m.addElement(new ExpandItem(new JTextField(), "item1", icon));
		m.addElement(new ExpandItem(new JScrollPane(new JTable(5,3)), "item2", icon));
		m.addElement(new ExpandItem(new JScrollPane(new JTree()), "item3", icon));
		JExpandBar eb = new JExpandBar(m);
		result.add(new JScrollPane(eb.getUI()), BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ExpandBarDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
