package EditableList;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 * @author santosh
 * @jdk 1.5
 */
public class EdiableListDemo extends JFrame {

	public EdiableListDemo() {
		super(EdiableListDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultMutableListModel dmlm = new DefaultMutableListModel();
		for (int i = 1; i <= 30;i++) {
			dmlm.addElement("item"+i);
		}
		JList editableList = new JEditableList(dmlm);
		//editableList.setLayoutOrientation(JList.HORIZONTAL_WRAP);//works also fo horizontal mode.
		result.add(new JScrollPane(editableList),BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
		}  
		JFrame f = new EdiableListDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
