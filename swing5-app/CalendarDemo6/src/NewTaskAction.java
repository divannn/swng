import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import calendar.CalendarSchedule;
import calendar.action.AbstractCalendarAction;

/**
 * @author idanilov
 *
 */
public class NewTaskAction extends AbstractCalendarAction {

	public NewTaskAction(final CalendarSchedule calSched) {
		super(calSched, "New task...");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_DOWN_MASK));
	}

	public void actionPerformed(ActionEvent ae) {
		Window parent = calendarSchedule.getParentWindow();
		NewTaskDialog newDlg = null;
		if (parent instanceof Frame) {
			newDlg = new NewTaskDialog((Frame) parent, calendarSchedule, day, null);
		} else if (parent instanceof Dialog) {
			newDlg = new NewTaskDialog((Dialog) parent, calendarSchedule, day, null);
		} else {
			newDlg = new NewTaskDialog((Frame) null, calendarSchedule, day, null);
		}
		newDlg.pack();
		newDlg.setLocationRelativeTo(null);
		newDlg.open();
	}

}
