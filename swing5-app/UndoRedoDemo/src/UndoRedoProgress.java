/**
 * @author idanilov
 *
 */
public class UndoRedoProgress {

	private boolean progress;

	public void start() {
		progress = true;
	}

	public void stop() {
		progress = false;
	}

	public boolean isInProgress() {
		return progress;
	}
}
