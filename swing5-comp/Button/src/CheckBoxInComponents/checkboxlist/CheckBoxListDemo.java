package CheckBoxInComponents.checkboxlist;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class CheckBoxListDemo extends JFrame {

	public CheckBoxListDemo() {
		super(CheckBoxListDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String [] data = {"disabled","111","222","333"};
		DefaultComboBoxModel dcm = new DefaultComboBoxModel(data);		
		final CheckBoxList cbList = new CheckBoxList(dcm);
		cbList.setDelegateRenderer(new CustomRenderer());
		//check some items.
		cbList.setChecked(0,true);
		cbList.setChecked(3,true);
		//disable some items.
		cbList.setEnabled(0,false);
		//for demo purpose - prints selected objects.
		cbList.addCheckListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent lse) {
				Object [] selected = cbList.getCheckedObjects();
				System.out.println("Selected objects:" + Arrays.asList(selected));
			}
			
		});
		result.add(new JScrollPane(cbList),BorderLayout.CENTER);
		return result;
	}
    
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
		}       		
		JFrame frame = new CheckBoxListDemo();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static class CustomRenderer extends DefaultListCellRenderer {
		
		private static Icon ICON = new ImageIcon(CustomRenderer.class.getResource("dots2.png")); 
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel result = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			result.setIcon(ICON);
			return result; 
		}
		
	}
	
}
