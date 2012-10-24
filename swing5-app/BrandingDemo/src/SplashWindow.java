import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import window.AbstractWindow;

/**
 * @author idanilov
 *
 */
public class SplashWindow extends AbstractWindow {

	public SplashWindow() {
		super((Frame)null);
		create();
	}
	
	protected JComponent createContents() {
		JComponent result = new JPanel(new BorderLayout());
		result.setBorder(new LineBorder(Color.BLACK,1));
		result.add(createImagePanel(),BorderLayout.CENTER);
		return result;		
	}
	
	private JComponent createImagePanel() {
		URL url = SplashWindow.class.getResource("about.gif");
		ImageIcon image = new ImageIcon(url);
		JLabel result = new JLabel(image);
		return result;
	}
	
}