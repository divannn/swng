package ExpandBar.expandbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author idanilov
 *
 */
class ExpandItemUI extends JPanel {

	private JComponent expandBar;
	private JLabel titleLabel;
	private JLabel toggleLabel;

	private ExpandItem expandItem;

	private static Border EMPTY_BORDER = new EmptyBorder(1, 1, 1, 1);
	private static Border FOCUS_BORDER = UIManager.getBorder("List.focusCellHighlightBorder");

	private static Icon DOWN_ICON = new ImageIcon(ExpandItemUI.class.getResource("down.png"));
	private static Icon UP_ICON = new ImageIcon(ExpandItemUI.class.getResource("up.png"));

	private static Border LEFT_INSET_BORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);

	/**
	 * Listens to the model and updates item UI.
	 */
	private ChangeListener openStateShangeListener;

	private FocusListener focusListener;
	private MouseListener mouseListener;
	private ActionListener toggleListener;

	ExpandItemUI(final ExpandItem item) {
		super(new BorderLayout(0, 2));
		expandItem = item;
		createContents();
		installListeners();
	}

	protected void createContents() {
		expandBar = createExpandBar();
		add(expandBar, BorderLayout.NORTH);
		JComponent c = expandItem.getComponent();
		c.setVisible(false);
		add(c, BorderLayout.CENTER);
	}

	private JComponent createExpandBar() {
		JPanel result = new JPanel(new BorderLayout()) {

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (isOpaque()) {
					int width = getWidth();
					int height = getHeight();
					Graphics2D g2 = (Graphics2D) g;
					Color controlColor = UIManager.getColor("control");
					Paint oldPaint = g2.getPaint();
					g2.setPaint(new GradientPaint(0, 0, controlColor, width, 0, Color.WHITE));
					g2.fillRect(0, 0, width, height);
					g2.setPaint(oldPaint);
				}
			}
		};
		result.setFocusable(true);
		result.setBorder(EMPTY_BORDER);
		titleLabel = new JLabel(expandItem.getTitle(), expandItem.getIcon(), SwingConstants.LEFT);
		titleLabel.setBorder(LEFT_INSET_BORDER);
		result.add(titleLabel, BorderLayout.CENTER);
		toggleLabel = new JLabel(DOWN_ICON);
		result.add(toggleLabel, BorderLayout.EAST);

		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return result;
	}

	protected void installListeners() {
		openStateShangeListener = new ChangeListener() {

			public void stateChanged(ChangeEvent ce) {
				ExpandItem source = (ExpandItem) ce.getSource();
				setExpanded(source.isExpanded());
			}

		};
		expandItem.addStateChangeListener(openStateShangeListener);

		focusListener = new FocusListener() {

			public void focusGained(FocusEvent e) {
				expandBar.setBorder(FOCUS_BORDER);
			}

			public void focusLost(FocusEvent e) {
				expandBar.setBorder(EMPTY_BORDER);
			}

		};
		expandBar.addFocusListener(focusListener);
		mouseListener = new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				if (SwingUtilities.isLeftMouseButton(me)) {
					toggle();
					expandBar.requestFocusInWindow();
				}
			}

		};
		expandBar.addMouseListener(mouseListener);
		toggleListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				toggle();
			}

		};
		expandBar.registerKeyboardAction(toggleListener, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
				0), JComponent.WHEN_FOCUSED);
	}

	protected void uninstallListeners() {
		expandBar.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
		expandBar.removeMouseListener(mouseListener);
		expandBar.removeFocusListener(focusListener);

		expandItem.removeStateChangeListener(openStateShangeListener);
	}

	void dispose() {
		uninstallListeners();
	}

	private void toggle() {
		JComponent c = expandItem.getComponent();
		expandItem.setExpanded(!c.isVisible());
	}

	private void setExpanded(boolean open) {
		if (open) {
			toggleLabel.setIcon(UP_ICON);
			toggleLabel.revalidate();
			toggleLabel.repaint();
			JComponent c = expandItem.getComponent();
			c.setVisible(true);
		} else {
			toggleLabel.setIcon(DOWN_ICON);
			toggleLabel.revalidate();
			toggleLabel.repaint();
			JComponent c = expandItem.getComponent();
			c.setVisible(false);
		}
		//validate();
		//repaint();
	}

}