package ImprovedTableForAutoResizedModeOff;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;


/**
 * See MS Word document about in this project for details.
 * @author idanilov
 * @jdk 1.5
 */
public class ImrovedTableForAutoResizedModeOffDemo extends JFrame {
	
	public ImrovedTableForAutoResizedModeOffDemo() {
		super(ImrovedTableForAutoResizedModeOffDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(2,1));
		Dimension dim = new Dimension(200,150);
		JTable defaultTable = new JTable(2,2);
		defaultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		defaultTable.setPreferredScrollableViewportSize(dim);
		defaultTable.setComponentPopupMenu(createPopup());
		JScrollPane scrollPane1 = new JScrollPane(defaultTable);
		result.add(scrollPane1);
		
		JTable extTable = new JTableEx(2,2);
		extTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		extTable.setPreferredScrollableViewportSize(dim);
		extTable.setComponentPopupMenu(createPopup());
		JScrollPane scrollPane2 = new JScrollPane(extTable);
		//scrollPane2.getViewport().setBackground(extTable.getBackground());
		result.add(scrollPane2);
		return result;
	}
	
	private JPopupMenu createPopup() {
		JPopupMenu result = new JPopupMenu();
		result.add(new JMenuItem("Item1"));
		result.add(new JMenuItem("Item2"));
		result.addSeparator();
		result.add(new JMenuItem("Item3"));
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
        ImrovedTableForAutoResizedModeOffDemo f = new ImrovedTableForAutoResizedModeOffDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	private static class JTableEx extends JTable {
		
		private JPanel headerPanel = new JPanel(new BorderLayout()); 
		private JLabel dummyColumn = new JLabel() {
			
		    public Border getBorder() { 
		        return UIManager.getBorder("TableHeader.cellBorder"); 
		    } 
		}; 
		
		private MouseListener viewPortMouseListener = new MouseAdapter() { 
		    public void mouseClicked(MouseEvent e){ 
		        repostEvent(e); 
		    } 
		    public void mousePressed(MouseEvent e){ 
		        repostEvent(e); 
		    } 
		    public void mouseReleased(MouseEvent e){ 
		        repostEvent(e); 
		    } 
		    public void mouseEntered(MouseEvent e){ 
		        repostEvent(e); 
		    } 
		    public void mouseExited(MouseEvent e){ 
		        repostEvent(e); 
		    } 
		    private void repostEvent(MouseEvent e) { 
		        MouseEvent newEvent = SwingUtilities.convertMouseEvent(e.getComponent(), e, JTableEx.this); 
		        dispatchEvent(newEvent); 
		    } 
		};
		
		public JTableEx(final int nRows, final int nCols) {
			super(nRows,nCols);
			createExtra();
		}
		
		private void createExtra() {
			headerPanel = new JPanel(new BorderLayout()); 
			dummyColumn = new JLabel() {
			    public Border getBorder() { 
			        return UIManager.getBorder("TableHeader.cellBorder"); 
			    } 
			}; 
		}
		
		//make table expand in the viewport bounds.
		public boolean getScrollableTracksViewportHeight() {
			return getPreferredSize().height < getParent().getHeight(); 
		}
		
		//set background for viewport (righ part - see doc in project).
		protected void configureEnclosingScrollPane() {
			super.configureEnclosingScrollPane();
	        Container p = getParent();
	        if (p instanceof JViewport) {
	        	p.setBackground(getBackground());
	        	p.addMouseListener(viewPortMouseListener);
	            Container gp = p.getParent();
	            if (gp instanceof JScrollPane) {
	                JScrollPane scrollPane = (JScrollPane)gp;
					headerPanel.add(getTableHeader(), BorderLayout.WEST); 
					headerPanel.add(dummyColumn, BorderLayout.CENTER); 
					scrollPane.setColumnHeaderView(headerPanel); 
	            }
	        }
		}
		
		protected void unconfigureEnclosingScrollPane() {
			Container p = getParent();
	        if (p instanceof JViewport) {
	        	p.removeMouseListener(viewPortMouseListener);
	        }				
			super.unconfigureEnclosingScrollPane();
		}
		
	}
	
}