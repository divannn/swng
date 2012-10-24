package WaterMark.layeredpane;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class WatermarkDemo2 extends JFrame {

	public WatermarkDemo2() {
		super(WatermarkDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JWatermark.createWatermark(this, "Hello, I love you!");
        ImageIcon icon = new ImageIcon(WatermarkDemo2.class.getResource("me.jpg"));
        JLabel label = new JLabel(icon);
        result.add(label, BorderLayout.CENTER);
		return result;
	}
	
    public static void main(String[] args) {
        JFrame f = new WatermarkDemo2();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
