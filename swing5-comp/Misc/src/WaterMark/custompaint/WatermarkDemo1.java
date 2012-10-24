package WaterMark.custompaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A demonstration of how one might use WatermarkViewport.
 * @author sun
 * @jdk 1.5
 */
public class WatermarkDemo1 extends JFrame 
		implements ChangeListener {

    private static final String[] bgPainters = new String[] {"custompaint.FillPainter",
                                                             "custompaint.GradientPainter",
                                                             "None"};

    private static final String[] fgPainters = new String[] {"custompaint.StampPainter",
                                                             "custompaint.AnimPainter",
                                                             "None"};

    private JMenu bgMenu = new JMenu("Background");
    private JMenu fgMenu = new JMenu("Foreground");

    private JTabbedPane tp = new JTabbedPane();

    public WatermarkDemo1() {
        super(WatermarkDemo1.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());

        JScrollPane sp;
        WatermarkViewport vp;
        WatermarkPainter bgPainter;
        WatermarkPainter fgPainter;

        sp = new JScrollPane();
        bgPainter = new FillPainter();
        fgPainter = new StampPainter();
        vp = new WatermarkViewport(bgPainter, fgPainter);
        vp.setView(createTable());
        sp.setViewport(vp);

        tp.addTab("Table", sp);
        configureMenuFromPainter(bgMenu, bgPainter, bgPainters.length);
        configureMenuFromPainter(fgMenu, fgPainter, fgPainters.length);

        sp = new JScrollPane();
        bgPainter = new FillPainter();
        fgPainter = new StampPainter();
        vp = new WatermarkViewport(bgPainter, fgPainter);
        vp.setView(createTextArea());
        sp.setViewport(vp);

        tp.addTab("Text Area", sp);

        getContentPane().add(tp, BorderLayout.CENTER);
        
        tp.addChangeListener(this);
    }
    
    private void configureMenuFromPainter(JMenu menu, WatermarkPainter painter, int count) {
        for (int i = menu.getItemCount() - 1; i > count - 1; i--) {
            menu.remove(i);
        }
        if (painter == null) {
            return;
        }
        String[] commands = painter.getCommands();
        if (commands == null || commands.length == 0) {
            return;
        }
        JMenu options = new JMenu("Options");
        JMenuItem item;
        
        for (int i = 0; i < commands.length; i++) {
        	item = new JMenuItem(commands[i]);
            options.add(item);
            item.addActionListener(painter);
        }
        
        menu.add(new JSeparator());
        menu.add(options);
    }
    
    public void stateChanged(ChangeEvent ce) {
        JScrollPane sp = (JScrollPane)tp.getSelectedComponent();
        if (sp == null) {
            return;
        }
        WatermarkViewport vp = (WatermarkViewport)sp.getViewport();
        configureMenuFromPainter(bgMenu, vp.getBackgroundPainter(), bgPainters.length);
        configureMenuFromPainter(fgMenu, vp.getForegroundPainter(), fgPainters.length);
    }

    public static void main(String [] args) {
        JFrame f = new WatermarkDemo1();
        f.setSize(590, 510);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenuItem item;

        JMenu fileMenu = new JMenu("File");
        item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        fileMenu.add(item);
        bar.add(fileMenu);
        
        ActionListener colorListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JScrollPane sp = (JScrollPane)tp.getSelectedComponent();

                if (sp == null) {
                    return;
                }

                JComponent comp = (JComponent)sp.getViewport().getView();

                String command = ae.getActionCommand();

                if (command.equals("Black")) {
                    comp.setForeground(Color.BLACK);
                } else if (command.equals("Red")) {
                    comp.setForeground(Color.RED);
                } else if (command.equals("Yellow")) {
                    comp.setForeground(Color.YELLOW);
                } else if (command.equals("White")) {
                    comp.setForeground(Color.WHITE);
                } else if (command.equals("Blue")) {
                    comp.setForeground(Color.BLUE);
                }
            }
        };

        ActionListener bgListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JScrollPane sp = (JScrollPane)tp.getSelectedComponent();

                if (sp == null) {
                    return;
                }
                
                WatermarkPainter painter = null;
                
                String command = ae.getActionCommand();
                if (!command.equals("None")) {
                    try {
                        painter = (WatermarkPainter)Class.forName(command).newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                WatermarkViewport vp = (WatermarkViewport)sp.getViewport();
                vp.setBackgroundPainter(painter);
                configureMenuFromPainter(bgMenu, painter, bgPainters.length);
            }
        };

        ActionListener fgListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JScrollPane sp = (JScrollPane)tp.getSelectedComponent();

                if (sp == null) {
                    return;
                }
                
                WatermarkPainter painter = null;
                
                String command = ae.getActionCommand();
                if (!command.equals("None")) {
                    try {
                        painter = (WatermarkPainter)Class.forName(command).newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                WatermarkViewport vp = (WatermarkViewport)sp.getViewport();
                vp.setForegroundPainter(painter);
                configureMenuFromPainter(fgMenu, painter, fgPainters.length);
            }
        };

        for (int i = 0; i < bgPainters.length; i++) {
            bgMenu.add(item = new JMenuItem(bgPainters[i]));
            item.addActionListener(bgListener);
        }
        bar.add(bgMenu);

        for (int i = 0; i < fgPainters.length; i++) {
            fgMenu.add(item = new JMenuItem(fgPainters[i]));
            item.addActionListener(fgListener);
        }
        bar.add(fgMenu);
        
        JMenu colorMenu = new JMenu("Text Color");
        colorMenu.add(item = new JMenuItem("Black"));
        item.addActionListener(colorListener);
        colorMenu.add(item = new JMenuItem("Red"));
        item.addActionListener(colorListener);
        colorMenu.add(item = new JMenuItem("Yellow"));
        item.addActionListener(colorListener);
        colorMenu.add(item = new JMenuItem("White"));
        item.addActionListener(colorListener);
        colorMenu.add(item = new JMenuItem("Blue"));
        item.addActionListener(colorListener);
        bar.add(colorMenu);
        
        return bar;
    }

    private JTextArea createTextArea() {
        JTextArea ta = new JTextArea();
        ta.setOpaque(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        ta.setForeground(Color.BLUE);
        
        // set this text area's selection color to be a translucent
        // version of its default selection color
        Color oldCol = ta.getSelectionColor();
        Color newCol = new Color(oldCol.getRed(), oldCol.getGreen(), oldCol.getBlue(), 128);
        ta.setSelectionColor(newCol);
        readFileIntoTextArea("text", ta);

        return ta;
    }
    
    private static void readFileIntoTextArea(String file, JTextArea ta) {
        try {
            InputStream stream = WatermarkDemo1.class.getResource(file).openStream();
            InputStreamReader reader = new InputStreamReader(stream);
            ta.append(file);
            ta.append("\n----------------------\n");
            ta.getUI().getEditorKit(ta).read(reader, ta.getDocument(), ta.getDocument().getLength());
            ta.append("\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JTable createTable() {
        String[] names = new String[] {"Column 1", "Column 2", "Column 3"};
        Object[][] values = new Object[][] {{"zero", new Integer(0), Boolean.FALSE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"zero", new Integer(0), Boolean.FALSE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"zero", new Integer(0), Boolean.FALSE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"zero", new Integer(0), Boolean.FALSE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"zero", new Integer(0), Boolean.FALSE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE},
                                            {"zero", new Integer(0), Boolean.FALSE},
                                            {"one", new Integer(1), Boolean.TRUE},
                                            {"two", new Integer(2), Boolean.TRUE},
                                            {"three", new Integer(3), Boolean.TRUE}};

        final JTable table = new JTable(values, names);
        table.setOpaque(false);
        table.setForeground(Color.BLUE);
        table.getTableHeader().setReorderingAllowed(false);

        // create a custom renderer that paints selected cells translucently
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            boolean isSelected = false;
            Color selectionColor;

            {
                // we'll use a translucent version of the table's default
                // selection color to paint selections
                Color oldCol = table.getSelectionBackground();
                selectionColor = new Color(oldCol.getRed(), oldCol.getGreen(), oldCol.getBlue(), 128);
                
                // need to be non-opaque since we'll be translucent
                setOpaque(false);
            }

            public Component getTableCellRendererComponent(JTable t, Object value,
                                 boolean isSel, boolean hasFocus, int row, int column) {
                // save the selected state since we'll need it when painting
                this.isSelected = isSel;
                return super.getTableCellRendererComponent(t, value, isSel, hasFocus, row, column);
            }

            // since DefaultTableCellRenderer is really just a JLabel, we can override
            // paintComponent to paint the translucent selection when necessary
            public void paintComponent(Graphics g) {
                if (isSelected) {
                    g.setColor(selectionColor);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };

        // set our custom renderer on the JTable
        table.setDefaultRenderer(Object.class, renderer);

        return table;
    }
}
