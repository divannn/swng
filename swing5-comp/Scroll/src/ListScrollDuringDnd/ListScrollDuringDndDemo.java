package ListScrollDuringDnd;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.dnd.Autoscroll;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;


/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class ListScrollDuringDndDemo extends JFrame {

	public ListScrollDuringDndDemo() {
		super(ListScrollDuringDndDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout(5,0));
		result.add(createDragSourcePanel(),BorderLayout.WEST);
		result.add(createDropTargetPanel(),BorderLayout.CENTER);
		return result;
	}
	
	private JComponent createDragSourcePanel() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Drag source:"),BorderLayout.NORTH);
		JList list = new JList(new String [] {"111","222","333"} );
		list.setDragEnabled(true);
		JScrollPane scrollPane = new JScrollPane(list);
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}

	private JComponent createDropTargetPanel() {
		JPanel result = new JPanel(new GridLayout(2,1));
		result.add(createDefaultList());
		result.add(createAutoscrollList());
		return result;
	}
	
	private JComponent createDefaultList() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Default JList:"),BorderLayout.NORTH);
		JList list = new JList(createListModel());
		JScrollPane scrollPane = new JScrollPane(list);
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}
	
	private JComponent createAutoscrollList() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Autoscroll JList:"),BorderLayout.NORTH);
		JList list = new AutoscrollList(createListModel());
		JScrollPane scrollPane = new JScrollPane(list);
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}
	
	private ListModel createListModel() {
		DefaultListModel result = new DefaultListModel();
		result.addElement("one");
		result.addElement("two");
		result.addElement("tree");
		result.addElement("four");
		result.addElement("five");
		result.addElement("six");
		result.addElement("seven");
		result.addElement("eight");
		result.addElement("nine");
		result.addElement("ten");
		return result;	
	}
	
	public static void main(String[] args) {
		JFrame f = new ListScrollDuringDndDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	private class AutoscrollList extends JList 
			implements Autoscroll {
		
	    private AutoscrollSupport scrollSupport; 
	 
	    public AutoscrollList(final ListModel model) { 
	        super(model); 
	        scrollSupport = new AutoscrollSupport(this, new Insets(10, 10, 10, 10));
	    } 
	 
	    public Insets getAutoscrollInsets() { 
	        return scrollSupport.getAutoscrollInsets(); 
	    } 
	 
	    public void autoscroll(Point cursorLocn) { 
	        scrollSupport.autoscroll(cursorLocn); 
	    }
	    
	}
	
}