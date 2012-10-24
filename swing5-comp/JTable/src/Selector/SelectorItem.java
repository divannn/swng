package Selector;

import javax.swing.Icon;

/**
 * @author idanilov
 *
 */
public class SelectorItem {

	private String name;
	private Icon icon;
	private boolean isEnabled;

	public SelectorItem(final String n, final Icon i) {
		name = n;
		icon = i;
		setEnabled(true);
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(final Icon icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	//TODO: fire event.
	public void setName(final String n) {
		this.name = n;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	//TODO: fire event.
	public void setEnabled(final boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
