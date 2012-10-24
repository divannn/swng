package FadingGlass;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


/**
 * Press F12 to toggle fade. 
 * @author romainguy
 * @author idanilov
 *
 */
public class FadingGlassDemo extends JFrame {
	
    public FadingGlassDemo() {
        super(FadingGlassDemo.class.getSimpleName());
        setDefaultCloseOperation(EXIT_ON_CLOSE);        
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JComponent result = new JPanel(new BorderLayout());
    	ImageIcon icon = new ImageIcon(FadingGlassDemo.class.getResource("img.png"));
        JLabel label = new JLabel("Toggle F12 to start/stop fading!",icon,SwingConstants.LEFT);
        label.setHorizontalTextPosition(SwingConstants.RIGHT);
        result.add(BorderLayout.NORTH, label);
        result.add(BorderLayout.CENTER, createCenter());
        return result;
    }

    private JComponent createCenter() {
    	JSplitPane result = new JSplitPane();
    	JTree tree = new JTree();
    	JScrollPane scrollPane = new JScrollPane(tree);
    	result.setLeftComponent(scrollPane);
    	result.setRightComponent(createTable());
    	result.setDividerLocation(200);
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
        return scrollPane;
    }

    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
        SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               FadingGlassDemo f = new FadingGlassDemo();
               new GlassPaneActivator(new FadingPanel(), f.getGlassPane(), KeyEvent.VK_F12).install();
               f.pack();
               f.setLocationRelativeTo(null);
               f.setVisible(true);
           }
        });
    }
}
