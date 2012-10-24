package undoredo;

import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEdit;

/**
 * @author idanilov
 *
 */
public class SmartUndoManger extends MyUndoManager
		implements Runnable {

	private UndoableEdit edit;

	public void undoableEditHappened(UndoableEditEvent event) {
		if (edit == null) {
			edit = event.getEdit();
			SwingUtilities.invokeLater(this);
		} else if (!edit.addEdit(event.getEdit())) {
			CompoundEdit compoundEdit = new CompoundEdit();
			compoundEdit.addEdit(edit);
			compoundEdit.addEdit(event.getEdit());
			edit = compoundEdit;
		}
	}

	public void run() {
		if (edit instanceof CompoundEdit) {
			CompoundEdit compoundEdit = (CompoundEdit) edit;
			if (compoundEdit.isInProgress()) {
				compoundEdit.end();
			}
		}
		super.undoableEditHappened(new UndoableEditEvent(this, edit));
		edit = null;
	}

}
