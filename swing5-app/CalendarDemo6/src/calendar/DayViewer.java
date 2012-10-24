package calendar;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * @author idanilov
 *
 */
public class DayViewer extends AbstractCalendarViewer {

	private static final int ROW = 1;
	private static final int COLUMN = 1;

	public DayViewer(CalendarSchedule calendarSchedule) {
		super(calendarSchedule);
	}

	protected CalendarViewerModel createModel() {
		return new CalendarViewerModel(ROW, COLUMN) {

			public int getInterval() {
				return Calendar.DAY_OF_YEAR;
			}

			protected int getCurrentDayIndex(Calendar calendar) {
				return 0;
			}

			public boolean isViewportFull() {
				return true;
			}

		};
	}

	public String getName() {
		return "DayViewer";
	}

	public String getTitle() {
		Calendar calendar = getCalendarSchedule().getCalendar();
		return DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
	}

}
