package GradientPanel;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class GradientPanelDemo extends JFrame {
	
    public GradientPanelDemo() {
        super(GradientPanelDemo.class.getSimpleName());
        setDefaultCloseOperation(EXIT_ON_CLOSE);        
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JComponent result = new JPanel(new BorderLayout());
        result.add(BorderLayout.NORTH, createHeader());
        result.add(BorderLayout.CENTER, createClientArea());
        return result;
    }

    private JComponent createHeader() {
    	ImageIcon icon = new ImageIcon(GradientPanelDemo.class.getResource("hint.png"));
        HeaderPanel result = new HeaderPanel(icon);
        return result;
    }
    
    private JComponent createClientArea() {
        JPanel result = new GradientPanel(new BorderLayout(),GradientPanel.Direction.HOR);
    	Icon icon = new ImageIcon(GradientPanelDemo.class.getResource("hint.png"));
    	JLabel label = new JLabel("", icon, SwingConstants.CENTER);
    	label.setBorder(new EmptyBorder(0,10,0,0));
        result.add(BorderLayout.WEST, label);
        result.add(BorderLayout.CENTER, createTable());
        return result;
    }

	private JComponent createTable() {
    	String [] colNames = new String [] { 
    			"col1","col2","col3",
    	};
    	String [][] data = new String [][] { 
    			{ "1","2","3" },
    			{ "str1","str2","str3" },
    			{ "0.7","8.2","-9.1" },
    	}; 
    	
        TableModel dtm = new DefaultTableModel(data,colNames); 
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        //set opaque border around scroll pane in order to parent gradient panel be visible. 
        Border compBorder = new CompoundBorder(new EmptyBorder(10,10,10,10),scrollPane.getBorder());
        scrollPane.setBorder(compBorder);
        scrollPane.setOpaque(false);
        return scrollPane;
    }

    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		GradientPanelDemo f = new GradientPanelDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
    }
}
