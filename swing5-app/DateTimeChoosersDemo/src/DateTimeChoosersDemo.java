import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class DateTimeChoosersDemo extends JFrame {

	private JTabbedPane tabPane;

	private JFormattedTextField timeMaskChooser;
	private JFormattedTextField dateMaskChooser;

	private JSpinner timeChooserCommit;
	private JSpinner dateChooserCommit;
	private JSpinner timeChooserPersist;
	private JSpinner dateChooserPersist;
	private JSpinner timeChooserRevert;
	private JSpinner dateChooserRevert;
	private JSpinner timeChooserCommitOrRevert;
	private JSpinner dateChooserCommitOrRevert;

	private JSpinner alwaysValidTimeChooser;
	private JSpinner alwaysValidDateChooser;

	private JSpinner dateWithNullChooser;
	private JSpinner timeWithNullChooser;
	private JButton setButton, clearButton;

	private JSpinner diffDisplayTimeSpinner;

	//cool controls.
	private ComboTimeChooser comboTimeChooser;
	private JDateChooserEx dateChooserEx;

	public DateTimeChoosersDemo() {
		super(DateTimeChoosersDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		tabPane = new JTabbedPane();
		tabPane.addTab("Mask Choosers", createMaskSpinnersTab());
		tabPane.addTab("Possible Invalid Choosers", createPossibleInvalidSpinnersTab());
		tabPane.addTab("Always Valid Choosers", createAlwaysValidSpinnersTab());
		tabPane.addTab("Choosers with NULL date/time value", createNullableSpinnersTab());
		tabPane.addTab("Different formatters", createDifferentFormattersTab());
		tabPane.addTab("Combo time chooser with autocompletion", createComboTimeChooserTab());
		tabPane.addTab("Improved JCalendar DateEditor", createJCalendarDateChooserTab());
		result.add(tabPane, BorderLayout.CENTER);
		init();
		return result;
	}

	private JComponent createMaskSpinnersTab() {
		JPanel result = new JPanel(new GridLayout(0, 2));
		timeMaskChooser = new JFormattedTextField();
		DateTimeChooserFactory.getInstance().prepareMaskTimeChooser(timeMaskChooser);
		result.add(timeMaskChooser);

		dateMaskChooser = new JFormattedTextField();
		DateTimeChooserFactory.getInstance().prepareMaskDateChooser(dateMaskChooser);
		result.add(dateMaskChooser);
		return result;
	}

	private JPanel createPossibleInvalidSpinnersTab() {
		JPanel result = new JPanel(new GridLayout(4, 4));
		JLabel timeLabelCommit = new JLabel("COMMIT");
		result.add(timeLabelCommit);
		timeChooserCommit = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareTimeChooser(timeChooserCommit,
				JFormattedTextField.COMMIT, false);
		result.add(timeChooserCommit);

		JLabel dateCommitLabel = new JLabel("COMMIT");
		result.add(dateCommitLabel);
		dateChooserCommit = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareDateChooser(dateChooserCommit,
				JFormattedTextField.COMMIT);
		result.add(dateChooserCommit);

		JLabel timeLabelPersist = new JLabel("PERSIST");
		result.add(timeLabelPersist);
		timeChooserPersist = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareTimeChooser(timeChooserPersist,
				JFormattedTextField.PERSIST, false);
		result.add(timeChooserPersist);

		JLabel dateLabelPERSIST = new JLabel("PERSIST");
		result.add(dateLabelPERSIST);
		dateChooserPersist = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareDateChooser(dateChooserPersist,
				JFormattedTextField.PERSIST);
		result.add(dateChooserPersist);

		JLabel timeLabelRevert = new JLabel("REVERT");
		result.add(timeLabelRevert);
		timeChooserRevert = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareTimeChooser(timeChooserRevert,
				JFormattedTextField.REVERT, false);
		result.add(timeChooserRevert);

		JLabel dateLabelREVERT = new JLabel("REVERT");
		result.add(dateLabelREVERT);
		dateChooserRevert = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareDateChooser(dateChooserRevert,
				JFormattedTextField.REVERT);
		result.add(dateChooserRevert);

		JLabel timeLabelCommitOrRevert = new JLabel("COMMIT_OR_REVERT");
		result.add(timeLabelCommitOrRevert);
		timeChooserCommitOrRevert = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareTimeChooser(timeChooserCommitOrRevert,
				JFormattedTextField.COMMIT_OR_REVERT, false);
		result.add(timeChooserCommitOrRevert);

		JLabel dateLabelCommitOrRevert = new JLabel("COMMIT_OR_REVERT");
		result.add(dateLabelCommitOrRevert);
		dateChooserCommitOrRevert = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareDateChooser(dateChooserCommitOrRevert,
				JFormattedTextField.COMMIT_OR_REVERT);
		result.add(dateChooserCommitOrRevert);

		return result;
	}

	private JPanel createAlwaysValidSpinnersTab() {
		JPanel result = new JPanel(new GridLayout(0, 2));
		alwaysValidTimeChooser = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareAlwaysValidTimeChooser(alwaysValidTimeChooser,
				false);
		result.add(alwaysValidTimeChooser);

		alwaysValidDateChooser = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareAlwaysValidDateChooser(alwaysValidDateChooser);
		result.add(alwaysValidDateChooser);
		return result;
	}

	private JPanel createNullableSpinnersTab() {
		JPanel result = new JPanel(new GridLayout(0, 2));
		dateWithNullChooser = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareDateChooserForNullSupport(dateWithNullChooser);
		timeWithNullChooser = new JSpinner();
		DateTimeChooserFactory.getInstance().prepareTimeChooserForNullSupport(timeWithNullChooser,
				false);

		setButton = new JButton("Set");
		setButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dateWithNullChooser.getModel().setValue(new Date());
				timeWithNullChooser.getModel().setValue(new Date());
			}

		});
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clear();
			}

		});
		result.add(dateWithNullChooser);
		result.add(timeWithNullChooser);
		result.add(setButton);
		result.add(clearButton);
		return result;
	}

	private JPanel createDifferentFormattersTab() {
		JPanel result = new JPanel(new GridLayout(0, 2));
		JLabel timeLabel = new JLabel("Time is :");
		result.add(timeLabel);
		diffDisplayTimeSpinner = new JSpinner();
		createTimeChooserWithDiffFormatters(diffDisplayTimeSpinner, JFormattedTextField.COMMIT);
		result.add(diffDisplayTimeSpinner);
		return result;
	}

	private void createTimeChooserWithDiffFormatters(final JSpinner spinner, final int type) {
		SpinnerDateModel timeModel = new SpinnerDateModel();
		spinner.setModel(timeModel);
		JFormattedTextField timeTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter defaultFormatter = DateTimeChooserFactory.getInstance().createTimeFormatter(
				false, true, false, false);

		DateFormatter displayFormatter = new DateFormatter(new SimpleDateFormat("HH 'h', mm 'm'"));
		displayFormatter.setAllowsInvalid(true);
		displayFormatter.setOverwriteMode(false);
		displayFormatter.setCommitsOnValidEdit(false);

		DefaultFormatterFactory timeFactory = new DefaultFormatterFactory(defaultFormatter,
				displayFormatter, defaultFormatter);
		timeTextField.setFormatterFactory(timeFactory);
		timeTextField.setFocusLostBehavior(type);
		timeTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) defaultFormatter.getFormat()).toPattern());
	}

	private JPanel createComboTimeChooserTab() {
		JPanel result = new JPanel(new GridLayout(0, 2));
		JLabel timeLabel = new JLabel("Granulated time:");
		result.add(timeLabel);
		comboTimeChooser = new ComboTimeChooser(15);
		ComboAutoCompletion.install(comboTimeChooser.getCombo(), comboTimeChooser);
		result.add(comboTimeChooser);
		return result;
	}

	private JPanel createJCalendarDateChooserTab() {
		JPanel result = new JPanel(new GridLayout(0, 2));
		JLabel timeLabel = new JLabel("Date:");
		result.add(timeLabel);
		dateChooserEx = new JDateChooserEx(JFormattedTextField.COMMIT);
		result.add(dateChooserEx);
		return result;
	}

	private void init() {
		Calendar sampleCalendar = Calendar.getInstance();
		sampleCalendar.set(Calendar.YEAR, 2006);
		sampleCalendar.set(Calendar.MONTH, Calendar.MARCH);
		sampleCalendar.set(Calendar.DAY_OF_MONTH, 2);
		sampleCalendar.set(Calendar.HOUR_OF_DAY, 17);
		sampleCalendar.set(Calendar.MINUTE, 45);
		sampleCalendar.set(Calendar.SECOND, 0);
		timeMaskChooser.setValue(new SimpleDateFormat(DateTimeChooserFactory.TIME_PATTERN)
				.format(sampleCalendar.getTime()));
		dateMaskChooser.setValue(new SimpleDateFormat(DateTimeChooserFactory.DATE_PATTERN)
				.format(sampleCalendar.getTime()));
		timeChooserCommit.setValue(sampleCalendar.getTime());
		dateChooserCommit.setValue(sampleCalendar.getTime());
		alwaysValidTimeChooser.setValue(sampleCalendar.getTime());
		alwaysValidDateChooser.setValue(sampleCalendar.getTime());
		clear();
	}

	private void clear() {
		dateWithNullChooser.getModel().setValue(null);
		timeWithNullChooser.getModel().setValue(null);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new DateTimeChoosersDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
