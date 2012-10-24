package MultilineTableHeader;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class MultilineTableHeaderDemo extends JFrame {

	public MultilineTableHeaderDemo() {
		super(MultilineTableHeaderDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] { { "a", "b", "c" }, { "A", "B", "C" } }, new Object[] {
				"1st", "2nd\n2nd", "3rd\n3rd\n3rd" });
		JTable table = new JTable(dm);
		MultiLineHeaderRenderer headerRenderer = new MultiLineHeaderRenderer();
		//1.this doesn't work ;) because of BasicTableHeaderUI#getHeaderHeight gets column renderer to calculate height of table header. 
		//table.getTableHeader().setDefaultRenderer(headerRenderer);
		//2. this works.
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			en.nextElement().setHeaderRenderer(headerRenderer);
		}
		JScrollPane scroll = new JScrollPane(table);
		result.add(scroll, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new MultilineTableHeaderDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class MultiLineHeaderRenderer extends JList
			implements TableCellRenderer {

		public MultiLineHeaderRenderer() {
			setOpaque(true);
			setForeground(UIManager.getColor("TableHeader.foreground"));
			setBackground(UIManager.getColor("TableHeader.background"));
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			ListCellRenderer listRenderer = getCellRenderer();
			((JLabel) listRenderer).setHorizontalAlignment(SwingConstants.CENTER);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			setFont(table.getFont());
			String str = (value == null) ? "" : value.toString();
			BufferedReader br = new BufferedReader(new StringReader(str));
			List<String> listData = new ArrayList<String>();
			String nextLine;
			try {
				while ((nextLine = br.readLine()) != null) {
					listData.add(nextLine);
				}
				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			setListData(listData.toArray());
			return this;
		}

	}

}
