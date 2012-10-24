import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * True type fonts can be bundled with app and loaded dinamically.
 * @author idanilov
 * 
 */
public class FontLoadingDemo {

	public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		//just show available fonts in system.
		for (Font f : fonts) {
			System.err.println(f);
		}

		try {
			JLabel plainLabel = new JLabel("Default font - обычный фонт");
			Font font32Pt = plainLabel.getFont().deriveFont(32f);
			plainLabel.setFont(font32Pt);
			InputStream in = FontLoadingDemo.class.getResourceAsStream("comic.ttf");
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
			Font dynamicFont32Pt = dynamicFont.deriveFont(32f);
			// draw something with it
			JLabel testLabel = new JLabel("Dynamically loaded font \"" + dynamicFont.getName()
					+ "\" - загруженный фонт");
			testLabel.setFont(dynamicFont32Pt);
			JFrame frame = new JFrame(FontLoadingDemo.class.getSimpleName());
			frame.getContentPane().add(plainLabel, BorderLayout.NORTH);
			frame.getContentPane().add(testLabel, BorderLayout.SOUTH);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
