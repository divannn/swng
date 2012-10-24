package TableRowHeader;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class TableRowHeaderDemo extends JFrame {

	private String [] columnNames = {
		"col1","col2","col3"
	};
	private String [][] data = {	
		{"1","2","3",},
		{"4","5","6",},
		{"7","8","9",},
	};
	
	public TableRowHeaderDemo() {
		super(TableRowHeaderDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
		
	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		JTable table = new JTable(dtm);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setRowHeaderView(createRowHeader(table));
		result.add(scroll,BorderLayout.CENTER);
		return result;
	}	
	
	private JComponent createRowHeader(final JTable jTable) {
		ListModel listModel = new AbstractListModel() {
			public int getSize() { 
				return jTable.getModel().getRowCount(); 
			}
			  
			public Object getElementAt(int index) {
			    return new Integer(index + 1);
			}
		};
	  	JList result = new JList(listModel);
	  	result.setFocusable(false);
	  	result.setBackground(jTable.getTableHeader().getBackground());
  		result.setFixedCellWidth(50);
		result.setFixedCellHeight(jTable.getRowHeight());
//  					   + jTable.getRowMargin());
//  					   + table.getIntercellSpacing().height);
		result.setCellRenderer(new RowHeaderRenderer(jTable));		
		return result;
	}
	
	private static class RowHeaderRenderer extends JLabel 
			implements ListCellRenderer {
		  
		RowHeaderRenderer(final JTable table) {
			JTableHeader tHeader = table.getTableHeader();
			setOpaque(true);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setHorizontalAlignment(SwingConstants.CENTER);
			setForeground(tHeader.getForeground());
			setBackground(tHeader.getBackground());
			setFont(tHeader.getFont());
		}
		  
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
        TableRowHeaderDemo f = new TableRowHeaderDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
