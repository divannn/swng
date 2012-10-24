package ShakeDialog;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * @author swinghacks
 * @author idanilov
 * @jdk 1.5
 */
public class ShakeDialogDemo {

	public static final int SHAKE_DISTANCE = 10;
	public static final double SHAKE_CYCLE = 100;
	public static final int SHAKE_DURATION = 1000;
	public static final int SHAKE_UPDATE = 5;
	private JDialog dialog;
	private Point naturalLocation;
	private long startTime;
	private Timer shakeTimer;
	private final double TWO_PI = Math.PI * 2.0;

	public ShakeDialogDemo(final JDialog d) {
		dialog = d;
	}

	public void startShake() {
		naturalLocation = dialog.getLocation();
		startTime = System.currentTimeMillis();
		shakeTimer = new Timer(SHAKE_UPDATE, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//calculate elapsed time.
				long elapsed = System.currentTimeMillis() - startTime;
				//use sin to calculate an x-offset.
				double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
				double angle = waveOffset * TWO_PI;
				//offset the x-location by an amount
				//proportional to the sine, up to shake_distance.
				int shakenX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + naturalLocation.x);
				dialog.setLocation(shakenX, naturalLocation.y);
				dialog.repaint();
				//	 should we stop timer?
				if (elapsed >= SHAKE_DURATION) {
					stopShake();
				}
			}
		});
		shakeTimer.start();
	}

	public void stopShake() {
		shakeTimer.stop();
		dialog.setLocation(naturalLocation);
		dialog.repaint();
	}

	public static void main(String[] args) {
		JOptionPane optPane = new JOptionPane("You've totally screwed up your login\n"
				+ "Go back and do it again... and do you think\n"
				+ "you could remember your password this time?", JOptionPane.ERROR_MESSAGE,
				JOptionPane.OK_OPTION);
		JDialog d = optPane.createDialog(null, "Shakin'!");
		ShakeDialogDemo dec = new ShakeDialogDemo(d);
		d.pack();
		dec.startShake();
		d.setVisible(true);
		//wait forever for a non-null selection.
		while (optPane.getValue() == JOptionPane.UNINITIALIZED_VALUE) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}
		}
		System.exit(0);
	}

}
