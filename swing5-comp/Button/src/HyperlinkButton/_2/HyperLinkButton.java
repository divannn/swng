package HyperlinkButton._2;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JLabel;

/**
 * @author idanilov
 * 
 */
public class HyperLinkButton extends JLabel {

	private Action action;

	public HyperLinkButton(String text, String tooltip, Action a) {
		super("<html><a href=''>" + text + "</a></html>");
		action = a;
		setToolTipText(tooltip);
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent arg0) {
				action.actionPerformed(new ActionEvent(this, 0, ""));
			}

			public void mouseEntered(MouseEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

		});
	}

}
