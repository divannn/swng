package calendar.dnd;

import java.util.Date;
import java.util.List;

import javax.swing.JTable;

import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public class CalendarTransferData {

	private List<ICalendarItem> selectedItems;
	private JTable sourceTable;
	private Date sourceDate;

	public CalendarTransferData(final JTable sourceTable, final Date sourceDate,
			final List<ICalendarItem> selectedItems) {
		this.sourceTable = sourceTable;
		this.selectedItems = selectedItems;
		this.sourceDate = sourceDate;
	}

	/**
	 * @return source table component
	 */
	public JTable getSourceTable() {
		return sourceTable;
	}

	/**
	 * @return source day
	 */
	public Date getSourceDay() {
		return sourceDate;
	}

	/**
	 * @return selected calendar items
	 */
	public List<ICalendarItem> getSelectedItems() {
		return selectedItems;
	}

}
