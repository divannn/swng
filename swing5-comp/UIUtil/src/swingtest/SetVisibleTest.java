package swingtest;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Shows that:
 * <br>
 * 1. setVisible() method is not called for components in hierarchy.
 * All components are visible by default except top-level windows 
 * and JInternalFrames.
 * <br>
 * 2. addNotify() called through children in depth.
 * @author idanilov
 * @jdk 1.5
 */
public class SetVisibleTest extends JFrame {

	public SetVisibleTest() {
		super(SetVisibleTest.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout()) {

			public void setVisible(boolean aFlag) {
				super.setVisible(aFlag);
				System.err.println("1 setVisible");
			}

			public void addNotify() {
				super.addNotify();
				System.err.println("1 addNotify ");
			}
		};
		JPanel p = new JPanel() {

			public void setVisible(boolean aFlag) {
				super.setVisible(aFlag);
				System.err.println("2 setVisible");
			}

			public void addNotify() {
				super.addNotify();
				System.err.println("2 addNotify");
			}
		};
		p.add(new JTextField("text    ") {

			public void setVisible(boolean aFlag) {
				super.setVisible(aFlag);
				System.err.println("3 setVisible");
			}

			public void addNotify() {
				System.err.println("3 addNotify before: displayable= " + isDisplayable());
				super.addNotify();
				System.err.println("3 addNotify after: displayable= " + isDisplayable());
			}
		});
		p.add(new JButton("Button") {

			public void setVisible(boolean aFlag) {
				super.setVisible(aFlag);
				System.err.println("4 setVisible");
			}

			public void addNotify() {
				super.addNotify();
				System.err.println("4 addNotify");
			}
		});
		result.add(p, BorderLayout.CENTER);
		JTable t = new JTable(5, 3) {

			public void setVisible(boolean aFlag) {
				super.setVisible(aFlag);
			}

			public void addNotify() {
				super.addNotify();
				System.err.println("5 addNotify");
			}
		};
		result.add(t, BorderLayout.EAST);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new SetVisibleTest();
		System.err.println("frame visble before= " + f.isVisible());
		f.pack();//this will call addNotify for all components hierarchy.
		f.setLocationRelativeTo(null);
		f.setVisible(true);//also will call addNotify.
		System.err.println("frame visble after= " + f.isVisible());
	}

}
