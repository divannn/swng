package calendar.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 * @author idanilov
 *
 */
public final class DateTimeChooserFactory {

	private static DateTimeChooserFactory ourInstance;

	private DateTimeChooserFactory() {
	}

	public static DateTimeChooserFactory getInstance() {
		if (ourInstance == null) {
			ourInstance = new DateTimeChooserFactory();
		}
		return ourInstance;
	}

	public void prepareDateChooser(final JSpinner spinner) {
		SpinnerDateModel dateModel = new SpinnerDateModel();
		spinner.setModel(dateModel);
		DateFormatter df = createDefaultDateFormatter(false);
		DefaultFormatterFactory dateFactory = new DefaultFormatterFactory(df, null, null, null);
		JFormattedTextField dateTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		dateTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) df.getFormat()).toPattern());
		dateTextField.setFormatterFactory(dateFactory);
		adjustFont(dateTextField);
	}

	public void prepareTimeChooser(final JSpinner spinner) {
		SpinnerDateModel timeModel = new SpinnerDateModel();
		restrictTimeModel(timeModel);
		//timeModel.setCalendarField(Calendar.HOUR_OF_DAY);
		spinner.setModel(timeModel);
		DateFormatter tf = createDefaultTimeFormatter(false);
		DefaultFormatterFactory timeFactory = new DefaultFormatterFactory(tf, null, null, null);
		JFormattedTextField timeTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		timeTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) tf.getFormat()).toPattern());
		timeTextField.setFormatterFactory(timeFactory);
		timeTextField.setColumns(5);
		adjustFont(timeTextField);
	}

	public void restrictTimeModel(final SpinnerDateModel timeModel) {
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(0);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		timeModel.setStart(start.getTime());
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(0);
		end.set(Calendar.HOUR_OF_DAY, end.getMaximum(Calendar.HOUR_OF_DAY));
		end.set(Calendar.MINUTE, end.getMaximum(Calendar.MINUTE));
		timeModel.setEnd(end.getTime());
	}

	private DateFormatter createDefaultTimeFormatter(final boolean isLeninent) {
		DateFormat timeFormat = CalendarUtil.TIME_FORMAT;
		timeFormat.setLenient(isLeninent);
		DateFormatter result = new DateFormatter(timeFormat);
		result.setAllowsInvalid(true);
		result.setOverwriteMode(false);
		result.setCommitsOnValidEdit(false);
		return result;
	}

	private DateFormatter createDefaultDateFormatter(final boolean isLeninent) {
		DateFormat dateFormat = CalendarUtil.DATE_FORMAT;
		dateFormat.setLenient(isLeninent);
		DateFormatter result = new DateFormatter(dateFormat);
		result.setAllowsInvalid(true);
		result.setOverwriteMode(false);
		result.setCommitsOnValidEdit(false);
		return result;
	}

	/**
	 * In Windows L&F JSpinner has a smaller font size then JTextField.
	 * This size applied to its internal JFormattetTextField. Looks not good.   
	 */
	private void adjustFont(final JFormattedTextField ftf) {
		JFormattedTextField defaultFTF = new JFormattedTextField();
		ftf.setFont(defaultFTF.getFont());
	}
}
