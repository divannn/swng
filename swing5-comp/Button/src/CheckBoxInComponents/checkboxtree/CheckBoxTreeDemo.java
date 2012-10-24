package CheckBoxInComponents.checkboxtree;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/**
 * @author idanilov
 * @jdk 1.5
 */
public class CheckBoxTreeDemo extends JFrame {

	private DefaultMutableTreeNode root,child1,child2,subChild11,subChild12,subChild21,subChild22,subChild23,leaf1,leaf2,leaf3;
	private CheckBoxTree cbTree;
	
	public CheckBoxTreeDemo() {
		super(CheckBoxTreeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setContentPane(createContents());
		init();
	}
	
	private void init() {
		//check some items.
		TreePath subChild22Path = new TreePath(new Object [] {root,child2,subChild22});
		TreePath leaf3Path = new TreePath(new Object [] {root,leaf3});
		cbTree.setChecked(subChild22Path,true);
		cbTree.setChecked(leaf3Path,true);
		//expand some.
		cbTree.expandRow(3);
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		cbTree = new CheckBoxTree(new DefaultTreeModel(createNodes()));
		
		//for demo purpose - prints selecetd objects.
		cbTree.addCheckListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent tse) {
				Object [] selected = cbTree.getCheckedObjects();
				System.out.println(Arrays.asList(selected));
			}
			
		});
		result.add(new JScrollPane(cbTree),BorderLayout.CENTER);
		return result;
	}
    
	private TreeNode createNodes() {
		root = new DefaultMutableTreeNode("Root");
		leaf1 = new DefaultMutableTreeNode("leaf1",true);
	    root.add(leaf1);
		
		child1 = new DefaultMutableTreeNode("1");
		subChild11 = new DefaultMutableTreeNode("1.1");
		subChild12 = new DefaultMutableTreeNode("1.2");
		child1.add(subChild11);
		child1.add(subChild12);
		root.add(child1);
	    
	    child2 = new DefaultMutableTreeNode("2");
		subChild21 = new DefaultMutableTreeNode("2.1");
		subChild22 = new DefaultMutableTreeNode("2.2");
		subChild23 = new DefaultMutableTreeNode("2.3");
		child2.add(subChild21);
		child2.add(subChild22);
		child2.add(subChild23);
	    root.add(child2);
	    
		leaf2 = new DefaultMutableTreeNode("leaf2",false);
		leaf3 = new DefaultMutableTreeNode("leaf3",true);
	    root.add(leaf2);
	    root.add(leaf3);
	    
	    return root;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
		}       		
		JFrame frame = new CheckBoxTreeDemo();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
