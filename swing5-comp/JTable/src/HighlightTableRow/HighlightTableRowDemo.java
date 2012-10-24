package HighlightTableRow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Higlight row / column during mouse move.
 * @author idanilov
 * @jdk 1.5
 */
public class HighlightTableRowDemo extends JFrame {

	private int hoverRow = -1;
	private int hoverColumn = -1;
	
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

	public HighlightTableRowDemo() {
		super(HighlightTableRowDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new GridLayout(2,1));
		JTable tableForRows = createRowDemoTable();
		JScrollPane rowTableScroll = new JScrollPane(tableForRows);
		result.add(rowTableScroll);
		JTable tableForColumns = createColumnDemoTable();
		JScrollPane columnTableScroll = new JScrollPane(tableForColumns);
		result.add(columnTableScroll);
		return result;
	}
	
	private JTable createRowDemoTable() {
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		final JTable result = new JTable(dtm);
		result.setPreferredScrollableViewportSize(new Dimension(300,200));
		result.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		result.setDefaultRenderer(Object.class,new RowHoverRenderer());
		MouseInputAdapter rowHoverAdapter = new MouseInputAdapter() {
			
			public void mouseMoved(MouseEvent me) {
				int row = result.rowAtPoint(me.getPoint());
				if (row >= 0) {
					if (hoverRow != row) {//optimization.
						hoverRow = row;
						result.repaint();
					}
				}
			}
			
			public void mouseExited(MouseEvent me) {
				hoverRow = -1;
				result.repaint();
			}
			
		};
		result.addMouseMotionListener(rowHoverAdapter);
		result.addMouseListener(rowHoverAdapter);
		return result;
	}
	
	private JTable createColumnDemoTable() {
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		final JTable result = new JTable(dtm);
		result.setPreferredScrollableViewportSize(new Dimension(300,200));
		result.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		result.setDefaultRenderer(Object.class,new ColumnHoverRenderer());
		MouseInputAdapter columnHoverAdapter = new MouseInputAdapter() {
			
			public void mouseMoved(MouseEvent me) {
				int column = result.columnAtPoint(me.getPoint());
				if (column >= 0) {
					if (hoverColumn != column) {//optimization.
						hoverColumn = column;
						result.repaint();
					}
				}
			}
			
			public void mouseExited(MouseEvent me) {
				hoverColumn = -1;
				result.repaint();
			}
			
		};
		result.addMouseMotionListener(columnHoverAdapter);
		result.addMouseListener(columnHoverAdapter);
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
		HighlightTableRowDemo f = new HighlightTableRowDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private class RowHoverRenderer extends DefaultTableCellRenderer {
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel comp = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (!isSelected) {
				if (row == hoverRow) {
					comp.setBackground(Color.RED);
				} else {
					comp.setBackground(table.getBackground());
				}
			}
			return comp;
		}
		
	}

	private class ColumnHoverRenderer extends DefaultTableCellRenderer {
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel comp = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (!isSelected) {
				if (column == hoverColumn) {
					comp.setBackground(Color.RED);
				} else {
					comp.setBackground(table.getBackground());
				}
			}
			return comp;
		}
		
	}
	
}