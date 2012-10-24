import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author idanilov
 * @jdk 1.6
 */
public class DesktopDemo extends JFrame {

	private Desktop desktop;
	
	public DesktopDemo() {
		super(DesktopDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		} else {
			System.err.println("Oops...Desktop is not supported.");
		}
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		final JTextField urlTextField = new JTextField("http://", 25);
		result.add(urlTextField,BorderLayout.CENTER);
		JButton b = new JButton("Browse");
		result.add(b,BorderLayout.EAST);
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				String target = urlTextField.getText();
				try {
					URI uri = new URI(target);
					if (desktop != null) {
						desktop.browse(uri);
					}
				} catch (IOException ioe) {
					System.err.println("Cannot load browser:");
					ioe.printStackTrace();
				} catch (URISyntaxException use) {
					System.err.println("URI cannot be parsed:");
					use.printStackTrace();
				}

			}
		});
		return result;
	}

	public static void main(final String[] args) {
		JFrame f = new DesktopDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
