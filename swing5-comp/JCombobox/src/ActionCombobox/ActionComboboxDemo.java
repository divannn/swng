package ActionCombobox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Do not fire selection and action events while traversion items in combobox 
 * by up/down arrows in open combo popup. This can be useful in table editors.
 * @author santosh
 * @jdk 1.5
 */
public class ActionComboboxDemo extends JFrame {
	
	public ActionComboboxDemo() {
		super(ActionComboboxDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		JLabel normalComboLabel = new JLabel("Normal combobox:");
		result.add(normalComboLabel);
		JComboBox normalComboBox = new JComboBox(new String [] {"111","222","333"});
		result.add(normalComboBox);
		normalComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JComboBox combo = (JComboBox)e.getSource();
				System.err.println("normal action: " + combo.getSelectedItem());
			}
			
		});
		normalComboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					JComboBox combo = (JComboBox)e.getSource(); 
					System.err.println("normal item : " + combo.getSelectedItem());
				}
			}
			
		});
		

		JLabel adjustedComboLabel = new JLabel("Adjusted combobox:");
		result.add(adjustedComboLabel);
		final JComboBox adjustedComboBox = new JComboBox(new String [] {"Item 1","Item 2","Item 3","Item 4"});
		adjustedComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JComboBox combo = (JComboBox)e.getSource();
				System.err.println("adjusted action: " + combo.getSelectedItem());
				//make sure popup is closed when 'isTableCellEditor' is used.
				combo.hidePopup();
			}
			
		});
		adjustedComboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					JComboBox combo = (JComboBox)e.getSource(); 
					System.err.println("adjusted item : " + combo.getSelectedItem());
				}
			}
			
		});
		//this prevents action events from being fired when the
		//up/down arrow keys are used on the dropdown menu.
		adjustedComboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		result.add(adjustedComboBox);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ActionComboboxDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
