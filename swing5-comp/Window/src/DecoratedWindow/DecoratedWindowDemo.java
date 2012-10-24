package DecoratedWindow;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;

/**
 * Play with decorated JDialog's and JFrame's buttons.
 * 'Close' button (x) is removed from JDialog.
 * 'Min','Max' and 'Close' button (x) are removed from JFrame.
 * @see MetalTitlePane
 * @author idanilov
 * @jdk 1.5
 */
public class DecoratedWindowDemo extends JFrame {

	public DecoratedWindowDemo() {
		super(DecoratedWindowDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel();
		JButton showDialogButton = new JButton("show dialog");
		showDialogButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				JDialog dialog = new JDialog(DecoratedWindowDemo.this, "Dialog");
				dialog.setModal(false);
				dialog.setSize(200, 100);
				dialog.setLocationRelativeTo(null);
				removeButtons(dialog, 0);
				dialog.setVisible(true);
			}
		});
		result.add(showDialogButton);
		JButton showFrameButton = new JButton("show frame");
		showFrameButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				//MetalTitlePane#setState() called on each resize or state change.
				//As result - minimize and restore buttons are recreated every time.
				final JFrame frame = new JFrame("Frame") {

					//Prohibit frame state change.
					//This is allows to mximize frmae by double click.
					public synchronized void setExtendedState(int state) {
					}
				};
				/*frame.addComponentListener(new ComponentListener() {

				 public void componentResized(ComponentEvent e) {
				 removeButtons(frame, 0);
				 frame.validate();
				 frame.repaint();
				 }

				 public void componentMoved(ComponentEvent e) {
				 }

				 public void componentShown(ComponentEvent e) {
				 removeButtons(frame, 0);
				 frame.validate();
				 frame.repaint();
				 }

				 public void componentHidden(ComponentEvent e) {
				 }
				 
				 });*/
				frame.setSize(600, 400);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				removeButtons(frame, 0);
				frame.validate();
				frame.repaint();
			}
		});
		result.add(showFrameButton);
		return result;
	}

	static void removeButtons(Component comp, int indent) {
		for (int i = indent; i > 0; --i) {
			System.out.print(" ");
		}
		indent += 4;
		System.out.print(comp.getClass().getName());
		if (comp instanceof AbstractButton) {
			comp.getParent().remove(comp);
			System.out.println(" [removed]");
		} else {
			System.out.println();
		}
		if (comp instanceof Container) {
			Component[] children = ((Container) comp).getComponents();
			for (int i = 0; i < children.length; ++i) {
				removeButtons(children[i], indent);
			}
		}
		if (comp instanceof JMenu) {
			Component[] children = ((JMenu) comp).getMenuComponents();
			for (int i = 0; i < children.length; ++i) {
				removeButtons(children[i], indent);
			}
		}
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame f = new DecoratedWindowDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
