package TipForSliderValues;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Tooltip for slider labels.
 * @author unknown
 * @author idanilov
 * @jdk 1.5
 */
public class TipForSliderValuesDemo extends JFrame {

	public TipForSliderValuesDemo() {
		super(TipForSliderValuesDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JPanel createContents() {
		JPanel result = new JPanel();
		JSlider slider = new JSlider(SwingConstants.VERTICAL, 0, 120, 60) {
		String [] tooltips = {"Call 911",
								"Seeing red",
								"Really mad",
								"Ticked off",
								"Slightly peeved",
								"Oh bother",
								"Feel good"};
		
			public String getToolTipText(MouseEvent me) {
				Point p = me.getPoint();
				Rectangle rect = getBounds();
				int n = getLabelTable().size();
				int index = n * p.y / rect.height;
				return tooltips[index];
			}
			
		};
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(20);
		slider.setPaintLabels(true);
		slider.setToolTipText("");//just to register into TooltipManager.
		result.add(slider);
		return result;
	}
	
	public static void main(final String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        }
        JFrame f = new TipForSliderValuesDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}

}