package DebugGlassPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Can be used to gebug swing components placement.
 * @author swinghacks
 * @author idanilov
 * @jdk 1.5
 *
 */
public class DebugGlassPaneDemo extends JComponent {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Component Boundary Glasspane");
        Container root = frame.getContentPane();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        final JButton activate = new JButton("Show component boundaries");
        root.add(activate);
        root.add(new JLabel("Juice Settings"));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel("Flavor"));
        panel.add(new JTextField("          "));
        root.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        final DebugGlassPaneDemo glass = new DebugGlassPaneDemo(frame);
        frame.setGlassPane(glass);

        activate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                glass.setVisible(true);
            }
        });

    }

    private JFrame frame;
    private Point cursor;

    public DebugGlassPaneDemo(JFrame frame) {
        this.frame = frame;
        cursor = new Point();
        this.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent evt) {
                cursor = new Point(evt.getPoint());
                DebugGlassPaneDemo.this.repaint();
            }

        });
        this.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                DebugGlassPaneDemo.this.setVisible(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cursor = null;
                DebugGlassPaneDemo.this.repaint();
            }
        });
    }

    public void paint(Graphics g) {
        Container root = frame.getContentPane();
        rPaint(root, g);
    }

    private void rPaint(Component comp, Graphics g) {
        if (cursor == null)
            return;
        int x = comp.getX();
        int y = comp.getY();
        g.translate(x, y);
        cursor.translate(-x, -y);

        int w = comp.getWidth();
        int h = comp.getHeight();

        // draw background
        g.setColor(new Color(1.0f, 0.5f, 0.5f, 0.3f));
        g.fillRect(0, 0, w - 1, h - 1);
        g.setColor(Color.red);
        g.drawRect(0, 0, w - 1, h - 1);

        // if the mouse is over this component
        if (comp.contains(cursor)) {
            // draw the text
            String cls_name = comp.getClass().getName();
            FontMetrics fm = g.getFontMetrics();
            int text_width = fm.stringWidth(cls_name);
            int text_height = fm.getHeight();
            int text_ascent = fm.getAscent();

            // draw text background
            g.setColor(new Color(1f, 1f, 1f, 0.7f));
            g.fillRect(0, 0, text_width, text_height);

            g.setColor(Color.white);
            g.drawRect(0, 0, text_width, text_height);

            // draw text
            g.setColor(Color.black);
            g.drawString(cls_name, 0, 0 + text_ascent);
        }

        if (comp instanceof Container) {
            Container cont = (Container) comp;
            for (int i = 0; i < cont.getComponentCount(); i++) {
                Component child = cont.getComponent(i);
                rPaint(child, g);
            }
        }

        cursor.translate(x, y);
        g.translate(-x, -y);
    }

}
