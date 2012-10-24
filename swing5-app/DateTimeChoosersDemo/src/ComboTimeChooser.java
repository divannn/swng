import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * @author idanilov
 *
 */
public class ComboTimeChooser extends JSpinner
		implements ChangeListener, ComboAutoCompletion.ILabelProvider {

	private JComboBox comboBox;

	public ComboTimeChooser(final int granularity) {
		this(granularity, null);
	}

	public ComboTimeChooser(final int granularity, final JComboBox combo) {
		super(new GranulatedDateModel(granularity));
		addChangeListener(this);
		if (combo == null) {
			comboBox = new JComboBox();
		} else {
			comboBox = combo;
		}
		prepareCombo(comboBox);
		setEditor(comboBox);
		setValue(comboBox.getModel().getSelectedItem());
	}

	/**
	 * Set selected item in combo and spinner.
	 * Use it instead of this#setValue()
	 * @param d time
	 */
	public void setTime(final Date d) {
		comboBox.setSelectedItem(normalize(d));
	}

	public static Date normalize(final Date date) {
		Calendar sourceCalenar = Calendar.getInstance();
		sourceCalenar.setTime(date);
		Calendar normalizedCalendar = Calendar.getInstance();
		normalizedCalendar.setTimeInMillis(0);
		normalizedCalendar.set(Calendar.HOUR_OF_DAY, sourceCalenar.get(Calendar.HOUR_OF_DAY));
		normalizedCalendar.set(Calendar.MINUTE, sourceCalenar.get(Calendar.MINUTE));
		normalizedCalendar.set(Calendar.SECOND, 0);
		return normalizedCalendar.getTime();
	}

	public JComboBox getCombo() {
		return comboBox;
	}

	private void prepareCombo(final JComboBox combo) {
		combo.setModel(createTimeModel());
		combo.setEditor(new TimeComboBoxEditor());
		combo.setRenderer(new TimeCellRenderer(combo.getRenderer()));
		combo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					JComboBox source = (JComboBox) ie.getSource();
					Date selectedItem = (Date) source.getSelectedItem();
					setValue(selectedItem);
				}
			}
		});
	}

	public void setModel(SpinnerModel model) {
		if (!(model instanceof GranulatedDateModel)) {
			throw new IllegalArgumentException("Model must be instance of "
					+ GranulatedDateModel.class.getName());
		}
		super.setModel(model);
	}

	public String getLabel(final Object o) {
		return getLabel((Date) o);
	}

	public void stateChanged(final ChangeEvent ce) {
		SpinnerDateModel model = (SpinnerDateModel) getModel();
		Date date = model.getDate();
		comboBox.setSelectedItem(date);
		comboBox.repaint();
		repaint();
	}

	private static String getLabel(final Date value) {
		if (value == null) {
			return null;
		}
		return DateTimeChooserFactory.TIME_FORMAT.format(value);
	}

	private ComboBoxModel createTimeModel() {
		int granularity = ((GranulatedDateModel) getModel()).getGranularity();
		Vector<Date> data = new Vector<Date>();
		Calendar cal = Calendar.getInstance();
		for (int j = 0; j < 24; j++) {
			int amount = 60 / granularity;
			for (int i = 0; i < amount; i++) {
				cal.setTimeInMillis(0);
				cal.set(Calendar.HOUR_OF_DAY, j);
				cal.set(Calendar.MINUTE, granularity * i);
				cal.set(Calendar.SECOND, 0);
				Date nextValue = new Date(cal.getTimeInMillis());
				data.add(nextValue);
			}
		}
		DefaultComboBoxModel result = new DefaultComboBoxModel(data);
		return result;
	}

	/** !!! Use delegate in order to not to override rendered in passed combo.*/
	private static class TimeCellRenderer
			implements ListCellRenderer {

		private ListCellRenderer delegate;

		public TimeCellRenderer(final ListCellRenderer delegate) {
			this.delegate = delegate;
		}

		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel result = (JLabel) delegate.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);
			Date date = (Date) value;
			result.setText(getLabel(date));
			return result;
		}

	}

	/**
	 * Needed when combox is editable.
	 * Used to convert Date and String to each other. 
	 * @author idanilov
	 *
	 */
	private static class TimeComboBoxEditor extends BasicComboBoxEditor {

		public Object getItem() {
			String str = editor.getText();
			DateFormat df = DateTimeChooserFactory.TIME_FORMAT;
			df.setLenient(false);
			Date result = null;
			try {
				Date time = df.parse(str);
				result = time;
			} catch (ParseException pe) {
				//no need to habdle.
			}
			return result;
		}

		public void setItem(Object anObject) {
			String str = getLabel((Date) anObject);
			super.setItem(str);
		}

	}

}