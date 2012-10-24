package ComboToolTip;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class ComboToolTipDemo extends JFrame {

	private String [] items = {"1","2","3"};
	private String [] tooltips = {"First","Second","Third"};
	
	public ComboToolTipDemo() {
		super(ComboToolTipDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JComboBox combo = new JComboBox(items);
		combo.setRenderer(new ComboRenderer());
		result.add(combo,BorderLayout.NORTH);
		return result;
	}

	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 		
		JFrame f = new ComboToolTipDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private class ComboRenderer extends BasicComboBoxRenderer {
		
	    public Component getListCellRendererComponent(JList list, 
	           Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    	JLabel comp = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
			if (isSelected) {
				if (index > -1) {
					comp.setToolTipText(tooltips[index]);
				}
			}
			return comp;
	    } 
	    
	}	
}