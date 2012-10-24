package changeaware;

import java.util.EventObject;

/**
 * @author idanilov
 */
public class StateChangeEvent extends EventObject {

	public StateChangeEvent(final IChangeAware comp) {
		super(comp);
	}

	/** 
	 * @return The IChangeAware whose state was changed.
	 */
	public IChangeAware getSource() {
		return (IChangeAware) super.getSource();
	}
}
