import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;

import calendar.CalendarSchedule;
import calendar.action.AbstractCalendarAction;
import calendar.model.CalendarSelectionEvent;
import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public class EditTaskAction extends AbstractCalendarAction {

	public EditTaskAction(final CalendarSchedule calSched) {
		super(calSched, "Open...");
		setEnabled(false);
	}

	public void actionPerformed(ActionEvent ae) {
		ICalendarItem firstSelecteditem = day.getItem(selectedItemIndices[0]);
		Window parent = calendarSchedule.getParentWindow();
		NewTaskDialog newDlg = null;
		if (parent instanceof Frame) {
			newDlg = new NewTaskDialog((Frame) parent, calendarSchedule, day, firstSelecteditem);
		} else if (parent instanceof Dialog) {
			newDlg = new NewTaskDialog((Dialog) parent, calendarSchedule, day, firstSelecteditem);
		} else {
			newDlg = new NewTaskDialog((Frame) null, calendarSchedule, day, firstSelecteditem);
		}
		newDlg.pack();
		newDlg.setLocationRelativeTo(null);
		newDlg.open();
	}

	public void itemSelectionChanged(CalendarSelectionEvent cse) {
		super.itemSelectionChanged(cse);
		setEnabled(selectedItemIndices.length == 1);
	}

}
