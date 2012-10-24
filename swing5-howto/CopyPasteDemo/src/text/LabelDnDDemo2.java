package text;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
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
public class LabelDnDDemo2 extends JPanel {

	JTextField textField;
	JLabel label;

	public LabelDnDDemo2() {
		super(new BorderLayout());
		JColorChooser chooser = new JColorChooser();
		chooser.setDragEnabled(true);
		label = new JLabel("I'm a Label and I accept color!", SwingConstants.LEADING);
		label.setTransferHandler(new TransferHandler("foreground"));
		MouseListener listener = new DragMouseAdapter();
		label.addMouseListener(listener);
		JPanel lpanel = new JPanel(new GridLayout(1, 1));
		TitledBorder t2 = BorderFactory.createTitledBorder("JLabel: drop color onto the label");
		lpanel.add(label);
		lpanel.setBorder(t2);
		add(chooser, BorderLayout.CENTER);
		add(lpanel, BorderLayout.PAGE_END);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("LabelDnD2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContentPane = new LabelDnDDemo2();
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