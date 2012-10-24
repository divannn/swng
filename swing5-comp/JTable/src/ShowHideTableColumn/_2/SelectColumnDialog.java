package ShowHideTableColumn._2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * @author idanilov
 *
 */
public class SelectColumnDialog extends JDialog {
	
	private CBList cbList;
	private JButton selectAllButton;
	private JButton clearAllButton;
	private JButton moveUpButton;
	private JButton moveDownButton;	
	
	private JButton okButton;
	private JButton cancelButton;
	
	private TableColumnModel fullTcm;
	private TableColumnModel selectedTcm;
	private String[] fixedIds;
	
	public SelectColumnDialog(final JFrame parent, final TableColumnModel full, final TableColumnModel selected, final String[] fixedIds) {
		super(parent);
		setTitle("Select Columns");
		this.fullTcm = full;
		this.selectedTcm = selected;
		this.fixedIds = fixedIds;
		setContentPane(createContents());
		initDialog();
	}

	protected void initDialog() {
		moveUpButton.setEnabled(false);
		moveDownButton.setEnabled(false);
		initList();
	}

	private void initList() {
		//1.move selected columns to top.
		//2.check them.
		DefaultListModel dlm = (DefaultListModel)cbList.getModel();
		int [] checked = new int [selectedTcm.getColumnCount()];
		for (int j = 0; j < selectedTcm.getColumnCount(); j++) {
			TableColumn nextSelectedColumn = selectedTcm.getColumn(j);
			int nextCorrespondentColumnIndex = findColumnInList(nextSelectedColumn.getIdentifier().toString());
			//System.err.println(" replace " + nextSelectedColumn.getHeaderValue() + " from " + nextCorrespondentColumnIndex + " to " + j);			
			dlm.remove(nextCorrespondentColumnIndex);
			dlm.add(j,nextSelectedColumn);
			checked[j] = j;
		}			
		cbList.checkIndices(checked);
		//3.mark fixed columns.
		for (String nextFixedId : fixedIds) {
			int nextCorrespondentColumnIndex = findColumnInList(nextFixedId);
			if (nextCorrespondentColumnIndex != -1) {
				cbList.setEnabled(nextCorrespondentColumnIndex,false);
			}
		}
	}	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout(5,5));
		DefaultListModel dlm = new DefaultListModel();
		dlm.setSize(fullTcm.getColumnCount());
		for (int i = 0; i < fullTcm.getColumnCount(); i++) {
			TableColumn nextColumn = fullTcm.getColumn(i);
			dlm.setElementAt(nextColumn,i);
		}				
		cbList = new CBList(dlm);
		cbList.setDelegateRenderer(new TableColumnCellRenderer());
		cbList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent lse) {
				if (!lse.getValueIsAdjusting()) {
					ListSelectionModel lsm = (ListSelectionModel)lse.getSource();
					int selectedIndex = lsm.getMinSelectionIndex();
					boolean allowUp = (selectedIndex != -1) && (selectedIndex > 0);
					moveUpButton.setEnabled(allowUp);
					boolean allowDown = (selectedIndex != -1) && (selectedIndex < cbList.getModel().getSize() - 1);
					moveDownButton.setEnabled(allowDown);
				}
				
			}
			
		});		
		result.add(new JScrollPane(cbList),BorderLayout.CENTER);
		result.add(createRightPanel(),BorderLayout.EAST);
		result.add(createBottomPanel(),BorderLayout.SOUTH);
		return result;
	}

	private JPanel createRightPanel() {
		JPanel result = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel(new GridLayout(0,1,0,5));
		selectAllButton = new JButton("Select all");
		selectAllButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cbList.checkIndices(getAvailableIndices());
			}
			
		});		
		buttonsPanel.add(selectAllButton);
		clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cbList.clearIndices(getAvailableIndices());
			}
			
		});
		buttonsPanel.add(clearAllButton);
		buttonsPanel.add(new JLabel(""));
		moveUpButton = new JButton("Up");
		moveUpButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ListSelectionModel lsm = cbList.getSelectionModel();
				if (!lsm.isSelectionEmpty()) {
					int fromInd = lsm.getMinSelectionIndex();
					int toInd = fromInd > 0 ? fromInd - 1 : -1;
					if (toInd != -1) {
						//System.err.println(":::: move up from " + fromInd);
						cbList.swap(fromInd,toInd);
					}
				}				
			}
			
		});
		buttonsPanel.add(moveUpButton);
		moveDownButton = new JButton("Down");
		moveDownButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ListSelectionModel lsm = cbList.getSelectionModel();
				DefaultListModel dlm = (DefaultListModel)cbList.getModel();
				if (!lsm.isSelectionEmpty()) {
					int fromInd = lsm.getMinSelectionIndex();
					int toInd = fromInd < dlm.size() - 1 ? fromInd + 1 : -1;
					if (toInd != -1) {
						//System.err.println(":::: move down from " + fromInd);
						cbList.swap(fromInd,toInd);
					}
				}
			}
			
		});
		buttonsPanel.add(moveDownButton);
		result.add(buttonsPanel,BorderLayout.NORTH);
		return result;
	}
	
	private JPanel createBottomPanel() {
		JPanel result = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel(new GridLayout(1,2,5,0));
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clickOnOk();
			}
			
		});
		buttonsPanel.add(okButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clickOnCancel();
			}
			
		});
		buttonsPanel.add(cancelButton);
		result.add(buttonsPanel,BorderLayout.EAST);
		result.add(new JSeparator(),BorderLayout.NORTH);
		return result;
	}
	
	private int findColumnInList(final String columnId) {
		DefaultListModel dlm = (DefaultListModel)cbList.getModel();
		for (int i = 0; i < dlm.getSize(); i++) {
			TableColumn nextColumn = (TableColumn)dlm.get(i);
			if (columnId.equals(nextColumn.getIdentifier())) {
				return i;
			}
		}
		return -1;
	}

	private int [] getAvailableIndices() {
		int itemCount = cbList.getModel().getSize();
		int [] result = new int[itemCount];
		for (int i = 0 ; i < itemCount; i++) {
			result[i] = i;
		}
		for (String nextFixedId : fixedIds) {
			int nextCorrespondentColumnIndex = findColumnInList(nextFixedId);
			if (nextCorrespondentColumnIndex != -1) {
				result[nextCorrespondentColumnIndex] = -1;
			}
		}
		return result;
	}
	
	private void clickOnOk() {
		Object [] selectedTableColumns = cbList.getCheckedObjects();
		while (selectedTcm.getColumnCount() > 0) {
			TableColumn firstColumn = selectedTcm.getColumn(0); 
			selectedTcm.removeColumn(firstColumn);
		}			  
		for (int i = 0; i < selectedTableColumns.length; i++) {
			TableColumn nextColumn = (TableColumn)selectedTableColumns[i];
			selectedTcm.addColumn(nextColumn);
		}
		close();
	}
	
	private void clickOnCancel() {
		close();
	}
	
	public void open(final boolean modal) {
		pack();
		setLocationRelativeTo(getOwner());
		setModal(modal);
		setVisible(true);
	}
	
	public void close() {
		setVisible(false);
		dispose();
	}

	private static class TableColumnCellRenderer extends DefaultListCellRenderer {
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel comp = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
			TableColumn tc = (TableColumn)value;
			comp.setText(tc.getHeaderValue().toString());
			return comp;
		}
		
	}
	
}