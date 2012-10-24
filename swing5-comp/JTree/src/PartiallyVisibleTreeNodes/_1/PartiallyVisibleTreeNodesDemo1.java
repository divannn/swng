package PartiallyVisibleTreeNodes._1;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreePath;

/**
 * Show partially visible nodes via tooltips.
 * @author idanilov
 * @jdk 1.5
 */
public class PartiallyVisibleTreeNodesDemo1 extends JFrame {

	public PartiallyVisibleTreeNodesDemo1() {
		super(PartiallyVisibleTreeNodesDemo1.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTree tree = new JTree() {
			public String getToolTipText(MouseEvent me) {
				String r = null;
				Point p = me.getPoint();
				int row = getRowForLocation(p.x,p.y);
				if (row != -1) {
					Rectangle rowRect = getRowBounds(row);
					Rectangle visibleRect = getVisibleRect();
					if (!visibleRect.contains(rowRect)) {
						TreePath path = getPathForRow(row);
						r = path.getLastPathComponent().toString();
					}
				}
				return r;
			}
			
			public Point getToolTipLocation(MouseEvent me) {
				Point p = me.getPoint();
				int row = getRowForLocation(p.x,p.y);
				if (row != -1 && getToolTipText(me) != null) {
					Rectangle rowRect = getRowBounds(row);
					return new Point(rowRect.x,rowRect.y);
				}
				return super.getToolTipLocation(me);
			}
		};
		tree.setToolTipText("");//just register to TooltipManager.
		tree.expandRow(3);
		JScrollPane scrollPane = new JScrollPane(tree);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(scrollPane);
		splitPane.setRightComponent(new JPanel());
		splitPane.setDividerLocation(75);
		result.add(splitPane,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new PartiallyVisibleTreeNodesDemo1();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
