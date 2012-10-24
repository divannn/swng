package CheckBoxInComponents.checkboxlist;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import CheckBoxInComponents.ui.FlatCheckBox;
import CheckBoxInComponents.ui.FlatCheckBoxIcon;
import CheckBoxInComponents.util.CheckBoxUtil;


/**
 * JList with ability to select item by checkbox.
 * <p>
 * Check icon designed fo Windows L&F only.
 * @author idanilov
 */
public class CheckBoxList extends JList 
		implements ListSelectionListener, ActionListener {

    private ListSelectionModel checkModel;
	private ListSelectionModel enablementModel;
    private ListCellRenderer delegateCellRenderer;
    private ListDataListener stateUpdater;
    private static final Dimension CHECK_SPOT = new Dimension(FlatCheckBoxIcon.SIZE,FlatCheckBoxIcon.SIZE); 
    
    public CheckBoxList() {
    	this(new DefaultListModel());
    }
    
    public CheckBoxList(final ListModel model) {
    	super(model);
		checkModel = new DefaultListSelectionModel();
        checkModel.addListSelectionListener(this);
		enablementModel = new DefaultListSelectionModel();
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//only single selection allowed.
        setDelegateRenderer(new DefaultListCellRenderer());
        super.setCellRenderer(new CheckListCellRenderer());
        registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_FOCUSED); 
        addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent me) {	    	
    	        int index = locationToIndex(me.getPoint()); 
    	        if (index < 0) {
    	            return; 
    	        }
    	        Rectangle clickArea = getCellBounds(index, index); 
    	        if (me.getX() > clickArea.x + CHECK_SPOT.width) { 
    	            return; 
    	        }
    	        if (me.getY() > clickArea.y + CHECK_SPOT.height) { 
    	            return; 
    	        }
    	        toggleSelection(index); 
    	    } 
        });
        stateUpdater = new StateUpdater();
        model.addListDataListener(stateUpdater);
    } 

    /** 
     * Use <code>setDelegateRenderer()</code> instead.
     */
	public final void setCellRenderer(final ListCellRenderer cellRenderer) {
    	
	}
    
    /**
     * Should be used instead of setCellRenderer().
     * @param render
     */
    public void setDelegateRenderer(final ListCellRenderer render) {
    	delegateCellRenderer = render;
	}
    
    public ListCellRenderer getDelegateCellRenderer() {
		return delegateCellRenderer;
	}
    
	public Object [] getCheckedObjects() {
        ListModel dm = getModel();
        int iMin = checkModel.getMinSelectionIndex();
        int iMax = checkModel.getMaxSelectionIndex();
        if ((iMin < 0) || (iMax < 0)) {
            return new Object[0];
        }
        Object[] rvTmp = new Object[1+ (iMax - iMin)];
        int n = 0;
        for (int i = iMin; i <= iMax; i++) {
            if (checkModel.isSelectedIndex(i)) {
                rvTmp[n++] = dm.getElementAt(i);
            }
        }
        Object[] result = new Object[n];
        System.arraycopy(rvTmp, 0, result, 0, n);
        return result;
	}
	
	/**
	 * @return Model indices of items that are currently checked.
	 */
	public int [] getCheckedIndices() {
		return CheckBoxUtil.getSelectedIndices(checkModel);
	} 

	/**
	 * @return Model indices of items that are currently disabled.
	 */
	public int [] getDisabledIndices() {
		return CheckBoxUtil.getSelectedIndices(enablementModel);
	} 
	
	/**
	 * @param i item index
	 * @return true if item in position i is enabled
	 */
	public boolean isEnabled(final int i) {
		return !enablementModel.isSelectedIndex(i);
	}

	/**
	 * Enable/disable list item in position i. 
	 * @param i item index
	 * @param enable false to disable
	 */
	public void setEnabled(final int i,final boolean enable) {
		if (enable) {
			enablementModel.removeSelectionInterval(i, i);
		} else {
			enablementModel.addSelectionInterval(i, i);
		}		
	}
	
    public boolean isChecked(final int i) {
		return checkModel.isSelectedIndex(i);
	}
    
	/**
	 * Check/uncheck list item in position i. 
	 * @param i item index
	 * @param state false to uncheck
	 */    
    public void setChecked(final int i, final boolean state) {
		if (state) {
			checkModel.addSelectionInterval(i, i);
		} else {
			checkModel.removeSelectionInterval(i, i);
		}
	}
    
	/**
	 * Check items in passed indices.
	 * @param indices
	 */
	public void checkIndices(int [] indices) {
		int modelSize = getModel().getSize();
        for(int i = 0; i < indices.length; i++) {
        	int nextIndex = indices[i]; 
	    	if (nextIndex < modelSize && nextIndex >= 0) {
	    		checkModel.addSelectionInterval(nextIndex, nextIndex);
	    	}
        }
	}

	/**
	 * Uncheck items in passed indices.
	 * @param indices
	 */
	public void clearIndices(int [] indices) {
		int modelSize = getModel().getSize();
        for(int i = 0; i < indices.length; i++) {
        	int nextIndex = indices[i]; 
	    	if (nextIndex < modelSize && nextIndex >= 0) {
	    		checkModel.removeSelectionInterval(nextIndex, nextIndex);
	    	}
        }
	}
	
	public void setModel(final ListModel model) {
		ListModel oldModel = getModel();
		if (oldModel != null) {
			oldModel.removeListDataListener(stateUpdater);
		}
		checkModel.clearSelection();
		enablementModel.clearSelection();
		super.setModel(model);
        model.addListDataListener(stateUpdater);		
	} 
	
	/**
	 * Swaps two items in list and their selcted/enables states.
	 * @param fromInd
	 * @param toInd
	 */
	public void swap(final int fromInd, final int toInd) {
		boolean isSelectedObjectChecked = isChecked(fromInd);
		boolean isOppositeObjectChecked = isChecked(toInd);
		boolean isSelectedObjectEnabled = isEnabled(fromInd);
		boolean isOppositeObjectEnabled = isEnabled(toInd);
		ListSelectionModel lsm = getSelectionModel();
		DefaultListModel dlm = (DefaultListModel)getModel();
		Object selectedObj = dlm.get(fromInd);
		Object oppositeObj = dlm.get(toInd);
		dlm.set(toInd,selectedObj);
		dlm.set(fromInd,oppositeObj);
		//restore check state.
		setChecked(toInd,isSelectedObjectChecked);
		setChecked(fromInd,isOppositeObjectChecked);
		//restore enabled state.
		setEnabled(toInd,isSelectedObjectEnabled);
		setEnabled(fromInd,isOppositeObjectEnabled);
		//restore selection to swapped object.
		lsm.setSelectionInterval(toInd,toInd);
	}
	
    private void toggleSelection(final int index) {
    	if (isEnabled(index)) {
    		setChecked(index,!isChecked(index));
    	}
    } 
	 
    /* 
     * Just update cell.
     */
    public void valueChanged(final ListSelectionEvent lse) {
    	Rectangle rect = getCellBounds(lse.getFirstIndex(), lse.getLastIndex());
    	if (rect != null) {
            repaint(rect); 
    	} else {
    		repaint();
    	}
    } 
	 
    public void actionPerformed(final ActionEvent ae) {
        toggleSelection(getSelectedIndex()); 
    } 
	    
    public void addCheckListener(final ListSelectionListener lse) {
    	checkModel.addListSelectionListener(lse);
    }

    public void removeCheckListener(final ListSelectionListener lse) {
    	checkModel.removeListSelectionListener(lse);
    }
    
    /**
     * Compose plain checkbox and label.
     * @author idanilov
     *
     */
    private class CheckListCellRenderer extends JPanel 
    		implements ListCellRenderer { 
    	
        private JCheckBox checkBox; 
     
        public CheckListCellRenderer() { 
            super(new BorderLayout(1,0)); 
            setOpaque(false); 
            checkBox = new FlatCheckBox();
            checkBox.setOpaque(false);
        } 
     
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            Component delegateComp = delegateCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            CheckBoxList cbl = (CheckBoxList)list;
            checkBox.setSelected(cbl.isChecked(index));
            boolean isCellEnabled = cbl.isEnabled(index);
            checkBox.setEnabled(isCellEnabled);
            delegateComp.setEnabled(isCellEnabled);
            removeAll(); 
            add(checkBox, BorderLayout.WEST); 
            add(delegateComp, BorderLayout.CENTER); 
            return this; 
        } 
    }
   
    /**
     * Clears check state and enablement state for added/removed objects in list model.
     */
    private class StateUpdater 
    		implements ListDataListener {

		public void intervalAdded(ListDataEvent lde) {
			//System.err.println("add " + lde.getIndex0() + " " + lde.getIndex1());
			int start = lde.getIndex0();
			int end = lde.getIndex1();
			clearState(start,end);
			int length = end - start + 1;
			//shift down checked state.
			int [] checked = getCheckedIndices();
			for (int nextChecked : checked) {
				if (nextChecked >= start) {
					checkModel.addSelectionInterval(nextChecked + length,nextChecked + length);
				}
			}
			//shift down enabled state.
			int [] disabled = getDisabledIndices();
			for (int nextDisabled : disabled) {
				if (nextDisabled >= start) {
					enablementModel.addSelectionInterval(nextDisabled + length,nextDisabled + length);
				}
			}
		}

		public void intervalRemoved(ListDataEvent lde) {
			clearState(lde.getIndex0(),lde.getIndex1());
		}

		public void contentsChanged(ListDataEvent lde) {
		}

		private void clearState(final int first, final int end) {
			//System.err.println("+++ update state : beging = " + first + " end = " + end);
			for (int i = first; i <= end; i++) {
				checkModel.removeSelectionInterval(i,i);
				enablementModel.removeSelectionInterval(i,i);
			}
		} 

    }    
} 	
