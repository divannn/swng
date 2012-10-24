import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;

/**
 * @author idanilov
 * @jdk 1.6
 */
public class GroupLayoutDemo extends JFrame {

	public GroupLayoutDemo() {
		super(GroupLayoutDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();

		JLabel label = new JLabel("Find:");
		JTextField textField = new JTextField();
		JCheckBox caseCheckBox = new JCheckBox("Match case");
		JCheckBox wrapCheckBox = new JCheckBox("Wrap around");
		JCheckBox wholeCheckBox = new JCheckBox("Whole words");
		JCheckBox backCheckBox = new JCheckBox("Search backwards");
		JButton findButton = new JButton("Find");
		JButton cancelButton = new JButton("Cancel");

		// remove redundant default border of check boxes - they would hinder
		// correct spacing and aligning (maybe not needed on some look and feels).
		caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		wrapCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		wholeCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		backCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		GroupLayout layout = new GroupLayout(result);
		//comment this 2 lines and see results.
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(label).addGroup(
				layout.createParallelGroup(Alignment.LEADING).addComponent(textField).addGroup(
						layout.createSequentialGroup().addGroup(
								layout.createParallelGroup(Alignment.LEADING).addComponent(
										caseCheckBox).addComponent(wholeCheckBox)).addGroup(
								layout.createParallelGroup(Alignment.LEADING).addComponent(
										wrapCheckBox).addComponent(backCheckBox)))).addGroup(
				layout.createParallelGroup(Alignment.LEADING).addComponent(findButton)
						.addComponent(cancelButton)));

		layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(Alignment.BASELINE).addComponent(label).addComponent(
						textField).addComponent(findButton)).addGroup(
				layout.createParallelGroup(Alignment.LEADING).addGroup(
						layout.createSequentialGroup().addGroup(
								layout.createParallelGroup(Alignment.BASELINE).addComponent(
										caseCheckBox).addComponent(wrapCheckBox)).addGroup(
								layout.createParallelGroup(Alignment.BASELINE).addComponent(
										wholeCheckBox).addComponent(backCheckBox))).addComponent(
						cancelButton)));
		result.setLayout(layout);
		return result;
	}

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new GroupLayoutDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
