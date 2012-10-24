package undoredo;

import javax.swing.event.UndoableEditListener;

/**
 * @author idanilov
 *
 */
public interface UndoableModel {

	void addUndoableEditListener(UndoableEditListener listener);

	void removeUndoableEditListener(UndoableEditListener listener);
}
