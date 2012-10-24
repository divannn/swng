
import javax.swing.DefaultListModel;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import undoredo.UndoableModel;

/**
 * @author idanilov
 *
 */
public class UndoableListModel extends DefaultListModel
		implements UndoableModel {

	private UndoRedoProgress progress;

	private UndoableEditSupport undoEditSupport;

	public UndoableListModel() {
		progress = new UndoRedoProgress();
		undoEditSupport = new UndoableEditSupport();
	}

	public void addElement(Object object) {
		super.addElement(object);
		if (!progress.isInProgress()) {
			fireUndoableEditHappened(new ListElementEdit(progress, this, size() - 1));
		}
	}

	public void removeElementAt(int i) {
		ListElementEdit le = new ListElementEdit(progress, this, i);
		super.removeElementAt(i);
		if (!progress.isInProgress()) {
			fireUndoableEditHappened(le);
		}
	}

	public void addUndoableEditListener(UndoableEditListener l) {
		undoEditSupport.addUndoableEditListener(l);
	}

	public void removeUndoableEditListener(UndoableEditListener l) {
		undoEditSupport.removeUndoableEditListener(l);
	}

	private void fireUndoableEditHappened(UndoableEdit edit) {
		UndoableEditEvent event = new UndoableEditEvent(this, edit);
		UndoableEditListener[] listeners = undoEditSupport.getUndoableEditListeners();
		for (UndoableEditListener nextListener : listeners) {
			nextListener.undoableEditHappened(event);
		}
	}

}
