package ExtendedScroll;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JRootPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * @author idanilov
 * 
 */
public class ScrollGestureRecognizer
		implements AWTEventListener {

	private static ScrollGestureRecognizer instance = new ScrollGestureRecognizer();

	private ScrollGestureRecognizer() {
		start();
	}

	public static ScrollGestureRecognizer getInstance() {
		return instance;
	}

	void start() {
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK);
	}

	void stop() {
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);
	}

	public void eventDispatched(AWTEvent event) {
		MouseEvent me = (MouseEvent) event;
		boolean isGesture = SwingUtilities.isMiddleMouseButton(me)
				&& me.getID() == MouseEvent.MOUSE_PRESSED;
		if (isGesture) {
			JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, me
					.getComponent());
			if (viewPort != null) {
				JRootPane rootPane = SwingUtilities.getRootPane(viewPort);
				if (rootPane != null) {
					Point location = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), rootPane
							.getGlassPane());
					//Really cool usage of Robot! This is need for scrolling during mouse grag (when middle button is selected).
					//The problem in that mouseDragged events are actually delivered to the component where click was performed
					//- not to a glass pane. This happens even if we cover the JTextArea with our ScrollGlassPane.
					//Because as we have generated here mouseReleased() event using Robot, 
					//all mouseDragged events actually become mouseMoved() events. 
					//Thus even though user is actually dragging the mouse, 
					//mouseDragged(...) never gets called, rather mouseMoved(...) will be called.
					try{ 
			            Robot robot = new Robot(); 
			            robot.mouseRelease(InputEvent.BUTTON2_MASK); 
			        } catch(AWTException ignore){ 
			            ignore.printStackTrace(); 
			        }
			        
					ScrollGlassPane glassPane = new ScrollGlassPane(rootPane.getGlassPane(), viewPort, location);
					rootPane.setGlassPane(glassPane);
					glassPane.setVisible(true);
				}
			}
		}
	}
	
}
