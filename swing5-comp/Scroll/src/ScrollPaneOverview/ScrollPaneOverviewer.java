package ScrollPaneOverview;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.awt.image.RasterFormatException;
import java.lang.ref.WeakReference;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

/**
 * @author idanilov
 *
 */
public class ScrollPaneOverviewer {

	//to avoid circular reference beetween JScrolPane and OverviewScrollPane.
	private WeakReference<JScrollPane> scrollPane;
	private ScrollImage display;
	private Popup popup;

	public static void install(final JScrollPane scrollPane) {
		new ScrollPaneOverviewer(scrollPane);
	}

	private ScrollPaneOverviewer(final JScrollPane scrollPane) {
		this.scrollPane = new WeakReference<JScrollPane>(scrollPane);
		JButton button = new ToggleButton();
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				showOverviewPanel();
			}
		});
		scrollPane.setCorner(ScrollPaneConstants.LOWER_TRAILING_CORNER, button);
	}

	private void hideOverviewPanel() {
		if (popup != null) {
			popup.hide();
			popup = null;
		}
	}

	private void showOverviewPanel() {
		if (popup != null) {
			return;
		}
		display = new ScrollImage();
		JPanel overviewPanel = new JPanel(new BorderLayout());
		overviewPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		overviewPanel.add(display);

		PopupFactory factory = PopupFactory.getSharedInstance();
		Dimension dSP = new Dimension();
		Point pSource = new Point();
		SwingUtilities.convertPointToScreen(pSource, scrollPane.get());

		scrollPane.get().getSize(dSP);
		Dimension dimensionPanel = overviewPanel.getPreferredSize();

		popup = factory.getPopup(scrollPane.get(), overviewPanel, pSource.x + dSP.width
				- dimensionPanel.width, pSource.y + dSP.height - dimensionPanel.height);
		popup.show();
		if (display.isDisplayDarkZone()) {
			display.addMouseMotionListener(new MouseMotionAdapter() {

				public void mouseMoved(MouseEvent e) {
					display.mouseOver(e.getX(), e.getY());
				}

				public void mouseDragged(MouseEvent e) {
					display.drag(e.getX(), e.getY());
				}
			});
			display.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent me) {
					display.endDrag(me.getX(), me.getY());
				}

				public void mousePressed(MouseEvent me) {
					display.beginDrag(me.getX(), me.getY());
				}

				public void mouseExited(MouseEvent me) {
					display.abortDrag();
					hideOverviewPanel();
				}

				public void mouseClicked(MouseEvent me) {
					hideOverviewPanel();
				}
			});
		} else {
			display.addMouseListener(new MouseAdapter() {

				public void mouseExited(java.awt.event.MouseEvent arg0) {
					hideOverviewPanel();
				}

				public void mouseClicked(MouseEvent arg0) {
					hideOverviewPanel();
				}
			});
		}
		try {
			Robot robot = new Robot();
			robot.mouseMove(pSource.x + dSP.width - 10, pSource.y + dSP.height - 10);
		} catch (AWTException unused) {
		}
	}

	/**
	 * @author idanilov
	 *
	 */
	private class ScrollImage extends JComponent {

		private BufferedImage resizedImage;
		private Image resizedImageDark;
		private double scaleFactor;
		private Rectangle rectLight = new Rectangle();
		private Rectangle rectVisibleResized;
		private int initialHorizontalValue;
		private int initialVerticalValue;
		private Dimension deltaMouseCenterLightRect = new Dimension();
		private boolean dragOnGoing;
		private boolean displayDarkZone;

		// darkFilter used to draw darker zones.
		private RGBImageFilter darkFilter = new RGBImageFilter() {

			public int filterRGB(int x, int y, int rgb) {
				int r = 0;
				int g = 0;
				int b = 0;
				int a = (rgb & 0xFF000000);
				int max = 0xFF;
				r = (rgb >> 16) & max;
				g = (rgb >> 8) & max;
				b = (rgb >> 0) & max;

				r = Math.min(max, (int) (r * 0.6));
				g = Math.min(max, (int) (g * 0.6));
				b = Math.min(max, (int) (b * 0.6));
				return (a | (r << 16) | (g << 8) | (b));
			}
		};

		private ScrollImage() {
			if (scrollPane.get().getViewport().getView() == null) {
				throw new RuntimeException("No view in viewport");
			}
			Component component = scrollPane.get().getViewport().getView();
			BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			component.paint(image.getGraphics());
			Dimension d = new Dimension();
			scrollPane.get().getViewport().getSize(d);

			int maxWidth = d.width / 2;
			int maxHeight = d.height / 2;

			double scalfactorX = maxWidth;
			scalfactorX /= image.getWidth();

			double scalfactorY = maxHeight;
			scalfactorY /= image.getHeight();

			this.scaleFactor = Math.min(scalfactorX, scalfactorY);

			rectLight.x = 0;
			rectLight.y = 0;

			Rectangle rectVisible = scrollPane.get().getVisibleRect();
			rectVisibleResized = new Rectangle(rectVisible);
			rectVisibleResized.height *= scaleFactor;
			rectVisibleResized.width *= scaleFactor;

			rectLight.height = rectVisibleResized.height;
			rectLight.width = rectVisibleResized.width;

			resizedImage = new BufferedImage((int) Math.ceil(image.getWidth() * scaleFactor),
					(int) Math.ceil(image.getHeight() * scaleFactor), BufferedImage.TYPE_INT_RGB);
			AffineTransform xform = new AffineTransform();
			xform.scale(scaleFactor, scaleFactor);

			Graphics2D g2 = (Graphics2D) resizedImage.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2.drawRenderedImage(image, xform);

			initialHorizontalValue = scrollPane.get().getHorizontalScrollBar().getValue();
			int hmax = scrollPane.get().getHorizontalScrollBar().getMaximum()
					- scrollPane.get().getHorizontalScrollBar().getVisibleAmount();
			int hmin = scrollPane.get().getHorizontalScrollBar().getMinimum();

			initialVerticalValue = scrollPane.get().getVerticalScrollBar().getValue();
			int vmax = scrollPane.get().getVerticalScrollBar().getMaximum()
					- scrollPane.get().getVerticalScrollBar().getVisibleAmount();
			int vmin = scrollPane.get().getVerticalScrollBar().getMinimum();

			int currentx = initialHorizontalValue - hmin;
			double rx = hmax - hmin;
			if (currentx != 0) {
				rx /= currentx;
			}
			int currenty = initialVerticalValue - vmin;
			double ry = vmax - vmin;
			if (currenty != 0) {
				ry /= currenty;
			}
			if (rx != 0 && ry != 0) {
				rectLight = new Rectangle();
				rectLight.x = (int) Math.ceil((resizedImage.getWidth() - rectVisibleResized.width)
						/ rx);
				rectLight.y = (int) Math
						.ceil((resizedImage.getHeight() - rectVisibleResized.height) / ry);
				rectLight.width = rectVisibleResized.width;
				rectLight.height = rectVisibleResized.height;
				checksRectlight();
				if (rectLight.width < resizedImage.getWidth()
						|| rectLight.height < resizedImage.getHeight()) {
					displayDarkZone = true;

					g2.setColor(Color.WHITE);
					g2.drawRect(rectLight.x, rectLight.y, rectLight.width, rectLight.height);
					try {
						resizedImageDark = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(resizedImage.getSource(), darkFilter));
					} catch (RasterFormatException unused) {
					}
				}
			}
		}

		public boolean isDisplayDarkZone() {
			return displayDarkZone;
		}

		private void checksRectlight() {
			if (rectLight.x < 0) {
				rectLight.x = 0;
			}
			if (rectLight.y < 0) {
				rectLight.y = 0;
			}
			if (rectLight.width > resizedImage.getWidth()) {
				rectLight.width = resizedImage.getWidth();
			}
			if (rectLight.height > resizedImage.getHeight()) {
				rectLight.height = resizedImage.getHeight();
			}
		}

		public void drag(int mx, int my) {
			if (dragOnGoing) {
				int x = mx + deltaMouseCenterLightRect.width;
				int y = my + deltaMouseCenterLightRect.height;
				Rectangle r = getLightedRectangle(x, y);
				if (!r.equals(rectLight)) {
					rectLight.setBounds(r);
					checksRectlight();
					ScrollImage.this.repaint();
					setScrollValues(x, y);
				}
				deltaMouseCenterLightRect.setSize(rectLight.x + (rectLight.width / 2) - mx,
						rectLight.y + (rectLight.height / 2) - my);
			}
		}

		public void endDrag(int mx, int my) {
			if (dragOnGoing) {
				dragOnGoing = false;
				setScrollValues(mx + deltaMouseCenterLightRect.width, my
						+ deltaMouseCenterLightRect.height);
				hideOverviewPanel();
				setCursor(Cursor.getDefaultCursor());
			}
		}

		public void beginDrag(int mx, int my) {
			if (mx > rectLight.x && mx < rectLight.x + rectLight.width && my > rectLight.y
					&& my < rectLight.y + rectLight.height) {
				deltaMouseCenterLightRect.setSize(rectLight.x + (rectLight.width / 2) - mx,
						rectLight.y + (rectLight.height / 2) - my);
				dragOnGoing = true;
			}
		}

		public void mouseOver(int mx, int my) {
			if (mx > rectLight.x && mx < rectLight.x + rectLight.width && my > rectLight.y
					&& my < rectLight.y + rectLight.height) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else {
				setCursor(Cursor.getDefaultCursor());
			}
		}

		public void abortDrag() {
			scrollPane.get().getHorizontalScrollBar().setValue(initialHorizontalValue);
			scrollPane.get().getVerticalScrollBar().setValue(initialVerticalValue);
		}

		/**
		 * Set new values for scrollbars, (x,y) is the center of the lighted
		 * rectangle
		 * 
		 * @param x
		 * @param y
		 */
		private void setScrollValues(int x, int y) {

			x = Math.max(x - (rectVisibleResized.width / 2), 0);
			y = Math.max(y - (rectVisibleResized.height / 2), 0);
			x = Math.min(x + rectVisibleResized.width, resizedImage.getWidth())
					- rectVisibleResized.width;
			y = Math.min(y + rectVisibleResized.height, resizedImage.getHeight())
					- rectVisibleResized.height;

			if (resizedImage.getHeight() != rectVisibleResized.height) {
				y *= scrollPane.get().getVerticalScrollBar().getMaximum()
						- scrollPane.get().getVerticalScrollBar().getVisibleAmount()
						- scrollPane.get().getVerticalScrollBar().getMinimum();
				y /= (resizedImage.getHeight() - rectVisibleResized.height);
				scrollPane.get().getVerticalScrollBar().setValue(y);
			}
			if (resizedImage.getWidth() != rectVisibleResized.width) {
				x *= scrollPane.get().getHorizontalScrollBar().getMaximum()
						- scrollPane.get().getHorizontalScrollBar().getVisibleAmount()
						- scrollPane.get().getHorizontalScrollBar().getMinimum();
				x /= (resizedImage.getWidth() - rectVisibleResized.width);
				scrollPane.get().getHorizontalScrollBar().setValue(x);
			}
		}

		/**
		 * Return the lighted rectangle, if bounds are checked
		 * 
		 * @param x
		 *            x coordinate of center of next position of the lighted
		 *            rectangle
		 * @param y
		 *            y coordinate of center of next position of the lighted
		 *            rectangle
		 */
		private Rectangle getLightedRectangle(int x, int y) {
			Rectangle r = new Rectangle(rectVisibleResized);
			// checks if lightedRect is not out of image.
			if (x < rectVisibleResized.width / 2) {
				r.x = 0;
			} else {
				r.x = x - (rectVisibleResized.width / 2);
			}

			if (y < rectVisibleResized.height / 2) {
				r.y = 0;
			} else {
				r.y = y - (rectVisibleResized.height / 2);
			}

			if (r.x + rectVisibleResized.width > resizedImage.getWidth()) {
				r.x = resizedImage.getWidth() - rectVisibleResized.width;
			}
			if (r.y + rectVisibleResized.height > resizedImage.getHeight()) {
				r.y = resizedImage.getHeight() - rectVisibleResized.height;
			}
			return r;

		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (displayDarkZone) {
				g.drawImage(resizedImageDark, 0, 0, null);
				BufferedImage image = resizedImage.getSubimage(rectLight.x, rectLight.y,
						rectLight.width, rectLight.height);
				g.drawImage(image, rectLight.x, rectLight.y, null);
				g.setColor(Color.BLACK);
				g.drawRect(rectLight.x, rectLight.y, rectLight.width, rectLight.height);
			} else {
				g.drawImage(resizedImage, 0, 0, this);
			}

		}

		public Dimension getPreferredSize() {
			return new Dimension(resizedImage.getWidth(), resizedImage.getHeight());
		}

	}

	/**
	 * Button installed into bottom-right corener of a scroll pane.  
	 * @author idanilov
	 *
	 */
	private class ToggleButton extends JButton {

		public ToggleButton() {
			super(new Icon() {

				public int getIconHeight() {
					return 11;
				}

				public int getIconWidth() {
					return 11;
				}

				public void paintIcon(Component c, Graphics g, int x, int y) {
					int xPoints[] = { 3, 9, 3 };
					int yPoints[] = { 3, 3, 9 };
					g.setColor(Color.BLACK);
					for (int i = 0; i < 3; i++) {
						xPoints[i] += x;
						yPoints[i] += y;
					}
					g.fillPolygon(new Polygon(xPoints, yPoints, 3));

				}

			});
		}

	}
}
