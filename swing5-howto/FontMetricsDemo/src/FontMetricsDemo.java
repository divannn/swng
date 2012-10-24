import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RectangularShape;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class FontMetricsDemo extends JFrame {

	public FontMetricsDemo() {
		super(FontMetricsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		final JPanel result = new JPanel();
        Font f = new Font("Monospaced", Font.ITALIC, 48);
        result.setFont(f);
        final int XBASE = 50;
        final int YBASE = 100;
        final String test_string = "hqQWpy`/\'|i\\,{_!^";

        JButton button1 = new JButton("Draw text bounds");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = result.getGraphics();
                FontMetrics fm = g.getFontMetrics();
                // draw a text string.
                g.drawString(test_string, XBASE, YBASE);
                // draw a bounding box around it.
                RectangularShape rs = fm.getStringBounds(test_string, g);
                Rectangle r = rs.getBounds();
                g.setColor(Color.RED);
                g.drawRect(XBASE + r.x,YBASE + r.y, r.width, r.height);
            }
        });
        result.add(button1);
        
        JButton button2 = new JButton("Draw text metrics");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = result.getGraphics();
                FontMetrics fm = g.getFontMetrics();
                int ascent = fm.getAscent();
                int descent = fm.getDescent();
                int width = fm.stringWidth(test_string);
                // draw a text string.
                g.drawString(test_string, XBASE, YBASE);
                // draw the ascent line.
                g.setColor(Color.RED);
                g.drawLine(XBASE, YBASE - ascent,XBASE + width, YBASE - ascent);
                // draw the base line.
                g.setColor(Color.GREEN);
                g.drawLine(XBASE, YBASE,XBASE + width, YBASE);
                // draw the descent line.
                g.setColor(Color.BLUE);
                g.drawLine(XBASE, YBASE + descent,XBASE + width, YBASE + descent);
            }
        });
        result.add(button2);
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new FontMetricsDemo();
		f.setSize(600,300);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
