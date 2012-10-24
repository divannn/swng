package calendar.model;

import java.util.Arrays;
import java.util.Date;

import javax.swing.event.EventListenerList;

import calendar.util.CalendarLog;
import calendar.util.CalendarUtil;

/**
 * Single day selection mode supported only.
 * @author idanilov
 *
 */
public class CalendarSelectionModel {

	private Date selectedDay;
	private int[] selectedItems;

	protected EventListenerList listenerList;

	public CalendarSelectionModel() {
		//default selection - today.
		selectedDay = new Date();
		selectedItems = new int[] {};
		listenerList = new EventListenerList();
	}

	public Date getSelectedDay() {
		return selectedDay;
	}

	public int[] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedDay(final Date day) {
		if ((day != null && ((selectedDay != null && !CalendarUtil.equalDays(selectedDay, day)) || selectedDay == null))
				|| (day == null && selectedDay != null)) {
			Date oldDay = selectedDay;
			int[] oldItems = selectedItems;
			selectedDay = day;
			selectedItems = new int[] {};
			CalendarLog.SELECTION_LOGGER.fine("[selection] Set selected day: " + day);
			fireDaySelectionChanged(new CalendarSelectionEvent(this, oldDay, oldItems));
		}
	}

	public void setSelectedItems(final int[] itemIndices) {
		if (itemIndices == null) {
			throw new IllegalArgumentException();
		}
		int[] oldItems = selectedItems;
		selectedItems = itemIndices;
		CalendarLog.SELECTION_LOGGER.fine("[selection] Set selected items: "
				+ Arrays.toString(itemIndices));
		fireItemSelectionChanged(new CalendarSelectionEvent(this, null, oldItems));
	}

	public void addCalendarSelectionListener(final CalendarSelectionListener csl) {
		if (csl != null) {
			listenerList.add(CalendarSelectionListener.class, csl);
		}
	}

	public void removeCalendarSelectionListener(final CalendarSelectionListener csl) {
		if (csl != null) {
			listenerList.remove(CalendarSelectionListener.class, csl);
		}
	}

	protected void fireDaySelectionChanged(final CalendarSelectionEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CalendarSelectionListener.class) {
				((CalendarSelectionListener) listeners[i + 1]).daySelectionChanged(e);
			}
		}
	}

	protected void fireItemSelectionChanged(final CalendarSelectionEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CalendarSelectionListener.class) {
				((CalendarSelectionListener) listeners[i + 1]).itemSelectionChanged(e);
			}
		}
	}

	/*public String toString() { 
	 String result = "[CalendarSelection] = " +
	 day; result += "\n"; result += " items : " + items.length; return result; }
	 */

}