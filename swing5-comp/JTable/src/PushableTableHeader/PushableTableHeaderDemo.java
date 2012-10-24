package PushableTableHeader;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class PushableTableHeaderDemo extends JFrame {

	public PushableTableHeaderDemo() {
		super(PushableTableHeaderDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String[] headerStr = { "Push","me","here" };        
		DefaultTableModel dm = new DefaultTableModel(headerStr, 5);    
		JTable table = new JTable(dm);    
		ButtonHeaderRenderer buttonRenderer = new ButtonHeaderRenderer();    
		JTableHeader header = table.getTableHeader();    
		header.setDefaultRenderer(buttonRenderer);
		header.addMouseListener(new HeaderListener(header,buttonRenderer));    
		JScrollPane scrollPane = new JScrollPane(table);
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new PushableTableHeaderDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class HeaderListener extends MouseAdapter {
		
		private JTableHeader header;    
		private ButtonHeaderRenderer renderer;
		
		HeaderListener(JTableHeader header, ButtonHeaderRenderer renderer) {      
			this.header   = header;      
			this.renderer = renderer;    
		}      
		
		public void mousePressed(MouseEvent me) {      
			int col = header.columnAtPoint(me.getPoint());
			int modelIndex = header.getColumnModel().getColumn(col).getModelIndex();
			renderer.setPressedColumn(modelIndex);
			header.repaint();            
		}      
		
		public void mouseReleased(MouseEvent me) {      
			renderer.setPressedColumn(-1);                
			header.repaint();    
		}      
	}

	private static class ButtonHeaderRenderer extends JButton 
			implements TableCellRenderer {
		
		private int pressedColumn;      
		
		public ButtonHeaderRenderer() {      
			pressedColumn = -1;      
			setMargin(new Insets(0,0,0,0));    
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value ==null) ? "" : value.toString());
			int modelIndex = table.getColumnModel().getColumn(column).getModelIndex();
			boolean isPressed = (modelIndex == pressedColumn);
			getModel().setPressed(isPressed);      
			getModel().setArmed(isPressed);      
			return this;    
		}      
		
		public void setPressedColumn(int col) {      
			pressedColumn = col;    
		}  
		
	}
}
