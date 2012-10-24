package ShowHideTableColumn._1;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SizeRequirements;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;

/**
 * Shrink column width.
 * @author idanilov
 * @jdk 1.5
 */
public class ShowHideTableColumnDemo1 extends JFrame {
	
	public ShowHideTableColumnDemo1() {
		super(ShowHideTableColumnDemo1.class.getSimpleName());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		final JTable table = new JTable(10, 5);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(2).setMinWidth(0);
		final String showColumnLabel = "Show Column C";
		final String hideColumnLabel = "Hide Column C";
		final JToggleButton button = new JToggleButton(hideColumnLabel);
		button.addItemListener(new ItemListener() {
			// the old size of the column.
			private SizeRequirements size = new SizeRequirements();

			public void itemStateChanged(ItemEvent ie) {
				TableColumn col = table.getColumnModel().getColumn(2);
				// get the current size of the column
				SizeRequirements temp = new SizeRequirements();
				temp.preferred = col.getPreferredWidth();
				temp.minimum = col.getMinWidth();
				temp.maximum = col.getMaxWidth();
				//change the column size to the old size (or 0 if the first time).
				col.setMinWidth(size.minimum);
				col.setMaxWidth(size.maximum);
				col.setPreferredWidth(size.preferred);
				//save the old size.
				size = temp;
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					button.setText(showColumnLabel);
				} else {
					button.setText(hideColumnLabel);
				}				
			}
			
		});
		
		JPanel result = new JPanel(new BorderLayout(0, 10));
		result.add(button, BorderLayout.NORTH);
		result.add(new JScrollPane(table),BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new ShowHideTableColumnDemo1();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

