package undoredo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;

/**
 * @author idanilov
 *
 */
public class MyUndoManager extends UndoManager {

	private Action undoAction;
	private Action redoAction;

	public MyUndoManager() {
		undoAction = new UndoAction();
		redoAction = new RedoAction();
		stateChanged();
	}

	public Action getUndoAction() {
		return undoAction;
	}

	public Action getRedoAction() {
		return redoAction;
	}

	private class UndoAction extends AbstractAction {

		public UndoAction() {
			super("Undo");
			//putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("undo.gif")));
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
					.getDefaultToolkit().getMenuShortcutKeyMask()));
		}

		public void actionPerformed(ActionEvent ae) {
			undo();
		}
	}

	private class RedoAction extends AbstractAction {

		public RedoAction() {
			super("Redo");
			//putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("redo.gif")));
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit
					.getDefaultToolkit().getMenuShortcutKeyMask()));
		}

		public void actionPerformed(ActionEvent ae) {
			redo();
		}
	}

	public synchronized void undo() {
		super.undo();
		stateChanged();
	}

	public synchronized void redo() {
		super.redo();
		stateChanged();
	}

	public void undoableEditHappened(UndoableEditEvent undoableEditEvent) {
		super.undoableEditHappened(undoableEditEvent);
		stateChanged();
	}

	private void stateChanged() {
		undoAction.setEnabled(canUndo());
		redoAction.setEnabled(canRedo());
	}

}
