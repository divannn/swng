package ContextMenuForTextComponent;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 * Install popup menu for every text component in the application via EventQueue.
 * @author santosh
 * @jdk 1.5
 */
public class ContextMenuForTextComponentDemo extends JFrame {

	public ContextMenuForTextComponentDemo() {
		super(ContextMenuForTextComponentDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(2,2));
		JLabel textLabel = new JLabel("Text field:");
		result.add(textLabel);
		JTextField textField = new JFormattedTextField();
		textField.setColumns(10);
		result.add(textField);
		JLabel textLabel2 = new JLabel("Text field with custom popup menu:");
		result.add(textLabel2);
		JTextField textFieldWithCustomPupup = new JFormattedTextField();
		textFieldWithCustomPupup.setColumns(10);
		textFieldWithCustomPupup.setComponentPopupMenu(createPopup());
		result.add(textFieldWithCustomPupup);
		return result;
	}
	
	private JPopupMenu createPopup() {
		JPopupMenu result = new JPopupMenu();
		result.add(new JMenuItem("Menu 1"));
		result.addSeparator();
		result.add(new JMenuItem("Menu 2"));
		return result;
	}
	
	public static void main(String[] args) {
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(new EventQueueWithPopupMenu()); 
		JFrame f = new ContextMenuForTextComponentDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
