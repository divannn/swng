package com.id.swingeditor.d2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.List;

/**
 * @author idanilov
 *
 */
public interface XFigure {

	String getName();

	void setName(String name);

	XFigure getParent();

	void setParent(XFigure parent);

	List<XFigure> getChildren();

	void add(XFigure figure);

	void add(XFigure figure, int index);
	
	void add(XFigure figure, Object constraint);
	
	void add(XFigure figure, Object constraint, int index);

	void remove(XFigure figure);

	Rectangle getBounds();

	void setBounds(Rectangle b);

	void setLocation(int x, int y);

	void setSize(int w, int h);

	void setConstraint(XFigure child, Object constraint);

	XBorder getBorder();

	void setBorder(XBorder border);

	Color getBackgroundColor();

	void setBackgroundColor(Color c);

	Insets getInsets();

	Rectangle getClientArea();

	ILayoutManager getLayoutManager();

	void setLayoutManager(ILayoutManager lm);

	boolean containsPoint(int x, int y);

	void erase();

	boolean isVisible();

	void setVisible(boolean v);

	boolean isEnabled();

	void setEnabled(boolean e);

	boolean isOpaque();

	void setOpaque(boolean o);

	/**
	 * @return The Cursor used when the mouse is over this IFigure
	 */
	Cursor getCursor();

	void setCursor(Cursor cursor);

	String getTooltip();

	void setTooltip(String tip);

	void validate();

	//void revalidate();

	void paint(Graphics2D g);

	void repaint();

	void repaint(Rectangle rect);

	XFigure findMouseEventTargetAt(int x, int y);

	/**
	 * Returns the IFigure at the specified location. May return <code>this</code> or
	 * <code>null</code>.
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 * @return The IFigure at the specified location
	 */
	XFigure findFigureAt(int x, int y);

	/**
	 * Returns the IFigure at the specified location based on the conditional TreeSearch.  May
	 * return <code>this</code> or <code>null</code>
	 * @param x the X coordinate
	 * @param y the Y coordinate
	 * @param search the conditional TreeSearch
	 * @return the IFigure at the specified location
	 */
	XFigure findFigureAt(int x, int y, TreeSearch search);

	/**
	 * Returns the IFigure at the specified location. May return <code>this</code> or
	 * <code>null</code>.
	 * @param p The point
	 * @return The IFigure at the specified location
	 */
	XFigure findFigureAt(Point p);

	/**
	 * Returns the IFigure at the specified location, excluding any IFigures in
	 * <code>collection</code>. May return <code>this</code> or <code>null</code>.
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 * @param collection A collection of IFigures to be excluded
	 * @return The IFigure at the specified location, excluding any IFigures in collection
	 */
	XFigure findFigureAtExcluding(int x, int y, Collection collection);

	void handleMouseEntered(MEvent e);

	void handleMouseExited(MEvent e);

	void handleMousePressed(MEvent e);

	void handleMouseReleased(MEvent e);

	void handleMouseMoved(MEvent e);

	void handleMouseDragged(MEvent e);

	/**
	 * This method is <b>for internal purposes only</b> and should not be called.
	 * @return The event dispatcher
	 */
	IEventDispatcher internalGetEventDispatcher();

}
