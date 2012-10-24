package Selector;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 * Selector chooser demo - control similar that used in FireFOx preferences dialog.
 * Items can be arranged to grid in any columns or any rows.
 * <br>
 * Rollover behaviour can be easily added to highlight item on mouse move.
 * @author idanilov
 * @jdk 1.5
 */
public class SelectorDemo extends JFrame {

	public SelectorDemo() {
		super(SelectorDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		List<SelectorItem> data = new ArrayList<SelectorItem>();
		Icon pngIcon = new ImageIcon(SelectorDemo.class.getResource("png.png"));
		data.add(new SelectorItem("PNG", pngIcon));
		Icon gifIcon = new ImageIcon(SelectorDemo.class.getResource("gif.png"));
		data.add(new SelectorItem("GIF", gifIcon));
		Icon jpgIcon = new ImageIcon(SelectorDemo.class.getResource("jpg.png"));
		SelectorItem disabledItem = new SelectorItem("JPG", jpgIcon);
		disabledItem.setEnabled(false);
		data.add(disabledItem);
		SelectorTableModel tm = new SelectorTableModel(data, 2, false);
		SelectorTable selectorTable = new SelectorTable(tm);
		result.add(new JScrollPane(selectorTable), BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame f = new SelectorDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
