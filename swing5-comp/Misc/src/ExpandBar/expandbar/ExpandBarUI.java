package ExpandBar.expandbar;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.ListModel;

/**
 * @author idanilov
 *
 */
public class ExpandBarUI extends JPanel {

	private JExpandBar expandBar;
	private HashMap<ExpandItem,ExpandItemUI> model2viewMap;
	private JPanel contents;
	
	public ExpandBarUI(final JExpandBar bar) {
		expandBar = bar;
		model2viewMap = new HashMap<ExpandItem,ExpandItemUI>();
		createContents();
	}

	protected void createContents() {
		setLayout(new BorderLayout());
		contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		add(contents,BorderLayout.NORTH);
	}
	
	void draw() {
		contents.removeAll();
		ListModel dataModel = expandBar.getModel();
		for (int i = 0; i < dataModel.getSize();i++) {
			ExpandItem nextItem = (ExpandItem)dataModel.getElementAt(i);
			addBar(nextItem);
		}
	}

	public void addBar(ExpandItem item) {
		ExpandItemUI itemUI = new ExpandItemUI(item);
		model2viewMap.put(item,itemUI);
		contents.add(itemUI);
	}

	public void removeBar(ExpandItem item) {
		ExpandItemUI itemUI = model2viewMap.get(item);
		if (itemUI != null) {
			model2viewMap.remove(item);
			contents.remove(itemUI);
		}
	}

}
