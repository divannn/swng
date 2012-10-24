package DropShadow;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 * Shows nice drop shadows around menusþ
 * @author swinhacks
 * @jdk 1.5
 */
public class DropShadowDemo {

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("PopupMenuUI", "DropShadow.CustomPopupMenuUI");

        JFrame frame = new JFrame(DropShadowDemo.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar mb = new JMenuBar();
        frame.setJMenuBar(mb);
        JMenu menu = new JMenu("File");
        mb.add(menu);
        menu.add(new JMenuItem("Open"));
        menu.add(new JMenuItem("Save"));
        menu.add(new JMenuItem("Close"));
        menu.add(new JMenuItem("Exit"));
        menu = new JMenu("Edit");
        mb.add(menu);
        menu.add(new JMenuItem("Cut"));
        menu.add(new JMenuItem("Copy"));
        menu.add(new JMenuItem("Paste"));
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add("North", new JButton("Button"));
        frame.getContentPane().add("Center", new JLabel("a label"));
        frame.getContentPane().add("South", new JCheckBox("checkbox"));
        frame.pack();
        frame.setSize(200, 150);
        frame.setLocationRelativeTo(null);
        frame.show();
    }

}
