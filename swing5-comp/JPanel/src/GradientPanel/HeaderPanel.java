package GradientPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * @author idanilov
 *
 */
public class HeaderPanel extends GradientPanel {

    private ImageIcon icon;

    public HeaderPanel(final ImageIcon icon) {
        super(new BorderLayout(),GradientPanel.Direction.DIAGONAL);
        this.icon = icon;
        add(BorderLayout.CENTER, createContents());
        add(BorderLayout.SOUTH, new JSeparator(SwingConstants.HORIZONTAL));
    }

    private JComponent createContents() {
    	JPanel result = new JPanel(new BorderLayout());
    	result.setOpaque(false);
    	result.setBorder(new EmptyBorder(10,10,10,10));
    	result.add(BorderLayout.CENTER, createMessagePanel());
    	result.add(BorderLayout.EAST, createIconPanel());
    	return result;
	}
    
    private JComponent createMessagePanel() {
        JPanel result = new JPanel(new GridLayout(3, 1));
        result.setOpaque(false);
        JLabel titleLabel = new JLabel("Header Title");
        Font boldFont = titleLabel.getFont().deriveFont(Font.BOLD);
        titleLabel.setFont(boldFont);
        result.add(titleLabel);
        JLabel subLablel1 = new JLabel("Message 1");
        result.add(subLablel1);
        JLabel subLablel2 = new JLabel("Message 2");
        result.add(subLablel2);
        Border b = new EmptyBorder(0, 20, 0, 0);
        subLablel1.setBorder(b);
        subLablel2.setBorder(b);
		return result;
	}

    private JComponent createIconPanel() {
        JLabel result = new JLabel(this.icon);
        return result;
	}
    
}
