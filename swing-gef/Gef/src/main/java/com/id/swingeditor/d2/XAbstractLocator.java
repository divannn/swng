package com.id.swingeditor.d2;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author idanilov
 * TODO:finish D2 locator
 *
 */
public abstract class XAbstractLocator
		implements XLocator {

	private int relativePosition = XPositionConstants.CENTER;
	private int gap;

	/**
	 * Returns the reference point in absolute coordinates used to calculate the location.
	 * @return The reference point in absolute coordinates
	 * @since 2.0
	 */
	protected abstract Point getReferencePoint();

	/**
	 * Returns the position of the figure with respect to the center point. Possible values
	 * can be found in {@link PositionConstants} and include CENTER, NORTH, SOUTH, EAST, WEST,
	 * NORTH_EAST, NORTH_WEST, SOUTH_EAST, or SOUTH_WEST.
	 * 
	 * @return An int constant representing the relative position
	 * @since 2.0
	 */
	public int getRelativePosition() {
		return relativePosition;
	}

	/**
	 * Sets the position of the figure with respect to the center point. Possible values can
	 * be found in {@link PositionConstants} and include CENTER, NORTH, SOUTH, EAST, WEST,
	 * NORTH_EAST, NORTH_WEST, SOUTH_EAST, or SOUTH_WEST.
	 * 
	 * @param pos The relative position
	 * @since 2.0
	 */
	public void setRelativePosition(int pos) {
		relativePosition = pos;
	}

	/**
	 * Returns the number of pixels to leave between the figure being located and the
	 * reference point. Only used if {@link #getRelativePosition()} returns something other
	 * than {@link PositionConstants#CENTER}.
	 * 
	 * @return The gap
	 * @since 2.0
	 */
	public int getGap() {
		return gap;
	}

	/**
	 * Sets the gap between the reference point and the figure being placed. Only used if
	 * getRelativePosition() returns something other than  {@link PositionConstants#CENTER}.
	 * 
	 * @param i The gap
	 * @since 2.0
	 */
	public void setGap(int i) {
		gap = i;
	}

	/**
	 * Recalculates the position of the figure and returns the updated bounds.
	 * @param target The figure to relocate
	 */
	public void relocate(XFigure target) {
		//[ID]		
		//		Dimension prefSize = target.getPreferredSize();
		//		Point center = getReferencePoint();
		//		target.translateToRelative(center);
		//		target.setBounds(getNewBounds(prefSize, center));
	}

	/**
	 * Recalculate the location of the figure according to its desired position relative to
	 * the center point.
	 * 
	 * @param size The size of the figure
	 * @param center The center point
	 * @return The new bounds
	 * @since 2.0
	 */
	protected Rectangle getNewBounds(Dimension size, Point center) {
		Rectangle bounds = new Rectangle(center, size);

		bounds.x -= bounds.width / 2;
		bounds.y -= bounds.height / 2;

		int xFactor = 0, yFactor = 0;
		int position = getRelativePosition();

		if ((position & XPositionConstants.NORTH) != 0)
			yFactor = -1;
		else if ((position & XPositionConstants.SOUTH) != 0)
			yFactor = 1;

		if ((position & XPositionConstants.WEST) != 0)
			xFactor = -1;
		else if ((position & XPositionConstants.EAST) != 0)
			xFactor = 1;

		bounds.x += xFactor * (bounds.width / 2 + getGap());
		bounds.y += yFactor * (bounds.height / 2 + getGap());

		return bounds;
	}

}
