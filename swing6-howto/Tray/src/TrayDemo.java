import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

/**
 * @author idanilov
 * @jdk 1.6
 */
public class TrayDemo {

	public TrayDemo() {
		create();
	}

	private void create() {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Exiting...");
					System.exit(0);
				}
			};

			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Exit");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);
			ImageIcon icon = new ImageIcon(TrayDemo.class.getResource("window16.gif"));
			final TrayIcon trayIcon = new TrayIcon(icon.getImage(), "Tray Demo", popup);
			trayIcon.setImageAutoSize(true);			
			ActionListener actionListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					//this called on dbl click.
					trayIcon.displayMessage("Action Event", "An Action Event Has Been Performed!",
							TrayIcon.MessageType.INFO);
				}
			};
			trayIcon.addActionListener(actionListener);
			
			MouseListener mouseListener = new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					System.out.println("Tray Icon - Mouse clicked!");
				}

				public void mouseEntered(MouseEvent e) {
					System.out.println("Tray Icon - Mouse entered!");
				}

				public void mouseExited(MouseEvent e) {
					System.out.println("Tray Icon - Mouse exited!");
				}

				public void mousePressed(MouseEvent e) {
					System.out.println("Tray Icon - Mouse pressed!");
				}

				public void mouseReleased(MouseEvent e) {
					System.out.println("Tray Icon - Mouse released!");
				}
			};
			trayIcon.addMouseListener(mouseListener);
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}
		} else {
			System.err.println("Tray is not supported.");
		}
	}

	public static void main(final String[] args) {
		new TrayDemo();
	}

}
