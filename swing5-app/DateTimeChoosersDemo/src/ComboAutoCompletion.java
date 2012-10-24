
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

/**
 * Very usefull. 
 * This framework does not allow to enter invalid value int combobox 
 * (values that does not exists in combobox model).
 * @author idanilov
 *
 */
public class ComboAutoCompletion extends PlainDocument {

	private JComboBox comboBox;
	private ComboBoxModel model;
	private JTextComponent editor;
	private ILabelProvider labelProvider;
	// flag to indicate if setSelectedItem has been called
	// subsequent calls to remove/insertString should be ignored
	private boolean selecting;
	private boolean hidePopupOnFocusLoss;
	private boolean hitBackspace;
	private boolean hitBackspaceOnSelection;

	private KeyListener editorKeyListener;
	private FocusListener editorFocusListener;

	public ComboAutoCompletion(final JComboBox comboBox, final ILabelProvider labelProvider) {
		this.comboBox = comboBox;
		this.labelProvider = labelProvider;
		model = comboBox.getModel();
		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!selecting) {
					highlightCompletedText(0);
				}
			}
		});
		comboBox.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent e) {
				if (e.getPropertyName().equals("editor")) {
					configureEditor((ComboBoxEditor) e.getNewValue());
				}
				if (e.getPropertyName().equals("model")) {
					model = (ComboBoxModel) e.getNewValue();
				}
			}
		});
		editorKeyListener = new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				//filter ESC to allow processing it as close request for parent window.
				if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
					if (comboBox.isDisplayable()) {
						comboBox.setPopupVisible(true);
					}
				}
				hitBackspace = false;
				switch (e.getKeyCode()) {
					// determine if the pressed key is backspace (needed by the remove method)
					case KeyEvent.VK_BACK_SPACE:
						hitBackspace = true;
						hitBackspaceOnSelection = editor.getSelectionStart() != editor
								.getSelectionEnd();
						break;
					// ignore delete key
					case KeyEvent.VK_DELETE:
						e.consume();
						comboBox.getToolkit().beep();
						break;
					default:
						break;
				}
			}
		};
		// Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
		hidePopupOnFocusLoss = System.getProperty("java.version").startsWith("1.5");
		// Highlight whole text when gaining focus
		editorFocusListener = new FocusAdapter() {

			public void focusGained(FocusEvent e) {
				highlightCompletedText(0);
			}

			public void focusLost(FocusEvent e) {
				// Workaround for Bug 5100422 - Hide Popup on focus loss
				if (hidePopupOnFocusLoss) {
					comboBox.setPopupVisible(false);
				}
			}
		};
		configureEditor(comboBox.getEditor());
		// Handle initially selected object
		Object selected = comboBox.getSelectedItem();
		if (selected != null) {
			setText(getItemString(selected));
		}
		highlightCompletedText(0);
	}

	public static void install(final JComboBox comboBox, final ILabelProvider labelProvider) {
		// has to be editable
		comboBox.setEditable(true);
		// change the editor's document
		new ComboAutoCompletion(comboBox, labelProvider);
	}

	private void configureEditor(final ComboBoxEditor newEditor) {
		if (editor != null) {
			editor.removeKeyListener(editorKeyListener);
			editor.removeFocusListener(editorFocusListener);
		}
		if (newEditor != null) {
			editor = (JTextComponent) newEditor.getEditorComponent();
			editor.addKeyListener(editorKeyListener);
			editor.addFocusListener(editorFocusListener);
			editor.setDocument(this);
		}
	}

	public void remove(int offs, int len) throws BadLocationException {
		// return immediately when selecting an item
		if (selecting) {
			return;
		}
		if (hitBackspace) {
			// user hit backspace => move the selection backwards
			// old item keeps being selected
			if (offs > 0) {
				if (hitBackspaceOnSelection) {
					offs--;
				}
			} else {
				// User hit backspace with the cursor positioned on the start => beep
				comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
			}
			highlightCompletedText(offs);
		} else {
			super.remove(offs, len);
		}
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// return immediately when selecting an item
		if (selecting) {
			return;
		}
		// insert the string into the document

		super.insertString(offs, str, a);
		// lookup and select a matching item
		Object item = lookupItem(getText(0, getLength()));
		if (item != null) {
			//System.err.println("+++ item fount: " + item);
			setSelectedItem(item);
		} else {
			//System.err.println("--- itemNOT fount for text: " + getText(0, getLength()));
			// keep old item selected if there is no match
			item = comboBox.getSelectedItem();
			// imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
			offs = offs - str.length();
			// provide feedback to the user that his input has been received but can not be accepted
			comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
		}
		setText(getItemString(item));
		// select the completed part
		highlightCompletedText(offs + str.length());
	}

	private void setText(String text) {
		try {
			// remove all text and insert the completed string
			super.remove(0, getLength());
			super.insertString(0, text, null);
		} catch (BadLocationException e) {
			throw new RuntimeException(e.toString());
		}
	}

	private void highlightCompletedText(final int start) {
		editor.setCaretPosition(getLength());
		editor.moveCaretPosition(start);
	}

	private void setSelectedItem(final Object item) {
		selecting = true;
		model.setSelectedItem(item);
		selecting = false;
	}

	private Object lookupItem(final String pattern) {
		Object selectedItem = model.getSelectedItem();
		// only search for a different item if the currently selected does not match
		if (selectedItem != null && startsWithIgnoreCase(getItemString(selectedItem), pattern)) {
			return selectedItem;
		}
		for (int i = 0, n = model.getSize(); i < n; i++) {
			Object currentItem = model.getElementAt(i);
			// current item starts with the pattern?
			if (currentItem != null && startsWithIgnoreCase(getItemString(currentItem), pattern)) {
				return currentItem;
			}
		}
		// no item starts with the pattern => return null
		return null;
	}

	// checks if str1 starts with str2 - ignores case
	private boolean startsWithIgnoreCase(final String str1, final String str2) {
		return str1.toUpperCase().startsWith(str2.toUpperCase());
	}

	private String getItemString(final Object comboItem) {
		return labelProvider.getLabel(comboItem);
	}

	/**
	 * @author idanilov
	 *
	 */
	public interface ILabelProvider {

		String getLabel(Object o);

	}

}