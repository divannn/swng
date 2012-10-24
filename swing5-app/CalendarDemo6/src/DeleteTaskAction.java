import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import window.DialogConstant;
import window.MessageDialog;
import calendar.CalendarSchedule;
import calendar.action.AbstractCalendarAction;
import calendar.model.CalendarSelectionEvent;
import calendar.model.CalendarTask;
import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public class DeleteTaskAction extends AbstractCalendarAction {

	private static final ImageIcon ICON = new ImageIcon(DeleteTaskAction.class
			.getResource("delete.gif"));

	public DeleteTaskAction(final CalendarSchedule calSched) {
		super(calSched, "Delete");
		putValue(Action.SMALL_ICON, ICON);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		update();
	}

	public void actionPerformed(ActionEvent e) {
		if (selectedItemIndices.length > 0) {
			String msg = "Delete {0} item(s)?";
			int ret = MessageDialog.createYesNoDialog((Frame) calendarSchedule.getParentWindow(),
					"Confirm",
					MessageFormat.format(msg, new Object[] { "" + selectedItemIndices.length }),
					MessageDialog.ICON_TYPE.ERROR).open();
			if (ret == DialogConstant.YES_ID) {
				List<CalendarTask> tasksToRemove = new ArrayList<CalendarTask>();
				for (int nextItemIndex : selectedItemIndices) {
					ICalendarItem nextItem = day.getItem(nextItemIndex);
					CalendarTask nextTask = nextItem.getParentTask();
					tasksToRemove.add(nextTask);
				}
				calendarSchedule.getModel().removeTasks(tasksToRemove);
			}
		}
	}

	public void itemSelectionChanged(CalendarSelectionEvent cse) {
		super.itemSelectionChanged(cse);
		update();
		//System.err.println("[item change]= " 
		//	+ (day != null ? CalendarUtil.toMediumDate(day.getDate()): "NULL"));
	}

	private void update() {
		boolean canDelete = calendarSchedule.isEditable() && canDelete();
		setEnabled(canDelete);
	}

	private boolean canDelete() {
		return (selectedItemIndices != null && selectedItemIndices.length > 0);
	}

}