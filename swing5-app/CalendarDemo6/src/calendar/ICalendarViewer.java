package calendar;

import java.util.Date;
import java.util.List;

import calendar.model.CalendarRule;
import calendar.view.ICalendarDay;
import calendar.view.ICalendarViewerModel;

/**
 * @author idanilov
 *
 */
public interface ICalendarViewer {

	ICalendarViewerModel getModel();

	ViewerUI getUI();

	String getName();

	String getTitle();

	/**
	 * @param rule
	 * @return list of calendar days that satisfies passed rule 
	 */
	List<ICalendarDay> getDaysForRule(final CalendarRule rule);

	void updateDays();

	void updateDaysContent();

	void updateSelection(final Date oldSelectedDay, final Date newSelectedDay);

}
