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
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

import com.sun.java.swing.plaf.windows.WindowsCheckBoxUI;

/**
 * @author idanilov
 */
public class ChangeAwareCheckBox extends JCheckBox
		implements IChangeAware {

	private boolean isChanged;
	private Object stateValue;
	private ItemListener dataChangeListener;
	private Color iconBackgroundColor;

	/**
	 * Taken from WindowsIconFactory#CheckBoxIcon
	 */
	private static final Color NORMAL_COLOR = UIManager.getColor("CheckBox.interiorBackground");

	public ChangeAwareCheckBox() {
		this(null, false);
	}

	public ChangeAwareCheckBox(final String text) {
		this(text, false);
	}

	public ChangeAwareCheckBox(final String text, final boolean isSelected) {
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

	private void refreshVisuals() {
		setColor();
		repaint();
	}

	private void setColor() {
		if (isChanged) {
			iconBackgroundColor = IChangeAware.CHANGED_COLOR;
		} else {
			iconBackgroundColor = NORMAL_COLOR;
		}
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
		setUI(ChangeAwareCheckboxUI.createUI(this));
	}

	private static class ChangeAwareCheckboxUI extends WindowsCheckBoxUI {

		private static final ChangeAwareCheckboxUI UI = new ChangeAwareCheckboxUI();

		public static ComponentUI createUI(JComponent c) {
			return UI;
		}

		public void installDefaults(final AbstractButton ab) {
			super.installDefaults(ab);
			icon = new CheckBoxIcon();
		}

		protected void paintFocus(Graphics g, Rectangle textRect, Dimension size) {
			super.paintFocus(g, textRect, size);
		}
	}

	/**
	 * Copied from WindowsIconFactory#CheckBoxIcon.
	 * Unfortunately impossible to override it.
	 * @author idanilov
	 */
	private static class CheckBoxIcon
			implements Icon {

		private final static int SIZE = 13;

		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			ChangeAwareCheckBox cb = (ChangeAwareCheckBox) c;
			ButtonModel model = cb.getModel();
			// outer bevel
			if (!cb.isBorderPaintedFlat()) {
				// Outer top/left
				g.setColor(UIManager.getColor("CheckBox.shadow"));
				g.drawLine(x, y, x + 11, y);
				g.drawLine(x, y + 1, x, y + 11);

				// Outer bottom/right
				g.setColor(UIManager.getColor("CheckBox.highlight"));
				g.drawLine(x + 12, y, x + 12, y + 12);
				g.drawLine(x, y + 12, x + 11, y + 12);

				// Inner top.left
				g.setColor(UIManager.getColor("CheckBox.darkShadow"));
				g.drawLine(x + 1, y + 1, x + 10, y + 1);
				g.drawLine(x + 1, y + 2, x + 1, y + 10);

				// Inner bottom/right
				g.setColor(UIManager.getColor("CheckBox.light"));
				g.drawLine(x + 1, y + 11, x + 11, y + 11);
				g.drawLine(x + 11, y + 1, x + 11, y + 10);

				// inside box
				paintBackground(g, cb, x, y);
			} else {
				g.setColor(UIManager.getColor("CheckBox.shadow"));
				g.drawRect(x + 1, y + 1, SIZE - 3, SIZE - 3);
				paintBackground(g, cb, x, y);
			}

			if (model.isEnabled()) {
				g.setColor(UIManager.getColor("CheckBox.darkShadow"));
			} else {
				g.setColor(UIManager.getColor("CheckBox.shadow"));
			}

			// paint check
			if (model.isSelected()) {
				g.drawLine(x + 9, y + 3, x + 9, y + 3);
				g.drawLine(x + 8, y + 4, x + 9, y + 4);
				g.drawLine(x + 7, y + 5, x + 9, y + 5);
				g.drawLine(x + 6, y + 6, x + 8, y + 6);
				g.drawLine(x + 3, y + 7, x + 7, y + 7);
				g.drawLine(x + 4, y + 8, x + 6, y + 8);
				g.drawLine(x + 5, y + 9, x + 5, y + 9);
				g.drawLine(x + 3, y + 5, x + 3, y + 5);
				g.drawLine(x + 3, y + 6, x + 4, y + 6);
			}
		}

		private void paintBackground(final Graphics g, final ChangeAwareCheckBox cb, final int x,
				final int y) {
			ButtonModel model = cb.getModel();
			if ((model.isPressed() && model.isArmed()) || !model.isEnabled()) {
				g.setColor(UIManager.getColor("CheckBox.background"));
			} else {
				g.setColor(cb.iconBackgroundColor);
			}
			g.fillRect(x + 2, y + 2, SIZE - 4, SIZE - 4);
		}

		public int getIconWidth() {
			return SIZE;
		}

		public int getIconHeight() {
			return SIZE;
		}
	}

}
