import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import undoredo.MyUndoManager;
import undoredo.SmartUndoManger;

/**
 * @author santoch
 * @jdk 1.5
 */
public class UndoRedoDemo extends JFrame {

	public UndoRedoDemo() {
		super(UndoRedoDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());

		ListPanel listPanel = new ListPanel();
		MyUndoManager undoManager = new SmartUndoManger /*MyUndoManager*/();
		((UndoableListModel) listPanel.getListModel()).addUndoableEditListener(undoManager);
		result.add(listPanel, BorderLayout.CENTER);

		JToolBar toolbar = new JToolBar();
		toolbar.add(undoManager.getUndoAction());
		toolbar.add(undoManager.getRedoAction());
		result.add(toolbar, BorderLayout.NORTH);

		return result;
	}

	public static void main(String[] args) {
		JFrame f = new UndoRedoDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
