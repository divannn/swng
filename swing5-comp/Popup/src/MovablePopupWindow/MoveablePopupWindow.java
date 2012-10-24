package MovablePopupWindow;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * Uded undecorated JDialog as a host.
 * TODO: Consider possibility to use JWindow instead of JDialog.
 * @author idanilov
 * @author TOS
 *
 */
public class MoveablePopupWindow {

    private static final String BORDER_KEY = "ComboBox.popupBorderColor";

    protected final JDialog wnd;
    private final Listener listener = new Listener();
    private Point last_location = new Point();

    private int last_x;
    private int last_y;
    private boolean closed;

    private boolean close_by_timer_enabled;

    private Timer timer;
    private int delay;
    private PopupPanel panel;

    public MoveablePopupWindow(Component parent, PopupPanel panel, String title) {
        this.panel = panel;

        Component ancestor = parent == null ? null : parent instanceof Window ? parent
                : SwingUtilities.getWindowAncestor(parent);
        if (ancestor instanceof Frame)
            wnd = new JDialog((Frame) ancestor);
        else if (ancestor instanceof Dialog)
            wnd = new JDialog((Dialog) ancestor);
        else
            wnd = new JDialog((JFrame) null);
        wnd.setUndecorated(true);

        JPanel content = new JPanel(new BorderLayout());
        if (title != null) {
            JLabel title_label = new JLabel(title);
            title_label.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
            title_label.setForeground(UIManager.getColor("activeCaptionText"));

            JPanel title_panel = new JPanel(new BorderLayout());
            title_panel.setBackground(UIManager.getColor("SectionHeader.Background"));
            title_panel.setBorder(BorderFactory.createLineBorder(UIManager.getColor(BORDER_KEY)));
            title_panel.add(createCloseButton(listener), BorderLayout.EAST);
            title_panel.add(title_label, BorderLayout.WEST);
            content.add(title_panel, BorderLayout.NORTH);
        }

        content.add(panel, BorderLayout.CENTER);
        wnd.getContentPane().setLayout(new BorderLayout());
        wnd.getContentPane().add(content, BorderLayout.CENTER);

        panel.setPopupWindow(this);

        wnd.addMouseMotionListener(listener);
        wnd.addMouseListener(listener);
        wnd.addKeyListener(listener);
        wnd.addWindowListener(listener);
    }

    public void popupOverComponent(Component parent, int x, int y) {
        popupOverComponent(parent, x, y, getPreferredSize());
    }

    public void popupOverComponent(Component parent, int x, int y, Dimension prefSize) {
        popupAt(getFixedLocation(parent, new Point(x, y), prefSize));
    }

    public void popupAt(Point loc) {
        popupAt(loc, true);
    }

    public void popupAt(Point loc, boolean focused) {
        show(loc, focused);
    }

    /**
     * Shows this popup window 'near' specified point. The bounds of enclosing window
     * of specified parent component are used to fit this popup window into the screen.
     */
    public void popupNear(Component parent, int x, int y) {
        popupNear(parent, x, y, SwingUtilities.getRoot(parent));
    }

    public void popupNear(Component parent, int x, int y, Component root) {
        Point p = SwingUtilities.convertPoint(parent, x, y, root);
        Dimension d = getPreferredSize();
        p.x = Math.max(0, Math.min(p.x, root.getWidth() - d.width));
        p.y = Math.max(0, Math.min(p.y, root.getHeight() - d.height));
        SwingUtilities.convertPointToScreen(p, root);
        show(p, true);
    }

    public Dimension getPreferredSize() {
        return wnd.getPreferredSize();
    }

    private void show(Point loc, boolean focused) {
        close_by_timer_enabled = (!wnd.isVisible() || close_by_timer_enabled) && !focused;

        // Note -- for some reason in J2SE 1.3 our own window does not receive "window deactivated"
        // event, so we additionally attach listeners for activation of any other frame.
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            frame.removeWindowListener(listener);
            frame.addWindowListener(listener);
        }

