package HtmlTooltip;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;


import sun.net.www.HeaderParser;

/**
 * @author idanilov
 * 
 */
public class HtmlContentLoader
        implements Runnable {

    private HtmlTooltip ui;
    private URL url;
    Thread t;

    public HtmlContentLoader(HtmlTooltip ui, URL url) {
        this.ui = ui;
        this.url = url;
    }

    public void go() {
        t = new Thread(this);
        t.start();
    }

    public void stop() {
        if (t != null) {
            try {
                t.stop();
            } catch (Throwable t) {
            }
        }
    }

    public void run() {
        try {
            byte[] content = getHtmlContent(url);
            if (content == null) {
                String text = "Could not load page";
                try {
                    content = text.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //System.err.println(new String(content));
            Thread.sleep(500);
            //updateUI(url);
            updateUI(content);
        } catch (InterruptedException e) {
        }
    }

    public static byte[] getHtmlContent(URL url) {
        byte[] result = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(url.openStream());
            result = new byte[is.available()];
            int wasRead = is.read(result);
            //System.err.println("WAS READ: " + wasRead);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

    /*
        private void updateUI(final URL url) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    try {
                        ui.p.setPage(url);
                        ui.getParent().setSize(400, 300);
                        // Component root = SwingUtilities.getRoot(ui.p);
                        // root.setSize(200,100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/

    private void updateUI(final byte[] s) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    ui.textPane.setDocument(createDocument(s));
                } catch (IOException e) {
                    ui.textPane.setText("Cannot load page");
                    e.printStackTrace();
                }
                
                //there are 2 types of popups: light.medium & heavy - see PopupFactory for detail.
                //actually instead of checking these 2 types - ui.getParent() can be used. 
                Component comp = SwingUtilities.getAncestorOfClass(JWindow.class, ui);//heavy weight.
                if (comp instanceof JWindow) {
                    comp.setSize(getSizeForPopup());
                    comp.validate();
                    System.err.println("JWINDOW");
                    return;
                }
                comp = SwingUtilities.getAncestorOfClass(Panel.class, ui);//medium weight.
                //Used when ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
                if (comp instanceof Panel) {
                    comp.setSize(getSizeForPopup());
                    comp.validate();
                    System.err.println("Panel");
                    return;
                }
                comp = SwingUtilities.getAncestorOfClass(JPanel.class, ui);//light weight.
                if (comp instanceof JPanel) {
                    comp.setSize(getSizeForPopup());
                    // ui.getParent().setSize(getSize());
                    System.err.println("JPanel " + ui.textPane.getPreferredSize());
                    return;
                }

            }

        });

    }

    private HTMLDocument createDocument(byte[] content) throws IOException {
        HTMLEditorKit kit = new HTMLEditorKit();
        HTMLDocument result = (HTMLDocument) kit.createDefaultDocument();
        result.setBase(url);
        insertHTML(kit, content, result, null);
        return result;
    }

    //see method JEditorPane#read(InputStream in, Document doc) for details. Code borrowed there.
    private void insertHTML(HTMLEditorKit kit, byte[] content, HTMLDocument doc, String charSet)
            throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        Reader reader = null;
        try {
            reader = charSet == null ? new InputStreamReader(in) : new InputStreamReader(in,
                    charSet);
            kit.read(reader, doc, 0);
        } catch (ChangedCharSetException e) {
            String charSetSpec = e.getCharSetSpec();
            String chSet = null;
            if (e.keyEqualsCharSet())
                chSet = charSetSpec;
            else
                chSet = getCharsetFromContentTypeParameters(charSetSpec);
            try {
                doc.remove(0, doc.getLength());
            } catch (BadLocationException ble) {
            }
            doc.putProperty("IgnoreCharsetDirective", Boolean.valueOf(true));
            //System.err.println("CS :" + chSet);
            if (chSet != null)
                insertHTML(kit, content, doc, chSet);
        } catch (BadLocationException e) {
            throw new IOException(e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }
    }

    private static String getCharsetFromContentTypeParameters(String paramlist) {
        String charset = null;
        try {
            // paramlist is handed to us with a leading ';', strip it.
            int semi = paramlist.indexOf(';');
            if (semi > -1 && semi < paramlist.length() - 1) {
                paramlist = paramlist.substring(semi + 1);
            }
            if (paramlist.length() > 0) {
                // parse the paramlist into attr-value pairs & get the
                // charset pair's value
                HeaderParser hdrParser = new HeaderParser(paramlist);
                charset = hdrParser.findValue("charset");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    private Dimension getSizeForPopup() {
        //Dimension result = ui.p.getPreferredSize();
        Dimension result = new Dimension(300, 250);
        //result.width += ui.getInsets().left + ui.getInsets().right;
        //result.height += ui.getInsets().top + ui.getInsets().bottom;
        return result;
    }

}
