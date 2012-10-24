package ShowHideTableColumn._2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Realized the same behaviour as in Windows Explorer.
 * @author idanilov
 * @jdk 1.5
 */
public class ShowHideTableColumnDemo2 extends JFrame {

	private JTable table;
	private JTableHeader header;
	private JPopupMenu popup;
	
	private TableColumnModel selectedColumnModel;
	private TableColumnModel fullColumnModel;
	private String [] columnNames = {
		"col1","col2","col3","col4","col5"
	};
	private String [][] data = {	
		{"1","2","3","4","5"},
		{"6","7","8","9","10"},
		{"11","12","13","14","15"},
		{"16","17","18","19","20"},
		{"21","22","23","24","25"},
	};

	private static final String [] PREDEFINDED = { "col1","col4" };
	private static final String [] FIXED = { "col1" };
	
	public ShowHideTableColumnDemo2() {
		super(ShowHideTableColumnDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private TableColumnModel createPredefinedColumnModel() {
		TableColumnModel result = new DefaultTableColumnModel();
		for (int i = 0; i < PREDEFINDED.length; i++) {
			String nextPredefinedId = PREDEFINDED[i];
			int nextPredefinedIndex = fullColumnModel.getColumnIndex(nextPredefinedId);
			TableColumn nextPredefinedColumn = fullColumnModel.getColumn(nextPredefinedIndex);
			result.addColumn(nextPredefinedColumn);
		}
		return result;
	}

	private TableColumnModel createFullColumnModel() {
		TableColumnModel result = new DefaultTableColumnModel();
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn nextNewColumn = new TableColumn(i);
			nextNewColumn.setHeaderValue(columnNames[i]);
			nextNewColumn.setIdentifier(columnNames[i]);//use name as identifier.
			result.addColumn(nextNewColumn);
		}
		return result;
	}
	
	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		fullColumnModel = createFullColumnModel();
		selectedColumnModel = createPredefinedColumnModel();
		table = new JTable(dtm,selectedColumnModel);
		popup = new JPopupMenu();
		popup.addPopupMenuListener(
			new PopupMenuListener() {
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					fillPopup();
				}
	
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				}
	
				public void popupMenuCanceled(PopupMenuEvent e) {
				}
			}
		);		
		header = table.getTableHeader();
		header.setToolTipText("Right click to open popup");
		header.setReorderingAllowed(true);
		//header.setComponentPopupMenu(popup);
    	//XXX: !!!Use good old way to register popup - new one results to exception with draggable column.
    	header.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {
				/*TableColumn dc = header.getDraggedColumn();
				System.err.println("PRES dg:" + ((dc != null) ? dc.getHeaderValue() : "null"));*/
				showPopup(me);
			}

			public void mouseReleased(MouseEvent me) {
				/*TableColumn dc = header.getDraggedColumn();
				System.err.println("REL dg:" + ((dc != null) ? dc.getHeaderValue() : "null"));*/
				showPopup(me);
			}
			
			private void showPopup(MouseEvent me) {
		        if (me.isPopupTrigger()) {
		        	Component comp = me.getComponent();
		        	popup.show(comp,me.getX(), me.getY());
		        }
		    }			
    	});		
		JScrollPane scroll = new JScrollPane(table);
		result.add(scroll,BorderLayout.CENTER);
		return result;
	}
	
	private void fillPopup() {
		popup.removeAll();
		final TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < fullColumnModel.getColumnCount(); i++) {
			TableColumn nextColumn = fullColumnModel.getColumn(i);
			JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(nextColumn.getHeaderValue().toString());
			popup.add(menuItem);
			for (int j = 0; j < tcm.getColumnCount(); j++) {
				TableColumn nextSelectedColumn = tcm.getColumn(j);
				if (nextSelectedColumn.getHeaderValue().equals(nextColumn.getHeaderValue())) {
					menuItem.setSelected(true);
				}				
			}			
		}
		for (int i = 0; i < FIXED.length; i++) {
			String nextFixedId = FIXED[i];
			int nextFixedIndex = fullColumnModel.getColumnIndex(nextFixedId);
			JCheckBoxMenuItem nextComp = (JCheckBoxMenuItem)popup.getComponent(nextFixedIndex);
			nextComp.setEnabled(false);
		}
		for (int i = 0; i < popup.getComponentCount(); i++) {
			JCheckBoxMenuItem nextComp = (JCheckBoxMenuItem)popup.getComponent(i);
			nextComp.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ie) {
					JCheckBoxMenuItem source = (JCheckBoxMenuItem)ie.getSource(); 
					if (ie.getStateChange() == ItemEvent.SELECTED) {
						int targetInd = fullColumnModel.getColumnIndex(source.getText());
						TableColumn targetColumn = fullColumnModel.getColumn(targetInd);
						tcm.addColumn(targetColumn);
						int targetModelInd = targetColumn.getModelIndex();
						int lastInd = tcm.getColumnCount()-1;
						for (int k = 0; k < lastInd; k++) {
							TableColumn tc = tcm.getColumn(k);
							if (targetModelInd < tc.getModelIndex()) {
								targetInd = k;
								break;
							} 
						}
						if (targetInd < lastInd) {
							//System.err.println("moved to : " + targetInd);
							tcm.moveColumn(lastInd,targetInd);
						}
					} else {
						int targetInd = tcm.getColumnIndex(source.getText());
						TableColumn col = tcm.getColumn(targetInd);
						//System.err.println("removed " + col.getHeaderValue());
						tcm.removeColumn(col); 
					}
				}});						
		}
		popup.addSeparator();
		JMenuItem moreItem = new JMenuItem("More...");
		moreItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SelectColumnDialog selectColumnDlg = new SelectColumnDialog(ShowHideTableColumnDemo2.this,fullColumnModel,selectedColumnModel,FIXED);
				selectColumnDlg.open(true);
			}
			
		});
		popup.add(moreItem);
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
		ShowHideTableColumnDemo2 f = new ShowHideTableColumnDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}