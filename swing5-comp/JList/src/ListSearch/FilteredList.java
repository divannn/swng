package ListSearch;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author idanilov
 *
 */
public class FilteredList extends JList {

	private JTextField input;
	   
	public FilteredList() {
		setModel(new FilteredModel());
	}
	   
	public JTextField getTextField() {
		return input;
	}
	
	public void installTextField(final JTextField tf) {
		if (tf != null) {
			input = tf;
			FilteredModel model = (FilteredModel)getModel();
			tf.getDocument().addDocumentListener(model);
		}
	}
	   
	public void uninstallJTextField(final JTextField tf) {
		if (tf != null) {
			FilteredModel model = (FilteredModel)getModel();
			tf.getDocument().removeDocumentListener(model);
			input = null;
		}
	}
	   
	public void setModel(ListModel model) {
		if (!(model instanceof FilteredModel)) {
			throw new IllegalArgumentException("Model must be " +FilteredModel.class.getSimpleName());
		}
		super.setModel(model);
	}
	   
	public void addElement(Object element) {
	     ((FilteredModel)getModel()).addElement(element);
	}
	   
	private static class FilteredModel extends AbstractListModel
	       implements DocumentListener {
		
		private List<Object> list;
		private List<Object> filteredList;
		private String lastCondition;
	   
		public FilteredModel() {
			list = new ArrayList<Object>();
			filteredList = new ArrayList<Object>();
			lastCondition = "";
		}
	   
		public void addElement(Object element) {
			list.add(element);
			filter(lastCondition);
		}
	   
		public int getSize() {
			return filteredList.size();
		}
	   
		public Object getElementAt(int index) {
	       Object returnValue;
	       if (index < filteredList.size()) {
	         returnValue = filteredList.get(index);
	       } else {
	         returnValue = null;
	       }
	       return returnValue;
		}
	   
		/**
		 * Perform <code>starsWith</code> comparing.
		 * Can be any other criteria.
		 * @param search
		 */
		private void filter(final String search) {
			filteredList.clear();
			for (Object nextElement: list) {
				if (nextElement.toString().startsWith(search)) {
					filteredList.add(nextElement);
				}
			}
			fireContentsChanged(this, 0, getSize());
		}
	   
		public void insertUpdate(DocumentEvent event) {
			Document doc = event.getDocument();
			try {
				lastCondition = doc.getText(0, doc.getLength());
				filter(lastCondition);
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	   
		public void removeUpdate(DocumentEvent event) {
			Document doc = event.getDocument();
			try {
				lastCondition = doc.getText(0, doc.getLength());
				filter(lastCondition);
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	   
		public void changedUpdate(DocumentEvent event) {
		}
		
	}

}