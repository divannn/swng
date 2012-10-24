package FrameMaximumBounds;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class FrameMaximumBoundsDemo extends JFrame {

	public FrameMaximumBoundsDemo() {
		super(FrameMaximumBoundsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//define maximized frame bounds centered with insets of 1/5 of screen size.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int left = screenSize.width/5;
		int top = screenSize.height/5;
		int height = screenSize.height - top*2;
		int width = screenSize.width - left*2;
		setMaximizedBounds(new Rectangle(left,top,width,height));
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTree tree = new JTree();
		result.add(new JScrollPane(tree),BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new FrameMaximumBoundsDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
