package PartiallyVisibleTableCells;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Show cell value in tooltip if value doesn't fit into cell bounds.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class PartiallyVisibleTableCellsDemo extends JFrame {

	private JTable table;
	
	public PartiallyVisibleTableCellsDemo() {
		super(PartiallyVisibleTableCellsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
	    DefaultTableModel dm = new DefaultTableModel();    
	    dm.setDataVector(new Object[][] { 
	    		{"aaaaaaaaaaa","bbbbbbbb","cccccccc"}, 
	    		{"AAAAAA","BBBBBBBBBBBBBB","CCCCCCCcC"} },
	    		new Object[] {"1st","2nd","3rd"} 
    	);
		table = new JTable(dm) {
			public String getToolTipText(MouseEvent me) {
				String ret = table.getToolTipText();
				Point p = me.getPoint();
				int col = table.columnAtPoint(p);
				int row = table.rowAtPoint(p);
				if (row > -1 && col > -1) {
		            TableCellRenderer renderer = getCellRenderer(row, col);
		            Component component = prepareRenderer(renderer, row, col);
		            Dimension prefSize = component.getPreferredSize();
					Rectangle cellBounds = table.getCellRect(row,col,false);
					if (cellBounds.width < prefSize.width) {
						ret = table.getModel().getValueAt(row,col).toString();
					} 
				}
				return ret;
			}
		};
		table.setToolTipText("Table tooltip");
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    JScrollPane scroll = new JScrollPane(table);
	    result.add(scroll,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new PartiallyVisibleTableCellsDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
