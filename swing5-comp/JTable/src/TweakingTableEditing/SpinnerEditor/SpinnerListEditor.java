package TweakingTableEditing.SpinnerEditor;

import java.awt.Component;
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatterFactory;

/**
 * Editor for strings.
 * @author idanilov
 */
public class SpinnerListEditor extends AbstractSpinnerEditor {

	protected JSpinner createSpinner() {
		return new JSpinner(new SpinnerListModel(Level.ALL_LEVELS));
	}

	protected void prepareTextField() {
		super.prepareTextField();
		// formatter to convert SkillLevel to/from String.
		getTextField().setFormatterFactory(new DefaultFormatterFactory(new ListFormatter()));
	}

	public boolean stopCellEditing() {
		//sync. textfield's value. At this moment text may be not commited to value 
		//(if entered by typing - since we don't have autocommit functionality in ListFormatter).
		Object curValue = getTextField().getValue();
		Level levelFromText = new Level(getTextField().getText());
		if (!levelFromText.equals(curValue)) {
			getTextField().setValue(levelFromText);//spinner's value will be update automatically.
		}
		return super.stopCellEditing();
	}

	public Component getTableCellEditorComponent(JTable table, final Object value,
			boolean isSelected, int row, int column) {
		// See related SUN bug 6210276.
		// Otherwise editor is deawn badly when it is invoked by double cliked on up/down buttons area.
		((AbstractTableModel) table.getModel()).fireTableCellUpdated(row, column);

		//spinner.setValue(value);
		getTextField().setValue(value);

		// request textfield to get focus when editor activated.
		// !!! later.
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				getTextField().requestFocusInWindow();
			}
		});
		return spinner;
	}

	/**
	 * Converts strings to {@link Level}.
	 * XXX:Maybe add document filter from {@link JSpinner.ListEditor.LitFormatter}.
	 */
	private static class ListFormatter extends JFormattedTextField.AbstractFormatter {

		public String valueToString(Object value) throws ParseException {
			if (value == null) {
				return "";
			}
			return value.toString();
		}

		public Object stringToValue(String string) throws ParseException {
			return new Level(string);
		}

	}
}
