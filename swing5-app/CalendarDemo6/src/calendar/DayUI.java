package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import calendar.view.ICalendarDay;

/**
 * @author idanilov
 *
 */
public class DayUI extends JPanel {

	/**
	 * Table index in current viewer. 
	 * @value int 
	 */
	static final String INDEX = "index";

	/**
	 * Associated with table <code>ICalendarDay</code> instance.
	 * @value ICalendarDay
	 */
	public static final String DAY = "day";

	private JLabel dayNameLabel;
	private JScrollPane scrollPane;
	private JTable dayTable;

	private static FocusListener focusUpdater = new TableFocusListener();

	static final Color CELL_COLOR = new Color(255, 255, 192);
	static final Color ADJACENT_CELL_COLOR = new Color(255, 255, 128);

	/**
	 * Used for current day indication (day number).
	 */
	static final Color TODAY_COLOR = Color.RED;
	static final Color NORMAL_DAY_B = new JLabel().getBackground();
	static final Color SELECTED_DAY_B = new JTable().getSelectionBackground();
	static final Color NORMAL_DAY_F = new JLabel().getForeground();
	static final Color SELECTED_DAY_F = new JTable().getSelectionForeground();
	static final Color SPECIAL_DAY_CELL_COLOR = new Color(128, 128, 255);

	private static final Font NORMAL_FONT = new JLabel().getFont();
	/**
	 * Used for current day indication (day number).
	 */
	private static final Font TODAY_FONT = NORMAL_FONT.deriveFont(Font.BOLD);

	private static final Border DAY_FOCUS_BORDER = CalendarUIUtil.FOCUS_BORDER;
	private static final Border DAY_NON_FOCUS_BORDER = CalendarUIUtil.NON_FOCUS_BORDER;

	public DayUI() {
		super(new BorderLayout());
		setBorder(DAY_NON_FOCUS_BORDER);
		dayNameLabel = createDayNameLabel();
		dayTable = createDayTable();
		scrollPane = createTableScrollPane(dayTable);
		add(dayNameLabel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		setFocusable(false);
	}

	protected JLabel createDayNameLabel() {
		JLabel result = new JLabel("", SwingConstants.RIGHT);
		result.setOpaque(true);
		result.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		//result.addMouseListener(dayLabelMouseListener);
		return result;
	}

	protected JTable createDayTable() {
		JTable result = new JTable() {

			//expand table to full viewport hight.
			public boolean getScrollableTracksViewportHeight() {
				if (getParent() instanceof JViewport) {
					return (((JViewport) getParent()).getHeight() > getPreferredSize().height);
				}
				return false;
			}
		};
		//result.putClientProperty(AbstractCalendarViewer.INDEX, new Integer(index));
		//result.putClientProperty(AbstractCalendarViewer.DAY, days[index]);
		result.setTableHeader(null);
		//result.setDefaultRenderer(Object.class, itemRenderer);
		Dimension dim = new Dimension(100, 100);
		result.setPreferredScrollableViewportSize(dim);
		result.addFocusListener(focusUpdater);
		//result.getSelectionModel().addListSelectionListener(itemSelectionListener);

		//up cycle keys.
		result.setFocusTraversalKeys(KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS,
				CalendarUI.UP_CYCLE_KEYS);
		return result;
	}

	protected JScrollPane createTableScrollPane(final JTable table) {
		JScrollPane result = new JScrollPane(table);
		result.setViewportBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		return result;
	}

	JTable getDayTable() {
		return dayTable;
	}

	JLabel getDayNameLabel() {
		return dayNameLabel;
	}

	void setDayLabelText(final String s) {
		dayNameLabel.setText(s);
	}

	void setFocused() {
		if (!dayTable.isFocusOwner()) {
			dayTable.requestFocusInWindow();
		}
	}

	void setDayBackground(final Color c) {
		dayTable.setBackground(c);
		scrollPane.setBackground(c);
	}

	void installKeyboardAction(final Action action, final KeyStroke ks, final int when) {
		dayTable.registerKeyboardAction(action, ks, when);
	}

	void uninstallKeyboardAction(final KeyStroke ks) {
		dayTable.unregisterKeyboardAction(ks);
	}

	void setTransferHandlerObject(final TransferHandler handler) {
		dayTable.setTransferHandler(handler);
	}

	void setDragEnabled(final boolean dragEnabled) {
		dayTable.setDragEnabled(dragEnabled);
	}

	void setData(final ICalendarDay day) {
		CalendarItemTableModel tm = new CalendarItemTableModel(day.getItems());
		dayTable.setModel(tm);
	}

	void setDaySelection(final boolean isSelected, final boolean hasFocus, final boolean isToday) {
		setDayLabelBackround(isSelected);
		setDayLabelForeground(isSelected, isToday);
	}

	private void setDayLabelBackround(final boolean isSelected) {
		Color bc = isSelected ? SELECTED_DAY_B : NORMAL_DAY_B;
		dayNameLabel.setBackground(bc);
	}

	private void setDayLabelForeground(final boolean isSelected, final boolean isToday) {
		if (isToday) {//mark today.
			dayNameLabel.setForeground(TODAY_COLOR);
			dayNameLabel.setFont(TODAY_FONT);
		} else {
			Color fc = isSelected ? SELECTED_DAY_F : NORMAL_DAY_F;
			dayNameLabel.setForeground(fc);
			dayNameLabel.setFont(NORMAL_FONT);
		}
	}

	protected void dispose() {
		dayTable.removeFocusListener(focusUpdater);
	}

	/**
	 * Sets focus border around focused day.
	 * @author idanilov
	 *
	 */
	private static class TableFocusListener
			implements FocusListener {

		public void focusGained(FocusEvent fe) {
			if (!fe.isTemporary()) {
				JComponent c = (JComponent) fe.getComponent();
				JComponent dayUI = (JComponent) SwingUtilities.getAncestorOfClass(DayUI.class, c);
				if (dayUI != null) {
					dayUI.setBorder(DAY_FOCUS_BORDER);
				}
			}
		}

		public void focusLost(FocusEvent fe) {
			if (!fe.isTemporary()) {
				JComponent c = (JComponent) fe.getComponent();
				JComponent dayUI = (JComponent) SwingUtilities.getAncestorOfClass(DayUI.class, c);
				if (dayUI != null) {
					dayUI.setBorder(DAY_NON_FOCUS_BORDER);
				}
			}
		}

	}

}