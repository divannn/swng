package CheckBoxInComponents.optiontree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashSet;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import CheckBoxInComponents.ui.FlatCheckBox;
import CheckBoxInComponents.ui.FlatCheckBoxIcon;
import CheckBoxInComponents.ui.FlatRadioButton;


/**
 * JTree with ability to select item by checkbox or radio button.
 * Must be used for selection purposes only.
 * Not intended to be modified - items addition/removal is not suported.
 * <p>
 * Check and radio icon designed fo Windows L&F only.
 * @author idanilov
 */
public class OptionTree extends JTree 
		implements TreeSelectionListener, ActionListener {

    private TreeCellRenderer delegateCellRenderer;
    private TreeSelectionModel checkModel;
    private HashSet<TreePath> enablementModel;
    private static final Dimension CHECK_SPOT = new Dimension(FlatCheckBoxIcon.SIZE,FlatCheckBoxIcon.SIZE);
    
	public OptionTree() {
		this(getDefaultTreeModel());
	}
	
	public OptionTree(final TreeModel model) {
		super(model);
		checkModel = new DefaultTreeSelectionModel();
        checkModel.addTreeSelectionListener(this);
        enablementModel = new HashSet<TreePath>();
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
	
	/**
	 * @param p item path
	 * @return true if item in position p is enabled
	 */
	public boolean isEnabled(final TreePath p) {
		return !enablementModel.contains(p);
	}
    
	/**
	 * Enable/disable item in position p. 
	 * @param enable false to disable
	 */
	public void setEnabled(final TreePath p,final boolean enable) {
		if (enable) {
			enablementModel.remove(p);
		} else {
			enablementModel.add(p);
		}
	}
	
    public boolean isChecked(final TreePath p) {
		return checkModel.isPathSelected(p);
	}

    /**
     * @param p path to be checked
     * @param state true to check item
     */
    public void setChecked(final TreePath p, final boolean state) {
		Object o = p.getLastPathComponent();
		if (isRadio(o)) {
			if (state && !isChecked(p)) {
				mark(p,true);
				updateRadioSiblings(p);
			}
		} else {
    		mark(p,state);
		}
	}
	
    private boolean isRadio(final Object o) {
    	return (o instanceof IRadioNode);
    }
    
    private void mark(final TreePath p, final boolean state) {
		if (state) {
			checkModel.addSelectionPath(p);
		} else {
			checkModel.removeSelectionPath(p);
		}
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
		if (enablementModel != null) {
			enablementModel.clear();
		}
	} 
	
    private void toggleSelection(final TreePath path) {
    	if (isEnabled(path)) {
    		setChecked(path,!isChecked(path));
    	}
    } 

    private void updateRadioSiblings(final TreePath p) {
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)p.getLastPathComponent();
    	TreeNode parentNode = node.getParent();
    	TreePath parentPath = p.getParentPath();
		Enumeration childNodes = parentNode.children();
		while (childNodes.hasMoreElements()) {
			DefaultMutableTreeNode nextChildNode = (DefaultMutableTreeNode)childNodes.nextElement();
			if (isRadio(nextChildNode)) {
				if (node == nextChildNode) {
					continue;
				}
				TreePath nextChildPath = parentPath.pathByAddingChild(nextChildNode);
	            if (isChecked(nextChildPath)) {
					mark(nextChildPath,false);
				}
			}
		}
	}

    /**
     * Marker interface to distinguish radio nodes in tree.
     * @author idanilov
     *
     */
    public interface IRadioNode {
    	
    }
    
    /**
     * Compose plain button and label.
     * @author idanilov
     *
     */
    private class CheckTreeCellRenderer extends JPanel 
    		implements TreeCellRenderer { 
    	
        private JCheckBox checkBox;
        private JRadioButton radioButton;
        private AbstractButton buttonStamp;
     
        public CheckTreeCellRenderer() { 
            super(new BorderLayout(1,0)); 
            setOpaque(false); 
            checkBox = new FlatCheckBox();
            checkBox.setOpaque(false);
            radioButton = new FlatRadioButton();
            radioButton.setOpaque(false);
        } 
     
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			OptionTree ct = (OptionTree)tree;
			TreePath path = getPathForRow(row);
            boolean isCellEnabled = ct.isEnabled(path);
			if (getModel().isLeaf(value)) {
				if (isRadio(value)) {
					buttonStamp = radioButton;
				} else {
					buttonStamp = checkBox; 
				}
	            buttonStamp.setSelected(ct.isChecked(path));
	            buttonStamp.setEnabled(isCellEnabled);
			} else {
				buttonStamp = null;
			}
            Component delegateComp = delegateCellRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            //if uncomment - icon will disappear (default behaviour for JLabel).
            //delegateComp.setEnabled(isCellEnabled);
            removeAll(); 
            if (buttonStamp != null) {
                add(buttonStamp, BorderLayout.WEST); 
            }
            add(delegateComp, BorderLayout.CENTER); 
            return this; 
		} 
    }    
}
