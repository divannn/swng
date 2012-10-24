package com.id.swingeditor.gef.editpart;

import com.id.swingeditor.d2.XFigure;

/**
 * @author idanilov
 *
 */
public interface XLayerManager {

	Object ID = new Object();

	XFigure getLayer(Object key);

	/**
	 * Identifies the layer containing the primary pieces of the application.
	 */
	String PRIMARY_LAYER = "Primary Layer"; //$NON-NLS-1$

	/**
	 * Identifies the layer containing connections, which typically appear
	 * on top of anything in the primary layer.
	 */
	//[ID]
	//String CONNECTION_LAYER = "Connection Layer"; //$NON-NLS-1$

	/**
	 * Identifies the layer where the grid is painted.
	 */
	//[ID]
	//String GRID_LAYER = "Grid Layer"; //$NON-NLS-1$

	/**
	 * Identifies the layer where Guides add feedback to the primary viewer.
	 */
	//[ID]
	//String GUIDE_LAYER = "Guide Layer"; //$NON-NLS-1$

	/**
	 * Identifies the layer containing handles, which are typically editing
	 * decorations that appear on top of any model representations.
	 */
	String HANDLE_LAYER = "Handle Layer"; //$NON-NLS-1$

	/**
	 * The layer containing feedback, which are generally temporary visuals that
	 * appear on top of all other visuals.
	 */
	String FEEDBACK_LAYER = "Feedback Layer"; //$NON-NLS-1$

	/**
	 * The layer containing scaled feedback.
	 */
	//[ID]
	//String SCALED_FEEDBACK_LAYER = "Scaled Feedback Layer"; //$NON-NLS-1$

	/**
	 * The layer that contains all printable layers.
	 */
	String PRINTABLE_LAYERS = "Printable Layers"; //$NON-NLS-1$

	/**
	 * The layer that contains all scaled layers.
	 */
	//[ID]
	//String SCALABLE_LAYERS = "Scalable Layers"; //$NON-NLS-1$

}
