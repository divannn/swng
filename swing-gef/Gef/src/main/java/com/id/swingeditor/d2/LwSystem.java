package com.id.swingeditor.d2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author idanilov
 *
 */
public class LwSystem {

	private JComponent canvas;
	private IEventDispatcher eventDispatcher;
	private XFigure root;
	private XFigure contents;

	public LwSystem() {
		canvas = new DefaultCanvas();
		setRootFigure(new XRootFigure(this));
		hookAwt();
		setEventDispatcher(new DefaultEventDispatcher());
	}

	public IEventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	public JComponent getCanvas() {
		return canvas;
	}

	//	public void setCanvas(JComponent canvas) {
	//		this.canvas = canvas;
	//	}

	public XFigure getRootFigure() {
		return root;
	}

	protected void setRootFigure(XRootFigure root) {
		//[ID]
		//getUpdateManager().setRoot(root);
		this.root = root;
	}

	public void setContents(XFigure f) {
		if (contents != null) {
			root.remove(contents);
		}
		contents = f;
		root.add(contents);
	}

	public void setEventDispatcher(IEventDispatcher dispatcher) {
		eventDispatcher = dispatcher;
		dispatcher.setRoot(root);
		dispatcher.setControl(canvas);
	}

	protected void _paint(Graphics2D g) {
		System.err.println("-<LWS paint>-");
		root.paint(g);
	}

	protected void _layout() {
		System.err.println("-<LWS layout>-");
		root.setBounds(new Rectangle(0, 0, canvas.getWidth(), canvas.getHeight()));

		//[ID]
		//root.revalidate();
		//getUpdateManager().performUpdate();

		root.validate();
		//canvas.repaint();
	}

	private void hookAwt() {
		EventHandler eh = new EventHandler();
		canvas.addMouseListener(eh);
		canvas.addMouseMotionListener(eh);
		/*[ID] override doLayout() incanvas instead.
		 * canvas.addComponentListener(new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				_resize();
			}

		});*/

		//		root.setBounds(new Rectangle(0, 0, canvas.getWidth(), canvas.getHeight()));
		//		root.validate();
	}

	/**
	 * Traps AWT mouse events on forwards tham to LWS. 
	 *
	 */
	private class EventHandler
			implements MouseListener, MouseMotionListener {

		public void mouseEntered(MouseEvent e) {
			eventDispatcher.dispatchMouseEntered(e);
		}

		public void mouseExited(MouseEvent e) {
			eventDispatcher.dispatchMouseExited(e);
		}

		public void mousePressed(MouseEvent e) {
			eventDispatcher.dispatchMousePressed(e);
		}

		public void mouseReleased(MouseEvent e) {
			eventDispatcher.dispatchMouseReleased(e);
		}

		public void mouseClicked(MouseEvent e) {
			eventDispatcher.dispatchMouseClicked(e);
		}

		public void mouseDragged(MouseEvent e) {
			eventDispatcher.dispatchMouseDragged(e);
		}

		public void mouseMoved(MouseEvent e) {
			eventDispatcher.dispatchMouseMoved(e);
		}

	}

	private class DefaultCanvas extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			_paint((Graphics2D) g);
			//System.err.println("clip " + g.getClip());
		}

		@Override
		public void doLayout() {
			super.doLayout();
			_layout();
		}

		//		@Override
		//		public Point getToolTipLocation(MouseEvent event) {
		//			return new Point(event.getX() + 5, event.getY() + 5);
		//			//return null;
		//		}
	}

}
