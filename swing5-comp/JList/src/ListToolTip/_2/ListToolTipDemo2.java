package ListToolTip._2;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Provide tooltip in list cell renderer.
 * @author idanilov
 * @jdk 1.5
 */
public class ListToolTipDemo2 extends JFrame {

	public ListToolTipDemo2() {
		super(ListToolTipDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
	    String [] items = {"A", "B", "C", "D"};
	    JList list = new JList(items);
	    list.setCellRenderer(new DefaultListCellRenderer() {
	    	public Component getListCellRendererComponent(JList l, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    		JComponent renderer = (JComponent)super.getListCellRendererComponent(l, value, index, isSelected,
	    				cellHasFocus);
	    		renderer.setToolTipText("tool tip for " + value.toString() + " item");
	    		return renderer;
	    	}
	    });
	    result.add(new JScrollPane(list),BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new ListToolTipDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}