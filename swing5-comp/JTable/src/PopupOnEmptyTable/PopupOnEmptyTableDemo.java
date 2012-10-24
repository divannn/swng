package PopupOnEmptyTable;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 1. Stretch table to fill viewport.
 * 2. Focus table if it was clicked in non-rows area. 
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class PopupOnEmptyTableDemo extends JFrame {

	public PopupOnEmptyTableDemo() {
		super(PopupOnEmptyTableDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(2, 1));
		Dimension dim = new Dimension(200, 100);
		JTable defaultTable = new JTable(2, 5);
		defaultTable.setPreferredScrollableViewportSize(dim);
		defaultTable.setComponentPopupMenu(createPopup());
		JScrollPane scrollPane1 = new JScrollPane(defaultTable);
		result.add(scrollPane1);

		final JTable extentedTable = new JTable(2, 5) {

			//make table expand in the viewport bounds.
			public boolean getScrollableTracksViewportHeight() {
				return getPreferredSize().height < getParent().getHeight();
			}
		};
		extentedTable.setPreferredScrollableViewportSize(dim);
		extentedTable.setComponentPopupMenu(createPopup());
		
		//make the table take focus when clicked, which BasicTableUI does not do when there aren't any rows.
		extentedTable.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (!e.isConsumed() && extentedTable.isEnabled() && SwingUtilities.isLeftMouseButton(e)) {
					// if the UI ignores our desperate plea for focus when
					// the table is clicked beyond the last row or last column,
					// we'll try to grab focus anyway.
					int row = extentedTable.rowAtPoint(e.getPoint());
					int column = extentedTable.columnAtPoint(e.getPoint());
					if (row == -1 || column == -1) {
						if (!extentedTable.hasFocus() && extentedTable.isRequestFocusEnabled()) {
							extentedTable.requestFocusInWindow();
						}
					}
				}
			}
		});
		JScrollPane scrollPane2 = new JScrollPane(extentedTable);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		PopupOnEmptyTableDemo f = new PopupOnEmptyTableDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}