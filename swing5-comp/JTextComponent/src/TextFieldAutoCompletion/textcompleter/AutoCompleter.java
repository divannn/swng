package TextFieldAutoCompletion.textcompleter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;

/**
 * @author idanilov
 * 
 */
public abstract class AutoCompleter {

	protected JList list;
	protected JPopupMenu popup;
	protected JTextField textField;
	private static final String AUTOCOMPLETER = "AUTOCOMPLETER"; 

	private static Action acceptAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			JComponent tf = (JComponent) e.getSource();
			AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER);
			completer.popup.setVisible(false);
			completer.acceptedListItem((String)completer.list.getSelectedValue());
		}
	};

	DocumentListener documentListener = new DocumentListener() {
		public void insertUpdate(DocumentEvent e) {
			showPopup();
		}

		public void removeUpdate(DocumentEvent e) {
			showPopup();
		}

		public void changedUpdate(DocumentEvent e) {
		}
	};
	
	public AutoCompleter(final JTextField tf) {
		textField = tf;
		textField.putClientProperty(AUTOCOMPLETER, this);
		list = new JList();
		list.setFocusable(false);
		//select item by mouse click.
		list.addMouseListener(new MouseAdapter() { 
		    public void mouseClicked(MouseEvent me) { 
		        popup.setVisible(false); 
		        acceptedListItem((String)list.getSelectedValue()); 
		    } 
		});		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setFocusable(false);
		scrollPane.getHorizontalScrollBar().setFocusable(false);

		popup = new JPopupMenu();
		popup.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		popup.add(scrollPane);
		textField.registerKeyboardAction(showAction, KeyStroke.getKeyStroke(
				KeyEvent.VK_DOWN, 0), JComponent.WHEN_FOCUSED);
		textField.getDocument().addDocumentListener(documentListener);
		textField.registerKeyboardAction(upAction, 
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_FOCUSED);
		textField.registerKeyboardAction(hidePopupAction, 
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED);

		popup.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				textField.unregisterKeyboardAction(KeyStroke.getKeyStroke(
						KeyEvent.VK_ENTER, 0));
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		list.setRequestFocusEnabled(false);
	}
	
	private void showPopup() {
		popup.setVisible(false);
		if (textField.isEnabled() && updateListData()
				&& list.getModel().getSize() != 0) {
			textField.registerKeyboardAction(acceptAction, KeyStroke
					.getKeyStroke(KeyEvent.VK_ENTER, 0),JComponent.WHEN_FOCUSED);
			int size = list.getModel().getSize();
			list.setVisibleRowCount(size < 10 ? size : 10);

			int x = 0;
			try {
				int pos = Math.min(textField.getCaret().getDot(), textField
						.getCaret().getMark());
				x = textField.getUI().modelToView(textField, pos).x;
			} catch (BadLocationException ble) {
				// this should never happen!!!
				ble.printStackTrace();
			}
			popup.show(textField, x, textField.getHeight());
		}
		textField.requestFocusInWindow();
	}

	private Action showAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			JComponent tf = (JComponent) e.getSource();
			if (tf.isEnabled()) {
				if (popup.isVisible()) {
					selectNextPossibleValue();
				} else {
					showPopup();
				}
			}
		}
	};
	
	private Action upAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			JComponent tf = (JComponent) e.getSource();
			if (tf.isEnabled()) {
				if (popup.isVisible()) {
					selectPreviousPossibleValue();
				}
			}
		}
	};

	private Action hidePopupAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			JComponent tf = (JComponent) e.getSource();
			if (tf.isEnabled()) {
				popup.setVisible(false);
			}
		}
	};

	/**
	 * Selects the next item in the list. It won't change the selection if the
	 * currently selected item is already the last item.
	 */
	protected void selectNextPossibleValue() {
		int si = list.getSelectedIndex();
		if (si < list.getModel().getSize() - 1) {
			list.setSelectedIndex(si + 1);
			list.ensureIndexIsVisible(si + 1);
		}
	}

	/**
	 * Selects the previous item in the list. It won't change the selection if
	 * the currently selected item is already the first item.
	 */
	protected void selectPreviousPossibleValue() {
		int si = list.getSelectedIndex();
		if (si > 0) {
			list.setSelectedIndex(si - 1);
			list.ensureIndexIsVisible(si - 1);
		}
	}

	//update list model depending on the data in textfield.
	protected abstract boolean updateListData();

	//user has selected some item in the list. Update textfield accordingly.
	protected abstract void acceptedListItem(String selected);

}