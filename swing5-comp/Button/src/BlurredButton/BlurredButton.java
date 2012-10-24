package BlurredButton;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @author swinghacks
 *
 */
public class BlurredButton extends JFrame {

    public BlurredButton() {
        super(BlurredButton.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JPanel result = new JPanel(new BorderLayout());
        final JButton blurredButton = new BlurJButton("Blurred Button");
        result.add(blurredButton);
        JButton switchButton = new JButton("Switch");
        switchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                blurredButton.setEnabled(!blurredButton.isEnabled());
            }
        });

        result.add(switchButton, BorderLayout.SOUTH);
        return result;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new BlurredButton();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}

class BlurJButton extends JButton {

    public BlurJButton(String text) {
        super(text);
    }

    public void paintComponent(Graphics g) {
        if (isEnabled()) {
            super.paintComponent(g);
            return;
        }
        BufferedImage buf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        super.paintComponent(buf.getGraphics());
        Image img = blur(buf);
        g.drawImage(img, 0, 0, null);
    }

    private Image blur(BufferedImage buf) {
        float[] my_kernel = { 0.10f, 0.10f, 0.10f, 0.10f, 0.20f, 0.10f, 0.10f, 0.10f, 0.10f };
        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, my_kernel));
        Image img = op.filter(buf, null);
        return img;
    }

}
