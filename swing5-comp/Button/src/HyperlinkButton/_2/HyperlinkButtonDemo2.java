package HyperlinkButton._2;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class HyperlinkButtonDemo2 extends JFrame {

	public HyperlinkButtonDemo2() {
		super(HyperlinkButtonDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
    	JPanel result = new JPanel();
    	Action action = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("Link pressed");
			}
    		
    	};
        JLabel linkButton = new HyperLinkButton("Link...","Tooltip",action);
        result.add(linkButton);
        return result;
	}

	public static void main(String[] args) {
		JFrame f = new HyperlinkButtonDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
