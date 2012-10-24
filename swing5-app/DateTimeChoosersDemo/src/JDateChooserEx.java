import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ChoiceFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

/**
 * This is workaround for #XTSce62679,#XTSce62837.
 * The problem is that the combo chooser used as editor in month spinner is shown 
 * in popup (JDateChooser). I.e. popup in popup. As result we have problems described in requests.
 * Solution is not to use combo editor for month spinner.
 * @author idanilov
 */
public class JDateChooserEx extends JPanel
		implements ActionListener, PropertyChangeListener {

	private static final ImageIcon ICON = new ImageIcon(JDateChooser.class
			.getResource("images/JDateChooserIcon.gif"));

	private JSpinner dateSpinner;
	private JButton calendarButton;
	private JCalendar jcalendar;
	private JPopupMenu popup;

	public JDateChooserEx(final int type) {
		this(new JSpinner(new SpinnerDateModel()), type);
	}

	public JDateChooserEx(final JSpinner spinner, final int type) {
		super(new BorderLayout());
		jcalendar = new JCalendar();
		JSpinner sp = (JSpinner) jcalendar.getMonthChooser().getSpinner();
		prepareMonthSpinner(sp);
		jcalendar.getDayChooser().addPropertyChangeListener(this);
		jcalendar.getDayChooser().setAlwaysFireDayProperty(true); // always fire "day" property even if the user selects the already selected day again

		dateSpinner = spinner;
		DateTimeChooserFactory.getInstance().prepareDateChooser(dateSpinner, type);

		setDate(jcalendar.getDate());
		add(dateSpinner, BorderLayout.CENTER);

		calendarButton = new JButton(ICON);
		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		calendarButton.addActionListener(this);
		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		add(calendarButton, BorderLayout.EAST);

		popup = new JPopupMenu();
		popup.setLightWeightPopupEnabled(true);
		popup.add(jcalendar);
	}

	public JSpinner getSpinner() {
		return dateSpinner;
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		dateSpinner.setEnabled(enabled);
		calendarButton.setEnabled(enabled);
	}

	//3. Set spinner model value (sync. on calendar show).
	public void actionPerformed(ActionEvent e) {
		int x = calendarButton.getWidth() - (int) popup.getPreferredSize().getWidth();
		int y = calendarButton.getY() + calendarButton.getHeight();
		Date d = getDate();
		if (d != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			jcalendar.setCalendar(calendar);
			//    System.err.println("model: " + calendar.getTime().getMonth());
			//update spinner model - bug in JMonthChooser.
			JSpinner mSpinner = (JSpinner) jcalendar.getMonthChooser().getSpinner();
			mSpinner.getModel().setValue(new Double(calendar.get(Calendar.MONTH)));
		}
		popup.show(calendarButton, x, y);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("day")) {
			popup.setVisible(false);
			setDate(jcalendar.getCalendar().getTime());
		} else if (evt.getPropertyName().equals("date")) {
			setDate((Date) evt.getNewValue());
		}
	}

	public Date getDate() {
		return (Date) dateSpinner.getValue();
	}

	public void setDate(Date date) {
		dateSpinner.setValue(date);
		if (getParent() != null) {
			getParent().validate();
		}
	}

	private SpinnerModel createMonthModel() {
		SpinnerNumberModel result = new SpinnerNumberModel(new Double(Calendar.JANUARY),
				new Double(Calendar.JANUARY), new Double(Calendar.DECEMBER), new Double(1));
		return result;
	}

	//4. Replace month spinner editor (throw out combo editor).
	public void prepareMonthSpinner(final JSpinner spinner) {
		spinner.setPreferredSize(new Dimension(100, 20));
		spinner.setModel(createMonthModel());
		DefaultEditor ed = new NumberEditor(spinner);
		spinner.setEditor(ed);
		DefaultFormatterFactory dateFactory = new DefaultFormatterFactory(createMonthFormatter(),
				null, null, null);
		JFormattedTextField dateTextField = ed.getTextField();
		dateTextField.setFormatterFactory(dateFactory);
		dateTextField.setEditable(false);
		dateTextField.setBackground(Color.WHITE);

		spinner.removeChangeListener(jcalendar.getMonthChooser()); // 1. Removed parent item listenr from combo.
		spinner.addChangeListener(new ChangeListener() { //2. Just set month value - not scroll month cyclically (can be done if needed).

					public void stateChanged(ChangeEvent ce) {
						SpinnerNumberModel monthSpinnerModel = (SpinnerNumberModel) ((JSpinner) ce
								.getSource()).getModel();
						int value = monthSpinnerModel.getNumber().intValue();
						jcalendar.getMonthChooser().setMonth(value);
						//System.err.println("==== " + value);
					}
				});
	}

	/**
	 * Maps month number values (java.util.Calendar values actually) to months names. 
	 * @return
	 */
	private NumberFormatter createMonthFormatter() {
		double[] limits = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
		String[] monthNames = dateFormatSymbols.getMonths();
		String[] names = new String[12];
		for (int i = 0; i < names.length; i++) {
			names[i] = monthNames[i];
		}

		ChoiceFormat monthFormat = new ChoiceFormat(limits, names);
		NumberFormatter result = new NumberFormatter(monthFormat);
		result.setAllowsInvalid(false);
		result.setOverwriteMode(true);
		result.setCommitsOnValidEdit(true);
		return result;
	}

}