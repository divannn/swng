package ExpandBar.expandbar;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * @author idanilov
 *
 */
public class ExpandItem {

	private String title;
	private Icon icon;
	private boolean expanded;
	private JComponent comp;

	protected EventListenerList listenerList;

	public ExpandItem(final JComponent c, final String t, final Icon i) {
		comp = c;
		title = t;
		icon = i;
		listenerList = new EventListenerList();
	}

	public String getTitle() {
		return title;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setExpanded(boolean expanded) {
		if (this.expanded != expanded) {
			this.expanded = expanded;
			fireStateChangeEvent(new ChangeEvent(this));
		}
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setComponent(final JComponent comp) {
		this.comp = comp;
	}

	public JComponent getComponent() {
		return comp;
	}

	public void addStateChangeListener(final ChangeListener cl) {
		if (cl != null) {
			listenerList.add(ChangeListener.class, cl);
		}
	}

	public void removeStateChangeListener(final ChangeListener cl) {
		if (cl != null) {
			listenerList.remove(ChangeListener.class, cl);
		}
	}

	protected void fireStateChangeEvent(final ChangeEvent ce) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				ChangeListener nextListener = (ChangeListener) (listeners[i + 1]);
				nextListener.stateChanged(ce);
			}
		}
	}

}
