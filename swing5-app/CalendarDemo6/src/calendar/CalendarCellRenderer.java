package calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Sets cell's background color to table default.
 * @author idanilov
 *
 */
class CalendarCellRenderer extends DefaultTableCellRenderer {

	private ViewerUI viewerUI;
	private static final Color DEFAULT_TABLE_B = UIManager.getColor("Table.background");

	CalendarCellRenderer(final ViewerUI viewerUI) {
		super();
		if (viewerUI == null) {
			throw new IllegalArgumentException("Provide non-null calendar viewer UI.");
		}
		this.viewerUI = viewerUI;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		JLabel result = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		if (!isSelected) {
			result.setBackground(DEFAULT_TABLE_B);
		}
		Icon icon = viewerUI.getLabelProvider().getIcon((CalendarItem) value);
		String text = viewerUI.getLabelProvider().getText((CalendarItem) value);
		result.setIcon(icon);
		result.setText(text);
		return result;
	}

}