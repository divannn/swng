package GradientProgressBar;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

import com.sun.java.swing.SwingUtilities2;

/**
 * <strong>Notes:</strong>
 * <li>Right-to-left orientation is not supported though can be added easily.
 * @author idanilov
 *
 */
public class GradientProgressBar extends JProgressBar {
	
	private static final String uiClassID = "GradientProgressBarUI";
	
	/**
	 * Block pixels 
	 */
	private static final int INCREMENT = 1;
	
	/**
	 * Millsecond between repaint.
	 */
	private static final int REPAINT_INTERVAL = 10;
	
	
	public GradientProgressBar(final int orientation) {
		super(orientation);
	}
	
    public String getUIClassID() {
        return uiClassID;
    }

	public void updateUI() {
		setUI(GradientProgressBarUI.createUI(this));
	}
	
	private static class GradientProgressBarUI extends BasicProgressBarUI 
			implements ActionListener { 
			
		private int movingPoint, delta; 
		private Timer timer; 
		
	    public static ComponentUI createUI(JComponent c) {
	    	return new GradientProgressBarUI();
        }

		protected void startAnimationTimer() { 
		    if (timer == null) {
		        timer = new Timer(REPAINT_INTERVAL, this);
		    }
		    movingPoint = getMin(); 
		    delta = INCREMENT; 
		    timer.start(); 
		} 
		
		protected void stopAnimationTimer() { 
		    timer.stop(); 
		} 
		
		public void actionPerformed(ActionEvent ae){ 
		    if (movingPoint == getMin()) { 
		        delta = INCREMENT;
		    } else if (movingPoint >= getMax()) { 
		        delta = -INCREMENT; 
		    }
		    movingPoint+= delta; 
		    progressBar.repaint(); 
		} 
		
		private int getMin() {
			int result = 0;
			if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
				result = progressBar.getInsets().left;
			} else {
				result = progressBar.getInsets().top;
			}
			return result;
		}

		private int getMax() {
			Insets b = progressBar.getInsets();
			int result = 0;
			if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
				result = progressBar.getWidth() - b.right;
			} else {
				result = progressBar.getHeight() - b.bottom;
			}
			return result;
		}

		public void paintIndeterminate(Graphics g, JComponent c) {
			if (!(g instanceof Graphics2D)) {
				return;
			}
			Graphics2D g2 = (Graphics2D)g;
			Insets ins = progressBar.getInsets();
			int barRectX = ins.left;
			int barRectY = ins.top;
			int barRectWidth = progressBar.getWidth() - (ins.right + ins.left);
			int barRectHeight = progressBar.getHeight() - (ins.top + ins.bottom);
			boolean toRight = delta > 0;
			if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
			    if (toRight) { 
			        GradientPaint toRightPaint = new GradientPaint(barRectX, barRectY, getSelectionForeground(), movingPoint, barRectY, getSelectionBackground(), false); 
			        g2.setPaint(toRightPaint); 
			        g2.fillRect(barRectX, barRectY,movingPoint - barRectX, barRectHeight);
			    } else { 
			        GradientPaint toLeftPaint = new GradientPaint(movingPoint, barRectY, getSelectionBackground(), barRectX + barRectWidth, barRectY, getSelectionForeground(), false); 
			        g2.setPaint(toLeftPaint); 
					g2.fillRect(movingPoint, barRectY, (barRectX + barRectWidth) - movingPoint, barRectHeight);
			    }
			} else {
				if (toRight) { 
			        GradientPaint toRightPaint = new GradientPaint(barRectX, barRectY, getSelectionForeground(), barRectX, movingPoint, getSelectionBackground(), false); 
			        g2.setPaint(toRightPaint); 
			        g2.fillRect(barRectX,barRectY,barRectWidth,movingPoint - barRectY);
			    } else { 
			        GradientPaint toLeftPaint = new GradientPaint(barRectX,movingPoint, getSelectionBackground(), barRectX, barRectY + barRectHeight, getSelectionForeground(), false); 
			        g2.setPaint(toLeftPaint); 
					g2.fillRect(barRectX,movingPoint,barRectWidth,(barRectY + barRectHeight) - movingPoint);
			    }
			}
			//paint string.
			if (progressBar.isStringPainted()) {
                paintString(g2, barRectX, barRectY, barRectWidth, barRectHeight,movingPoint,toRight);				
			}
		} 
	    
		private void paintString(Graphics2D g2, int barRectX, int barRectY, int width, int height,int currentPos, boolean toRight) {
			String progressString = progressBar.getString();
			Rectangle oldClip = g2.getClipBounds();
			if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
				g2.setFont(progressBar.getFont());
				Point stringLocation = getStringPlacement(g2, progressString, barRectX, barRectY, width, height);
				g2.setColor(getSelectionBackground());
				SwingUtilities2.drawString(progressBar, g2, progressString,
				                          stringLocation.x, stringLocation.y);
				int stringWidth = progressBar.getFontMetrics(progressBar.getFont()).stringWidth(progressString);
//g2.drawLine(0,0,stringLocation.x,stringLocation.y);//uncomment to see where location is calculated.
				if (toRight) {//->.
					if ((currentPos >= stringLocation.x) && (currentPos < barRectX + width)) {
						g2.setColor(getSelectionForeground());
						g2.clipRect(stringLocation.x,barRectY, currentPos - stringLocation.x, height);
						SwingUtilities2.drawString(progressBar, g2, progressString,
		                        stringLocation.x, stringLocation.y);
					} 					
				} else {//<-.
					if ((currentPos >= barRectX) && (currentPos < stringLocation.x + stringWidth)) {
						g2.setColor(getSelectionForeground());
						g2.clipRect(currentPos, barRectY, stringLocation.x + stringWidth - currentPos, height);
						SwingUtilities2.drawString(progressBar, g2, progressString,
		                        stringLocation.x, stringLocation.y);
					}					
				}
			} else {
				g2.setColor(getSelectionBackground());
				AffineTransform rotate = AffineTransform.getRotateInstance(Math.PI/2);
				g2.setFont(progressBar.getFont().deriveFont(rotate));
				Point stringLocation = getStringPlacement(g2, progressString, barRectX, barRectY, width, height);
//g2.drawLine(0,0,stringLocation.x,stringLocation.y);//uncomment to see where location is calculated.				
				g2.setColor(getSelectionBackground());
				SwingUtilities2.drawString(progressBar, g2, progressString,
				                          stringLocation.x, stringLocation.y);
				int stringWidth = progressBar.getFontMetrics(progressBar.getFont()).stringWidth(progressString);
				if (toRight) {//down.
					if ((currentPos >= stringLocation.y) && (currentPos < barRectY + height)) {
						g2.setColor(getSelectionForeground());
						g2.clipRect(barRectX, stringLocation.y, width, currentPos - stringLocation.y);
						SwingUtilities2.drawString(progressBar, g2, progressString,
		                        stringLocation.x, stringLocation.y);
					} 					
				} else {//up.
					if ((currentPos >= barRectY) && (currentPos < stringLocation.y + stringWidth)) {
						g2.setColor(getSelectionForeground());
						g2.clipRect(barRectX, currentPos, width, stringLocation.y + stringWidth - currentPos);
						SwingUtilities2.drawString(progressBar, g2, progressString,
		                        stringLocation.x, stringLocation.y);
					}					
				}				
			}
			g2.setClip(oldClip);
		}
		
	}	
	
}