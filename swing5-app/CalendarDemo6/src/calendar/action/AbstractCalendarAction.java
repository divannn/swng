package calendar.action;

import java.util.Date;

import javax.swing.AbstractAction;

import calendar.CalendarSchedule;
import calendar.model.CalendarSelectionEvent;
import calendar.model.CalendarSelectionListener;
import calendar.model.CalendarSelectionModel;
import calendar.view.ICalendarDay;

/**
 * @author idanilov
 *
 */
public abstract class AbstractCalendarAction extends AbstractAction
		implements ICalendarAction, CalendarSelectionListener {

	/**
	 * Used to filter default table key strokes during restering them from actions.
	 * F.e. filter Ctrl+C (copy),Ctrl+V (paste) 'cause they have set up by table Component UI by default..
	 */
	public static final String FAKE_ACCELERATOR = "fakeAccelerator";

	protected CalendarSchedule calendarSchedule;
	protected ICalendarDay day;
	protected int[] selectedItemIndices;

	public AbstractCalendarAction(final CalendarSchedule calSchedule, final String name) {
		super(name);
		this.calendarSchedule = calSchedule;
		CalendarSelectionModel csm = calendarSchedule.getSelectionModel();
		this.day = calendarSchedule.getUI().getViewer().getModel().getDay(csm.getSelectedDay());
		csm.addCalendarSelectionListener(this);
	}

	public void daySelectionChanged(CalendarSelectionEvent cse) {
		CalendarSelectionModel csm = (CalendarSelectionModel) cse.getSource();
		Date selectedDate = csm.getSelectedDay();
		this.day = calendarSchedule.getUI().getViewer().getModel().getDay(selectedDate);
	}

	public void itemSelectionChanged(CalendarSelectionEvent cse) {
		CalendarSelectionModel csm = (CalendarSelectionModel) cse.getSource();
		int[] selectedItems = csm.getSelectedItems();
		this.selectedItemIndices = selectedItems;
	}

	public void dispose() {
		calendarSchedule.getSelectionModel().removeCalendarSelectionListener(this);
	}
}