package image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.accessibility.Accessible;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * @author idanilov
 *
 */
class Picture extends JComponent
		implements MouseListener, FocusListener, Accessible {

	Image image;

	public Picture(Image image) {
		this.image = image;
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setFocusable(true);
		addMouseListener(this);
		addFocusListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		requestFocusInWindow();
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void focusGained(FocusEvent e) {
		//Draw the component with a red border
		//indicating that it has focus.
		this.repaint();
	}

	public void focusLost(FocusEvent e) {
		//Draw the component with a black border
		//indicating that it doesn't have focus.
		this.repaint();
	}

	protected void paintComponent(Graphics graphics) {
		Graphics g = graphics.create();

		//Draw in our entire space, even if isOpaque is false.
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, image == null ? 125 : image.getWidth(this), image == null ? 125 : image
				.getHeight(this));

		if (image != null) {
			//Draw image at its natural size of 125x125.
			g.drawImage(image, 0, 0, this);
		}

		//Add a border, red if picture currently has focus
		g.setColor(isFocusOwner() ? Color.RED : Color.BLACK);
		g.drawRect(0, 0, image == null ? 125 : image.getWidth(this), image == null ? 125 : image
				.getHeight(this));
		g.dispose();
	}
}
