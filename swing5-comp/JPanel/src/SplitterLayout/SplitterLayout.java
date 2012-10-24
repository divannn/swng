package SplitterLayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Hashtable;

/** SplitterLayout is a layout manager that will layout a container holding
 other components and SplitterBars.

 <p>Each component added to a container to be laid out using SplitterLayout
 must provide a String containing a "weight" for the component.  This
 weight will be used to determine the initial spacing of all components
 being laid out.  The weight numbers are arbitrary integers.  The
 amount of space initially allocated for a component is
 <pre>
 (wc/wt) * (size-insets-splitterSize)
 </pre>
 <p>where
 <dl>
 <dt>wc
 <dd>the weight number for the component
 <dt>wt
 <dd>the total weight of all visible components in the container
 <dt>size
 <dd>the space free to display the components
 <dt>insets
 <dd>space used by insets in the container
 <dt>splitterSize
 <dd>amount of space needed to display SplitterBars
 </dl>

 <p>If the container being laid out holds no SplitterBars, SplitterLayout
 acts like a relational-weight layout manager.  All components are always
 laid out based on their proportionate weights.

 <p>If the container being laid out holds some SplitterBars, SplitterLayout
 will initially size all non JSplitterBar components based on their weights.
 Any succesive layouts are computed strictly on the locations of the
 SplitterBars.

 <p>SplitterLayout can be oriented Horizontally or Vertically.  Any SpliterBars
 placed in the container will automatically be oriented.

 <p>If a JSplitterBar has been modified (adding components to it) you will
 need to add JSplitterSpace components to it.  See JSplitterBar for more
 details.

 <p><b>Known Problems</b>:
 <ul>
 <li>If there are any SplitterBars contained in the container,
 it is best to have them between <u>every</u> non-JSplitterBar.
 Otherwise, once SplitterBars are moved, some components will
 use their proportional size while others will use the
 JSplitterBar positions.  (Non-Splitterbars will check the next
 component to see if it's a JSplitterBar.  If it's not, it uses
 its proportional size.)  This may eventually be changed...
 <li>Results of adding new SplitterBars to an existing (and user-
 interacted) SplitterLayout-laid container might be a bit
 unpredictable.  The safest way to ensure the container is laid
 out correctly would be to explicitly set all pre-existing
 JSplitterBar positions to (0,0).  This will cause the relational
 layout algorithm to take effect.
 </ul>
 @author idanilov
 */
