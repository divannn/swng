package HideSplitParts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Shows how to nicely remove split pane part and update divider.
 * @author idanilov
 * @jdk 1.5
 */
public class HideSplitPartsDemo extends JFrame {

	private JSplitPane splitPane;
	private JComponent leftComp;
	private JComponent rightComp;

	public HideSplitPartsDemo() {
		super(HideSplitPartsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createButtonsPanel(), BorderLayout.NORTH);
		splitPane = createSplitPane();
		result.add(splitPane, BorderLayout.CENTER);
		return result;
	}

	private JComponent createButtonsPanel() {
		JPanel result = new JPanel();
		final String hideLeftTitle = "Hide Left";
		final String showLeftTitle = "Show Left";
		final JToggleButton hideShowLeftPartButton = new JToggleButton(hideLeftTitle);
		hideShowLeftPartButton.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					hideShowLeftPartButton.setText(showLeftTitle);
					setPartClosed(splitPane, leftComp, true, true);
					setDividerVisible(splitPane, false);
				} else {
					hideShowLeftPartButton.setText(hideLeftTitle);
					setPartClosed(splitPane, leftComp, true, false);
					showDividerIfNeeded(splitPane);
				}
			}

		});
		result.add(hideShowLeftPartButton);

		final String hideRightTitle = "Hide Right";
		final String showRightTitle = "Show Right";
		final JToggleButton hideShowRightPartButton = new JToggleButton(hideRightTitle);
		hideShowRightPartButton.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					hideShowRightPartButton.setText(showRightTitle);
					setPartClosed(splitPane, rightComp, false, true);
		            setDividerVisible(splitPane, false);
				} else {
					hideShowRightPartButton.setText(hideRightTitle);
					setPartClosed(splitPane, rightComp, false, false);
					showDividerIfNeeded(splitPane);
				}
			}

		});
		result.add(hideShowRightPartButton);
		return result;
	}

	private JSplitPane createSplitPane() {
		JSplitPane result = new JSplitPane();
		result.setBorder(null);//remove border - it looks ugly when one part is removed.
		JTree tree = new JTree();
		JTable table = new JTable(5, 3);
		table.setPreferredScrollableViewportSize(new Dimension(200, 100));
		leftComp = new JScrollPane(tree);
		result.setLeftComponent(leftComp);
		rightComp = new JScrollPane(table);
		result.setRightComponent(rightComp);
		return result;
	}

	private static void setPartClosed(final JSplitPane sp, final JComponent comp,
			final boolean leftComp, final boolean close) {
		if (close) {
			if (leftComp) {
				if (sp.getRightComponent() != null) {
					sp.setDividerLocation(0.0d);
				}
				sp.setLeftComponent(null);//forbid drag divider.
			} else {
				if (sp.getLeftComponent() != null) {
					sp.setDividerLocation(1.0d);
				}
				sp.setRightComponent(null);
			}
		} else {
			if (leftComp) {
				sp.setLeftComponent(comp);
			} else {
				sp.setRightComponent(comp);
			}
			if (isSplitFull(sp)) {//both comps added.
				sp.setDividerLocation(sp.getLastDividerLocation());
			}
		}
	}
	
    private static void showDividerIfNeeded(final JSplitPane sp) {
        if (isSplitFull(sp)) {
            setDividerVisible(sp, true);
        }
    }

    public static void setDividerVisible(final JSplitPane sp, final boolean visible) {
        if (sp != null) {
            SplitPaneUI spUI = sp.getUI();
            if (spUI instanceof BasicSplitPaneUI) {
                ((BasicSplitPaneUI)spUI).getDivider().setVisible(visible);
            }
        }
    }
	
	public static boolean isSplitFull(final JSplitPane split) {
		boolean result = false;
		if (split != null) {
			if (split.getLeftComponent() != null && split.getRightComponent() != null) {
				result = true;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame f = new HideSplitPartsDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
