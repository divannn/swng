package SeparatorPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * @author idanilov
 * 
 */
public class SeparatorPanel extends JPanel {

	private Component comp;

	public SeparatorPanel(final Component comp) {
		setLayout(new GridBagLayout());
		add(comp, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		add(new JSeparator(), new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
	}

	public Component getComp() {
		return comp;
	}

}
