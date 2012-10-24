package WideComboBox;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * @author idanilov
 * 
 */
public class WideComboBox extends JComboBox {

	private boolean layingOut;

	public WideComboBox(final Object items[]) {
		super(items);
	}

	public WideComboBox(Vector items) {
		super(items);
	}

	public WideComboBox(ComboBoxModel aModel) {
		super(aModel);
	}

	public void doLayout() {
		try {
			layingOut = true;
			super.doLayout();
		} finally {
			layingOut = false;
		}
	}

	public Dimension getSize() {
		Dimension dim = super.getSize();
		if (!layingOut) {
			dim.width = Math.max(dim.width, getPreferredSize().width);
		}
		return dim;
	}
	
}
