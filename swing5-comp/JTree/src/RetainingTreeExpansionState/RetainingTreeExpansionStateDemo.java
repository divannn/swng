package RetainingTreeExpansionState;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;


/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class RetainingTreeExpansionStateDemo extends JFrame {

	private String state;
	private JTree tree;
	
	public RetainingTreeExpansionStateDemo() {
		super(RetainingTreeExpansionStateDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		tree = new JTree();
		result.add(createButtonsPanel(),BorderLayout.NORTH);
		result.add(new JScrollPane(tree),BorderLayout.CENTER);
		return result;
	}
	
	private JComponent createButtonsPanel() {
		JPanel result = new JPanel(new GridLayout(1,2));
		JButton saveStateButton = new JButton("Save State");
		saveStateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				state = TreeUtil.getExpansionState(tree,0);
			}
			
		});
		JButton restoreStateButton = new JButton("Restore State");
		restoreStateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (state != null) {
					TreeUtil.restoreExpanstionState(tree,0,state);
				}
			}
			
		});
		
		result.add(saveStateButton);
		result.add(restoreStateButton);
		return result;
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new RetainingTreeExpansionStateDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
