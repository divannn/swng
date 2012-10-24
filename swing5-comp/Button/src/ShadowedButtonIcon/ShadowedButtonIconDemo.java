package ShadowedButtonIcon;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

/**
 * @author santosh
 * @author idanilov
 *
 */
public class ShadowedButtonIconDemo extends JFrame {

	public ShadowedButtonIconDemo() {
		super(ShadowedButtonIconDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		JToolBar tb = new JToolBar();
		tb.setRollover(true);
		ImageIcon i1 = new ImageIcon(ShadowedButtonIconDemo.class.getResource("apply.png")); 
		JButton b1 = new RolloverButton(i1); 
		tb.add(b1);
		ImageIcon i2 = new ImageIcon(ShadowedButtonIconDemo.class.getResource("delete.png")); 
		JButton b2 = new RolloverButton(i2); 
		tb.add(b2);
		ImageIcon i3 = new ImageIcon(ShadowedButtonIconDemo.class.getResource("pin.png")); 
		JButton b3 = new RolloverButton(i3); 
		tb.add(b3);
		result.add(tb);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		JFrame f = new ShadowedButtonIconDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class RolloverButton extends JButton {

		public RolloverButton(final Icon icon) {
			super(new InsetsIcon(icon));
			setRolloverEnabled(true); 
			setRolloverIcon(new ShadowedIcon(icon));
			setMargin(new Insets(0,0,0,0));
		}
		
	}
	
	private static class InsetsIcon 
			implements Icon { 
		
	    private static final Insets DEFAULT_INSETS = new Insets(2, 2, 0, 0);
	    
	    private Icon icon; 
	    private Insets insets; 
	 
	    public InsetsIcon(Icon icon) { 
	        this(icon, null); 
	    } 
	 
	    public InsetsIcon(Icon icon, Insets insets) { 
	        this.icon = icon; 
	        this.insets = insets == null ? DEFAULT_INSETS : insets; 
	    } 
	 
	    public int getIconHeight() { 
	        return icon.getIconHeight() + insets.top+insets.bottom; 
	    } 
	 
	    public int getIconWidth() { 
	        return icon.getIconWidth() + insets.left+insets.right; 
	    } 
	 
	    public void paintIcon(Component c, Graphics g, int x, int y) { 
	        icon.paintIcon(c, g, x + insets.left, y + insets.top); 
	    } 
	    
	}

	private static class ShadowedIcon 
			implements Icon {
		
	    private int shadowWidth = 2; 
	    private int shadowHeight = 2; 
	    private Icon icon, shadow; 
	 
	    public ShadowedIcon(Icon icon) { 
	        this.icon = icon; 
	        shadow = new ImageIcon(GrayFilter.createDisabledImage(((ImageIcon)icon).getImage())); 
	    } 
	 
	    public ShadowedIcon(Icon icon, int shadowWidth, int shadowHeight) { 
	        this(icon); 
	        this.shadowWidth = shadowWidth; 
	        this.shadowHeight = shadowHeight; 
	    } 
	 
	    public int getIconHeight() { 
	        return icon.getIconWidth() + shadowWidth; 
	    } 
	 
	    public int getIconWidth() { 
	        return icon.getIconHeight() + shadowHeight; 
	    } 
	 
	    public void paintIcon(Component c, Graphics g, int x, int y) { 
	        shadow.paintIcon(c, g, x + shadowWidth, y + shadowHeight); 
	        icon.paintIcon(c, g, x, y); 
	    } 
	    
	}	
	
}
