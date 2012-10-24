package ColorIcon;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;

/**
 * Shows flat toggel button wich change its icon by dimming when selected.
 * Also dimmed icon used in tree for selected nodes. 
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class ColorIconDemo extends JFrame {

	public ColorIconDemo() {
		super(ColorIconDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		ImageIcon cdIcon = new ImageIcon(ColorIconDemo.class.getResource("CD.png"));
		JPanel result = new JPanel(new BorderLayout());
		JPanel p = new JPanel();
		p.add(createFlatButton(cdIcon));
		result.add(p, BorderLayout.NORTH);
		JTree tree = createTree();
		result.add(new JScrollPane(tree), BorderLayout.CENTER);
		return result;
	}

	private JToggleButton createFlatButton(final Icon icon) {
		JToggleButton button = new JToggleButton("FlatButton", icon);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		Icon dimmedIcon = new DimmedIcon(icon);
		button.setPressedIcon(dimmedIcon);
		button.setSelectedIcon(dimmedIcon);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		return button;
	}

	private JTree createTree() {
		JTree result = new JTree();
		MutableTreeNode root = (MutableTreeNode)result.getModel().getRoot();
		root.setUserObject("Tree with dimmed icons");
		result.setCellRenderer(new DefaultTreeCellRenderer() {

			{
				setLeafIcon(new DimmedIcon(getLeafIcon()));
				setOpenIcon(new DimmedIcon(getOpenIcon()));
				setClosedIcon(new DimmedIcon(getClosedIcon()));
			}

			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
					boolean expanded, boolean leaf, int row, boolean hasFocus) {
				((DimmedIcon) getLeafIcon()).setColorPainted(sel);
				((DimmedIcon) getOpenIcon()).setColorPainted(sel);
				((DimmedIcon) getClosedIcon()).setColorPainted(sel);
				return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
						hasFocus);
			}
		});
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new ColorIconDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
