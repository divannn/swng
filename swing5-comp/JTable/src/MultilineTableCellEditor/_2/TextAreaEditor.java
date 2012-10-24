package MultilineTableCellEditor._2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * @author idanilov
 *
 */
public class TextAreaEditor extends DefaultCellEditor
		implements ActionListener {

	//impossible override with text area in constructor - so firstly call super with JTextfield
	//and costomize editor with textara later.
	public TextAreaEditor() {
		super(new JTextField());
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		//end editing via Ctrl+Enter.
		textArea.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
				InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);

		JScrollPane scrollPane = new JScrollPane(textArea) {
			//redirect focus to text area when editor is activated. 
			public void requestFocus() {
				super.requestFocus();
				textArea.requestFocusInWindow();
			}
		};
		scrollPane.setBorder(null);
		scrollPane.setFocusable(false);
		editorComponent = scrollPane;
		delegate = new DefaultCellEditor.EditorDelegate() {

			public void setValue(Object value) {
				textArea.setText((value != null) ? value.toString() : "");
			}

			public Object getCellEditorValue() {
				return textArea.getText();
			}
		};
	}

	public void actionPerformed(ActionEvent e) {
		stopCellEditing();
	}

}
