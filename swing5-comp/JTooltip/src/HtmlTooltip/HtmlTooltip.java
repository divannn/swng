package HtmlTooltip;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * @author idanilov
 * 
 */
public class HtmlTooltip extends JToolTip {

    JTextPane textPane;

    public HtmlTooltip(JComponent comp) {
        setComponent(comp);
        setLayout(new BorderLayout());
        textPane = new JTextPane();
        textPane.setForeground(getForeground());
        textPane.setBackground(getBackground());
        textPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        textPane.setText("Loading...");
        textPane.addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {

                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    System.err.println(">> link pressed:  " + e.getURL());
                }
            }
        });
        textPane.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                //needed to close tip window when mouse exits it (in case mouse entered to tip window from component).
                ToolTipManager.sharedInstance().mouseExited(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

        });
        add(textPane, BorderLayout.CENTER);
    }

    public HtmlTooltip(JTextPane text) {
        setLayout(new BorderLayout());
        add(text, BorderLayout.CENTER);
        textPane = text;
    }

    // @Override
    // public void setTipText(String tipText) {
    // System.err.println("1 " + tipText);
    // super.setTipText(tipText);
    // }

    @Override
    public Dimension getPreferredSize() {
        //return new Dimension(400, 250);
        return textPane.getPreferredSize();
    }

}