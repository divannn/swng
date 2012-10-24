package calendar;

import java.awt.ContainerOrderFocusTraversalPolicy;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Action;
import javax.swing.LayoutFocusTraversalPolicy;

import calendar.action.CalendarActionManager;
import calendar.dnd.CalendarTransferHandler;
import calendar.model.CalendarModel;
import calendar.model.CalendarModelEvent;
import calendar.model.CalendarModelListener;
import calendar.model.CalendarSelectionEvent;
import calendar.model.CalendarSelectionListener;
import calendar.model.CalendarSelectionModel;
import calendar.util.CalendarLog;
import calendar.util.CalendarUtil;

/**
 * @author idanilov
 *
 */
public class CalendarSchedule {

	private Window parentWindow;

	private Object input;
	private CalendarModel model;
	private CalendarUI calendarUI;
	private CalendarSelectionModel selectionModel;
	private Calendar calendar;

	private boolean isEditable;
	private boolean isDragEnabled;
	private CalendarActionManager actionManager;
	private CalendarTransferHandler transferHandler;

	private PropertyChangeSupport changeSupport;
	/**
	 * Calendar property name  fired when current calendar (date) is changed. 
	 */
	public static final String CALENDAR_PROPERTY = "calendar";
	public static final String MODEL_PROPERTY = "model";
	private UpdaterHandler updaterHandler;
	private CalendarSelectionListener daySelectionUpdater;

	public CalendarSchedule(final Window parentWindow) {
		super();
		setParentWindow(parentWindow);
		isEditable = true;
		setCalendar(Calendar.getInstance());
		selectionModel = new CalendarSelectionModel();

		calendarUI = createUI();
		updaterHandler = new UpdaterHandler();

		setModel(new CalendarModel());
		calendarUI.init();

		addPropertyChangeListener(updaterHandler);

		daySelectionUpdater = new DaySelectionUpdater();
		selectionModel.addCalendarSelectionListener(daySelectionUpdater);
	}

	private void setParentWindow(Window parentWindow) {
		this.parentWindow = parentWindow;
		//init parent window's focus traversal policy - disable down sycel focus autotransfer.
		FocusTraversalPolicy p = parentWindow.getFocusTraversalPolicy();
		if (p instanceof LayoutFocusTraversalPolicy) {//Swing default policy.
			((LayoutFocusTraversalPolicy) p).setImplicitDownCycleTraversal(false);
		} else if (p instanceof ContainerOrderFocusTraversalPolicy) {//AWT default policy.
			((LayoutFocusTraversalPolicy) p).setImplicitDownCycleTraversal(false);
		}
	}

	public Window getParentWindow() {
		return parentWindow;
	}

	public Calendar getToday() {
		return Calendar.getInstance();
	}

