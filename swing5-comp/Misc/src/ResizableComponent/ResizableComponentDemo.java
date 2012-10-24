package ResizableComponent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 * Allows to arbitrary resize/move any JComponents.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class ResizableComponentDemo extends JFrame {

	public ResizableComponentDemo() {
		super(ResizableComponentDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JPanel container = new JPanel(null);//absolute layout.
		container.setPreferredSize(new Dimension(500,400));

		JLabel label = new JLabel("Label:");
		container.add(label);
		JComponent res = createResizer(result,label);
		res.setLocation(10,10);

		JTree tree = new JTree();
		container.add(tree);
		res = createResizer(result,tree);
		res.setLocation(100,10);

		JTable table = new JTable(new Object[][] {
				{
						"[111]",
						"[222]",
						"[333]"
				},
				{
						"[999]", 
						"[888]",
						"[777]"
				},
				{
						"[555]", 
						"[666]",
						"[444]"
				}
		}, new String[] {
				"col1", "col2", "col3"
		});
		table.setPreferredScrollableViewportSize(new Dimension(300, 200));
		JScrollPane sp = new JScrollPane(table);
		container.add(sp);
		res = createResizer(result,sp);
		res.setLocation(10,100);

		result.add(container, BorderLayout.CENTER);
		return result;
	}

	private static JResizer createResizer(final JComponent cont, final JComponent comp) {
		Dimension bounds = comp.getPreferredSize();
		JResizer result = new JResizer(comp);
		Point location = new Point();
		result.setBounds(location.x, location.y, bounds.width, bounds.height);
		cont.add(result);
		cont.repaint();
		result.invalidate();
		cont.revalidate();
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ResizableComponentDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
