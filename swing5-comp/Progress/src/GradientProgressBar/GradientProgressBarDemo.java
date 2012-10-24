package GradientProgressBar;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class GradientProgressBarDemo extends JDialog {

	public GradientProgressBarDemo() {
		super((Frame) null, GradientProgressBarDemo.class.getSimpleName());
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JComponent result = new JPanel(new BorderLayout());
		result.add(createHorizontalBars(), BorderLayout.SOUTH);
		result.add(createVerticalBars(), BorderLayout.EAST);
		return result;
	}

	private JComponent createHorizontalBars() {
		JComponent result = new JPanel(new BorderLayout());
		result.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.NORTH);
		//standard progress bar.
		JProgressBar defaultBar = new JProgressBar(SwingConstants.HORIZONTAL);
		defaultBar.setBorder(new EmptyBorder(50, 50, 50, 50));//;)) default progress bar does not handle border.
		defaultBar.setIndeterminate(true);
		defaultBar.setString("Default progress bar...");
		defaultBar.setStringPainted(true);
		result.add(defaultBar, BorderLayout.CENTER);
		//gradient progress bar.
		GradientProgressBar gradientBar = new GradientProgressBar(SwingConstants.HORIZONTAL);
		//gradientBar.setBorder(new MatteBorder(50,50,50,50,Color.RED));
		gradientBar.setIndeterminate(true);
		gradientBar.setString("Gradient progress bar...");
		gradientBar.setStringPainted(true);
		result.add(gradientBar, BorderLayout.SOUTH);

		return result;
	}

	private JComponent createVerticalBars() {
		JComponent result = new JPanel(new BorderLayout());
		result.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.WEST);
		//standard progress bar.
		JProgressBar defaultBar = new JProgressBar(SwingConstants.VERTICAL);
		defaultBar.setBorder(new EmptyBorder(50, 50, 50, 50));//;)) default progress bar does not handle border.
		defaultBar.setIndeterminate(true);
		defaultBar.setString("Default progress bar...");
		defaultBar.setStringPainted(true);
		result.add(defaultBar, BorderLayout.CENTER);
		//gradient progress bar.
		GradientProgressBar gradientBar = new GradientProgressBar(SwingConstants.VERTICAL);
		//gradientBar.setBorder(new MatteBorder(50,50,50,50,Color.RED));
		gradientBar.setIndeterminate(true);
		gradientBar.setString("Gradient progress bar...");
		gradientBar.setStringPainted(true);
		result.add(gradientBar, BorderLayout.EAST);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDialog d = new GradientProgressBarDemo();
		d.setModal(true);
		d.pack();
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		d.dispose();
		System.exit(0);
	}

}