	public void setCalendar(final Calendar calendar) {
		if (calendar == null) {
			throw new IllegalArgumentException("Specify non-null calendar");
		}
		if (!CalendarUtil.equalDays(this.calendar, calendar)) {
			Calendar oldCalendar = this.calendar;
			this.calendar = calendar;
			CalendarLog.CALENDAR_LOGGER.fine("[calendar] Set calendar date: " + calendar.getTime());
			firePropertyChange(CALENDAR_PROPERTY, oldCalendar, this.calendar);
		}
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public CalendarUI getUI() {
		return calendarUI;
	}

	private CalendarUI createUI() {
		return new CalendarUI(this);
	}

	public void setInput(final Object input) {
		this.input = input;
		CalendarLog.CALENDAR_LOGGER.fine("[calendar] Set calendar input: " + input);
	}

	/**
	 * @return main object for wich calendar is indented. 
	 */
	public Object getInput() {
		return input;
	}

	public CalendarModel getModel() {
		return model;
	}

	public void setModel(final CalendarModel model) {
		if (model == null) {
			throw new IllegalArgumentException("Specify non-null calendar model");
		}
		if (this.model != model) {
			CalendarModel oldModel = this.model;
			if (oldModel != null) {
				oldModel.removeCalendarModelListener(updaterHandler);
			}
			this.model = model;
			this.model.addCalendarModelListener(updaterHandler);
			CalendarLog.CALENDAR_LOGGER.fine("[calendar] Set calendar model: " + model);
			firePropertyChange(MODEL_PROPERTY, oldModel, this.model);
		}
	}

	public CalendarSelectionModel getSelectionModel() {
		return selectionModel;
	}

	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * Allows modification from UI side.
	 * @param isEditable true to disable modifications
	 */
	public void setEditable(final boolean isEditable) {
		if (this.isEditable != isEditable) {
			this.isEditable = isEditable;
			if (actionManager != null) {
				List<Action> actions = actionManager.getActions();
				for (Action nextAction : actions) {
					nextAction.setEnabled(isEditable);
				}
			}
		}
	}

	public boolean isDragEnabled() {
		return isDragEnabled;
	}

	public void setDragEnabled(final boolean isDragEnabled) {
		this.isDragEnabled = isDragEnabled;
		//TODO:remake via property change.
		calendarUI.setDragEnabled(isDragEnabled);
	}

	public CalendarActionManager getActionManager() {
		return actionManager;
	}

	public void setActionManager(final CalendarActionManager actionManager) {
		if (actionManager == null) {
			throw new IllegalArgumentException("Specify non-null calendar action manager");
		}
		this.actionManager = actionManager;
		//TODO:remake via property change.
		calendarUI.setActionManager(actionManager);
	}

	public CalendarTransferHandler getCalendarTransferHandler() {
		return transferHandler;
	}

	public void setCalendarTransferHandler(final CalendarTransferHandler cth) {
		if (cth == null) {
			throw new IllegalArgumentException("Specify non-null calendar transfer handler");
		}
		transferHandler = cth;
		//TODO:remake via property change.
		calendarUI.setCalendarTransferHandler(transferHandler);
	}

	/**
	 * Copied from Component.
	 * @param listener
	 */
	public synchronized void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Copied from Component.
	 * @param listener
	 */
	public synchronized void removePropertyChangeListener(final PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Copied from Component.
	 */
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (changeSupport == null
				|| (oldValue != null && newValue != null && oldValue.equals(newValue))) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void dispose() {
		disposeUI();
		calendar = null;
		selectionModel = null;
		actionManager = null;
		transferHandler = null;
		model.removeCalendarModelListener(updaterHandler);
		model = null;
		removePropertyChangeListener(updaterHandler);
	}

	protected void disposeUI() {
		calendarUI.dispose();
	}

	/**
	 * Updates calendar UI. 
	 * @author idanilov
	 *
	 */
	private class UpdaterHandler
			implements CalendarModelListener, PropertyChangeListener {

		public void calendarModelChanged(CalendarModelEvent cme) {
			CalendarLog.UPDATE_LOGGER.fine("[update] content model change");
			getUI().drawDays();
			getUI().drawDaysContent();
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (MODEL_PROPERTY.equals(evt.getPropertyName())) {
				CalendarLog.UPDATE_LOGGER.fine("[update] model change");
				getUI().drawDays();
				getUI().drawDaysContent();
			} else if (CALENDAR_PROPERTY.equals(evt.getPropertyName())) {
				CalendarLog.UPDATE_LOGGER.fine("[update] calendar date change");
				CalendarViewerModel cvm = (CalendarViewerModel)getUI().getViewer().getModel();
				cvm.updateInterval(getCalendar());
				getUI().drawCalendar();
			}
		}

	}

	/**
	 * Updates calendar UI selection.
	 * @author idanilov
	 *
	 */
	private class DaySelectionUpdater
			implements CalendarSelectionListener {

		public void daySelectionChanged(CalendarSelectionEvent cse) {
			CalendarLog.UPDATE_LOGGER.fine("[update] selection change, viewer: "
					+ getUI().getViewer().getName());
			Date selectedDay = getSelectionModel().getSelectedDay();
			getUI().drawSelection(cse.getOldDay(), selectedDay);
		}

		public void itemSelectionChanged(CalendarSelectionEvent cse) {

		}

	}

}
