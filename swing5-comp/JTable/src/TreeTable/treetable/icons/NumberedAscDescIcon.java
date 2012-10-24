package TreeTable.treetable.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Icon;

/**
 * Ascending and Descending numbered icons
 **/
public class NumberedAscDescIcon implements Icon {
    int nom = 0;            // Number
    boolean down = true;    // True - Decsendind, false - Ascending

    public NumberedAscDescIcon(int number, boolean isDesc) {
        nom = number;
        down = isDesc;
    }

    public int getIconHeight() {
        return 10;
    }

    public int getIconWidth() {
        return 16;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Save state
        Color oldColor = g.getColor();
        Font oldFont = g.getFont();
        Color color = c.getBackground();

        if (down) {
            // __ Descending icon
            // \/
            g.setColor(new Color(color.getRed() / 3 * 2, color.getGreen() / 3 * 2, color.getBlue() / 3 * 2));
            g.drawLine(x + 1, y + 1, x + 5, y + 8);
            g.drawLine(x + 1, y + 1, x + 9, y + 1);
            g.setColor(new Color(255, 255, 255));
            g.drawLine(x + 5, y + 8, x + 9, y + 1);
        } else {
            // /\ Ascending icon
            // ~~
            g.setColor(new Color(255, 255, 255));
            g.drawLine(x + 5, y + 1, x + 9, y + 8);
            g.drawLine(x + 1, y + 8, x + 9, y + 8);
            g.setColor(new Color(color.getRed() / 3 * 2, color.getGreen() / 3 * 2, color.getBlue() / 3 * 2));
            g.drawLine(x + 1, y + 8, x + 5, y + 1);
        }
        // Number of icon
        g.setColor(new Color(0, 0, 0));
        g.setColor(new Color(color.getRed() / 2, color.getGreen() / 2,
                             color.getBlue() / 2));
        g.setFont(new Font("Serif" /*oldFont.getFamily()*/, 0 /*oldFont.getStyle()*/, 8));
        if (down) {
            g.drawString("" + nom, x + 4 +6, y + 9+1);
        } else {
            g.drawString("" + nom, x + 4 +6, y + 9-3);
        }

        g.setFont(oldFont);
        g.setColor(oldColor);
    }
}
