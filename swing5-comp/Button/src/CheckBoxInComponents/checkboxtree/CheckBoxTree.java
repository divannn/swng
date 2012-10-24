package CheckBoxInComponents.checkboxtree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import CheckBoxInComponents.ui.FlatCheckBox;
import CheckBoxInComponents.ui.FlatCheckBoxIcon;


/**
 * JTree with ability to select item by checkbox.
 * Must be used for selection purposes only.
 * Not intended to be modified - items addition/removal is not suported.
 * <p>
 * Check and radio icon designed fo Windows L&F only.
 * @author idanilov
 */
public class CheckBoxTree extends JTree 
		implements TreeSelectionListener, ActionListener {

    private TreeCellRenderer delegateCellRenderer;
    private TreeSelectionModel checkModel;
    private TreeSelectionModel grayModel;
    private static final Dimension CHECK_SPOT = new Dimension(FlatCheckBoxIcon.SIZE,FlatCheckBoxIcon.SIZE);
    
	public CheckBoxTree() {
		this(getDefaultTreeModel());
	}
	
	public CheckBoxTree(final TreeModel model) {
		super(model);
		checkModel = new DefaultTreeSelectionModel();
        checkModel.addTreeSelectionListener(this);
        grayModel = new DefaultTreeSelectionModel();
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setShowsRootHandles(true);
        setDelegateRenderer(new DefaultTreeCellRenderer());
        super.setCellRenderer(new CheckTreeCellRenderer());
        registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_FOCUSED); 
        addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent me) {	    	
    	        TreePath path = getPathForLocation(me.getX(), me.getY()); 
    	        if (path != null) { 
        	        if (me.getX() > getPathBounds(path).x + CHECK_SPOT.width) { 
        	            return;
        	        }
        	        if (me.getY() > getPathBounds(path).y + CHECK_SPOT.height) { 
        	            return;
        	        }
        	        toggleSelection(path); 
    	        }
    	    } 
        });        
	}
	
    /** 
     * Use <code>setDelegateRenderer()</code> instead.
     */
	public final void setCellRenderer(final TreeCellRenderer renderer) {
	}
	
    /**
     * Should be used instead of setCellRenderer().
     * @param render
     */
    public void setDelegateRenderer(final TreeCellRenderer renderer) {
    	delegateCellRenderer = renderer;
	}
    
    public TreeCellRenderer getDelegateCellRenderer() {
		return delegateCellRenderer;
	}
	
	public void valueChanged(final TreeSelectionEvent tse) {
		repaint();
	}

	public void actionPerformed(final ActionEvent ae) {
		toggleSelection(getSelectionPath());	
	}	    

    public void addCheckListener(final TreeSelectionListener tsl) {
    	checkModel.addTreeSelectionListener(tsl);
    }

    public void removeCheckListener(final TreeSelectionListener tsl) {
    	checkModel.removeTreeSelectionListener(tsl);
    }
	
	public Object [] getCheckedObjects() {
		Object [] result = null;
        TreePath [] selected = checkModel.getSelectionPaths();
        if (selected != null) {
            result = new Object[selected.length];
            for (int i = 0; i < selected.length; i++) {
            	result[i] = selected[i].getLastPathComponent();
            }
        } else {
        	result = new Object [0];
        }
        return result;
	}
	
	public void setModel(final TreeModel model) {
		super.setModel(model);
		if (checkModel != null) {
			checkModel.clearSelection();
		}
		if (grayModel != null) {
			grayModel.clearSelection();
		}
	} 
	
    private void toggleSelection(final TreePath path) {
        setGrayed(path,false);
        setChecked(path,!isChecked(path));
    } 

    public boolean isChecked(final TreePath p) {
		return checkModel.isPathSelected(p);
	}

    /**
     * @param p path to be checked
     * @param state true to check item
     * All children and parent items will affected also.
     */
    public void setChecked(final TreePath p, final boolean state) {
    	mark(p,state);
		update(p);
	}

    private void mark(final TreePath p, final boolean state) {
		if (state) {
			checkModel.addSelectionPath(p);
		} else {
			checkModel.removeSelectionPath(p);
		}
	}
    
    private boolean isGrayed(final TreePath p) {
		return grayModel.isPathSelected(p);
	}
    
    /**
     * @param p path to be grayed
     * @param state true to gray item
     */
    private void setGrayed(final TreePath p, final boolean state) {
		if (state) {
			grayModel.addSelectionPath(p);
		} else {
			grayModel.removeSelectionPath(p);
		}
	}
    
    private void update(final TreePath path) {
        updateChildrenItems(path);
        updateParentItems(path.getParentPath());
	}
    
    /**
     * Set state for child subtree.
     * @param p
     */
    private void updateChildrenItems(final TreePath p) {
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)p.getLastPathComponent();
		boolean isChecked = isChecked(p);
		Enumeration childNodes = node.children();
		while (childNodes.hasMoreElements()) {
			DefaultMutableTreeNode nextChildNode = (DefaultMutableTreeNode)childNodes.nextElement();
			TreePath nextChildPath = p.pathByAddingChild(nextChildNode);
            if ((isChecked(nextChildPath) != isChecked) || isGrayed(nextChildPath)) {
				mark(nextChildPath,isChecked);
				setGrayed(nextChildPath,false);
				updateChildrenItems(nextChildPath);
			}
		}
	}

    /**
     * Updates the check / gray state of all parent items
     */
    private void updateParentItems(final TreePath p) {
    	if (p != null) { //root.
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)p.getLastPathComponent();
    		Enumeration childNodes = node.children();
            boolean containsChecked = false;
            boolean containsUnchecked = false;
    		while (childNodes.hasMoreElements()) {
    			DefaultMutableTreeNode nextChildNode = (DefaultMutableTreeNode)childNodes.nextElement();
    			TreePath nextChildPath = p.pathByAddingChild(nextChildNode);
                containsChecked |= isChecked(nextChildPath);
                containsUnchecked |= !isChecked(nextChildPath) || isGrayed(nextChildPath);
    		}
    		mark(p,containsChecked);
    		setGrayed(p,containsChecked && containsUnchecked);
            updateParentItems(p.getParentPath());
    	}
    }
    
    /**
     * Compose plain button and label.
     * @author idanilov
     *
     */
    private class CheckTreeCellRenderer extends JPanel 
    		implements TreeCellRenderer { 
    	
        private JCheckBox checkBox;
     
        public CheckTreeCellRenderer() { 
            super(new BorderLayout(1,0)); 
            setOpaque(false); 
            checkBox = new FlatCheckBox();
            checkBox.setOpaque(false);
        } 
     
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component delegateComp = delegateCellRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            TreePath p = getPathForRow(row);
            CheckBoxTree cbt = (CheckBoxTree)tree;
            checkBox.setSelected(cbt.isChecked(p));
            checkBox.setEnabled(!cbt.isGrayed(p));
            removeAll(); 
            add(checkBox, BorderLayout.WEST); 
            add(delegateComp, BorderLayout.CENTER); 
            return this; 
		} 
    }    
}