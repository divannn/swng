package TextFieldAutoCompletion.textcompleter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

/**
 * @author idanilov
 * 
 */
public class ListAutoCompleter extends AutoCompleter {

	private List completionList;
	private boolean ignoreCase;

	public ListAutoCompleter(JTextField comp, List completionList,boolean ignoreCase) {
		super(comp);
		this.completionList = completionList;
		this.ignoreCase = ignoreCase;
	}

	protected boolean updateListData() {
		String value = textField.getText();
		int substringLen = value.length();
		List<String> possibleStrings = new ArrayList<String>();
		Iterator iter = completionList.iterator();
		while (iter.hasNext()) {
			String listEntry = (String) iter.next();
			if (substringLen >= listEntry.length())
				continue;

			if (ignoreCase) {
				if (value.equalsIgnoreCase(listEntry.substring(0, substringLen))) {
					possibleStrings.add(listEntry);
				}
			} else if (listEntry.startsWith(value)) {
				possibleStrings.add(listEntry);
			}
		}
		list.setListData(possibleStrings.toArray());
		return true;
	}

	protected void acceptedListItem(String selected) {
		if (selected == null) {
			return;
		}
		int prefixlen = textField.getDocument().getLength();
		try {
			textField.getDocument().insertString(textField.getCaretPosition(),
					selected.substring(prefixlen), null);
		} catch (BadLocationException ble) {
			ble.printStackTrace();
		}
	}

}