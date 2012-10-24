package HighlightTreeNodeDescendants;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class HighlightTreeNodeDescendantsDemo extends JFrame {

	public HighlightTreeNodeDescendantsDemo() {
		super(HighlightTreeNodeDescendantsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		HighlighterTree tree = new HighlighterTree();
		TreeCellRenderer higlightEditor = new HighlighterTree.MyTreeCellRenderer();
		tree.setCellRenderer(higlightEditor);
		JScrollPane scrollPane = new JScrollPane(tree);
		result.add(scrollPane, BorderLayout.CENTER);
		//highlight some.
		tree.setHighlightPath(tree.getPathForRow(2));
		// expand some.
		tree.expandRow(2);
		tree.expandRow(1);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new HighlightTreeNodeDescendantsDemo();
		frame.pack();
		frame.setSize(new Dimension(300, 400));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
