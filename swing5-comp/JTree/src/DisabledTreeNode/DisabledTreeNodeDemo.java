package DisabledTreeNode;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class DisabledTreeNodeDemo extends JFrame {

	public DisabledTreeNodeDemo() {
		super(DisabledTreeNodeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String[] strs = { "swing", "plaf", "basic", "metal", "JComponents", "JTable", "JTree" };

		DefaultDisabledNode[] nodes = new DefaultDisabledNode[strs.length];
		for (int i = 0; i < strs.length; i++) {
			nodes[i] = new DefaultDisabledNode(strs[i]);
		}
		nodes[0].add(nodes[1]);
		nodes[1].add(nodes[2]);
		nodes[1].add(nodes[3]);
		nodes[0].add(nodes[4]);
		nodes[4].add(nodes[5]);
		nodes[4].add(nodes[6]);
		//disable some nodes.
		nodes[3].setEnabled(false);
		nodes[4].setEnabled(false);
		JTree tree = new JTree(nodes[0]) {

			public boolean isPathEditable(TreePath path) {
				DisabledNode node = (DisabledNode) path.getLastPathComponent();
				if (node.isEnabled()) {
					return isEditable();
				}
				return false;
			}

		};
		tree.setEditable(true);
		DisabledNodeRenderer renderer = new DisabledNodeRenderer();
		ImageIcon leafIcon = new ImageIcon(DisabledTreeNodeDemo.class.getResource("image.gif"));
		renderer.setLeafIcon(leafIcon);
		tree.setCellRenderer(renderer);
		//expand.
		tree.expandRow(1);
		tree.expandRow(4);
		//tree.setEnabled(false);	    //uncomment to see completely disabled tree.
		JScrollPane scrollPane = new JScrollPane(tree);
		result.add(scrollPane, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new DisabledTreeNodeDemo();
		frame.pack();
		frame.setSize(new Dimension(300, 400));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static class DisabledNodeRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
			JLabel result = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			if (value instanceof DisabledNode) {
				DisabledNode node = (DisabledNode) value;
				boolean treeIsEnabled = tree.isEnabled();
				boolean nodeIsEnabled = node.isEnabled();
				boolean isEnabled = treeIsEnabled && nodeIsEnabled;
				result.setEnabled(isEnabled);
				Icon lIcon = getLeafIcon();
				Icon oIcon = getOpenIcon();
				Icon cIcon = getClosedIcon();
				Icon lGrayedIcon = lIcon != null ? new GrayedIcon(lIcon) : null;
				Icon oGrayedIcon = oIcon != null ? new GrayedIcon(oIcon) : null;
				Icon cGrayedIcon = cIcon != null ? new GrayedIcon(cIcon) : null;
				if (isEnabled) {
					selected = sel;
					if (leaf) {
						result.setIcon(lIcon);
					} else if (expanded) {
						result.setIcon(oIcon);
					} else {
						result.setIcon(cIcon);
					}
				} else {
					selected = false;
					if (leaf) {
						result.setDisabledIcon(lGrayedIcon);
					} else if (expanded) {
						result.setDisabledIcon(oGrayedIcon);
					} else {
						result.setDisabledIcon(cGrayedIcon);
					}
				}
			}
			return result;
		}

	}

}
