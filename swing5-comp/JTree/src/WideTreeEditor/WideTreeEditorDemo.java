package WideTreeEditor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;

/**
 * Uses full width of tree for editor.
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class WideTreeEditorDemo extends JFrame {

	public WideTreeEditorDemo() {
		super(WideTreeEditorDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTree tree = new JTree() {

			public String getToolTipText(MouseEvent event) {
				return "Start edit some row";
			}
		};
		ToolTipManager.sharedInstance().registerComponent(tree);//or tree.setToolTipText(""); 
		tree.setEditable(true);
		TreeCellEditor wideEditor = new WideCellEditor(tree, (DefaultTreeCellRenderer) tree
				.getCellRenderer());
		tree.setCellEditor(wideEditor);
		//expand some.
		tree.expandRow(1);
		JScrollPane scrollPane = new JScrollPane(tree);
		result.add(scrollPane, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new WideTreeEditorDemo();
		frame.pack();
		frame.setSize(new Dimension(300, 400));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static class WideCellEditor extends DefaultTreeCellEditor {

		public WideCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
			this(tree, renderer, null);
		}

		public WideCellEditor(final JTree tree, final DefaultTreeCellRenderer renderer,
				TreeCellEditor editor) {
			super(tree, renderer, editor);
		}

		protected Container createContainer() {
			return new WideEditorContainer();
		}

		private class WideEditorContainer extends DefaultTreeCellEditor.EditorContainer {

			public Dimension getPreferredSize() {
				Dimension result = super.getPreferredSize();
				int n = lastPath.getPathCount();
				Rectangle treeBounds = tree.getBounds();
				result.width = treeBounds.width - offset * (n - 1);
				return result;
			}
		}

	}

}
