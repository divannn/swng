import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * @author idanilov
 * @jdk 1.5
 */
public abstract class BrandingDemo {

	private BrandingDemo() {
	}

	public static void main(String[] args) {
		try {
			//setWinLookAndFeel();
			setMetalThemeLookAndFeel();//uncomment to see metal theme demo.
		} catch (Exception e) {
			e.printStackTrace();
		}
		SplashWindow splash = new SplashWindow();
		splash.pack();
		splash.setLocationRelativeTo(null);
		splash.setVisible(true);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			splash.close();
		}
		BrandingFrame f = new BrandingFrame();
		f.setSize(700, 500);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static void setMetalThemeLookAndFeel() throws UnsupportedLookAndFeelException {
		MetalLookAndFeel.setCurrentTheme(new AlcatelDefaultTheme());
		UIManager.setLookAndFeel(new MetalLookAndFeel());
	}

	private static void setWinLookAndFeel() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
}
