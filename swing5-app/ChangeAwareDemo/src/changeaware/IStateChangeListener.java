package changeaware;

import java.util.EventListener;

/**
 * @author idanilov
 */
public interface IStateChangeListener extends EventListener {

	/**
	 * Called after state of IChangeAware was changed.
	 * @param e
	 */
	void stateChanged(StateChangeEvent e);
	
}
