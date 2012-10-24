package calendar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import calendar.util.CalendarLog;
import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
public class CalendarModel {

	private ArrayList<CalendarTask> tasks;
	private ArrayList<Date> specialDays;

	private Vector<CalendarModelListener> listenersList;

	public CalendarModel() {
		this(new ArrayList<CalendarTask>(), new ArrayList<Date>());
	}

	public CalendarModel(final List<CalendarTask> tasks, final List<Date> specialDays) {
		this.tasks = new ArrayList<CalendarTask>(tasks);
		this.specialDays = new ArrayList<Date>(specialDays);
		listenersList = new Vector<CalendarModelListener>();
	}

	public void addTasks(final List<CalendarTask> tasksToAdd) {
		if (tasksToAdd == null || tasksToAdd.size() == 0) {
			throw new IllegalArgumentException("Provide not null list of tasks");
		}
		boolean wasAdded = false;
		for (CalendarTask nextTask : tasksToAdd) {
			if (!tasks.contains(nextTask)) {
				tasks.add(nextTask);
				wasAdded = true;
				CalendarLog.MODEL_LOGGER.fine("[model] Added task: " + nextTask);
			}
		}
		if (wasAdded) {
			fireModelChanged(new CalendarModelEvent(this));
		}
	}

	public void addTask(final CalendarTask task) {
		addTasks(Collections.singletonList(task));
	}

	public void removeTasks(final List<CalendarTask> tasksToRemove) {
		if (tasksToRemove == null || tasksToRemove.size() == 0) {
			throw new IllegalArgumentException("Provide not null list of tasks");
		}
		boolean wasRemoved = false;
		for (CalendarTask nextTask : tasksToRemove) {
			if (tasks.contains(nextTask)) {
				tasks.remove(nextTask);
				wasRemoved = true;
				CalendarLog.MODEL_LOGGER.fine("[model] Removed task: " + nextTask);
			}
		}
		if (wasRemoved) {
			fireModelChanged(new CalendarModelEvent(this));
		}
	}

	public void removeTask(final CalendarTask task) {
		removeTasks(Collections.singletonList(task));
	}

	public List<CalendarTask> getTasks() {
		return Collections.unmodifiableList(tasks);
	}

	public List<Date> getSpecialDays() {
		return Collections.unmodifiableList(specialDays);
	}

	public void addSpecialDay(final Date sDay) {
		if (sDay == null) {
			throw new IllegalArgumentException("Specify non-null date");
		}
		if (!specialDays.contains(sDay)) {
			specialDays.add(sDay);
			CalendarLog.MODEL_LOGGER.fine("[model] Added special day: " + sDay);
			fireModelChanged(new CalendarModelEvent(this));
		}
	}

	public void removeSpecialDay(final Date sDay) {
		if (specialDays.contains(sDay)) {
			specialDays.remove(sDay);
			CalendarLog.MODEL_LOGGER.fine("[model] Removed special day: " + sDay);
			fireModelChanged(new CalendarModelEvent(this));
		}
	}

	public boolean isSpecial(final Date date) {
		boolean result = false;
		for (Date nextSpecialDay : specialDays) {
			if (CalendarUtil.equalDays(nextSpecialDay, date)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public void addCalendarModelListener(final CalendarModelListener cml) {
		if (cml != null && !listenersList.contains(cml)) {
			listenersList.add(cml);
		}
	}

	public void removeCalendarModelListener(final CalendarModelListener cml) {
		listenersList.remove(cml);
	}

	protected void fireModelChanged(final CalendarModelEvent cme) {
		for (CalendarModelListener nextListener : listenersList) {
			nextListener.calendarModelChanged(cme);
		}
	}

}
