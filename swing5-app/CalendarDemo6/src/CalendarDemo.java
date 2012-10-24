import javax.swing.JComponent;
import javax.swing.UIManager;

import window.AbstractFrame;
import calendar.CalendarSchedule;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class CalendarDemo extends AbstractFrame {

	private CalendarSchedule taskCalendar;

	public CalendarDemo() {
		super("CalendarDemo");
		create();
	}

	protected JComponent createContents() {
		taskCalendar = new CalendarSchedule(this);
		taskCalendar.setCalendarTransferHandler(new TaskTransferHandler(taskCalendar));
		taskCalendar.setActionManager(new TaskActionManager(taskCalendar));
		//taskCalendar.setEditable(false);//uncomment to run calendar in readonly mode.
		//taskCalendar.setDragEnabled(false);//uncomment to forbid DnD calendar items.
		return taskCalendar.getUI();
	}

	public void dispose() {
		taskCalendar.dispose();
		super.dispose();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//JFrame.setDefaultLookAndFeelDecorated(true);
		CalendarDemo f = new CalendarDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
