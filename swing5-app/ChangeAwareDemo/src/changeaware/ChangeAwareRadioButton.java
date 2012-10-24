package changeaware;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

import com.sun.java.swing.plaf.windows.WindowsRadioButtonUI;

/**
 * @author idanilov
 */
public class ChangeAwareRadioButton extends JRadioButton
		implements IChangeAware {

	private boolean isChanged;
	private Object stateValue;
	private ItemListener dataChangeListener;
	private Color iconBackgroundColor;

	/**
	 * Taken from WindowsIconFactory#RadioButtonIcon
	 */
	private static final Color NORMAL_COLOR = UIManager.getColor("RadioButton.interiorBackground");

	public ChangeAwareRadioButton() {
		this(null, false);
	}

	public ChangeAwareRadioButton(final String text) {
		this(text, false);
	}

	public ChangeAwareRadioButton(final String text, final boolean isSelected) {
		super(text, isSelected);
		setColor();
		dataChangeListener = new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				computeState();
			}
		};
		addItemListener(dataChangeListener);
	}

	public boolean isStateChanged() {
		return isChanged;
	}

	public boolean isStateValid() {
		return true;
	}

	public void markState() {
		stateValue = new Boolean(isSelected());
		isChanged = false;
	}

	public void clearState() {
		stateValue = null;
		isChanged = false;
		refreshVisuals();
	}

	public void revertState() {
		if (isChanged) {
			setSelected(((Boolean) stateValue).booleanValue());
		}
	}

	public void addStateChangeListener(final IStateChangeListener scl) {
		if (scl != null) {
			listenerList.add(IStateChangeListener.class, scl);
		}
	}

	public void removeStateChangeListener(final IStateChangeListener scl) {
		if (scl != null) {
			listenerList.remove(IStateChangeListener.class, scl);
		}
	}

	private void computeState() {
		if (stateValue != null) {
			isChanged = !stateValue.equals(new Boolean(isSelected()));
			refreshVisuals();
			fireStateChangeEvent(new StateChangeEvent(this));
		}
	}

	private void setColor() {
		if (isChanged) {
			iconBackgroundColor = IChangeAware.CHANGED_COLOR;
		} else {
			iconBackgroundColor = NORMAL_COLOR;
		}
	}

	private void refreshVisuals() {
		setColor();
		repaint();
	}

	protected void fireStateChangeEvent(final StateChangeEvent sce) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStateChangeListener.class) {
				IStateChangeListener nextListener = (IStateChangeListener) (listeners[i + 1]);
				nextListener.stateChanged(sce);
			}
		}
	}

	public void updateUI() {
		setUI(ChangeAwareRadioButtonUI.createUI(this));
	}

	private static class ChangeAwareRadioButtonUI extends WindowsRadioButtonUI {

		private static final ChangeAwareRadioButtonUI UI = new ChangeAwareRadioButtonUI();

		public static ComponentUI createUI(JComponent c) {
			return UI;
		}

		public void installDefaults(final AbstractButton ab) {
			super.installDefaults(ab);
			icon = new RadioButtonIcon();
		}

		protected void paintFocus(Graphics g, Rectangle textRect, Dimension size) {
			super.paintFocus(g, textRect, size);
		}
	}

	/**
	 * Copied from WindowsIconFactory#RadioButtonIcon.
	 * Unfortunately impossible to override it.
	 * @author idanilov
	 */
	private static class RadioButtonIcon
			implements Icon {

		private final static int SIZE = 13;

		public void paintIcon(Component c, Graphics g, int x, int y) {
			ChangeAwareRadioButton rb = (ChangeAwareRadioButton) c;
			ButtonModel model = rb.getModel();

			// fill interior
			if ((model.isPressed() && model.isArmed()) || !model.isEnabled()) {
				g.setColor(UIManager.getColor("RadioButton.background"));
			} else {
				g.setColor(rb.iconBackgroundColor);
			}
			g.fillRect(x + 2, y + 2, 8, 8);

			// outter left arc
			g.setColor(UIManager.getColor("RadioButton.shadow"));
			g.drawLine(x + 4, y + 0, x + 7, y + 0);
			g.drawLine(x + 2, y + 1, x + 3, y + 1);
			g.drawLine(x + 8, y + 1, x + 9, y + 1);
			g.drawLine(x + 1, y + 2, x + 1, y + 3);
			g.drawLine(x + 0, y + 4, x + 0, y + 7);
			g.drawLine(x + 1, y + 8, x + 1, y + 9);

			// outter right arc
			g.setColor(UIManager.getColor("RadioButton.highlight"));
			g.drawLine(x + 2, y + 10, x + 3, y + 10);
			g.drawLine(x + 4, y + 11, x + 7, y + 11);
			g.drawLine(x + 8, y + 10, x + 9, y + 10);
			g.drawLine(x + 10, y + 9, x + 10, y + 8);
			g.drawLine(x + 11, y + 7, x + 11, y + 4);
			g.drawLine(x + 10, y + 3, x + 10, y + 2);

			// inner left arc
			g.setColor(UIManager.getColor("RadioButton.darkShadow"));
			g.drawLine(x + 4, y + 1, x + 7, y + 1);
			g.drawLine(x + 2, y + 2, x + 3, y + 2);
			g.drawLine(x + 8, y + 2, x + 9, y + 2);
			g.drawLine(x + 2, y + 3, x + 2, y + 3);
			g.drawLine(x + 1, y + 4, x + 1, y + 7);
			g.drawLine(x + 2, y + 8, x + 2, y + 8);

			// inner right arc
			g.setColor(UIManager.getColor("RadioButton.light"));
			g.drawLine(x + 2, y + 9, x + 3, y + 9);
			g.drawLine(x + 4, y + 10, x + 7, y + 10);
			g.drawLine(x + 8, y + 9, x + 9, y + 9);
			g.drawLine(x + 9, y + 8, x + 9, y + 8);
			g.drawLine(x + 10, y + 7, x + 10, y + 4);
			g.drawLine(x + 9, y + 3, x + 9, y + 3);

			// indicate whether selected or not
			if (model.isSelected()) {
				g.setColor(UIManager.getColor("RadioButton.darkShadow"));
				g.fillRect(x + 4, y + 5, 4, 2);
				g.fillRect(x + 5, y + 4, 2, 4);
			}
		}

		public int getIconWidth() {
			return SIZE;
		}

		public int getIconHeight() {
			return SIZE;
		}
	}

}
