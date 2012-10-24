package ListToolTip._1;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Override <code>getToolTipText(MouseEvent me)</code>.
 * br>
 * Intersting fact that JList have registration to TooltipManager by default (in constructor).
 * @author idanilov
 * @jdk 1.5
 */
public class ListToolTipDemo1 extends JFrame {

	public ListToolTipDemo1() {
		super(ListToolTipDemo1.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
	    String [] items = {"A", "B", "C", "D"};
	    JList list = new JList(items) {
	        //called as the cursor moves within the list.
	        public String getToolTipText(MouseEvent me) {
	        	Point p = me.getPoint();
	        	Rectangle bounds = getCellBounds(0,getModel().getSize() - 1);
	        	if (bounds != null && bounds.contains(p)) {//show tool tips when the mouse cursor over list items only.
		            //get item index.
		            int index = locationToIndex(p);
		            Object item = getModel().getElementAt(index);
		            return "tool tip for " + item + " item";
	        	} 
        		return super.getToolTipText(me);
	        }

	    };
	    result.add(new JScrollPane(list),BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new ListToolTipDemo1();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}