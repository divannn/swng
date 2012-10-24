package Magnifying;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author swinghacks
 * @author idanilov
 * @jdk 1.5
 *
 */
public class MagnifyingDemo {

    public MagnifyingDemo() throws URISyntaxException {
        // image frame
        URL url = MagnifyingDemo.class.getResource("tosBright.png");
        File f = new File(url.toURI());
        ImageIcon i = new ImageIcon(f.getPath());
        JLabel l = new JLabel(i);
        JFrame imgFrame = new JFrame(MagnifyingDemo.class.getSimpleName());
        imgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imgFrame.getContentPane().add(l);
        imgFrame.getContentPane().add(new JButton("Some Button"), BorderLayout.SOUTH);
        imgFrame.pack();
        imgFrame.setLocationRelativeTo(null);
        imgFrame.setVisible(true);

        // magnifying dialog
        JDialog magDlg = new JDialog(imgFrame, "Magnifier");
        magDlg.setResizable(false);
        MagnifyingGlass mag = new MagnifyingGlass(l, new Dimension(150, 150), 2.0);//scale 2x.
        magDlg.getContentPane().add(mag);
        magDlg.pack();
        magDlg.setLocation(new Point(imgFrame.getLocation().x + imgFrame.getWidth(), imgFrame
                .getLocation().y));
        magDlg.setVisible(true);
    }

    public static void main(String[] args) throws URISyntaxException {
        new MagnifyingDemo();
    }

}
