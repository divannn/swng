package EDTLockupChecker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author unknown
 * @author idanilov
 * @jdk 1.5
 */
public class EdtLockupChecker extends JFrame {

	public EdtLockupChecker() {
		super(EdtLockupChecker.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();
		JButton button = new JButton("Go");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println("go : " + new Date());
				try {
					//sleep for 10 seconds.
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
				}
			}
		});
		result.add(button);
		return result;
	}

	//install and init the alternative queue.
	private static void initQueue() {
		EventQueueWithWatchdog queue = EventQueueWithWatchdog.getInstance();
		queue.install();
		// install 3-seconds single-shot watchdog timer.
		queue.addWatchdog(3000, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(new Date() + " 3 seconds - single-shot");
			}
		}, false);
		// install 3-seconds multi-shot watchdog timer.
		queue.addWatchdog(3000, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(new Date() + " 3 seconds - multi-shot");
			}
		}, true);
		// install 11-seconds multi-shot watchdog timer.
		queue.addWatchdog(11000, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(new Date() + " 11 seconds - multi-shot");
			}
		}, true);
	}

	public static void main(String[] args) {
		initQueue();
		JFrame f = new EdtLockupChecker();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
