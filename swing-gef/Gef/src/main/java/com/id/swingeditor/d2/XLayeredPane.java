package com.id.swingeditor.d2;

import java.util.ArrayList;
import java.util.List;

/**
 * A figure capable of holding any number of layers. Only layers can be added to this 
 * figure. Layers are added to this figure with thier respective keys, which are used to
 * identify them.
 * @author idanilov
 *
 */
public class XLayeredPane extends XLayer {

	private List layerKeys = new ArrayList();

	public XLayeredPane() {
		this(null);
	}

	public XLayeredPane(String name) {
		super(name);
		setLayoutManager(new StackLayout());
	}

	/**
	 * Adds the given layer figure, identifiable with the given key, at the specified index. 
	 * While adding the layer, it informs the surrounding layers of the addition.
	 *
	 * @param figure the layer
	 * @param layerKey the layer's key
	 * @param index the index where the layer should be added
	 */
	public void add(XFigure figure, Object layerKey, int index) {
		if (index == -1)
			index = layerKeys.size();
		super.add(figure, null, index);
		layerKeys.add(index, layerKey);
	}

	/**
	 * Adds the given layer, identifiable with the given key, under the <i>after</i> layer 
	 * provided in the input.
	 *
	 * @param layer the layer
	 * @param key the layer's key
	 * @param after the layer under which the input layer should be added
	 */
	public void addLayerAfter(XLayer layer, Object key, Object after) {
		int index = layerKeys.indexOf(after);
		add(layer, key, ++index);
	}

	/**
	 * Adds the given layer, identifiable with the given key, above the <i>before</i> layer 
	 * provided in the input.
	 *
	 * @param layer the layer
	 * @param key the layer's key
	 * @param before the layer above which the input layer should be added
	 * @since 2.0
	 */
	public void addLayerBefore(XLayer layer, Object key, Object before) {
		int index = layerKeys.indexOf(before);
		add(layer, key, index);
	}

	/**
	 * Returns the layer identified by the key given in the input.
	 *
	 * @param key the key to identify the desired layer
	 * @return the desired layer
	 */
	public XLayer getLayer(Object key) {
		int index = layerKeys.indexOf(key);
		if (index == -1)
			return null;
		return (XLayer) getChildren().get(index);
	}

	/**
	 * Returns the layer at the specified index in this pane.
	 *
	 * @param index the index of the desired layer
	 * @return the desired layer
	 */
	protected XLayer getLayer(int index) {
		return (XLayer) getChildren().get(index);
	}

	/**
	 * @see org.eclipse.draw2d.IFigure#remove(org.eclipse.draw2d.IFigure)
	 */
	public void remove(XFigure figure) {
		int index = getChildren().indexOf(figure);
		if (index != -1)
			layerKeys.remove(index);
		super.remove(figure);
	}

	/**
	 * Removes the layer identified by the given key from this layered pane.
	 *
	 * @param key the key of the layer to be removed
	 */
	public void removeLayer(Object key) {
		removeLayer(layerKeys.indexOf(key));
	}

	/**
	 * Removes the given layer from this layered pane.
	 *
	 * @deprecated call {@link IFigure#remove(IFigure)} instead
	 * @param layer the layer to be removed
	 */
	public void removeLayer(XFigure layer) {
		remove(layer);
	}

	/**
	 * Removes the layer at the specified index from the list of layers in this layered pane. 
	 * It collapses the layers, occupying the space vacated by the removed layer.
	 *
	 * @param index the index of the layer to be removed
	 */
	protected void removeLayer(int index) {
		XLayer removeLayer = getLayer(index);
		remove(removeLayer);
	}

}
