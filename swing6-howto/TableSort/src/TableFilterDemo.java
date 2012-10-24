import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Shows new feature - ability to filter JTable rows.
 * @jdk 1.6
 */
public class TableFilterDemo extends JFrame {

	private Object[] columnNames = { "Symbol", "Name", "Price" };
	private Object[][] data = {
			{"AMZN", "Amazon", 41.28},
            {"EBAY", "eBay", 41.57},
            {"GOOG", "Google", 388.33},
            {"MSFT", "Microsoft", 26.56},
            {"NOK", "Nokia Corp", 17.13},
            {"ORCL", "Oracle Corp.", 12.52},
            {"SUNW", "Sun Microsystems", 3.86},
            {"TWX",  "Time Warner", 17.66},
            {"VOD",  "Vodafone Group", 26.02},
            {"YHOO", "Yahoo!", 37.69}
	};

	public TableFilterDemo() {
		super(TableFilterDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		DefaultTableModel tm = new DefaultTableModel(data, columnNames) {

			public Class<?> getColumnClass(int column) {
				Class returnValue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnValue = getValueAt(0, column).getClass();
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}

		};
		JTable table = new JTable(tm);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tm);
		table.setRowSorter(sorter);
		result.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Filter:");
		panel.add(label, BorderLayout.WEST);
		final JTextField filterText = new JTextField("SUN");
		panel.add(filterText, BorderLayout.CENTER);
		JButton sortButton = new JButton("Search");
		sortButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String text = filterText.getText();
				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					try {
						sorter.setRowFilter(RowFilter.regexFilter(text));
					} catch (PatternSyntaxException pse) {
						System.err.println("Bad regex pattern");
					}
				}
			}
		});
		panel.add(sortButton, BorderLayout.EAST);
		result.add(panel, BorderLayout.NORTH);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new TableFilterDemo();
		f.pack();
		f.setMinimumSize(f.getPreferredSize());
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
