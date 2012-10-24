package window;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * @author idanilov
 *
 */
public interface InsetConstant {

	/**
	 * Insets for controls placing.
	 * Preferred for GridBagLayout.  
	 */
	int TOP_INSET = 5;
	int BOTTOM_INSET = 5;
	int LEFT_INSET = 5;
	int RIGTH_INSET = 5;

	int BETWEEN_INSET = 5;
	int BORDER_MARGIN = 5;

	/**
	 * Default empty border around dialog or internal frame client area.
	 */
	Border DIALOG_CONTENT_BORDER = BorderFactory.createEmptyBorder(BORDER_MARGIN, BORDER_MARGIN,
			BORDER_MARGIN, BORDER_MARGIN);

}
