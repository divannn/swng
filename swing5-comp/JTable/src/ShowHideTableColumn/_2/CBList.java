package ShowHideTableColumn._2;

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


/**
 * This is an almost copy of CheckBoxList (only plain check box used as renderer).
 * Taken just for convenience.  
 * @author idanilov
 */
public class CBList extends JList 
		implements ListSelectionListener, ActionListener {

    private ListSelectionModel checkModel;
	private ListSelectionModel enablementModel;
    private ListCellRenderer delegateCellRenderer;
    private ListDataListener stateUpdater;
    
    private static final Dimension CHECK_SPOT = new JCheckBox().getPreferredSize(); 
    
    public CBList() {
    	this(new DefaultListModel());
    }
    
    public CBList(final ListModel model) {
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

    /* 
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
        int iMin = checkModel.getMinSelectionIndex();
        int iMax = checkModel.getMaxSelectionIndex();
        if ((iMin < 0) || (iMax < 0)) {
            return new int[0];
        }
        int[] rvTmp = new int[1+ (iMax - iMin)];
        int n = 0;
        for (int i = iMin; i <= iMax; i++) {
            if (checkModel.isSelectedIndex(i)) {
                rvTmp[n++] = i;
            }
        }
        int[] result = new int[n];
        System.arraycopy(rvTmp, 0, result, 0, n);
        return result;
	} 

	/**
	 * @return Model indices of items that are currently disabled.
	 */
	public int [] getDisabledIndices() {
        int iMin = enablementModel.getMinSelectionIndex();
        int iMax = enablementModel.getMaxSelectionIndex();
        if ((iMin < 0) || (iMax < 0)) {
            return new int[0];
        }
        int[] rvTmp = new int[1+ (iMax - iMin)];
        int n = 0;
        for (int i = iMin; i <= iMax; i++) {
            if (enablementModel.isSelectedIndex(i)) {
                rvTmp[n++] = i;
            }
        }
        int[] result = new int[n];
        System.arraycopy(rvTmp, 0, result, 0, n);
        return result;
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
		DefaultListModel dlm = (DefaultListModel)getModel();
		int size = dlm.getSize();
		if ((fromInd < 0 || fromInd > size - 1)
				|| (toInd < 0 || toInd > size - 1)) {
			throw new IllegalArgumentException("Specify valid indices");
		}
		if (fromInd != toInd) {//do not swap equal.
			boolean isSelectedObjectChecked = isChecked(fromInd);
			boolean isOppositeObjectChecked = isChecked(toInd);
			boolean isSelectedObjectEnabled = isEnabled(fromInd);
			boolean isOppositeObjectEnabled = isEnabled(toInd);
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
			ListSelectionModel lsm = getSelectionModel();
			lsm.setSelectionInterval(toInd,toInd);
		}
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
    	Rectangle rec = getCellBounds(lse.getFirstIndex(), lse.getLastIndex());
    	if (rec != null) {
            repaint(rec); 
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
		    setLayout(new BorderLayout()); 
		    setOpaque(false); 
		    checkBox = new JCheckBox();
		    checkBox.setOpaque(false);
		    checkBox.setBorderPaintedFlat(true);
		    checkBox.setBorder(null);
		} 
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            Component delegateComp = delegateCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            CBList cbl = (CBList)list;
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
