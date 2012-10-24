package changeaware;

import java.util.HashSet;

/**
 * Cache for union of related UI controls.
 * Generally one instance of ChangeAwareManager should be used in one dialog or frame.
 * Implementor must override stateChanged() to react on changes that apprear in
 * any of registered controls. 
 * @author idanilov
 *
 */
public abstract class ChangeAwareManager
		implements IStateChangeListener {

	protected HashSet<IChangeAware> controls;

	public ChangeAwareManager() {
		controls = new HashSet<IChangeAware>();
	}

	public HashSet<IChangeAware> getControls() {
		return controls;
	}

	public void registerComponent(final IChangeAware changeAware) {
		if (changeAware != null) {
			changeAware.addStateChangeListener(this);
			controls.add(changeAware);
		}
	}

	public void unregisterComponent(final IChangeAware changeAware) {
		if (changeAware != null) {
			changeAware.removeStateChangeListener(this);
			controls.remove(changeAware);
		}
	}

	public void unregisterAll() {
		for (IChangeAware nextControl : controls) {
			nextControl.removeStateChangeListener(this);
		}
		controls.clear();
	}

	public void markConrolsState() {
		for (IChangeAware nextControl : controls) {
			nextControl.markState();
		}
	}

	public void clearConrolsState() {
		for (IChangeAware nextControl : controls) {
			nextControl.clearState();
		}
	}

	public void revertConrolsState() {
		for (IChangeAware nextControl : controls) {
			nextControl.revertState();
		}
	}

	public boolean isSomethingChanged() {
		boolean result = false;
		for (IChangeAware nextControl : controls) {
			result = nextControl.isStateChanged();
			if (result) {
				break;
			}
		}
		return result;
	}

	public boolean isSomethingInvalid() {
		boolean result = false;
		for (IChangeAware nextControl : controls) {
			result = !nextControl.isStateValid();
			if (result) {
				break;
			}
		}
		return result;
	}
}
