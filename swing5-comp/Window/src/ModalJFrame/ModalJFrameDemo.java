package ModalJFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * Way to use of JFrame as modal JDialog.
 * 
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class ModalJFrameDemo extends JFrame {

	public ModalJFrameDemo() {
		super(ModalJFrameDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JButton b = new JButton("Show modal frame");
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				JFrame modalFrame = new JFrame("Modal JFrame");
				modalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
				modalFrame.setContentPane(createModalFrameContents(modalFrame));
				modalFrame.setLocationRelativeTo(ModalJFrameDemo.this);
				modalFrame.pack();
				ModalFrameUtil.showAsModal(modalFrame,ModalJFrameDemo.this);
			}
			
		});
		result.add(b,BorderLayout.NORTH);
		result.add(new JTree(),BorderLayout.CENTER);
		return result;
	}
	
	private JComponent createModalFrameContents(final JFrame frame) {
		JOptionPane result = new JOptionPane("JFrame like a modal JDialog!",JOptionPane.INFORMATION_MESSAGE);
		result.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent pce) {
				if (pce.getPropertyName().equals(JOptionPane.VALUE_PROPERTY)) {
					//close modal frame.
					frame.setVisible(false);
					frame.dispose();
				}
			}			
		});
		return result;
	}
	
	public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 
		JFrame f = new ModalJFrameDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
