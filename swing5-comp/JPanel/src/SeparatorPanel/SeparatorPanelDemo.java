package SeparatorPanel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class SeparatorPanelDemo extends JFrame {

	public SeparatorPanelDemo() {
		super(SeparatorPanelDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result,BoxLayout.Y_AXIS));
		JCheckBox comp1 = new JCheckBox("Check box");
		SeparatorPanel title1 = new SeparatorPanel(comp1);
		title1.setAlignmentX(Component.LEFT_ALIGNMENT);
		title1.setMaximumSize(new Dimension(Integer.MAX_VALUE,title1.getPreferredSize().height));
		result.add(title1);
		
		JLabel l = new JLabel("Text field:");
		JTextField tf = new JTextField("text");
		tf.setColumns(20);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(l);
		panel.add(new JLabel("   "));
		panel.add(tf);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,panel.getPreferredSize().height));
		result.add(panel);
		
		JLabel comp2 = new JLabel("Option");
		SeparatorPanel title2 = new SeparatorPanel(comp2);
		title2.setMaximumSize(new Dimension(Integer.MAX_VALUE,title2.getPreferredSize().height));
		title2.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		result.add(title2);
		JCheckBox cb = new JCheckBox("Use encription");
		cb.setAlignmentX(Component.LEFT_ALIGNMENT);
		result.add(cb);
		result.add(Box.createVerticalGlue());
		return result;
	}

	public static void main(String[] args) {
		/*try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        }		*/
		JFrame f = new SeparatorPanelDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