        if (!wnd.isVisible()) {
            last_location = loc;
            wnd.setLocation(loc);
            wnd.pack();
            wnd.setVisible(true);
        }

        if (!focused)
            wnd.setAlwaysOnTop(true);

        if (timer != null)
            timer.stop();
        timer = null;

        if (close_by_timer_enabled) {
            timer = new Timer(delay, listener);
            timer.start();
        }

        closed = false;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void close() {
        if (closed)
            return; // prevent recursive close attempt
        closed = true;

        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames)
            frame.removeWindowListener(listener);

        if (timer != null)
            timer.stop();
        timer = null;

        wnd.setVisible(false);
        wnd.dispose();

        panel.popupWindowClosed();
    }

    public int getX() {
        return last_location.x;
    }

    public int getY() {
        return last_location.y;
    }

    private void processMouseDragged(MouseEvent e) {
        int dx = e.getPoint().x - last_x;
        int dy = e.getPoint().y - last_y;
        last_location = wnd.getLocation();
        last_location.translate(dx, dy);
        wnd.setLocation(last_location);
    }

    private void processMousePressed(MouseEvent e) {
        last_x = e.getPoint().x;
        last_y = e.getPoint().y;
    }

    /**
     * Installs mouse handlers into the given child component, so that the window can be dragged even using
     * area that belongs to that component.
     */
    public void installMouseHandler(Component child) {
        child.addMouseMotionListener(listener);
        child.addMouseListener(listener);
    }

    public static JButton createCloseButton(ActionListener listener) {
        return new IconButton("InternalFrame.closeIcon", /*"InternalFrame.closeIcon"*/
        "InternalFrame.closeIcon", listener);
    }

    public static void setPopupBorder(PopupPanel popupPanel) {
        popupPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor(BORDER_KEY)));
    }

    protected void processWindowActivated(WindowEvent e) {
        // close if other window is activated
        if (e.getSource() != wnd && !close_by_timer_enabled)
            close();
    }

    protected void processWindowDeactivated(WindowEvent e) {
        if (e.getSource() == wnd && !close_by_timer_enabled)
            close();
    }

    private class Listener extends WindowAdapter
            implements MouseMotionListener, MouseListener, KeyListener, ActionListener {

        public void windowActivated(WindowEvent e) {
            processWindowActivated(e);
        }

        public void windowDeactivated(WindowEvent e) {
            processWindowDeactivated(e);
        }

        public void mouseDragged(MouseEvent e) {
            processMouseDragged(SwingUtilities.convertMouseEvent((Component) e.getSource(), e, wnd));
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            close_by_timer_enabled = false;
        }

        public void mousePressed(MouseEvent e) {
            close_by_timer_enabled = false;
            processMousePressed(SwingUtilities.convertMouseEvent((Component) e.getSource(), e, wnd));
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        //worked if only all components in PopupupPanel has no focus.
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                close();
        }

        public void keyReleased(KeyEvent e) {
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == timer) {
                if (close_by_timer_enabled)
                    close();
            } else { // close button
                close();
            }
        }
    }

    private static Point getFixedLocation(Component invoker, Point target, Dimension pref_size) {
        if (invoker == null)
            return target;

        Point p = invoker.isShowing() ? invoker.getLocationOnScreen() : new Point(0, 0);

        int x = target.x + p.x;
        int y = target.y + p.y;

        Container root = (Container) SwingUtilities.getRoot(invoker);
        if (root == null)
            return target;
        Insets insets = root.getInsets();

        int x_right = x + pref_size.width;
        int x_min = root.getX() + insets.left;
        int x_max = root.getX() + root.getWidth() - insets.right;
        if (x_right >= x_max || x < x_min)
            x = Math.max(x - (x_right - x_max), root.getX() + insets.left);

        int y_bottom = y + pref_size.height;
        int y_min = root.getY() + insets.top;
        int y_max = root.getY() + root.getHeight() - insets.bottom;
        if (y_bottom >= y_max || y < y_min)
            y = Math.max(y - pref_size.height, root.getY() + insets.top);

        return new Point(x, y);
    }
}
