package SyncScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Maybe useful for text merge tools.
 * 1. Discret sync - reacts on JVertical bar model change.
 * 2. Live sync - reacts on viewport's view position change.
 * @author idanilov
 * @jdk 1.5
 */
public class SyncScrollPaneDemo extends JFrame {

	private StringBuffer leftContent;
	private StringBuffer rightContent;

	private JTabbedPane tabPane;

	private JTextArea leftText1;
	private JTextArea rightText1;
	private JScrollPane leftScrollPane1;
	private JScrollPane rightScrollPane1;

	private JTextArea leftText2;
	private JTextArea rightText2;
	private JScrollPane leftScrollPane2;
	private JScrollPane rightScrollPane2;

	public SyncScrollPaneDemo() {
		super(SyncScrollPaneDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			leftContent = loadFiles("text1.txt");
			rightContent = loadFiles("text2.txt");
			setContentPane(createContents());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}
	}

	private StringBuffer loadFiles(final String fileName) throws IOException {
		StringBuffer result = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(SyncScrollPaneDemo.class
				.getResourceAsStream(fileName)));
		String nextLine = null;
		try {
			while ((nextLine = reader.readLine()) != null) {
				result.append(nextLine);
				result.append("\n");
			}
		} finally {
			reader.close();
		}
		return result;
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		tabPane = new JTabbedPane();
		tabPane.addTab("Discret sync", createDiscretSyncTab());
		tabPane.addTab("Live sync", createLiveSyncTab());
		result.add(tabPane, BorderLayout.CENTER);
		return result;
	}

	private JPanel createDiscretSyncTab() {
		JPanel result = new JPanel(new BorderLayout());
		leftText1 = new JTextArea();
		leftText1.setText(leftContent.toString());
		leftScrollPane1 = new JScrollPane(leftText1);
		rightText1 = new JTextArea();
		rightText1.setText(rightContent.toString());
		rightScrollPane1 = new JScrollPane(rightText1);
		final BoundedRangeModel leftVertScrollModel = leftScrollPane1.getVerticalScrollBar()
				.getModel();
		final BoundedRangeModel rightVertScrollModel = rightScrollPane1.getVerticalScrollBar()
				.getModel();
		ChangeListener cl = new ChangeListener() {

			public void stateChanged(ChangeEvent ce) {
				BoundedRangeModel source = (BoundedRangeModel) ce.getSource();
				if (source == leftVertScrollModel) {
					if (!leftVertScrollModel.getValueIsAdjusting()) {
						//System.err.println(" left model = " + source);
						rightVertScrollModel.removeChangeListener(this);
						//XXX : we should check range before set value but DefaultBoundedRangeModel#setValue() takes care about it ;).
						rightVertScrollModel.setValue(source.getValue());
						rightVertScrollModel.addChangeListener(this);
					}
				} else if (source == rightVertScrollModel) {
					if (!rightVertScrollModel.getValueIsAdjusting()) {
						//System.err.println(" right model = " + source);
						leftVertScrollModel.removeChangeListener(this);
						//XXX : we should check range before set value but DefaultBoundedRangeModel#setValue() takes care about it ;).
						leftVertScrollModel.setValue(source.getValue());
						leftVertScrollModel.addChangeListener(this);
					}
				}
			}
		};
		leftVertScrollModel.addChangeListener(cl);
		rightVertScrollModel.addChangeListener(cl);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(leftScrollPane1);
		splitPane.setRightComponent(rightScrollPane1);
		splitPane.setDividerLocation(100);
		result.add(splitPane, BorderLayout.CENTER);
		return result;
	}

	private JPanel createLiveSyncTab() {
		JPanel result = new JPanel(new BorderLayout());
		leftText2 = new JTextArea();
		leftText2.setText(leftContent.toString());
		leftScrollPane2 = new JScrollPane(leftText2);
		rightText2 = new JTextArea();
		rightText2.setText(rightContent.toString());
		rightScrollPane2 = new JScrollPane(rightText2);

		final JViewport leftViewport = leftScrollPane2.getViewport();
		final JViewport rightViewport = rightScrollPane2.getViewport();

		ChangeListener cl = new ChangeListener() {

			public void stateChanged(ChangeEvent ce) {
				JViewport source = (JViewport) ce.getSource();
				if (source == leftViewport) {
					//System.err.println(" left viewport = " + source.getViewPosition());
					int leftY = source.getViewPosition().y;
					int diff = rightViewport.getViewSize().height
							- rightViewport.getViewRect().height;
					int y;
					if (diff > 0) {//do not change if viewport fits fully.
						if (leftY < diff) {
							y = leftY;
						} else {
							y = diff;
						}
						rightViewport.removeChangeListener(this);
						rightViewport.setViewPosition(new Point(source.getViewPosition().x, y));
						rightViewport.addChangeListener(this);
					}
				} else if (source == rightViewport) {
					//System.err.println(" right viewport = " + source.getViewPosition());
					int rightY = source.getViewPosition().y;
					int diff = leftViewport.getViewSize().height
							- leftViewport.getViewRect().height;
					int y;
					if (diff > 0) {//do not change if viewport fits fully.
						if (rightY < diff) {
							y = rightY;
						} else {
							y = diff;
						}
						leftViewport.removeChangeListener(this);
						leftViewport.setViewPosition(new Point(source.getViewPosition().x, y));
						leftViewport.addChangeListener(this);
					}
				}
			}
		};
		leftViewport.addChangeListener(cl);
		rightViewport.addChangeListener(cl);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(leftScrollPane2);
		splitPane.setRightComponent(rightScrollPane2);
		splitPane.setDividerLocation(100);
		result.add(splitPane, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new SyncScrollPaneDemo();
		frame.pack();
		frame.setSize(new Dimension(300, 250));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
