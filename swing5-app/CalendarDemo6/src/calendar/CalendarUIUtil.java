package calendar;

import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * @author idanilov
 *
 */
public final class CalendarUIUtil {

	public static final Border FOCUS_BORDER = UIManager.getDefaults().getBorder(
			"Table.focusCellHighlightBorder");

	public static final Border NON_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

	public CalendarUIUtil() {

	}

}
