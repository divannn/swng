import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.6
 */
public class ModalityDemo {

	// first document: frame, modeless dialog, document-modal dialog
	private JFrame frame1;
	private JDialog modelessDialog1;
	private JDialog documentModalDialog1;

	// second document: frame, modeless dialog, document-modal dialog
	private JFrame frame2;
	private JDialog modelessDialog2;
	private JDialog documentModalDialog2;

	// third document: modal excluded frame
	private JFrame frame3;

	// fourth document: frame, parent of application-modal dialog.
	private JFrame frame4;

	private static WindowListener closeWindowListener = new WindowAdapter() {

		public void windowClosing(WindowEvent we) {
			we.getWindow().dispose();
		}
	};

	private void createGUI() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Insets ins = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		int sw = gc.getBounds().width - ins.left - ins.right;
		int sh = gc.getBounds().height - ins.top - ins.bottom;

		// first document
		frame1 = createDocument1();
		frame1.setBounds(32, 32, 300, 200);

		// second document
		frame2 = createDocument2();
		frame2.setBounds(sw - 300 - 32, 32, 300, 200);

		// third frame
		frame3 = createExcludedFrame();
		frame3.setBounds(32, sh - 200 - 32, 300, 200);

		// fourth document
		frame4 = createFrame4ApplicationModalDialog();
		frame4.setBounds(sw - 300 - 32, sh - 200 - 32, 300, 200);
	}

	private void start() {
		frame1.setVisible(true);
		frame2.setVisible(true);
		frame3.setVisible(true);
		frame4.setVisible(true);
	}

	private JFrame createDocument1() {
		JFrame result = new JFrame("Book 1 (parent frame)");
		result.addWindowListener(closeWindowListener);
		// create radio buttons
		final JRadioButton rb11 = new JRadioButton("Biography", true);
		final JRadioButton rb12 = new JRadioButton("Funny tale", false);
		final JRadioButton rb13 = new JRadioButton("Sonnets", false);
		// place radio buttons into a single group
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rb11);
		bg1.add(rb12);
		bg1.add(rb13);
		JButton b1 = new JButton("OK");
		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// get label of selected radiobutton
				String title = null;
				if (rb11.isSelected()) {
					title = rb11.getText();
				} else if (rb12.isSelected()) {
					title = rb12.getText();
				} else {
					title = rb13.getText();
				}
				// prepend radio button label to dialogs' titles
				modelessDialog1.setTitle(title + " (modeless dialog)");
				documentModalDialog1.setTitle(title + " (document-modal dialog)");
				modelessDialog1.setVisible(true);
			}
		});
		Container cp1 = result.getContentPane();
		// create three containers to improve layouting
		cp1.setLayout(new GridLayout(1, 3));
		// an empty container
		Container cp11 = new Container();
		// a container to layout components
		Container cp12 = new Container();
		// an empty container
		Container cp13 = new Container();
		// add a button into a separate panel
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(b1);
		// add radio buttons and the OK button one after another into a single column
		cp12.setLayout(new GridLayout(4, 1));
		cp12.add(rb11);
		cp12.add(rb12);
		cp12.add(rb13);
		cp12.add(p1);
		// add three containers
		cp1.add(cp11);
		cp1.add(cp12);
		cp1.add(cp13);

		modelessDialog1 = new JDialog(result);
		modelessDialog1.setBounds(132, 132, 300, 200);
		modelessDialog1.addWindowListener(closeWindowListener);
		JLabel l2 = new JLabel("Enter your name: ");
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		final JTextField tf2 = new JTextField(12);
		tf2.setHorizontalAlignment(SwingConstants.CENTER);
		JButton b2 = new JButton("OK");
		b2.setHorizontalAlignment(SwingConstants.CENTER);

		Container cp2 = modelessDialog1.getContentPane();
		// add label, text field and button one after another into a single column
		cp2.setLayout(new BorderLayout());
		cp2.add(l2, BorderLayout.NORTH);
		cp2.add(tf2, BorderLayout.CENTER);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(b2);
		cp2.add(p2, BorderLayout.SOUTH);

		documentModalDialog1 = new JDialog(modelessDialog1, "", Dialog.ModalityType.DOCUMENT_MODAL);
		documentModalDialog1.setBounds(232, 232, 300, 200);
		documentModalDialog1.addWindowListener(closeWindowListener);
		JTextArea ta3 = new JTextArea();
		final JLabel nameLabel = new JLabel();
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		Container cp3 = documentModalDialog1.getContentPane();
		cp3.setLayout(new BorderLayout());
		cp3.add(new JScrollPane(ta3), BorderLayout.CENTER);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p3.add(nameLabel);
		cp3.add(p3, BorderLayout.SOUTH);

		//
		b2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//pass a name into the document modal dialog
				nameLabel.setText("by " + tf2.getText());
				documentModalDialog1.setVisible(true);
			}
		});
		return result;
	}

	private JFrame createDocument2() {
		JFrame result = new JFrame("Book 2 (parent frame)");
		result.addWindowListener(closeWindowListener);
		// create radio buttons
		final JRadioButton rb41 = new JRadioButton("Biography", true);
		final JRadioButton rb42 = new JRadioButton("Funny tale", false);
		final JRadioButton rb43 = new JRadioButton("Sonnets", false);
		// place radio buttons into a single group
		ButtonGroup bg4 = new ButtonGroup();
		bg4.add(rb41);
		bg4.add(rb42);
		bg4.add(rb43);
		JButton b4 = new JButton("OK");
		b4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// get label of selected radiobutton
				String title = null;
				if (rb41.isSelected()) {
					title = rb41.getText();
				} else if (rb42.isSelected()) {
					title = rb42.getText();
				} else {
					title = rb43.getText();
				}
				// prepend radiobutton label to dialogs' titles
				modelessDialog2.setTitle(title + " (modeless dialog)");
				documentModalDialog2.setTitle(title + " (document-modal dialog)");
				modelessDialog2.setVisible(true);
			}
		});
		Container cp4 = result.getContentPane();
		// create three containers to improve layouting
		cp4.setLayout(new GridLayout(1, 3));
		Container cp41 = new Container();
		Container cp42 = new Container();
		Container cp43 = new Container();
		// add the button into a separate panel
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout());
		p4.add(b4);
		// add radiobuttons and the OK button one after another into a single column
		cp42.setLayout(new GridLayout(4, 1));
		cp42.add(rb41);
		cp42.add(rb42);
		cp42.add(rb43);
		cp42.add(p4);
		//add three containers
		cp4.add(cp41);
		cp4.add(cp42);
		cp4.add(cp43);

		modelessDialog2 = new JDialog(result);
		modelessDialog2.setBounds(432, 132, 300, 200);
		modelessDialog2.addWindowListener(closeWindowListener);
		JLabel l5 = new JLabel("Enter your name: ");
		l5.setHorizontalAlignment(SwingConstants.CENTER);
		final JTextField tf5 = new JTextField(12);
		tf5.setHorizontalAlignment(SwingConstants.CENTER);
		JButton b5 = new JButton("OK");
		b5.setHorizontalAlignment(SwingConstants.CENTER);

		Container cp5 = modelessDialog2.getContentPane();
		// add label, text field and button one after another into a single column
		cp5.setLayout(new BorderLayout());
		cp5.add(l5, BorderLayout.NORTH);
		cp5.add(tf5, BorderLayout.CENTER);
		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout());
		p5.add(b5);
		cp5.add(p5, BorderLayout.SOUTH);

		documentModalDialog2 = new JDialog(modelessDialog2, "", Dialog.ModalityType.DOCUMENT_MODAL);
		documentModalDialog2.setBounds(332, 232, 300, 200);
		documentModalDialog2.addWindowListener(closeWindowListener);
		JTextArea ta6 = new JTextArea();
		final JLabel nameLabel = new JLabel();
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		Container cp6 = documentModalDialog2.getContentPane();
		cp6.setLayout(new BorderLayout());
		cp6.add(new JScrollPane(ta6), BorderLayout.CENTER);
		JPanel p6 = new JPanel();
		p6.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p6.add(nameLabel);
		cp6.add(p6, BorderLayout.SOUTH);

		//
		b5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//pass a name into the document modal dialog
				nameLabel.setText("by " + tf5.getText());
				documentModalDialog2.setVisible(true);
			}
		});
		return result;
	}

	private JFrame createExcludedFrame() {
		JFrame result = new JFrame("Classics (excluded frame)");
		result.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		result.addWindowListener(closeWindowListener);
		JLabel l7 = new JLabel("Famous writers: ");
		l7.setHorizontalAlignment(SwingConstants.CENTER);
		// create radio buttons
		final JRadioButton rb71 = new JRadioButton("Burns", true);
		final JRadioButton rb72 = new JRadioButton("Dickens", false);
		final JRadioButton rb73 = new JRadioButton("Twain", false);
		// place radio buttons into a single group
		ButtonGroup bg7 = new ButtonGroup();
		bg7.add(rb71);
		bg7.add(rb72);
		bg7.add(rb73);
		Container cp7 = result.getContentPane();
		// create three containers to improve layouting
		cp7.setLayout(new GridLayout(1, 3));
		Container cp71 = new Container();
		Container cp72 = new Container();
		Container cp73 = new Container();
		// add the label into a separate panel
		JPanel p7 = new JPanel();
		p7.setLayout(new FlowLayout());
		p7.add(l7);
		// add a label and radio buttons one after another into a single column
		cp72.setLayout(new GridLayout(4, 1));
		cp72.add(p7);
		cp72.add(rb71);
		cp72.add(rb72);
		cp72.add(rb73);
		// add three containers
		cp7.add(cp71);
		cp7.add(cp72);
		cp7.add(cp73);
		return result;
	}

	private JFrame createFrame4ApplicationModalDialog() {
		JFrame result = new JFrame("Feedback (parent frame)");
		result.addWindowListener(closeWindowListener);
		JButton b8 = new JButton("Rate yourself");
		b8.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "I really like my book",
						"Question (application-modal dialog)", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
			}
		});
		Container cp8 = result.getContentPane();
		cp8.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
		cp8.add(b8);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		ModalityDemo md = new ModalityDemo();
		md.createGUI();
		md.start();
	}

}
