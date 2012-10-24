package RichJLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author swinghacks
 * @author idanilov
 * @jdk 1.5
 *
 */
public class RichJLabelDemo extends JLabel {

    private int tracking;

    public RichJLabelDemo(String text, int tracking) {
        super(text);
        this.tracking = tracking;
    }

    private int left_x, left_y, right_x, right_y;
    private Color left_color, right_color;

    public void setLeftShadow(int x, int y, Color color) {
        left_x = x;
        left_y = y;
        left_color = color;
    }

    public void setRightShadow(int x, int y, Color color) {
        right_x = x;
        right_y = y;
        right_color = color;
    }

    public Dimension getPreferredSize() {
        String text = getText();
        FontMetrics fm = this.getFontMetrics(getFont());

        int w = fm.stringWidth(text);
        w += (text.length() - 1) * tracking;
        w += left_x + right_x;

        int h = fm.getHeight();
        h += left_y + right_y;

        return new Dimension(w, h);
    }

    public void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        char[] chars = getText().toCharArray();

        FontMetrics fm = this.getFontMetrics(getFont());
        int h = fm.getAscent();
        LineMetrics lm = fm.getLineMetrics(getText(), g);
        g.setFont(getFont());

        int x = 0;

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            int w = fm.charWidth(ch) + tracking;

            g.setColor(left_color);
            g.drawString("" + chars[i], x - left_x, h - left_y);

            g.setColor(right_color);
            g.drawString("" + chars[i], x + right_x, h + right_y);

            g.setColor(getForeground());
            g.drawString("" + chars[i], x, h);

            x += w;
        }

    }

    public static void main(String[] args) {
        // drop shadow w/ highlight
        RichJLabelDemo label1 = new RichJLabelDemo("76", 0);
        label1.setLeftShadow(1, 1, Color.white);
        label1.setRightShadow(2, 3, Color.black);
        label1.setForeground(Color.gray);
        label1.setFont(label1.getFont().deriveFont(140f));

        // subtle outline
        RichJLabelDemo label2 = new RichJLabelDemo("76", 0);
        label2.setLeftShadow(1, 1, Color.white);
        label2.setRightShadow(1, 1, Color.white);
        label2.setForeground(Color.blue);
        label2.setFont(label2.getFont().deriveFont(140f));

        // 3d letters
        RichJLabelDemo label3 = new RichJLabelDemo("76", 0);
        label3.setLeftShadow(5, 5, Color.white);
        label3.setRightShadow(-3, -3, new Color(0xccccff));
        label3.setForeground(new Color(0x8888ff));
        label3.setFont(label3.getFont().deriveFont(140f));

        JFrame frame = new JFrame("RichJLabel hack");
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.getContentPane().setLayout(new GridLayout(3, 1));
        frame.getContentPane().add(label1);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(label3);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void p(String str) {
        System.out.println(str);
    }
}
