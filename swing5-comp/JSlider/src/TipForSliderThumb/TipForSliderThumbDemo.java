package TipForSliderThumb;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Show toolip while dragging.
 * @see ToolTipManager for details about actions.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class TipForSliderThumbDemo extends JFrame {

	public TipForSliderThumbDemo() {
		super(TipForSliderThumbDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JPanel createContents() {
		JPanel result = new JPanel();
		JSlider slider = new JSlider(1,100);
		SliderToolTips.enableSliderToolTips(slider);
		result.add(slider);
		return result;
	}
	
	public static void main(final String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { 
            e.printStackTrace(); 
        }
        JFrame f = new TipForSliderThumbDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}

	private static class SliderToolTips { 
		 
	    public static void enableSliderToolTips(final JSlider slider){ 
	        slider.addChangeListener(new ChangeListener() { 

	        	private boolean adjusting;
	            private String oldTooltip;
	            
	            public void stateChanged(ChangeEvent e){ 
	                if (slider.getModel().getValueIsAdjusting()) { 
	                    if (!adjusting) { 
	                        oldTooltip = slider.getToolTipText(); 
	                        adjusting = true; 
	                    } 
	                    slider.setToolTipText(String.valueOf(slider.getValue()));
	                    hideToolTip(slider); // to avoid flickering. 
	                    postToolTip(slider);
	                } else {
	                    hideToolTip(slider); 
	                    slider.setToolTipText(oldTooltip); 
	                    adjusting = false; 
	                    oldTooltip = null; 
	                } 
	            } 
	        }); 
	    } 
	 
	    public static void postToolTip(JComponent comp) { 
	        Action action = comp.getActionMap().get("postTip"); 
	        if(action == null) {// no tooltip 
	            return; 
	        }
	        ActionEvent ae = new ActionEvent(comp, ActionEvent.ACTION_PERFORMED, 
            		"postTip", EventQueue.getMostRecentEventTime(), 0); 
	        action.actionPerformed(ae); 
	    } 
	 
	    public static void hideToolTip(JComponent comp) { 
	        Action action = comp.getActionMap().get("hideTip"); 
	        if (action == null) { // no tooltip 
	            return; 
	        }
	        ActionEvent ae = new ActionEvent(comp, ActionEvent.ACTION_PERFORMED, 
                    "hideTip", EventQueue.getMostRecentEventTime(), 0); 
	        action.actionPerformed(ae); 
	    } 
	}	
}