package PartiallyVisibleTreeNodes._2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * Show partially visible nodes via glass pane.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class PartiallyVisibleTreeNodesDemo2 extends JFrame {
	
	public PartiallyVisibleTreeNodesDemo2() {
		super(PartiallyVisibleTreeNodesDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
        JTree tree = new JTree();
        tree.expandRow(3);
        new TooltipGlassPane(tree);
        JSplitPane rightSplitPane = new JSplitPane();
        JScrollPane scrollPane = new JScrollPane(tree);
        rightSplitPane.setLeftComponent(scrollPane);
        rightSplitPane.setRightComponent(new JPanel());
        rightSplitPane.setDividerLocation(50);

        JSplitPane leftSplitPane = new JSplitPane();
        leftSplitPane.setLeftComponent(new JPanel());
        leftSplitPane.setRightComponent(rightSplitPane);
        leftSplitPane.setDividerLocation(50);
        result.add(leftSplitPane,BorderLayout.CENTER);
        
		//scroll horizontally just to hide left side of tree.
        scrollPane.getHorizontalScrollBar().getModel().setValue(25);
		return result;
	}
	
    public static void main(String[] args){
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
        JFrame f = new PartiallyVisibleTreeNodesDemo2(); 
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true); 
    }
    
	private static class TooltipGlassPane extends JComponent
			implements MouseInputListener, TreeSelectionListener {
		
		private JTree tree; 
	    private Component oldGlassPane; 
	    private TreePath path; 
	    private int row; 
	    private Rectangle bounds;
    	    
	    public TooltipGlassPane(final JTree tree) {
			this.tree = tree;
			this.tree.addMouseListener(this); 
			this.tree.addMouseMotionListener(this); 
		}
		
		public void mouseClicked(MouseEvent me) {
		}

		public void mousePressed(MouseEvent me) {
		}

		public void mouseReleased(MouseEvent me) {
		}

		public void mouseEntered(MouseEvent me) {
		}

		public void mouseDragged(MouseEvent me) {
		}

		public void mouseExited(MouseEvent me) { 
			uninstallGlass(); 
		} 
 
		public void mouseMoved(MouseEvent me){ 
		    path = tree.getPathForLocation(me.getX(), me.getY()); 
		    if (path == null) { 
		        uninstallGlass(); 
		    } else {
			    row = tree.getRowForPath(path); 
			    bounds = tree.getPathBounds(path); 
			    if (!tree.getVisibleRect().contains(bounds)) {//cell doesn't fit into the tree. 
			        if (oldGlassPane == null) { 
						installGlass();
			        } else { 
			            tree.getRootPane().repaint();
			        }
			    } else { 
			        uninstallGlass(); 
			    } 
		    }
		}

		public void valueChanged(TreeSelectionEvent tse) { 
			tree.getRootPane().repaint(); //repaints glass pane on selection.
		} 
		
		//paint renderer on glass pane.
		public void paint(Graphics g) { 
			boolean selected = tree.isRowSelected(row); 
			Component renderer = tree.getCellRenderer().getTreeCellRendererComponent(tree, path.getLastPathComponent(), 
			        tree.isRowSelected(row), tree.isExpanded(row), tree.getModel().isLeaf(path.getLastPathComponent()), row, 
			        selected); 
			this.setFont(tree.getFont()); 
			Rectangle paintBounds = SwingUtilities.convertRectangle(tree, bounds, this); 
			SwingUtilities.paintComponent(g, renderer, this, paintBounds); 
			if (selected) { 
			    return; 
			}
			g.setColor(Color.BLACK);
			//paint border.
			((Graphics2D)g).draw(paintBounds); 
		}
		
		private void installGlass() {
            oldGlassPane = tree.getRootPane().getGlassPane(); 
            tree.getRootPane().setGlassPane(this); 
            this.setVisible(true); 
			//used to avoid artefact during selection partially invisible node which is being painted on glass pane.
            tree.addTreeSelectionListener(this);			
		}
		
		private void uninstallGlass() { 
			if (oldGlassPane != null) { 
				this.setVisible(false); 
				tree.getRootPane().setGlassPane(oldGlassPane); 
				oldGlassPane = null; 
				tree.removeTreeSelectionListener(this); 
			} 
		} 		
    }
	
}
