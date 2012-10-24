import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;

import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * Shows new DnD features for highlithing drop position in JTable,JTree and JList.
 * @author idanilov
 * @jdk 1.6
 */
public class DnDHighlight extends JFrame {

	public DnDHighlight() {
		super(DnDHighlight.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createDragSourcePanel(),BorderLayout.NORTH);
		result.add(createControls(),BorderLayout.CENTER);
		return result;
	}

	private JComponent createControls() {
		JPanel result = new JPanel(new GridLayout(1, 2));
		
		Object[] columnNames = { "col1", "col2" };
		Object[][] data = { { "4", 2.0 }, { "6", 1.2 }, { "1", 7.11 } };
		DefaultTableModel tm = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(tm);
		table.setPreferredScrollableViewportSize(new Dimension(100,100));
		//table.setDropMode(DropMode.ON);//play with drop modes.
		table.setDropMode(DropMode.INSERT);
		table.setTransferHandler(new TableDropHandler());
		result.add(new JScrollPane(table));

		JTree tree = new JTree();
		//tree.setDropMode(DropMode.ON);//play with drop modes.
		tree.setDropMode(DropMode.INSERT);
		tree.setTransferHandler(new TableDropHandler());
		result.add(new JScrollPane(tree));
		
		JList list = new JList(new String [] {"item1","item2"});
		//list.setDropMode(DropMode.ON);//play with drop modes.
		list.setDropMode(DropMode.INSERT);
		list.setTransferHandler(new TableDropHandler());
		result.add(new JScrollPane(list));
		return result;
	}

	private JComponent createDragSourcePanel() {
		JPanel result = new JPanel();
		JLabel dragSourceLabel = new JLabel("Drag source:");
		result.add(dragSourceLabel);
		JTextField tf = new JTextField("Drag fom here...");
		tf.setDragEnabled(true);
		result.add(tf);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new DnDHighlight();
		f.pack();
		f.setMinimumSize(f.getPreferredSize());
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private class TableDropHandler extends TransferHandler {

		public boolean canImport(TransferSupport support) {
			if (!support.isDrop()) {
				return false;
			}
			if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return false;
			}

			//			boolean copySupported = (COPY & support.getSourceDropActions()) == COPY;
			//
			//			//if COPY is supported, choose COPY and accept the transfer.
			//			//needed to provide COPY cursor (default is MOVE).
			//			if (copySupported) {
			//				support.setDropAction(COPY);
			//				return true;
			//			}

			return true;
		}

		public boolean importData(TransferSupport support) {
			if (!canImport(support)) {
				return false;
			}
			return true;
		}
	}
}
