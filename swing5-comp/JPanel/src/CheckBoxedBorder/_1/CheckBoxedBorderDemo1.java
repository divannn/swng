package CheckBoxedBorder._1;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Using rubber stamp. 
 * Note that impossible to use keyboard (i.e.TAB) to select title checbox.
 * @author santosh 
 * @author idanilov
 * @jdk 1.5
 */
public class CheckBoxedBorderDemo1 extends JFrame {

	public CheckBoxedBorderDemo1() {
		super(CheckBoxedBorderDemo1.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		final JPanel proxyPanel = new JPanel(); 
        proxyPanel.add(new JLabel("Proxy Host: ")); 
        proxyPanel.add(new JTextField("proxy.xyz.com")); 
        proxyPanel.add(new JLabel("  Proxy Port")); 
        proxyPanel.add(new JTextField("8080")); 
        final JCheckBox checkBox = new JCheckBox("Use Proxy", true); 
        checkBox.setFocusPainted(false); 
        TitledBorder componentBorder = 
                new TitledBorder(checkBox, proxyPanel, BorderFactory.createEtchedBorder()); 
        checkBox.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){ 
                boolean enable = checkBox.isSelected(); 
                Component comp[] = proxyPanel.getComponents(); 
                for(int i = 0; i<comp.length; i++){ 
                    comp[i].setEnabled(enable); 
                } 
            } 
        }); 
        proxyPanel.setBorder(componentBorder);
        result.add(proxyPanel);
		return result;	
	}
	
	public static void main(String[] args) {
		try{ 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
        JFrame f = new CheckBoxedBorderDemo1(); 
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);         
	}
	
}
