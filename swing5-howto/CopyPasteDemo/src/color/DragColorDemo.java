package color;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class DragColorDemo extends JPanel
		implements ActionListener {

	JCheckBox toggleForeground;
	ColorTransferHandler colorHandler;

	public DragColorDemo() {
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JColorChooser chooser = new JColorChooser();
		chooser.setDragEnabled(true);
		add(chooser, BorderLayout.PAGE_START);

		//Create the color transfer handler.
		colorHandler = new ColorTransferHandler();

		//Create a matrix of 9 buttons.
		JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++) {
			JButton tmp = new JButton("Button " + i);
			tmp.setTransferHandler(colorHandler);
			buttonPanel.add(tmp);
		}
		add(buttonPanel, BorderLayout.CENTER);

		//Create a check box.
		toggleForeground = new JCheckBox("Change the foreground color.");
		toggleForeground.setSelected(true);
		toggleForeground.addActionListener(this);
		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textPanel.add(toggleForeground, BorderLayout.PAGE_START);

		//Create a label.
		JLabel label = new JLabel(
				"Change the color of any button or this label by dropping a color.");
		label.setTransferHandler(colorHandler);
		textPanel.add(label, BorderLayout.PAGE_END);
		add(textPanel, BorderLayout.PAGE_END);
	}

	public void actionPerformed(ActionEvent e) {
		colorHandler.setChangesForegroundColor(toggleForeground.isSelected());
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("DragColorDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContentPane = new DragColorDemo();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				createAndShowGUI();
			}
		});
	}

}