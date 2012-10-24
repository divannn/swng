package calendar.action;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JPopupMenu;

import calendar.CalendarSchedule;
import calendar.view.ICalendarDay;

/**
 * @author idanilov
 *
 */
public abstract class CalendarActionManager {

	protected CalendarSchedule calendarSchedule;
	protected JPopupMenu popupMenu;
	protected List<Action> actions;

	public CalendarActionManager(final CalendarSchedule calendarSchedule) {
		this.calendarSchedule = calendarSchedule;
		actions = new ArrayList<Action>();
		createActions();
	}

	public List<Action> getActions() {
		return actions;
	}

	protected void add(final Action act) {
		actions.add(act);
	}

	public void init(final ICalendarDay day, final int[] selectedItems) {
	}

	/**
	 * Return action that shoul be called when double click on calendar item. 
	 * @return
	 */
	public Action getDefaultAction(final int[] selection) {
		return null;
	}

	public void showPopup(final Component comp, final int x, final int y) {
		if (popupMenu == null) {
			popupMenu = createPopup();
		}
		if ((popupMenu != null) && (popupMenu.getComponentCount() > 0)) {
			popupMenu.show(comp, x, y);
			//need for popup will be shown correctly for the first time.
			popupMenu.pack();
		}
	}

	protected abstract void createActions();

	protected abstract JPopupMenu createPopup();

}
