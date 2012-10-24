package HtmlTooltip;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

/**
 * Can be used to show simpple HTML tooltips (even download html conent from remote server).
 * @author idanilov
 * @jdk 1.5
 *
 */
public class AsyncHtmlTooltipDemo extends JFrame {

    public AsyncHtmlTooltipDemo() {
        super(AsyncHtmlTooltipDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
        //		System.err.println("Initial " + ToolTipManager.sharedInstance().getInitialDelay());
        //		System.err.println("Dismiss " + ToolTipManager.sharedInstance().getDismissDelay());
        //		System.err.println("Reshow " + ToolTipManager.sharedInstance().getReshowDelay());
        //        ToolTipManager.sharedInstance().setInitialDelay(3000);
        //        ToolTipManager.sharedInstance().setReshowDelay(3000);
        //        ToolTipManager.sharedInstance().setDismissDelay(7000);
    }

    private JComponent createContents() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton button = new JButton("Hover mouse over me!") {

            @Override
            public JToolTip createToolTip() {
                HtmlTooltip tip = new HtmlTooltip(this);
                try {
                    HtmlContentLoader helpRetriver = new HtmlContentLoader(tip, getUrl());
                    helpRetriver.go();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tip;
            }

            @Override
            public Point getToolTipLocation(MouseEvent event) {
                return new Point(getWidth() / 2, getHeight() - 2);
            }

        };
        button.setToolTipText("");
        result.add(button);
        JList list = new JList(new String[] { "111111", "222222", "333333" }) {

            @Override
            public JToolTip createToolTip() {
                HtmlTooltip tip = new HtmlTooltip(this);
                try {
                    HtmlContentLoader helpRetriver = new HtmlContentLoader(tip, getUrl());
                    helpRetriver.go();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tip;
            }
        };
        list.setToolTipText("");//set some text to allow tootip to show (despite JList invokes ToolTipManager#registerComponent(...)).
        result.add(new JScrollPane(list));
        //ToolTipManager.sharedInstance().registerComponent(list);
        return result;
    }

    private static URL getUrl() throws IOException {
        return getFileUrl();
        //return getWebUrl();
    }

    private static URL getFileUrl() throws MalformedURLException {
        //        File f = new File("d:\\test.html");
        //        return f.toURI().toURL();
        return AsyncHtmlTooltipDemo.class.getResource("test.html");
    }

    private static URL getWebUrl() throws MalformedURLException, UnsupportedEncodingException {
        String s = escapeSymbols("http://tosqa2.in.devexperts.com:7101/thinkscript/dark/preview/studies/AccumulationSwingIndex.html");
        return new URL(s);
    }

    private static String escapeSymbols(String text) {
        return text.replaceAll("%", "%25").replaceAll(" ", "%20").replaceAll("\\+", "%2B");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new AsyncHtmlTooltipDemo();
        frame.pack();
        frame.setSize(new Dimension(500, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
