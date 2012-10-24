import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Shows non-rectangular (partially transparent) image as a splash screen 
 * and performs some drawing on splash - kind of progress bar.
 * @author idanilov
 * @jdk 1.6
 */
public class SplashScreenDemo extends JFrame {

	public SplashScreenDemo() {
		super(SplashScreenDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel();
		return result;
	}

	private static void renderSplashFrame(Graphics2D g, int frame) {
		String[] status = { "foo", "bar", "baz" };
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(50, 120, 255, 30);
		g.setPaintMode();
		g.setColor(Color.BLACK);
		g.drawString("Loading " + status[(frame / 5) % 3] + "...", 50, 130);
		g.fillRect(50, 140, (frame * 10) % 255, 5);
	}

	public static void main(final String[] args) {
		SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash != null) {
			Graphics2D g = splash.createGraphics();
			if (g != null) {
				for (int i = 0; i < 50; i++) {
					renderSplashFrame(g, i);
					splash.update();
					try {
						Thread.sleep(200);
					} catch (InterruptedException ie) {
					}
				}
				//g.dispose();
				splash.close();
			}
		}

		JFrame f = new SplashScreenDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.toFront();
	}

}
