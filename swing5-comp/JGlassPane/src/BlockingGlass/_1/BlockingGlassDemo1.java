package BlockingGlass._1;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class BlockingGlassDemo1 extends JFrame 
		implements ActionListener {

    private JButton mouseButton; 
    private GlassPane glassPane; 
	
	public BlockingGlassDemo1 () {
		super(BlockingGlassDemo1.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
        setJMenuBar(createMenuBar());
        glassPane = new GlassPane();
        setGlassPane(glassPane);
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout()); 
        result.add(BorderLayout.NORTH, new JTextField("TextField1")); 
        result.add(BorderLayout.CENTER, new JTextField("TextField2")); 
        mouseButton = new JButton("Click here to show glass"); 
        mouseButton.addActionListener(this); 
        result.add(BorderLayout.WEST, mouseButton); 
        return result;
	}
	
	public void actionPerformed(ActionEvent ae) { 
        if (ae.getSource().equals(mouseButton)) {
            glassPane.setVisible(true); 
        } 
    } 
	
    //just to show how glass pane relates to menubar of JFrame.
	private JMenuBar createMenuBar() {
	    JMenuBar result = new JMenuBar();
	    JMenu m1 = new JMenu("Menu1");
	    m1.add("Menu Item1");
	    m1.add("Menu Item2");
	    m1.addSeparator();
	    m1.add("Menu Item3");
	    JMenu m2 = new JMenu("Menu2");
	    m2.setMnemonic('M');
	    JMenuItem mi = new JMenuItem("Item with shortut");
	    mi.setMnemonic('I');
		mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		mi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(BlockingGlassDemo1.this,"Menu item invoked!");
			}
			
		});
	    m2.add(mi);
	    result.add(m1);
	    result.add(m2);
		return result;
	}
	
	public static void main(String[] args) {
		//XXX : play with it - uncomment.
        //JFrame.setDefaultLookAndFeelDecorated(true);  
        JFrame f = new BlockingGlassDemo1();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}

	public class GlassPane extends JPanel {

		private boolean okToLooseFocus = true;
		
		public GlassPane() {
			setName("GlassPane");
			setVisible(false);
			setOpaque(false);
			//trap keyboard & mouse events.
			enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setInputVerifier(new GlassPaneInputVerifier());
		}

		protected void processMouseEvent(MouseEvent e) {
			if (e.getID() == MouseEvent.MOUSE_CLICKED) {
				Toolkit.getDefaultToolkit().beep();
				e.consume();
			}	
			super.processMouseEvent(e);
		}
	 
		protected void processKeyEvent(KeyEvent e) {
			if (KeyEvent.KEY_PRESSED == e.getID()) {
				Toolkit.getDefaultToolkit().beep();
				e.consume();
			}
			super.processKeyEvent(e);
		}
	 
		public void setVisible(boolean visible) {
			okToLooseFocus = !visible;
			super.setVisible(visible);
			if (visible) {
				requestFocusInWindow();
			}
		}
	 
		public void paint(Graphics g) {
			g.setColor(new Color(0,0,255,25));
			g.fillRect(0,0,getWidth(), getHeight());
		}
	 
		/**
		 * Simple hack to stop the glass loosing focus when it's visible.
		 */
		private class GlassPaneInputVerifier extends InputVerifier {
			public boolean verify(JComponent input) {
				return okToLooseFocus;
			}		
		}

	}
}
