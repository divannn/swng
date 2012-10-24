package ColorPicker;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Uses Robot to take any pixel on the screen and take its color.
 * @author swinghacks
 * @author idanilov
 * @jdk 1.5
 */
public class ColorPickerDemo extends JFrame {

    public ColorPickerDemo() {
        super(ColorPickerDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JPanel result = new JPanel();
        final JButton button = new JButton("Click to choose a color");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                JFrame fakeScreenFrame = new RobotFrame(button);
                fakeScreenFrame.show();
            }
        });
        result.add(button);
        return result;
    }

    public static void main(String[] args) {
        JFrame f = new ColorPickerDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static class RobotFrame extends JFrame
            implements MouseListener, MouseMotionListener {

        private JPanel imagePanel;
        private Dimension screenSize;
        private JComponent comp;
        private BufferedImage backgroundImage;
        private Robot robot;

        public RobotFrame(final JComponent comp) {
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.comp = comp;
            this.setUndecorated(true);
            this.setSize(screenSize.width, screenSize.height);
            //set up the panel that holds the screenshot.
            imagePanel = new JPanel() {

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, null);
                }
            };
            imagePanel.setPreferredSize(screenSize);
            this.getContentPane().add(imagePanel);
        }

        public void show() {
            try {
                //make the screenshot before showing the frame.
                Rectangle rect = new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize
                        .getHeight());
                robot = new Robot();
                backgroundImage = robot.createScreenCapture(rect);
                super.show();
            } catch (AWTException ex) {
                System.err.println("exception creating screenshot:");
                ex.printStackTrace();
            }
        }

        //update the selected color on mouse press, dragged, and release.
        public void mousePressed(MouseEvent evt) {
            setSelectedColor(robot.getPixelColor(evt.getX(), evt.getY()));
        }

        public void mouseDragged(MouseEvent evt) {
            setSelectedColor(robot.getPixelColor(evt.getX(), evt.getY()));
        }

        //for released we want to hide the frame as well.
        public void mouseReleased(MouseEvent evt) {
            setSelectedColor(robot.getPixelColor(evt.getX(), evt.getY()));
            this.setVisible(false);
            backgroundImage.flush();
        }

        //update both the display label and the component that was passed in.
        private void setSelectedColor(Color color) {
            comp.setBackground(color);
        }

        public void mouseClicked(MouseEvent evt) {
        }

        public void mouseEntered(MouseEvent evt) {
        }

        public void mouseExited(MouseEvent evt) {
        }

        public void mouseMoved(MouseEvent evt) {
        }
    }

}
