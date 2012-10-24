package calendar.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for all calendar logging activities.
 * @author idanilov
 *
 */
public class CalendarLog {

	private static final String CALENDAR_LOG = "calendar";
	private static final String MODEL_LOG = "calendar.model";
	private static final String UPDATE_LOG = "calendar.update";
	private static final String SELECTION_LOG = "calendar.selection";
	private static final String TRANSFER_LOG = "calendar.transfer";

	//public static final Logger ROOT_LOGGER = Logger.getLogger("");

	/**
	 * Calendar activities.
	 */
	public static final Logger CALENDAR_LOGGER = Logger.getLogger(CALENDAR_LOG);

	/**
	 * Calendar model activities.
	 */
	public static final Logger MODEL_LOGGER = Logger.getLogger(MODEL_LOG);

	/**
	 * Calendar update activities.
	 */
	public static final Logger UPDATE_LOGGER = Logger.getLogger(UPDATE_LOG);

	/**
	 * Calendar selection activities.
	 */
	public static final Logger SELECTION_LOGGER = Logger.getLogger(SELECTION_LOG);

	/**
	 * Calendar transfer (DnD and clipboard) activities.
	 */
	public static final Logger TRANSFER_LOGGER = Logger.getLogger(TRANSFER_LOG);

	private static final ConsoleHandler DEFAULT_HANDLER = new ConsoleHandler();

	static {
		initHandler();

		fineAllLoggers(); //debug by default.

		//offAllLoggers(); 
	}

	/* Default configuration - to avoid using logging.properties file.
	 * Own console handler is used. Parent handlers (default console handler) are disabled. 
	 */
	private static void initHandler() {
		CALENDAR_LOGGER.setUseParentHandlers(false);
		CALENDAR_LOGGER.addHandler(DEFAULT_HANDLER);

		MODEL_LOGGER.setUseParentHandlers(false);
		MODEL_LOGGER.addHandler(DEFAULT_HANDLER);

		UPDATE_LOGGER.setUseParentHandlers(false);
		UPDATE_LOGGER.addHandler(DEFAULT_HANDLER);

		SELECTION_LOGGER.setUseParentHandlers(false);
		SELECTION_LOGGER.addHandler(DEFAULT_HANDLER);

		TRANSFER_LOGGER.setUseParentHandlers(false);
		TRANSFER_LOGGER.addHandler(DEFAULT_HANDLER);
	}

	//debug.
	private static void fineAllLoggers() {
		DEFAULT_HANDLER.setLevel(Level.FINE);

		CALENDAR_LOGGER.setLevel(Level.FINE);
		MODEL_LOGGER.setLevel(Level.FINE);
		UPDATE_LOGGER.setLevel(Level.FINE);
		SELECTION_LOGGER.setLevel(Level.FINE);
		TRANSFER_LOGGER.setLevel(Level.FINE);
	}

	//turn off logging.
	private static void offAllLoggers() {
		DEFAULT_HANDLER.setLevel(Level.OFF);

		CALENDAR_LOGGER.setLevel(Level.OFF);
		MODEL_LOGGER.setLevel(Level.OFF);
		UPDATE_LOGGER.setLevel(Level.OFF);
		SELECTION_LOGGER.setLevel(Level.OFF);
		TRANSFER_LOGGER.setLevel(Level.OFF);
	}

}
