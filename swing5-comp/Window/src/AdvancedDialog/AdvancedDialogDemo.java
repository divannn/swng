package AdvancedDialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class AdvancedDialogDemo extends JDialog {

	private JComponent advancedArea;
	private JButton advancedButton;
	private String showTitle = "Advanced >>";
	private String hideTitle = "Advanced <<";

	public AdvancedDialogDemo() {
		super((JFrame) null, AdvancedDialogDemo.class.getSimpleName());
		setResizable(false);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createHeader(), BorderLayout.NORTH);
		result.add(createMainArea(), BorderLayout.CENTER);
		advancedArea = createaAdvancedArea();
		advancedArea.setVisible(false);
		result.add(advancedArea, BorderLayout.SOUTH);
		return result;
	}

	private JComponent createHeader() {
		JPanel result = new JPanel(new BorderLayout(10,0));
		ImageIcon image = new ImageIcon(AdvancedDialogDemo.class.getResource("login.png"));
		JLabel imageLabel = new JLabel(image);
		result.add(imageLabel,BorderLayout.WEST);
		JLabel txtLabel = new JLabel("Welcome!");
		txtLabel.setFont(txtLabel.getFont().deriveFont(Font.BOLD));
		result.add(txtLabel,BorderLayout.CENTER);
		return result;
	}

	private JComponent createMainArea() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createClientArea(), BorderLayout.CENTER);
		result.add(createButtonsBar(), BorderLayout.SOUTH);
		return result;
	}

	private JComponent createClientArea() {
		JPanel result = new JPanel(new GridBagLayout());
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.top = 5;
		result.add(new JLabel("Login:"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx++;
		gbc.insets.left = 5;
		JTextField tf = new JTextField();
		result.add(tf, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets.left = 0;
		result.add(new JLabel("Password:"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx++;
		gbc.insets.left = 5;
		JPasswordField pf = new JPasswordField();
		pf.setPreferredSize(tf.getPreferredSize());
		result.add(pf, gbc);
		return result;
	}

	private JComponent createButtonsBar() {
		JPanel result = new JPanel(new BorderLayout(0,5));
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel mainBar = new JPanel(new GridLayout(1, 0, 5, 0));
		mainBar.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		mainBar.add(okButton);
		JButton cancelButton = new JButton("Cancel");
		mainBar.add(cancelButton);
		result.add(new JSeparator(), BorderLayout.NORTH);
		result.add(mainBar, BorderLayout.EAST);

		advancedButton = new JButton(showTitle);
		advancedButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				toggleAdvanced();
			}

		});
		result.add(advancedButton, BorderLayout.WEST);

		return result;
	}

	private JComponent createaAdvancedArea() {
		JPanel result = new JPanel(new GridBagLayout());
		result.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.top = 5;
		result.add(new JLabel("Server:"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx++;
		gbc.insets.left = 5;
		result.add(new JComboBox(new String[] { "127.0.0.1", "255.255.0.0" }), gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets.left = 0;
		result.add(new JLabel("Port:"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx++;
		gbc.insets.left = 5;
		result.add(new JComboBox(new String[] { "999", "1001" }), gbc);
		return result;
	}

	private void toggleAdvanced() {
		advancedArea.setVisible(!advancedArea.isVisible());
		advancedButton.setText(advancedArea.isVisible() ? hideTitle : showTitle);
		pack();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		AdvancedDialogDemo d = new AdvancedDialogDemo();
		d.pack();
		d.setModal(true);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		System.exit(0);
	}

}