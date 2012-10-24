package MultilineTableCellEditor._1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;

/**
 * Ctrl+Enter used to commit changes because of Enter used by textArea for new line. 
 * @author idanilov
 *
 */
public class MultiLineTableCellEditor extends AbstractCellEditor
		implements TableCellEditor, ActionListener {

	public static final String UPDATE_BOUNDS = "updateBounds";
	
	private JTextArea textArea;
	private int clickCountToStart;

	public MultiLineTableCellEditor() {
		textArea = new AutoResizableTextArea();
		textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		textArea.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
				InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
		clickCountToStart = 2;
	}

	public Object getCellEditorValue() {
		return textArea.getText();
	}

	public int getClickCountToStart() {
		return clickCountToStart;
	}

	public void setClickCountToStart(int clickCountToStart) {
		this.clickCountToStart = clickCountToStart;
	}

	public boolean isCellEditable(EventObject e) {
		return !(e instanceof MouseEvent) || ((MouseEvent) e).getClickCount() >= clickCountToStart;
	}

	public void actionPerformed(ActionEvent ae) {
		stopCellEditing();
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column) {
		String text = value != null ? value.toString() : "";
		textArea.setText(text);
		return textArea;
	}

	/**
	 * Used to automatically increase size of text area during edit (when new lines are added). 
	 * @author idanilov
	 *
	 */
	private static class AutoResizableTextArea extends JTextArea {

		public void setBounds(int x, int y, int width, int height) {
			if (Boolean.TRUE.equals(getClientProperty(UPDATE_BOUNDS))) {
				super.setBounds(x, y, width, height);
			}
		}

		public void addNotify() {
			super.addNotify();
			getDocument().addDocumentListener(docListener);
		}

		public void removeNotify() {
			getDocument().removeDocumentListener(docListener);
			super.removeNotify();
		}

		private DocumentListener docListener = new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				updateBounds();
			}

			public void removeUpdate(DocumentEvent e) {
				updateBounds();
			}

			public void changedUpdate(DocumentEvent e) {
			}
		};

		private void updateBounds() {
			if (getParent() instanceof JTable) {
				JTable table = (JTable) getParent();
				if (table.isEditing()) {
					Rectangle cellRect = table.getCellRect(table.getEditingRow(), table
							.getEditingColumn(), false);
					Dimension prefSize = getPreferredSize();
					putClientProperty(UPDATE_BOUNDS, Boolean.TRUE);
					setBounds(getX(), getY(), Math.max(cellRect.width, prefSize.width), Math.max(
							cellRect.height, prefSize.height));
					putClientProperty(UPDATE_BOUNDS, Boolean.FALSE);
					validate();
				}
			}
		}
	}

}
