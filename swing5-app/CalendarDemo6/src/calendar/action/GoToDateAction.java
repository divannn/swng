package calendar.action;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;

import calendar.CalendarSchedule;
import calendar.GoToDateDialog;

/**
 * @author idanilov
 *
 */
public class GoToDateAction extends AbstractCalendarAction {

	public GoToDateAction(final CalendarSchedule calSched) {
		super(calSched, "Go to Date...");
	}

	public void actionPerformed(ActionEvent ae) {
		Window parent = calendarSchedule.getParentWindow();
		GoToDateDialog goToDateDlg = null;
		if (parent instanceof Frame) {
			goToDateDlg = new GoToDateDialog((Frame) parent, calendarSchedule, day);
		} else if (parent instanceof Dialog) {
			goToDateDlg = new GoToDateDialog((Dialog) parent, calendarSchedule, day);
		} else {
			goToDateDlg = new GoToDateDialog((Frame) null, calendarSchedule, day);
		}
		goToDateDlg.pack();
		goToDateDlg.setLocationRelativeTo(null);
		goToDateDlg.open();
	}

}
