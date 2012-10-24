package WideComboBox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Fit items in combo popup when they too long demo.
 * I've got this workaround from the following bug: 
 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4618607 
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class WideComboBoxDemo extends JFrame {

	private String [] items = {
			"Very long line item",
			"Very very long line item",
			"Very very very long line item"
	};
	
	public WideComboBoxDemo() {
		super(WideComboBoxDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result,BoxLayout.Y_AXIS));
		JComboBox defaultCombo = new JComboBox(items);
		defaultCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
		defaultCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE,defaultCombo.getPreferredSize().height));
		result.add(new JLabel("Default combobox:"));
		result.add(defaultCombo,BorderLayout.NORTH);
		result.add(new JLabel("Wide combobox:"));
		JComboBox wideCombo = new WideComboBox(items);
		wideCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
		wideCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE,wideCombo.getPreferredSize().height));
		result.add(wideCombo,BorderLayout.NORTH);
		result.add(Box.createVerticalGlue());
		return result;
	}

	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 		
		JFrame f = new WideComboBoxDemo();
		f.setSize(new Dimension(100,100));
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}