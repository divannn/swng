import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import window.AbstractDialog;
import window.MessageDialog;
import window.MessageDialog.ICON_TYPE;
import calendar.AbstractCalendarViewer;
import calendar.CalendarSchedule;
import calendar.model.CalendarModel;
import calendar.model.CalendarRule;
import calendar.model.CalendarTask;
import calendar.model.OneDayRule;
import calendar.model.WeeklyRule;
import calendar.util.CalendarUtil;
import calendar.util.DateTimeChooserFactory;
import calendar.view.ICalendarDay;
import calendar.view.ICalendarItem;

/**
 * @author idanilov
 *
 */
public class NewTaskDialog extends AbstractDialog {

	private ICalendarDay day;
	private ICalendarItem item;
	private CalendarSchedule calendarSchedule;

	private JSpinner timeChooser;
	private JSpinner dateChooser;
	private JRadioButton specialDayRadioButton;
	private JRadioButton recurrentDayRadioButton;
	private JCheckBox[] dayOfWeekCheckBoxes;
	private ItemListener itemListener;

	public NewTaskDialog(final Frame parent, final CalendarSchedule calendarSchedule,
			final ICalendarDay day, final ICalendarItem item) {
		super(parent);
		configure(calendarSchedule, day, item);
	}

	public NewTaskDialog(final Dialog parent, final CalendarSchedule calSchedule,
			final ICalendarDay selectedFay, final ICalendarItem selectedItem) {
		super(parent);
		configure(calSchedule, selectedFay, selectedItem);
	}

	private void configure(final CalendarSchedule calSchedule, final ICalendarDay selectedDay,
			final ICalendarItem selectedItem) {
		this.calendarSchedule = calSchedule;
		this.day = selectedDay;
		this.item = selectedItem;
		setTitle(isEditMode() ? "Edit Task" : "New Task");
		setModal(true);
		create();
	}

	private boolean isEditMode() {
		return item != null;
	}

	protected void initDialog() {
		super.initDialog();
		if (isEditMode()) {
			CalendarTask task = item.getParentTask();
			CalendarRule rule = task.getRecurrentRule();
			timeChooser.setValue(rule.getTime());
			if (rule instanceof OneDayRule) {
				specialDayRadioButton.setSelected(true);
			} else if (rule instanceof WeeklyRule) {
				recurrentDayRadioButton.setSelected(true);
			}
		} else {
			timeChooser.setValue(new Date());
			CalendarModel cm = calendarSchedule.getModel();
			if (cm.isSpecial(day.getDate())) {
				specialDayRadioButton.setSelected(true);
			} else {
				recurrentDayRadioButton.setSelected(true);
			}
		}
		dateChooser.setValue(day.getDate());
		Calendar tmpCal = Calendar.getInstance();
		tmpCal.setTime(day.getDate());
		selectDay(tmpCal.get(Calendar.DAY_OF_WEEK));
	}

	private void selectDay(final int dayOfWeek) {
		int firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();
		int nextDay = firstDayOfWeek;
		for (int i = 0; i < 7; i++) {
			if (nextDay == dayOfWeek) {
				dayOfWeekCheckBoxes[i].setSelected(true);
			}
			if (nextDay < 7) {
				nextDay++;
			} else {
				nextDay -= 6;
			}
		}
	}

