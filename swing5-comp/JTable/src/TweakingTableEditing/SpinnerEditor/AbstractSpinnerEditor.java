package TweakingTableEditing.SpinnerEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.table.TableCellEditor;

/**
 * @author idanilov
 *
 */
public abstract class AbstractSpinnerEditor extends AbstractCellEditor
		implements TableCellEditor, ActionListener {

	protected JSpinner spinner;

	public AbstractSpinnerEditor() {
		spinner = createSpinner();
		spinner.setBorder(BorderFactory.createEmptyBorder());// remove border.
		prepareTextField();
	}

	protected JSpinner createSpinner() {
		return new JSpinner();
	}

	protected void prepareTextField() {
		getTextField().addActionListener(this);
	}

	protected JFormattedTextField getTextField() {
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
		return editor.getTextField();
	}

	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			return (((MouseEvent) anEvent).getClickCount() >= 2);
		}
		return true;
	}

	public Object getCellEditorValue() {
		return spinner.getValue();
	}

	public void actionPerformed(ActionEvent e) {
		//stop editing cell if value in spinner parsed and set succesfully.
		stopCellEditing();
	}

}
