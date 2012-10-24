package CheckBoxedBorder._2;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Possible to access to checkbox by keyboard. 
 * @author idanilov
 * @jdk 1.5
 */
public class CheckBoxedBorderDemo2 extends JFrame {
	
	public CheckBoxedBorderDemo2() {
		super(CheckBoxedBorderDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
   
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JPanel checkPanel = new JPanel();
		checkPanel.add(new JLabel("A label 1"));
		checkPanel.add(new JTextField("A text field 1"));
		result.add(new TitledPanel("Check to enable",checkPanel), BorderLayout.NORTH);
		return result;
	}
	
	public static void main(String[] args) {
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch(Exception e){ 
			e.printStackTrace();
		}	   
		JFrame f = new CheckBoxedBorderDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
   
}
