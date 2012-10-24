import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public final class DateTimeChooserFactory {

	private static DateTimeChooserFactory ourInstance;
	private static final String NULL_STRING = "";

	public static final String TIME_MASK_PATTERN = "##:##";
	public static final String DATE_MASK_PATTERN = "##/##/####";
	public static final String TIME_PATTERN = "HH:mm";
	public static final String DATE_PATTERN = "dd/MM/yyyy";

	/**
	 * Common locale independent time format (used for all time controls).  
	 */
	public static final DateFormat TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.SHORT);
	/**
	 * Common locale independent date format (used for all date controls).  
	 */
	public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

	private DateTimeChooserFactory() {
	}

	public static DateTimeChooserFactory getInstance() {
		if (ourInstance == null) {
			ourInstance = new DateTimeChooserFactory();
		}
		return ourInstance;
	}

	public void prepareDateChooser(final JSpinner spinner, final int type) {
		SpinnerDateModel dateModel = new SpinnerDateModel();
		spinner.setModel(dateModel);
		JFormattedTextField dateTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter df = createDateFormatter(false, true, false, false);
		DefaultFormatterFactory dateFactory = new DefaultFormatterFactory(df, null, null, null);
		dateTextField.setFormatterFactory(dateFactory);
		dateTextField.setFocusLostBehavior(type);
		dateTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) df.getFormat()).toPattern());
		adjustFont(dateTextField);
	}

	public void prepareTimeChooser(final JSpinner spinner, final int type, final boolean cyclicTime) {
		SpinnerDateModel timeModel = new SpinnerDateModel();
		if (!cyclicTime) {
			restrictTimeModel(timeModel);
		}
		spinner.setModel(timeModel);
		JFormattedTextField timeTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter tf = createTimeFormatter(false, true, false, false);
		DefaultFormatterFactory timeFactory = new DefaultFormatterFactory(tf, null, null, null);
		timeTextField.setFormatterFactory(timeFactory);
		timeTextField.setFocusLostBehavior(type);
		timeTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) tf.getFormat()).toPattern());
		adjustFont(timeTextField);
	}

	public void prepareAlwaysValidDateChooser(final JSpinner spinner) {
		SpinnerDateModel dateModel = new SpinnerDateModel();
		spinner.setModel(dateModel);
		JFormattedTextField dateTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter df = createDateFormatter(false, false, true, true);
		DefaultFormatterFactory dateFactory = new DefaultFormatterFactory(df, null, null, null);
		dateTextField.setFormatterFactory(dateFactory);
		dateTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) df.getFormat()).toPattern());
		adjustFont(dateTextField);
	}

	public void prepareAlwaysValidTimeChooser(final JSpinner spinner, final boolean cyclic) {
		SpinnerDateModel timeModel = new SpinnerDateModel();
		if (!cyclic) {
			restrictTimeModel(timeModel);
		}
		spinner.setModel(timeModel);
		JFormattedTextField timeTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter tf = createTimeFormatter(false, false, true, true);
		DefaultFormatterFactory timeFactory = new DefaultFormatterFactory(tf, null, null, null);
		timeTextField.setFormatterFactory(timeFactory);
		timeTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) tf.getFormat()).toPattern());
		adjustFont(timeTextField);
	}

	public void prepareMaskDateChooser(final JFormattedTextField chooser) {
		MaskFormatter timeMF;
		try {
			timeMF = new MaskFormatter(DATE_MASK_PATTERN);
			timeMF.setPlaceholderCharacter('_');
			//dateMF.setPlaceholder();
			timeMF.setValidCharacters("0123456789");
			timeMF.setAllowsInvalid(false);
			chooser.setFormatterFactory(new DefaultFormatterFactory(timeMF));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		adjustFont(chooser);
	}

	public void prepareMaskTimeChooser(final JFormattedTextField chooser) {
		MaskFormatter timeMF;
		try {
			timeMF = new MaskFormatter(TIME_MASK_PATTERN);
			timeMF.setPlaceholderCharacter('_');
			//timeMF.setPlaceholder();
			timeMF.setValidCharacters("0123456789");
			timeMF.setAllowsInvalid(false);
			chooser.setFormatterFactory(new DefaultFormatterFactory(timeMF));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		chooser.setColumns(5);
		adjustFont(chooser);
	}

	public void prepareDateChooserForNullSupport(final JSpinner spinner) {
		SpinnerDateModelWithNull dateModel = new SpinnerDateModelWithNull();
		spinner.setModel(dateModel);
		JFormattedTextField dateTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter df = createNullDateFormatter(false, true, false, false);
		DefaultFormatterFactory dateFactory = new DefaultFormatterFactory(df);
		dateTextField.setFormatterFactory(dateFactory);
		//dateTextField.setFocusLostBehavior(type);
		dateTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) df.getFormat()).toPattern());
		adjustFont(dateTextField);
	}

	public void prepareTimeChooserForNullSupport(final JSpinner spinner, final boolean cyclic) {
		SpinnerDateModelWithNull timeModel = new SpinnerDateModelWithNull();
		if (!cyclic) {
			restrictTimeModel(timeModel);
		}
		spinner.setModel(timeModel);
		JFormattedTextField timeTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter tf = createNullTimeFormatter(false, true, false, false);
		DefaultFormatterFactory timeFactory = new DefaultFormatterFactory(tf);
		timeTextField.setFormatterFactory(timeFactory);
		//timeTextField.setFocusLostBehavior(type);
		timeTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) tf.getFormat()).toPattern());
		adjustFont(timeTextField);
	}

	/**
	 * Restrict min and max values for time model. This is used to prevent cyclic scrolling of time.
	 * @param timeModel
	 */
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

	public DateFormatter createTimeFormatter(final boolean isLeninent, final boolean allowsInvalid,
			final boolean overwrite, final boolean commitOnValid) {
		DateFormat timeFormat = (DateFormat) TIME_FORMAT.clone();
		timeFormat.setLenient(isLeninent);
		DateFormatter result = new DateFormatter(timeFormat);
		result.setAllowsInvalid(allowsInvalid);
		result.setOverwriteMode(overwrite);
		result.setCommitsOnValidEdit(commitOnValid);
		return result;
	}

	public DateFormatter createDateFormatter(final boolean isLeninent, final boolean allowsInvalid,
			final boolean overwrite, final boolean commitOnValid) {
		DateFormat dateFormat = (DateFormat) DATE_FORMAT.clone();
		dateFormat.setLenient(isLeninent);
		DateFormatter result = new DateFormatter(dateFormat);
		result.setAllowsInvalid(allowsInvalid);
		result.setOverwriteMode(overwrite);
		result.setCommitsOnValidEdit(commitOnValid);
		return result;
	}

	private DateFormatter createNullDateFormatter(final boolean isLeninent,
			final boolean allowsInvalid, final boolean overwrite, final boolean commitOnValid) {
		DateFormat dateFormat = (DateFormat) DATE_FORMAT.clone();
		dateFormat.setLenient(isLeninent);
		DateFormatter result = new DateFormatter(dateFormat) {

			public Object stringToValue(String text) throws ParseException {
				return NULL_STRING.equals(text) ? null : super.stringToValue(text);
			}

			public String valueToString(Object value) throws ParseException {
				return value == null ? NULL_STRING : super.valueToString(value);
			}
		};
		result.setAllowsInvalid(allowsInvalid);
		result.setOverwriteMode(overwrite);
		result.setCommitsOnValidEdit(commitOnValid);
		return result;
	}

	private DateFormatter createNullTimeFormatter(final boolean isLeninent,
			final boolean allowsInvalid, final boolean overwrite, final boolean commitOnValid) {
		DateFormat dateFormat = (DateFormat) TIME_FORMAT.clone();
		dateFormat.setLenient(isLeninent);
		DateFormatter result = new DateFormatter(dateFormat) {

			public Object stringToValue(String text) throws ParseException {
				return NULL_STRING.equals(text) ? null : super.stringToValue(text);
			}

			public String valueToString(Object value) throws ParseException {
				return value == null ? NULL_STRING : super.valueToString(value);
			}
		};
		result.setAllowsInvalid(allowsInvalid);
		result.setOverwriteMode(overwrite);
		result.setCommitsOnValidEdit(commitOnValid);
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
