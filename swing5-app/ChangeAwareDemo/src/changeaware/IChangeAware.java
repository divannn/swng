package changeaware;

import java.awt.Color;

/**
 * Intended for tracking state changes of Swing components.
 * Realizes ñonception of undo with depth = 1.
 * @author idanilov
 */
public interface IChangeAware {

	Color CHANGED_COLOR = new Color(255, 255, 128);
	Color INVALID_COLOR = Color.RED;

	/**
	 * Put <b>true</b> into IChangeAware component to show changes in UI. 
	 * Put <b>null</b> otherwise.
	 */
	String SHOW_CHANGE = "showChange";

	/**
	 * Put <b>true</b> to IChangeAware component to show incorrectness changes in UI.
	 * Put <b>null</b> otherwise. 
	 */
	String SHOW_INCORRECTNESS = "showIncorrectness";

	/**
	 * Save current state of UI control and start tracking changes.
	 */
	void markState();

	/**
	 * Stop tracking changes and clear saved state.
	 */
	void clearState();

	/**
	 * Restore previos saved state.
	 */
	void revertState();

	/**
	 * @return true if current value differs from saved value.
	 */
	boolean isStateChanged();

	/**
	 * @return false if current value is not valid (useful for text components).
	 */
	boolean isStateValid();

	void addStateChangeListener(IStateChangeListener scl);

	void removeStateChangeListener(IStateChangeListener scl);
}
