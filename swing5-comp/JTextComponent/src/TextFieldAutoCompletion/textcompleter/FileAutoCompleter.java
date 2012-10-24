package TextFieldAutoCompletion.textcompleter;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;


/**
 * @author idanilov
 * 
 */
public class FileAutoCompleter extends AutoCompleter {

	public FileAutoCompleter(final JTextField comp) {
		super(comp);
	}

	protected boolean updateListData() {
		String value = textField.getText();
		int index1 = value.lastIndexOf('\\');
		int index2 = value.lastIndexOf('/');
		int index = Math.max(index1, index2);
		if (index == -1) {
			return false;
		}
		String dir = value.substring(0, index + 1);
		final String prefix = index == value.length() - 1 ? null : value
				.substring(index + 1).toLowerCase();
		String[] files = new File(dir).list(new FilenameFilter() {
			public boolean accept(File parent, String name) {
				return prefix != null ? name.toLowerCase().startsWith(prefix) : true;
			}
		});
		if (files == null) {
			list.setListData(new String[0]);
			return true;
		} 
		if (files.length == 1 && files[0].equalsIgnoreCase(prefix)) {
			list.setListData(new String[0]);
		} else {
			list.setListData(files);
		}
		return true;
	}

	protected void acceptedListItem(final String selected) {
		if (selected == null) {
			return;
		}
		String value = textField.getText();
		int index1 = value.lastIndexOf('\\');
		int index2 = value.lastIndexOf('/');
		int index = Math.max(index1, index2);
		if (index != -1) {
			int prefixlen = textField.getDocument().getLength() - index - 1;
			try {
				textField.getDocument().insertString(textField.getCaretPosition(),
						selected.substring(prefixlen), null);
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	}

}
