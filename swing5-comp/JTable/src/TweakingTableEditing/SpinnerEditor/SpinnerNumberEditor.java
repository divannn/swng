package TweakingTableEditing.SpinnerEditor;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.InternationalFormatter;

/**
 * Editor for numbers.
 * @author idanilov
 *
 */
public class SpinnerNumberEditor extends AbstractSpinnerEditor {

	protected JSpinner createSpinner() {
		return new JSpinner(new SpinnerNumberModel());
	}

	protected void prepareTextField() {
		super.prepareTextField();
		InternationalFormatter f = (InternationalFormatter) getTextField().getFormatter();
		f.setCommitsOnValidEdit(true);//auto commit changes.
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column) {
		//workaround for problem 2). See related SUN bug 6210276. 
		//Otherwise editor is drawn badly when it is invoked by double cliked on up/down buttons area.
		((AbstractTableModel) table.getModel()).fireTableCellUpdated(row, column);
		try {
			Integer i = Integer.valueOf(value.toString());
			spinner.setValue(i);
		} catch (NumberFormatException nfe) {
		}
		//workaround for problem 3). 
		//Request textfield to get focus when editor activated.
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				getTextField().requestFocusInWindow();
			}
		});
		return spinner;
	}

}
