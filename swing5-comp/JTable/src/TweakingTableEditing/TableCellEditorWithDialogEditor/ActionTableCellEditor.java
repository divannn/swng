package TweakingTableEditing.TableCellEditorWithDialogEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

/**
 * XXX: Maybe combine this class with ActionTableCellRenderer.
 * @author idanilov
 *
 */
public abstract class ActionTableCellEditor
		implements TableCellEditor, ActionListener {

	private TableCellEditor delegate;
	private InvokerButton button;
	private boolean isEditable;

	protected JTable table;
	protected int row, column;

	public ActionTableCellEditor(final TableCellEditor delegate, final boolean isEditable) {
		this.delegate = delegate;
		this.isEditable = isEditable;
		button = new InvokerButton();
		button.addActionListener(this);
	}

	public Component getTableCellEditorComponent(JTable t, Object value, boolean isSelected, int r,
			int c) {
		JPanel result = new JPanel(new BorderLayout()) {

			/** 
			 * Once editor has got the focus - move it to delegate if editable.
			 * Otherwise - move it to the button to allow click it from keyboard.
			 * <br>
			 * This will be called if setSurrendersFocusOnKeystroke(true) is called on table where this editor is used.
			 */
			public void requestFocus() {
				super.requestFocus();
				if (isEditable) {
					getComponent(0).requestFocus();
				} else {
					button.requestFocusInWindow();
				}
			}

			/**
			 * Redispatch keybindings into editor.
			 * Actually I was supposed to do simply the following:
			 * editorComp.processKeyBinding(ks, e, condition, pressed);
			 * But JComponent.processKeyBinding(...) method is protected.s
			 */
			protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition,
					boolean pressed) {
				JComponent comp = (JComponent) getComponent(0);//text editor.
				InputMap map = comp.getInputMap(condition);
				ActionMap am = comp.getActionMap();

				if (map != null && am != null && isEnabled()) {
					Object binding = map.get(ks);
					Action action = (binding == null) ? null : am.get(binding);
					if (action != null) {
						return SwingUtilities.notifyAction(action, ks, e, comp, e.getModifiers());
					}
				}
				return false;
			}

		};
		//result.setRequestFocusEnabled(true);
		JTextComponent textComp = (JTextComponent) delegate.getTableCellEditorComponent(t, value,
				isSelected, r, c);
		if (!isEditable) {
			textComp.setEditable(false);
			textComp.setBackground(Color.WHITE);
		}
		result.add(textComp, BorderLayout.CENTER);
		result.add(button, BorderLayout.EAST);
		this.table = t;
		this.row = r;
		this.column = c;
		return result;
	}

	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	public boolean isCellEditable(EventObject anEvent) {
		return delegate.isCellEditable(anEvent);
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return delegate.shouldSelectCell(anEvent);
	}

	public boolean stopCellEditing() {
		return delegate.stopCellEditing();
	}

	public void cancelCellEditing() {
		delegate.cancelCellEditing();
	}

	public void addCellEditorListener(CellEditorListener l) {
		delegate.addCellEditorListener(l);
	}

	public void removeCellEditorListener(CellEditorListener l) {
		delegate.removeCellEditorListener(l);
	}

	public final void actionPerformed(ActionEvent e) {
        Object curValue = delegate.getCellEditorValue();
        delegate.cancelCellEditing();
        doEditCell(table, curValue,row, column);
	}

	/**
	 * Override to perform custom action on [...] button press.
	 */
	protected abstract void doEditCell(JTable t, Object curValue, int r, int c);

}