package MultilineTooltip._1;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class MultilineTooltipDemo1 extends JFrame {

	public MultilineTooltipDemo1() {
		super(MultilineTooltipDemo1.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		String longTip = "111111111111\nAAAbbbCCCdddFFF\n3333333333333";
		JButton button1 = new JButton("Tip devided by \'\\n\'") {
			public JToolTip createToolTip() {
				return new JMultiLineToolTip();
			}
		};
		button1.setToolTipText(longTip);
		result.add(button1);
		JButton button2 = new JButton("Tip width is 40 pixels") {
			public JToolTip createToolTip() {
				JMultiLineToolTip mlt = new JMultiLineToolTip();
				mlt.setFixedWidth(40);
				return mlt;
			}
		};
		button2.setToolTipText(longTip);
		result.add(button2);
		JButton button3 = new JButton("Tip's width is 3 columns") {
			public JToolTip createToolTip() {
				JMultiLineToolTip mlt = new JMultiLineToolTip();
				mlt.setColumns(3);
				return mlt;
			}
		};
		button3.setToolTipText(longTip);
		result.add(button3);
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
        JFrame frame = new MultilineTooltipDemo1();
        frame.pack();
        frame.setSize(new Dimension(300,200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}	
}
