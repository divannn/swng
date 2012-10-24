package calendar;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import window.AbstractDialog;
import calendar.util.DateTimeChooserFactory;
import calendar.view.ICalendarDay;

/**
 * @author idanilov
 *
 */
public class GoToDateDialog extends AbstractDialog {

	private CalendarSchedule calendarSchedule;
	private ICalendarDay day;
	private JSpinner dateSpinner;

	public GoToDateDialog(final Frame parent, final CalendarSchedule calendarSchedule,
			final ICalendarDay day) {
		super(parent);
		configure(calendarSchedule, day);
	}

	public GoToDateDialog(final Dialog parent, final CalendarSchedule calendarSchedule,
			final ICalendarDay day) {
		super(parent);
		configure(calendarSchedule, day);
	}

	private void configure(final CalendarSchedule calSchedule, final ICalendarDay selectedDay) {
		setTitle("Go to Date");
		setModal(true);
		setResizable(false);
		this.calendarSchedule = calSchedule;
		this.day = selectedDay;
		create();
	}

	protected void initDialog() {
		dateSpinner.setValue(day.getDate());
	}

	protected JComponent createClientArea() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createDatePanel(), BorderLayout.NORTH);
		return result;
	}

	private JComponent createDatePanel() {
		JPanel result = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel dateLabel = new JLabel("Date:");
		result.add(dateLabel, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx++;
		gbc.weightx = 1.0;
		gbc.insets.left = 0;
		dateSpinner = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareDateChooser(dateSpinner);
		result.add(dateSpinner, gbc);
		return result;
	}

	protected void clickOnOk() {
		super.clickOnOk();
		Calendar newCalendar = Calendar.getInstance();
		Date selectedDate = (Date) dateSpinner.getValue();
		newCalendar.setTime(selectedDate);
		calendarSchedule.setCalendar(newCalendar);
		//select target day.
		calendarSchedule.getSelectionModel().setSelectedDay(selectedDate);
	}

}
