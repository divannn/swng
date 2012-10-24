package ExtendedScrollPaneLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Shows how to add useful area for JScrollPane scroll bars.
 * @author santosh
 * @jdk 1.5
 */
public class ExtendedScrollPaneLayoutDemo extends JFrame {

	public ExtendedScrollPaneLayoutDemo() {
		super(ExtendedScrollPaneLayoutDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTable table = new JTable(40,10);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLayout(new ExtendedScrollPaneLayout());
		scrollPane.add(ExtendedScrollPaneLayout.HORIZONTAL_LEFT,new JTextField("text"));
		scrollPane.add(ExtendedScrollPaneLayout.HORIZONTAL_RIGHT,new JButton("Button"));
		JCheckBox cb = new JCheckBox();
		cb.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		scrollPane.add(ExtendedScrollPaneLayout.VERTICAL_TOP,cb);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100,100));
		scrollPane.add(ExtendedScrollPaneLayout.VERTICAL_BOTTOM,panel);
		
		result.add(scrollPane,BorderLayout.CENTER);

		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ExtendedScrollPaneLayoutDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
