package SplitterLayout;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * @author idanilov
 *
 */
public class JSplitterSpace extends JComponent {

	public synchronized Dimension getMinimumSize() {
		return new Dimension(10, 10);
	}

	public synchronized Dimension getPreferredSize() {
		return new Dimension(10, 10);
	}
}