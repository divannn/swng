package BlockingGlass._2;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class BlockingGlassDemo2 extends JFrame {
	
	private JPanel glassPane;

	public BlockingGlassDemo2() {
	    super(BlockingGlassDemo2.class.getSimpleName());
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setContentPane(createContents());
	    setJMenuBar(createMenuBar());
	    glassPane = new GlassPane();
	    setGlassPane(glassPane);
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
	    JPanel mainPane = new JPanel();
	    mainPane.setBackground(Color.WHITE);
	    JButton redB = new JButton("Red");
	    JButton blueB = new JButton("Blue");
	    JButton greenB = new JButton("Green");
	    mainPane.add(redB);
	    mainPane.add(greenB);
	    mainPane.add(blueB);
	    ActionListener al = new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(BlockingGlassDemo2.this, ae.getActionCommand());
		    }
	    };
	    redB.addActionListener(al);
	    greenB.addActionListener(al);
	    blueB.addActionListener(al);
	    JButton startButton = new JButton("Start the big operation!");
	    startButton.addActionListener(new ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent A) {
				glassPane.setVisible(true);
	        }
		});
	    result.add(mainPane, BorderLayout.CENTER);
	    result.add(startButton, BorderLayout.SOUTH);
		return result;
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
				JOptionPane.showMessageDialog(BlockingGlassDemo2.this,"Menu item invoked!");
			}
			
		});
	    m2.add(mi);
	    result.add(m1);
	    result.add(m2);
		return result;
	}

	public static void main(String[] args) {
		BlockingGlassDemo2 f = new BlockingGlassDemo2();
		f.setSize(new Dimension(400,300));
		f.setLocationRelativeTo(null);
    	f.setVisible(true);
  	}

	private static class GlassPane extends JPanel {

		private Timer timer;
		private JProgressBar progressBar;
		private GlassKeyEventBlocker keyEventDispatcher;
		
		private GlassPane() {
			super(new BorderLayout());
		    setOpaque(false);
		    //1. variant - add empty listener to panel be able to react on input event.
//		    result.addMouseListener(new MouseAdapter() {});
//		    result.addMouseMotionListener(new MouseMotionAdapter() {});
//		    result.addKeyListener(new KeyAdapter() {});
		    //2. variant - enable events directly.
			enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);
		    JPanel monitorPane = new JPanel(new GridLayout(2,1));
		    monitorPane.setOpaque(false);
		    monitorPane.add(new JLabel("Please wait..."));
		    progressBar = new JProgressBar(0, 100);
		    monitorPane.add(progressBar);
		    add(monitorPane,BorderLayout.NORTH);
		    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		  	keyEventDispatcher = new GlassKeyEventBlocker();
		}
		
		public void setVisible(boolean isVisible) {
			KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		    if (isVisible) {
		    	kfm.addKeyEventDispatcher(keyEventDispatcher);	
		    } else {
		    	kfm.removeKeyEventDispatcher(keyEventDispatcher);
		    }
			super.setVisible(isVisible);
			if (isVisible) {
		  		startTimer();
			} 
		}

		private void startTimer() {
			if (timer == null) {
				timer = new Timer(1000, new ActionListener() {
					int progress = 0;
					public void actionPerformed(ActionEvent A) {
						progress += 10;
						progressBar.setValue(progress);
						if (progress >= 100) {
							timer.stop();
							progress = 0;
							progressBar.setValue(progress);
							setVisible(false);
						}
					}
		        });
		    }
		    if (timer.isRunning()) {
				timer.stop();
		    }
		    timer.start();
		}		
	}

	private static class GlassKeyEventBlocker 
			implements KeyEventDispatcher {

		public boolean dispatchKeyEvent(KeyEvent ke) {
			//System.err.println("intercepted keystroke : " + KeyStroke.getKeyStrokeForEvent(ke));
			return true;
		}
		
	}
	
}
