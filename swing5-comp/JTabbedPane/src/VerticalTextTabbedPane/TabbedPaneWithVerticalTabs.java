package VerticalTextTabbedPane;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author idanilov
 *
 */
public class TabbedPaneWithVerticalTabs extends JTabbedPane {

	/**
	 * Added for simplicity. In order not to provide special TabbedPaneUI.
	 * @param tabPlacement
	 */
	public static JTabbedPane create(final int tabPlacement) {
		switch (tabPlacement) {
			case SwingConstants.LEFT:
			case SwingConstants.RIGHT:
				//replace some values.
				Object textIconGap = UIManager.get("TabbedPane.textIconGap");
				Insets tabInsets = UIManager.getInsets("TabbedPane.tabInsets");
				UIManager.put("TabbedPane.textIconGap", new Integer(1));
				UIManager.put("TabbedPane.tabInsets", new Insets(tabInsets.left, tabInsets.top,
						tabInsets.right, tabInsets.bottom));
				JTabbedPane tabPane = new TabbedPaneWithVerticalTabs(tabPlacement);
				//restore default values.
				UIManager.put("TabbedPane.textIconGap", textIconGap);
				UIManager.put("TabbedPane.tabInsets", tabInsets);
				return tabPane;
			default:
				return new JTabbedPane(tabPlacement);
		}
	}

	private TabbedPaneWithVerticalTabs(final int tabPosition) {
		super(tabPosition);
	}

	public void addTab(final String text, final Component comp) {
		switch (tabPlacement) {
			case SwingConstants.LEFT:
			case SwingConstants.RIGHT:
				super.addTab(null,
						new VerticalTextIcon(text, tabPlacement == SwingConstants.RIGHT), comp);
				return;
			default:
				super.addTab(text, null, comp);
		}
	}

}