public class SplitterLayout
		implements LayoutManager2, java.io.Serializable {

	/** Aligns components vertically -- SplitterBars will move up/down */
	public static final int VERTICAL = 0;
	/** Aligns components horizontally -- SplitterBars will move left-right */
	public static final int HORIZONTAL = 1;

	public static JSplitterBar dragee;

	private int lastW = -1, lastH = -1;
	private boolean newComponentAdded;
	private Hashtable relations;

	private int fieldOrientation = VERTICAL;

	/** Create a new SplitterLayout -- default orientation is VERTICAL */
	public SplitterLayout() {
		this(VERTICAL);
	}

	/** Create a new SplitterLayout
	 @param orientation -- VERTICAL or HORIZONTAL
	 */
	public SplitterLayout(int orientation) {
		setOrientation(orientation);
		relations = new Hashtable();
	}

	/** Adds a component w/ constraints to the layout.  This should only
	 be called by java.awt.Container's add method.
	 */
	public final void addLayoutComponent(Component comp, Object constraints) {
		if (constraints == null)
			constraints = "1";
		if (constraints instanceof Integer) {
			relations.put(comp, constraints);
			newComponentAdded = true;
		} else
			addLayoutComponent((String) constraints, comp);
	}

	/** Adds a component w/ a String constraint to the layout.  This should
	 only be called by java.awt.Container's add method.
	 */
	public final void addLayoutComponent(String name, Component comp) {
		newComponentAdded = true;
		if (comp instanceof JSplitterBar) {
			((JSplitterBar) comp).setOrientation(getOrientation());
		} else {
			if (name == null)
				name = "1";
			try {
				relations.put(comp, Integer.decode(name));
			} catch (NumberFormatException e) {
				relations.put(comp, new Integer(1));
			}
		}
	}

	public final Dimension checkLayoutSize(Container target, boolean getPrefSize) {
		Dimension dim = new Dimension(0, 0);
		Component c[] = target.getComponents();

		Dimension d;
		for (int i = 0; i < c.length; i++)
			if (c[i].isVisible()) {
				if (getPrefSize || (c[i] instanceof JSplitterBar))
					d = c[i].getPreferredSize();
				else
					d = c[i].getMinimumSize();
				if (getOrientation() == VERTICAL) {
					dim.width = Math.max(d.width, dim.width);
					dim.height += d.height;
				} else {
					dim.height = Math.max(d.height, dim.height);
					dim.width += d.width;
				}
			}

		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;

		return dim;
	}

	/** Tells the caller that we prefer to be centered */
	public final float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	/** Tells the caller that we prefer to be centered */
	public final float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}

	/**
	 * Gets the orientation property (int) value.
	 * @return The orientation property value.
	 * @see #setOrientation
	 */
	public int getOrientation() {
		/* Returns the orientation property value. */
		return fieldOrientation;
	}

	/** Does not have any effect (overridden to null the effect) */
	public final void invalidateLayout(Container target) {
	}

	/** Lays out the components in the specified container by telling
	 then what their size will be
	 */
	public final void layoutContainer(Container target) {
		Insets insets = target.getInsets();
		Dimension dim = target.getSize();
		int top = insets.top;
		int bottom = dim.height - insets.bottom;
		int left = insets.left;
		int right = dim.width - insets.right;

		boolean reScaleW = false, reScaleH = false;
		float scaleW = 0, scaleH = 0;

		// if the width/height has changed, scale the splitter bar positions
		if (lastW == -1) { // save it the first time
			lastW = dim.width;
			lastH = dim.height;
		} else {
			if (lastW != dim.width) {
				reScaleW = true;
				scaleW = (float) dim.width / (float) lastW;
				lastW = dim.width;
			}
			if (lastH != dim.height) {
				reScaleH = true;
				scaleH = (float) dim.height / (float) lastH;
				lastH = dim.height;
			}
		}

		dim.width = right - left;
		dim.height = bottom - top;

		// find out the totals we need to deal with...
		int relativeSize = 0;
		int numRelatives = 0;

		Component c[] = target.getComponents();
		Object pSize[] = new Object[c.length];
		int orientation = getOrientation();
		for (int i = 0; i < c.length; i++) {
			if (c[i].isVisible())
				if (c[i] instanceof JSplitterBar) {
					((JSplitterBar) c[i]).setOrientation(orientation);
					pSize[i] = c[i].getPreferredSize();
					if (orientation == VERTICAL) {
						dim.height -= ((Dimension) pSize[i]).height;
						if (reScaleH) {
							Point p = c[i].getLocation();
							c[i].setLocation(p.x, (int) (p.y * scaleH)); // dims set later
						}
					} else {
						dim.width -= ((Dimension) pSize[i]).width;
						if (reScaleW) {
							Point p = c[i].getLocation();
							c[i].setLocation((int) (p.x * scaleW), p.y); // dims set later
						}
					}
				} else {
					pSize[i] = relations.get(c[i]);
					relativeSize += ((Integer) pSize[i]).intValue();
					numRelatives++;
				}
		}

		// for each component being laid out, set its size
		for (int i = 0; i < c.length; i++)
			if (c[i].isVisible()) {
				Rectangle r = c[i].getBounds();
				if (c[i] instanceof JSplitterBar)
					if (orientation == VERTICAL) {
						if (r.x != left || r.y != top || r.width != dim.width
								|| r.height != ((Dimension) pSize[i]).height)
							c[i].setBounds(left, top, dim.width, ((Dimension) pSize[i]).height);
						top += ((Dimension) pSize[i]).height;
					} else {
						if (r.x != left || r.y != top || r.height != dim.height
								|| r.width != ((Dimension) pSize[i]).width)
							c[i].setBounds(left, top, ((Dimension) pSize[i]).width, dim.height);
						left += ((Dimension) pSize[i]).width;
					}
				else {
					if (i == (c.length - 1)) {
						if (orientation == VERTICAL) {
							if (r.x != left || r.y != top || r.width != dim.width
									|| r.height != (bottom - top))
								c[i].setBounds(left, top, dim.width, bottom - top);
						} else {
							if (r.x != left || r.y != top || r.width != (right - left)
									|| r.height != dim.height)
								c[i].setBounds(left, top, right - left, dim.height);
						}
					} else {
						// get pos of splitter bar
						Point p = c[i + 1].getLocation();
						if (!newComponentAdded && (c[i + 1] instanceof JSplitterBar)
								&& (p.x != 0 || p.y != 0)) {
							if (orientation == VERTICAL) {
								if (r.x != left || r.y != top || r.width != dim.width
										|| r.height != (p.y - top))
									c[i].setBounds(left, top, dim.width, p.y - top);
								top = p.y;
							} else {
								if (r.x != left || r.y != top || r.width != (p.x - left)
										|| r.height != dim.height)
									c[i].setBounds(left, top, p.x - left, dim.height);
								left = p.x;
							}
						} else {
							int rel = ((Integer) pSize[i]).intValue();
							float ratio = ((float) rel / (float) relativeSize);
							if (orientation == VERTICAL) {
								ratio *= dim.height;
								if (r.x != left || r.y != top || r.width != dim.width
										|| r.height != (int) ratio)
									c[i].setBounds(left, top, dim.width, (int) ratio);
								top += (int) ratio;
							} else {
								ratio *= dim.width;
								if (r.x != left || r.y != top || r.width != (int) ratio
										|| r.height != dim.height)
									c[i].setBounds(left, top, (int) ratio, dim.height);
								left += (int) ratio;
							}
						}
					}
				}
			}
		newComponentAdded = false;
	}

	/** Determines the maximum amount of space that could be used
	 when laying out the components in the specified container.
	 @param -- the container being laid out
	 */
	public final Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/** Determines the minimum amount of room requested for the layout
	 of components contained in the specified container.
	 @param target -- the Container being laid out
	 */
	//    public final Dimension minimumLayoutSize(Container target)   {return checkLayoutSize(target, false);}
	public final Dimension minimumLayoutSize(Container target) {
		return checkLayoutSize(target, true);
	}

	// TEMP -- CHECK TO SEE HOW minsize==prefsize seems

	/** Determines the preferred amount of room requested for the layout
	 of components contained in the specified container.
	 @param target -- the Container being laid out
	 */
	public final Dimension preferredLayoutSize(Container target) {
		return checkLayoutSize(target, true);
	}

	/** Removes a component from the layout.  This should
	 only be called by java.awt.Container's remove method.
	 */
	public final void removeLayoutComponent(Component comp) {
		relations.remove(comp);
		newComponentAdded = true; // so layout gets re-adjusted
	}

	/**
	 * Sets the orientation property (int) value.
	 * @param orientation The new value for the property.
	 * @see #getOrientation
	 */
	public void setOrientation(int orientation) {
		fieldOrientation = orientation;
		return;
	}

	public void swapOrientation(Container container) {
		setOrientation((getOrientation() == HORIZONTAL) ? VERTICAL : HORIZONTAL);
		Component comps[] = container.getComponents();
		for (int i = container.getComponentCount() - 1; i > -1; i--) {
			if (comps[i] instanceof JSplitterBar)
				((JSplitterBar) comps[i]).swapOrientation();
			comps[i].invalidate();
		}
		newComponentAdded = true; // to force re-position of splitter bars
		container.validate();
	}

	/** Returns a String representation of the Layout */
	public final String toString() {
		if (getOrientation() == VERTICAL) {
			return getClass().getName() + "[orientation=VERTICAL]";
		} else {
			return getClass().getName() + "[orientation=HORIZONTAL]";
		}
	}
}