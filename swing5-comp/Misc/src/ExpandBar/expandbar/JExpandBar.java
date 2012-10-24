package ExpandBar.expandbar;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.ListModel;

/**
 * @author idanilov
 * <br>
 * TODO: Needs list model listener that updates UI according to model changes (add, remove).
 */
public class JExpandBar {

	private ListModel dataModel;
	private ExpandBarUI ui;

	public JExpandBar() {
		this(new DefaultListModel());
	}

	public JExpandBar(final ListModel m) {
		ui = new ExpandBarUI(this);
		setModel(m);
	}

	void setModel(final ListModel m) {
		if (m == null) {
			throw new IllegalArgumentException("Specify non-null model");
		}
		dataModel = m;
		ui.draw();
	}

	public JComponent getUI() {
		return ui;
	}

	public ListModel getModel() {
		return dataModel;
	}

}
