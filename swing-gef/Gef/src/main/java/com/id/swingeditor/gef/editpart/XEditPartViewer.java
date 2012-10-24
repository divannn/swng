package com.id.swingeditor.gef.editpart;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;

import javax.swing.JComponent;

import com.id.swingeditor.d2.XFigure;
import com.id.swingeditor.gef.EditDomain;

/**
 * @author idanilov
 *
 */
public interface XEditPartViewer {

	/**
	 * An object which evaluates an EditPart for an arbitrary property. Conditionals are used
	 * when querying a viewer for an editpart.
	 * @author hudsonr
	 */
	interface Conditional {

		/**
		 * Returns <code>true</code> if the editpart meets this condition.
		 * @param editpart the editpart being evaluated
		 * @return <code>true</code> if the editpart meets the condition
		 */
		boolean evaluate(XEditPart editpart);
	}

	JComponent getControl();

//	void setControl(JComponent control);

	XRootEditPart getRootEditPart();

	/**
	 * Sets the <i>root</i> of this viewer.  The root should not be confused with the
	 * <i>contents</i>.
	 * @param root the RootEditPart
	 * @see #getRootEditPart()
	 * @see #getContents()
	 */
	void setRootEditPart(XRootEditPart root);

	/**
	 * Returns the {@link EditDomain EditDomain} to which this viewer belongs.
	 * @return the viewer's EditDomain
	 */
	EditDomain getEditDomain();

	/**
	 * Sets the <code>EditDomain</code> for this viewer. The Viewer will route all mouse and
	 * keyboard events to the EditDomain.
	 * @param domain The EditDomain
	 */
	void setEditDomain(EditDomain domain);

	/**
	 * Returns the <code>EditPartFactory</code> for this viewer.  The EditPartFactory is used
	 * to create the <i>contents</i> EditPart when {@link #setContents(Object)} is called. It
	 * is made available so that other EditParts can use it to create their children or
	 * connection editparts.
	 * @return EditPartFactory
	 */
	XEditPartFactory getEditPartFactory();

	/**
	 * Sets the EditPartFactory.
	 * @param factory the factory
	 * @see #getEditPartFactory()
	 */
	void setEditPartFactory(XEditPartFactory factory);

	/**
	 * Returns the <i>contents</i> of this Viewer.  The contents is the EditPart associated
	 * with the top-level model object. It is considered to be "The Diagram".  If the user has
	 * nothing selected, the <i>contents</i> is implicitly the selected object.
	 * <P>The <i>Root</i> of the Viewer is different.  By constrast, the root is never
	 * selected or targeted, and does not correspond to something in the model.
	 * @see #getRootEditPart()
	 * @return the <i>contents</i> <code>EditPart</code>
	 */
	XEditPart getContents();

	/**
	 * Sets the contents for this Viewer. The contents can also be set using {@link
	 * #setContents(Object)}.
	 * @param editpart the contents
	 * @see #getRootEditPart()
	 */
	void setContents(XEditPart editpart);

	/**
	 * Returns the {@link Map} for registering <code>EditParts</code> by <i>Keys</i>. 
	 * EditParts may register themselves using any method, and may register themselves
	 * with multiple keys. The purpose of such registration is to allow an EditPart to be
	 * found by other EditParts, or by listeners of domain notifiers. By default, EditParts
	 * are registered by their model.
	 * <P>
	 * Some models use a "domain" notification system, in which all changes are dispatched to
	 * a single listener. Such a listener might use this map to lookup editparts for a given
	 * model, and then ask the editpart to update.
	 * @return the registry map
	 */
	Map<Object, XEditPart> getEditPartRegistry();

	/**
	 * Returns the {@link Map} for associating <i>visual parts</i> with their
	 * <code>EditParts</code>. This map is used for hit-testing.  Hit testing is performed by
	 * first determining which visual part is hit, and then mapping that part to an
	 * <code>EditPart</code>.  What consistutes a <i>visual part</i> is viewer-specific. 
	 * Examples include <code>Figures</code> and <code>TreeItems</code>.
	 * @return the visual part map
	 */
	Map<XFigure, XEditPart> getVisualPartMap();

	/**
	 * Returns <code>null</code> or the <code>EditPart</code> associated with the specified
	 * location. The location is relative to the client area of the Viewer's
	 * <code>Control</code>.  An EditPart is not directly visible.  It is targeted using its
	 * <i>visual part</i> which it registered using the {@link #getVisualPartMap() visual part
	 * map}.  What constitutes a <i>visual part</i> is viewer-specific.  Examples include
	 * Figures and TreeItems.
	 * @param location The location
	 * @return <code>null</code> or an EditPart
	 */
	XEditPart findObjectAt(Point location);

	/**
	 * Returns <code>null</code> or the <code>EditPart</code> at the specified location,
	 * excluding the specified set.  This method behaves similarly to {@link
	 * #findObjectAt(Point)}.
	 * @param location The mouse location
	 * @param exclusionSet The set of EditParts to be excluded
	 * @return <code>null</code> or an EditPart
	 */
	XEditPart findObjectAtExcluding(Point location, Collection<XEditPart> exclusionSet);

	/**
	 * Returns <code>null</code> or the <code>EditPart</code> at the specified location,
	 * using the given exclusion set and conditional. This method behaves similarly to {@link
	 * #findObjectAt(Point)}.
	 * @param location The mouse location
	 * @param exclusionSet The set of EditParts to be excluded
	 * @param condition the Conditional used to evaluate a potential hit
	 * @return <code>null</code> or an EditPart
	 */
	XEditPart findObjectAtExcluding(Point location, Collection<XEditPart> exclusionSet,
			Conditional condition);
}