	protected JComponent createClientArea() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createDateTimePanel(), BorderLayout.NORTH);
		result.add(createWeeklyRulePanel(), BorderLayout.CENTER);
		return result;
	}

	private JComponent createDateTimePanel() {
		JPanel result = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel timeLabel = new JLabel("Time:");
		result.add(timeLabel, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx++;
		gbc.weightx = 1.0;
		timeChooser = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareTimeChooser(timeChooser);
		result.add(timeChooser, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0.0;
		specialDayRadioButton = new JRadioButton("Once");
		result.add(specialDayRadioButton, gbc);
		gbc.gridy++;
		gbc.insets.left = 10;

		JLabel dateLabel = new JLabel("Date:");
		result.add(dateLabel, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx++;
		gbc.weightx = 1.0;
		gbc.insets.left = 0;
		dateChooser = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareDateChooser(dateChooser);
		result.add(dateChooser, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0.0;
		recurrentDayRadioButton = new JRadioButton("Recurrent");
		result.add(recurrentDayRadioButton, gbc);

		itemListener = new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				Object source = e.getSource();
				dateChooser.setEnabled(specialDayRadioButton == source);
				for (JCheckBox nextCheckBox : dayOfWeekCheckBoxes) {
					nextCheckBox.setEnabled(recurrentDayRadioButton == source);
				}
			}
		};
		specialDayRadioButton.addItemListener(itemListener);
		recurrentDayRadioButton.addItemListener(itemListener);
		ButtonGroup bg = new ButtonGroup();
		bg.add(specialDayRadioButton);
		bg.add(recurrentDayRadioButton);
		return result;
	}

	private JComponent createWeeklyRulePanel() {
		JPanel result = new JPanel(new GridLayout(2, 5));
		result.setBorder(BorderFactory.createTitledBorder("Weekly"));
		dayOfWeekCheckBoxes = new JCheckBox[7];
		int firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();
		int nextDay = firstDayOfWeek;
		for (int i = 0; i < 7; i++) {
			dayOfWeekCheckBoxes[i] = new JCheckBox(CalendarUtil.getShortDayOfWeekName(nextDay));
			result.add(dayOfWeekCheckBoxes[i]);
			if (nextDay < 7) {
				nextDay++;
			} else {
				nextDay -= 6;
			}
		}
		return result;
	}

	protected void clickOnOk() {
		if (validateControls()) {
			if (isEditMode()) {
				CalendarTask task = item.getParentTask();
				calendarSchedule.getModel().removeTask(task);
			}
			Date time = (Date) timeChooser.getValue();
			if (specialDayRadioButton.isSelected()) {
				OneDayRule oneDayRule = getOneDayRule();
				oneDayRule.setTime(time);
				CalendarTask newTask = new Task();
				newTask.setRecurrentRule(oneDayRule);
				calendarSchedule.getModel().addTask(newTask);
				revealDate(oneDayRule.getDate());
			} else {
				List<CalendarTask> tasksToAdd = new ArrayList<CalendarTask>();
				WeeklyRule realWeekRule = getWeeklyRule();
				//split WeekRule - create WeekRules per day.
				//this done from simplicity - to avoid unclears when cut/copy/paste
				//compound series of recurrent trancition.
				List<Integer> daysOfWeek = realWeekRule.getDaysOfWeek();
				for (Integer nextDay : daysOfWeek) {
					WeeklyRule nextWeekRule = new WeeklyRule();
					nextWeekRule.setTime(time);
					nextWeekRule.addDay(nextDay.intValue());
					CalendarTask newNewTask = new Task();
					newNewTask.setRecurrentRule(nextWeekRule);
					tasksToAdd.add(newNewTask);
				}
				calendarSchedule.getModel().addTasks(tasksToAdd);
			}
			super.clickOnOk();
		}
	}

	public void dispose() {
		specialDayRadioButton.removeItemListener(itemListener);
		recurrentDayRadioButton.removeItemListener(itemListener);
		super.dispose();
	}

	private OneDayRule getOneDayRule() {
		OneDayRule result = new OneDayRule();
		Date date = (Date) dateChooser.getValue();
		result.setDate(date);
		return result;
	}

	private WeeklyRule getWeeklyRule() {
		WeeklyRule result = new WeeklyRule();
		int firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();
		int nextDay = firstDayOfWeek;
		for (int i = 0; i < 7; i++) {
			if (dayOfWeekCheckBoxes[i].isSelected()) {
				result.addDay(nextDay);
			}
			if (nextDay < 7) {
				nextDay++;
			} else {
				nextDay -= 6;
			}
		}
		return result;
	}

	private void revealDate(final Date date) {
		if (date == null) {
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		calendarSchedule.setCalendar(cal);
	}

	private boolean validateControls() {
		boolean result = true;
		result = validateDaySelected();
		if (result) {
			Date time = (Date) timeChooser.getValue();
			result = validateTime(time);
		}
		return result;
	}

	private boolean validateDaySelected() {
		boolean result = true;
		if (!specialDayRadioButton.isSelected()) {
			WeeklyRule weekRule = getWeeklyRule();
			List<Integer> daysOfWeek = weekRule.getDaysOfWeek();
			if (daysOfWeek.size() == 0) {
				MessageDialog.createOkDialog(this, "Warning", "Select day for recurrent item.",
						ICON_TYPE.WARNING).open();
				result = false;
			}
		}
		return result;
	}

	/**
	 * @param time
	 * @return true if there is no items in all days with specified time.
	 */
	private boolean validateTime(final Date time) {
		boolean result = true;
		CalendarRule rule = null;
		if (specialDayRadioButton.isSelected()) {
			rule = getOneDayRule();
		} else {
			rule = getWeeklyRule();
		}
		List<ICalendarDay> days = ((AbstractCalendarViewer) calendarSchedule.getUI().getViewer())
				.getDaysForRule(rule);
		for (ICalendarDay nextDay : days) {
			if (!checkDay(nextDay, time)) {
				MessageDialog.createOkDialog(
						this,
						"Warning",
						"Transition with the same time already exists: "
								+ DateFormat.getTimeInstance(DateFormat.SHORT).format(time) + ".",
						ICON_TYPE.WARNING).open();
				result = false;
				break;
			}
		}
		return result;
	}

	private boolean checkDay(final ICalendarDay d, final Date time) {
		boolean result = true;
		for (ICalendarItem nextItem : d.getItems()) {
			if (isEditMode() && (nextItem == item)) {
				continue;
			}
			Date nextTime = nextItem.getParentTask().getRecurrentRule().getTime();
			if (CalendarUtil.equalTime(time, nextTime)) {
				result = false;
				break;
			}
		}
		return result;
	}

}