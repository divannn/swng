package text;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class LabelDnDDemo extends JPanel {

	private JTextField textField;

	private JLabel label;

	public LabelDnDDemo() {
		super(new GridLayout(2, 1));
		textField = new JTextField(40);
		textField.setDragEnabled(true);
		JPanel tfpanel = new JPanel(new GridLayout(1, 1));
		TitledBorder t1 = BorderFactory.createTitledBorder("JTextField: drag and drop is enabled");
		tfpanel.add(textField);
		tfpanel.setBorder(t1);
		label = new JLabel("I'm a Label!", SwingConstants.LEADING);
		label.setTransferHandler(new TransferHandler("text"));
		MouseListener listener = new DragMouseAdapter();
		label.addMouseListener(listener);
		JPanel lpanel = new JPanel(new GridLayout(1, 1));
		TitledBorder t2 = BorderFactory
				.createTitledBorder("JLabel: drag from or drop to this label");
		lpanel.add(label);
		lpanel.setBorder(t2);
		add(tfpanel);
		add(lpanel);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("LabelDnD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContentPane = new LabelDnDDemo();
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

	private static class DragMouseAdapter extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
		}
	}

}