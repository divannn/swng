package ButtonsPanelLayout;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Useful layout for placing buttons in dialogs.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class ButtonsPanelLayoutDemo extends JFrame {
	
    public ButtonsPanelLayoutDemo() {
        super(ButtonsPanelLayoutDemo.class.getSimpleName());
        setDefaultCloseOperation(EXIT_ON_CLOSE);        
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JComponent result = new JPanel(new BorderLayout(0,5));
        //intersting way to add component. Usually arguments are reversed.
        result.add(BorderLayout.CENTER, createClientArea());
        result.add(BorderLayout.EAST, createRightPanel());
        result.add(BorderLayout.SOUTH, createBottomPanel());
        return result;
    }

    private JComponent createClientArea() {
    	JScrollPane result = new JScrollPane(new JTree());
        return result;
    }
	
    private JComponent createBottomPanel() {
        JPanel result = new JPanel(new ButtonsLayout(SwingConstants.RIGHT,5)); 
        result.add(new JButton("OK")); 
        result.add(new JButton("Cancel")); 
        result.add(new JButton("Apply")); 
        result.add(new JButton("Help"));
        return result;
	}

    private JComponent createRightPanel() {
        JPanel result = new JPanel(new ButtonsLayout(SwingConstants.VERTICAL,SwingConstants.TOP,5)); 
        result.add(new JButton("Select All")); 
        result.add(new JButton("Clear All")); 
        result.add(new JButton("Move Up")); 
        result.add(new JButton("Move Down"));
        return result;
	}
    
    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		ButtonsPanelLayoutDemo f = new ButtonsPanelLayoutDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
    }
}
