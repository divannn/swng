import javax.swing.DefaultListModel;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * @author idanilov
 *
 */
public class ListElementEdit extends AbstractUndoableEdit {

	private DefaultListModel model;
	private int elemIndex;
	private Object element;

	private UndoRedoProgress progress;

	public ListElementEdit(UndoRedoProgress progress, DefaultListModel model, int elemIndex) {
		this.progress = progress;
		this.model = model;
		this.elemIndex = elemIndex;
		element = model.elementAt(elemIndex);
	}

	public void undo() {
		progress.start();
		try {
			super.undo();
			model.removeElementAt(elemIndex);
		} finally {
			progress.stop();
		}
	}

	public void redo() {
		progress.start();
		try {
			super.redo();
			if (elemIndex < model.size()) {
				model.add(elemIndex, element);
			} else {
				model.addElement(element);
			}
		} finally {
			progress.stop();
		}
	}

}
