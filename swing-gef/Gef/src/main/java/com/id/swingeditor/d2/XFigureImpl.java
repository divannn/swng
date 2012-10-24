package com.id.swingeditor.d2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author idanilov
 * TODO: deffered validation is not supported (revalidate()). Think of it.
 */
public class XFigureImpl
		implements XFigure {

	private String name;
	private XFigure parent;
	private List<XFigure> children;

	private Rectangle bounds;
	private ILayoutManager layoutManager;
	private boolean visible;
	private boolean enabled;
	private boolean opaque;
	private Color bgColor;
	private XBorder border;
	private Cursor cursor;
	private String tooltip;

	public XFigureImpl() {
		children = new ArrayList<XFigure>(10);
		name = "";
		visible = true;
		enabled = true;
		bounds = new Rectangle(0, 0, 0, 0);
	}

	public XFigureImpl(String name) {
		this();
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		if (n == null) {
			n = "";
		}
		name = n;
	}

	public XFigure getParent() {
		return parent;
	}

	public void setParent(XFigure parent) {
		this.parent = parent;
	}

	public List<XFigure> getChildren() {
		return children;
	}

	public void add(XFigure figure) {
		add(figure, null, -1);
	}

	public void add(XFigure figure, int index) {
		add(figure, null, index);
	}

	public void add(XFigure figure, Object constraint) {
		add(figure, constraint, -1);
	}

	public void add(XFigure figure, Object constraint, int index) {
		if (figure == null) {
			return;
		}
		if (index < -1 || index > children.size())
			throw new IndexOutOfBoundsException("Index does not exist"); //$NON-NLS-1$
		//Check for cycle in hierarchy
		for (XFigure f = this; f != null; f = f.getParent()) {
			if (figure == f) {
				throw new IllegalArgumentException("Figure being added introduces cycle");
			}
		}
		//Detach the child from previous parent
		if (figure.getParent() != null) {
			figure.getParent().remove(figure);
		}
		if (index == -1) {
			children.add(figure);
		} else {
			children.add(index, figure);
		}
		figure.setParent(this);
		if (layoutManager != null) {
			layoutManager.setConstraint(figure, constraint);
		}
		//[ID]		
		//		revalidate();
		validate();
		//[ID]
		//		if (getFlag(FLAG_REALIZED))
		//			figure.addNotify();
		figure.repaint();
	}

	public void remove(XFigure figure) {
		if (figure == null) {
			return;
		}
		if ((figure.getParent() != this)) {
			throw new IllegalArgumentException("Figure is not a child");
		}
		//[ID]
		//if (getFlag(FLAG_REALIZED))
		//	figure.removeNotify();
		if (layoutManager != null) {
			layoutManager.remove(figure);
		}
		figure.erase();
		figure.setParent(null);
		children.remove(figure);
		//[ID]revalidate();
	}

	public Rectangle getBounds() {
		return bounds;
	}

	/* 
	 * Absolute coordinates.
	 */
	public void setBounds(Rectangle b) {
		if (b == null) {
			throw new IllegalArgumentException("Provide non null bounds");
		}
		Rectangle newRect = b.getBounds();
		boolean resize = true;
		boolean translate = true;
		Rectangle curRect = bounds.getBounds();
		resize = (newRect.width != curRect.width) || (newRect.height != curRect.height);
		translate = (newRect.x != curRect.x) || (newRect.y != curRect.y);
		if ((resize || translate) && isVisible()) {
			erase();
		}
		bounds = b;
		if (translate || resize) {
			//[ID]if (resize)
			//invalidate();
			validate();
			repaint();
		}
	}

	public void setLocation(int x, int y) {
		Rectangle r = getBounds().getBounds();
		if (r.x == x && r.y == y)
			return;
		r.setLocation(x, y);
		setBounds(r);
	}

	public void setSize(int w, int h) {
		Rectangle r = getBounds().getBounds();
		if (r.width == w && r.height == h)
			return;
		r.setSize(w, h);
		setBounds(r);
	}

	public void setConstraint(XFigure child, Object constraint) {
		if (child.getParent() != this)
			throw new IllegalArgumentException("Figure must be a child");
		if (layoutManager != null) {
			layoutManager.setConstraint(child, constraint);
		}
		//[ID]revalidate();
		validate();
	}

	public Rectangle getClientArea() {
		Rectangle result = new Rectangle(getBounds().getBounds());
		D2Util.shrink(result, getInsets());
		return result;
	}

	public XBorder getBorder() {
		return border;
	}

	public void setBorder(XBorder b) {
		border = b;
		//[ID]revalidate();
		repaint();
	}

	public Color getBackgroundColor() {
		return bgColor;
	}

	public void setBackgroundColor(Color c) {
		if (c != null && c.equals(getBackgroundColor()))
			return;
		bgColor = c;
		repaint();
	}

	public Insets getInsets() {
		if (border != null) {
			return border.getInsets(this);
		}
		return D2Util.EMPTY_INSTETS;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean v) {
		if (isVisible() == v)
			return;
		if (isVisible()) {
			erase();
		}
		visible = v;
		if (visible) {
			repaint();
		}
		//[ID]
		//revalidate();
	}

	public void erase() {
		if (getParent() == null || !isVisible())
			return;
		//[ID]
		//Rectangle r = new Rectangle(getBounds());
		//getParent().translateToParent(r);
		//getParent().repaint(r.x, r.y, r.width, r.height);
		getParent().repaint(getBounds());//ask parent to repaint my bounds.
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean e) {
		if (isEnabled() == e)
			return;
		enabled = e;
	}

	public boolean isOpaque() {
		return opaque;
	}

	public void setOpaque(boolean o) {
		if (isOpaque() == o)
			return;
		opaque = o;
		repaint();
	}

	public Cursor getCursor() {
		if (cursor == null && getParent() != null)
			return getParent().getCursor();
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		if (this.cursor == cursor)
			return;
		this.cursor = cursor;
		IEventDispatcher dispatcher = internalGetEventDispatcher();
		if (dispatcher != null)
			dispatcher.updateCursor();
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tip) {
		if (this.tooltip == tip)
			return;
		this.tooltip = tip;
		IEventDispatcher dispatcher = internalGetEventDispatcher();
		if (dispatcher != null)
			dispatcher.updateTooltip();
	}

	public ILayoutManager getLayoutManager() {
		return layoutManager;
	}

	public void setLayoutManager(ILayoutManager lm) {
		if (this.layoutManager == lm)
			return;
		layoutManager = lm;
	}

	public void validate() {
		//[ID]
		//		if (isValid())
		//			return;
		//		setValid(true);
		layout();
		for (int i = 0; i < children.size(); i++) {
			children.get(i).validate();
		}
	}

	//[ID]
	//    public void revalidate() {
	//        invalidate();
	//        if (getParent() == null || isValidationRoot())
	//            getUpdateManager().addInvalidFigure(this);
	//        else
	//            getParent().revalidate();
	//    }
	//	protected boolean isValidationRoot() {
	//		return false;
	//	}

	protected void layout() {
		if (layoutManager != null && bounds != null) {
			layoutManager.layout(this);
		}
	}

	/* Paints figure. For each invokation new graphics object is created.
	 * It's used to paint figure itself, client area and border. 
	 */
	public void paint(Graphics2D g) {
		try {
			//create new graphics for each figure.
			g = (Graphics2D) g.create();
			Rectangle r = getBounds().getBounds();
			//here each figure is kayed out so clip with its bounds.
			g.clipRect(r.x, r.y, r.width, r.height);

			if (getBackgroundColor() != null) {
				g.setColor(getBackgroundColor());
			}
			//[ID]		
			//		if (getLocalForegroundColor() != null)
			//			graphics.setForegroundColor(getLocalForegroundColor());
			paintFigure(g);
			paintClientArea(g);
			paintBorder(g);
		} finally {
			g.dispose();
		}
	}

	protected void paintFigure(Graphics2D graphics) {
		if (isOpaque()) {
			Rectangle b = getBounds().getBounds();
			graphics.fillRect(b.x, b.y, b.width, b.height);
		}
	}

	protected void paintClientArea(Graphics2D graphics) {
		//[ID]
		//        if (useLocalCoordinates()) {
		//            graphics.translate(getBounds().x + getInsets().left, getBounds().y + getInsets().top);
		//            if (!optimizeClip)
		//                graphics.clipRect(getClientArea(PRIVATE_RECT));
		//            graphics.pushState();
		//            paintChildren(graphics);
		//            graphics.popState();
		//            graphics.restoreState();
		//        } else {

		//graphics.setClip(getClientArea());
		//graphics.pushState();
		paintChildren(graphics);
		//graphics.popState();
		//graphics.restoreState();
		//        }
	}

	protected void paintChildren(Graphics2D graphics) {
		for (int i = 0; i < children.size(); i++) {
			XFigure child = children.get(i);
			Rectangle childBounds = child.getBounds().getBounds();
			if (child.isVisible()
					&& graphics.hitClip(childBounds.x, childBounds.y, childBounds.width,
							childBounds.height)) {
				//[ID] clip performed in paint().
				//graphics.clipRect(childBounds.x, childBounds.y, 
				//						childBounds.width,childBounds.height);
				child.paint(graphics);
				//[ID]graphics.restoreState();
			}
		}
	}

	protected void paintBorder(Graphics2D graphics) {
		if (border != null) {
			border.paint(this, graphics);
		}
	}

	public void repaint() {
		repaint(getBounds());
	}

	public void repaint(Rectangle rect) {
		if (isVisible()) {
			if (parent != null) {
				parent.repaint(rect);
			}
		}
	}

	public boolean containsPoint(int x, int y) {
		return getBounds().contains(x, y);
	}

	public void handleMouseDragged(MEvent e) {
		// TODO Auto-generated method stub

	}

	public void handleMouseEntered(MEvent e) {
		// TODO Auto-generated method stub

	}

	public void handleMouseExited(MEvent e) {
		// TODO Auto-generated method stub

	}

	public void handleMouseMoved(MEvent e) {
		// TODO Auto-generated method stub

	}

	public void handleMousePressed(MEvent e) {
		// TODO Auto-generated method stub

	}

	public void handleMouseReleased(MEvent e) {
		// TODO Auto-generated method stub

	}

	public XFigure findMouseEventTargetAt(int x, int y) {
		if (!containsPoint(x, y)) {
			return null;
		}
		XFigure f = findMouseEventTargetInDescendantsAt(x, y);
		if (f != null) {
			return f;
		}
		//[ID]
		//		if (isMouseEventTarget())
		return this;
	}

	protected XFigure findMouseEventTargetInDescendantsAt(int x, int y) {
		Point PRIVATE_POINT = new Point(x, y);
		//[ID]
		//translateFromParent(PRIVATE_POINT);

		if (!getClientArea().contains(PRIVATE_POINT))
			return null;

		XFigure fig;
		for (int i = children.size(); i > 0;) {
			i--;
			fig = children.get(i);
			if (fig.isVisible() && fig.isEnabled()) {
				if (fig.containsPoint(PRIVATE_POINT.x, PRIVATE_POINT.y)) {
					fig = fig.findMouseEventTargetAt(PRIVATE_POINT.x, PRIVATE_POINT.y);
					return fig;
				}
			}
		}
		return null;
	}

	public final XFigure findFigureAt(int x, int y) {
		return findFigureAt(x, y, IdentitySearch.INSTANCE);
	}

	public final XFigure findFigureAt(Point pt) {
		//[ID]return findFigureAtExcluding(pt.x, pt.y, Collections.EMPTY_LIST);
		//just to be consistent.
		return findFigureAt(pt.x, pt.y);
	}

	public final XFigure findFigureAtExcluding(int x, int y, Collection c) {
		return findFigureAt(x, y, new ExclusionSearch(c));
	}

	public XFigure findFigureAt(int x, int y, TreeSearch search) {
		if (!containsPoint(x, y))
			return null;
		if (search.prune(this))
			return null;
		XFigure child = findDescendantAtExcluding(x, y, search);
		if (child != null)
			return child;
		if (search.accept(this))
			return this;
		return null;
	}

	/**
	 * Returns a descendant of this Figure such that the Figure returned contains the point
	 * (x, y), and is accepted by the given TreeSearch. Returns <code>null</code> if none 
	 * found.
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 * @param search the TreeSearch
	 * @return The descendant Figure at (x,y)
	 */
	protected XFigure findDescendantAtExcluding(int x, int y, TreeSearch search) {
		Point PRIVATE_POINT = new Point(x, y);
		//[ID]
		//translateFromParent(PRIVATE_POINT);
		if (!getClientArea().contains(PRIVATE_POINT))
			return null;

		x = PRIVATE_POINT.x;
		y = PRIVATE_POINT.y;
		XFigure fig;
		for (int i = children.size(); i > 0;) {
			i--;
			fig = children.get(i);
			if (fig.isVisible()) {
				fig = fig.findFigureAt(x, y, search);
				if (fig != null)
					return fig;
			}
		}
		//No descendants were found
		return null;
	}

	//[ID]
	//	protected void setChildrenEnabled(boolean value) {
	//		FigureIterator iterator = new FigureIterator(this);
	//		while (iterator.hasNext())
	//			iterator.nextFigure().setEnabled(value);
	//	}

	public IEventDispatcher internalGetEventDispatcher() {
		if (getParent() != null)
			return getParent().internalGetEventDispatcher();
		return null;
	}

	@Override
	public String toString() {
		return getClass().getName() + " | " + name;
	}

	/**
	 * A search which does not filter any figures.
	 * @author idanilov
	 *
	 */
	protected static final class IdentitySearch
			implements TreeSearch {

		/**
		 * The singleton instance.
		 */
		public static final TreeSearch INSTANCE = new IdentitySearch();

		private IdentitySearch() {
		}

		/**
		 * Always returns <code>true</code>.
		 * @see TreeSearch#accept(IFigure)
		 */
		public boolean accept(XFigure f) {
			return true;
		}

		/**
		 * Always returns <code>false</code>.
		 * @see TreeSearch#prune(IFigure)
		 */
		public boolean prune(XFigure f) {
			return false;
		}
	}
}
