package MultilineTooltip._2;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class MultilineTooltipDemo2 extends JFrame {

	public MultilineTooltipDemo2() {
		super(MultilineTooltipDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		String htmlTooltip = "<html>In case you thought that tooltips had to be<p>boring, one line descriptions, the <font color=blue size=+2>Swing!</font> team<p> is happy to shatter your illusions.<p>In Swing, they can use HTML to <ul><li>Have Lists<li><b>Bold</b> text<li><em>emphasized</em> text<li>text with <font color=red>Color</font><li>text in different <font size=+3>sizes</font><li>and <font face=AvantGarde>Fonts</font></ul>Oh, and they can be multi-line, too.</html>";
		JLabel label = new JLabel("Simple label"); 
		label.setToolTipText(htmlTooltip);
		result.add(label);
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
        JFrame frame = new MultilineTooltipDemo2();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}	
	
}
