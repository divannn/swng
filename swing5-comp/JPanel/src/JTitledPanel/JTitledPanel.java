package JTitledPanel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * Higlight itself if focus owner is in it <code>contents</code> -
 * doesn't matter how deep this focus owner in component hierarchy. 
 * @author idanilov
 * 
 */
public class JTitledPanel extends JComponent {

	private JLabel titleLabel;
	private JComponent contents;

	private FocusOwnerTracker tracker = new FocusOwnerTracker(this) {

		public void focusGained() {
			titleLabel.setForeground(UIManager.getColor("textHighlightText"));
			titleLabel.setBackground(UIManager.getColor("textHighlight"));
			titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
		}

		public void focusLost() {
			titleLabel.setForeground(UIManager.getColor("textText"));
			titleLabel.setBackground(UIManager.getColor("control"));
			titleLabel.setFont(titleLabel.getFont().deriveFont(Font.PLAIN));
		}
	};

	public JTitledPanel(final String title,final JComponent comp) {
		if (comp == null) {
			throw new IllegalArgumentException("Specify non-null contents"); 
		}
		contents = comp;
		setLayout(new BorderLayout());
		titleLabel = new JLabel();
		titleLabel.setText(title);
		titleLabel.setOpaque(true);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		tracker.focusLost();
		add(titleLabel, BorderLayout.NORTH);
		add(contents, BorderLayout.CENTER);
		tracker.start();
	}

	public JComponent getContents() {
		return contents;
	}
	
}
