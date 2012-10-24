package MultilineTableCellEditor._1;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

/**
 * <code>editCellAt(int row, int column, EventObject e)</code> is duplicated as impossible 
 * to override bounds setting for editor.
 * <br>
 * CellEditorRemover is duplicated the same.
 * @author idanilov
 *
 */
public class TableEx extends JTable {

	private PropertyChangeListener editorRemover;

	public TableEx(final Object[][] data, final Object[] colNames) {
		super(data, colNames);
	}

	public boolean getScrollableTracksViewportHeight() {
		if (getParent() instanceof JViewport) {
			return getParent().getHeight() > getPreferredSize().height;
		} 
		return false;
	}

	public boolean editCellAt(int row, int column, EventObject e) {
		if (cellEditor != null && !cellEditor.stopCellEditing()) {
			return false;
		}

		if (row < 0 || row >= getRowCount() || column < 0 || column >= getColumnCount()) {
			return false;
		}

		if (!isCellEditable(row, column)) {
			return false;
		}

		if (editorRemover == null) {
			KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			editorRemover = new CellEditorRemover(fm);
			fm.addPropertyChangeListener("permanentFocusOwner", editorRemover);
		}

		TableCellEditor editor = getCellEditor(row, column);
		if (editor != null && editor.isCellEditable(e)) {
			editorComp = prepareEditor(editor, row, column);
			if (editorComp == null) {
				removeEditor();
				return false;
			}

			Rectangle cellRect = getCellRect(row, column, false);
			//[SK]+
			if (editor instanceof MultiLineTableCellEditor) {
				Dimension prefSize = editorComp.getPreferredSize();
				((JComponent)editorComp).putClientProperty(MultiLineTableCellEditor.UPDATE_BOUNDS, Boolean.TRUE);
				Rectangle r = new Rectangle(cellRect.x, cellRect.y,
						Math.max(cellRect.width,prefSize.width), 
						Math.max(cellRect.height, prefSize.height));
				editorComp.setBounds(r);
				((JComponent)editorComp).putClientProperty(MultiLineTableCellEditor.UPDATE_BOUNDS, Boolean.FALSE);
			} else {
				editorComp.setBounds(cellRect);
			}
			//[SK]-

			add(editorComp);
			editorComp.validate();
			//[ID]+ 
			/* I don't know why JTable do not repaint editor comp here.
			For some cases border of editor is not painted.
			I corrected and it seems everything is OK now.*/
			
			//editorComp.repaint();
			//repain now thought line above also works.
			((JComponent)editorComp).paintImmediately(editorComp.getBounds());
			//[ID]-

			setCellEditor(editor);
			setEditingRow(row);
			setEditingColumn(column);
			editor.addCellEditorListener(this);

			return true;
		}
		return false;
	}

	/**
	 * Just to remove our <code>editorRemoved</code> from listening "permanentFocusOwner".
	 */
	public void removeNotify() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener(
				"permanentFocusOwner", editorRemover);
		editorRemover = null;
		super.removeNotify();
	}

	/**
	 * 1.Compute editor bounds to repaint. 
	 * 2.Remove our <code>editorRemoved</code> from listening "permanentFocusOwner".
	 */
	public void removeEditor() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener(
				"permanentFocusOwner", editorRemover);
		editorRemover = null;
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			editor.removeCellEditorListener(this);
			Rectangle cellRect = getCellRect(editingRow, editingColumn, false);
			//[SK]+
			if (editorComp != null) {
				cellRect = cellRect.union(editorComp.getBounds());
				remove(editorComp);
			}
			//[SK]-
			setCellEditor(null);
			setEditingColumn(-1);
			setEditingRow(-1);
			editorComp = null;
			repaint(cellRect);
		}
	}

	private class CellEditorRemover
			implements PropertyChangeListener {

		KeyboardFocusManager focusManager;

		public CellEditorRemover(KeyboardFocusManager fm) {
			this.focusManager = fm;
		}

		public void propertyChange(PropertyChangeEvent ev) {
			if (!isEditing() || getClientProperty("terminateEditOnFocusLost") != Boolean.TRUE) {
				return;
			}

			Component c = focusManager.getPermanentFocusOwner();
			while (c != null) {
				if (c == TableEx.this) {
					// focus remains inside the table
					return;
				} else if ((c instanceof Window) || (c instanceof Applet && c.getParent() == null)) {
					if (c == SwingUtilities.getRoot(TableEx.this)) {
						if (!getCellEditor().stopCellEditing()) {
							getCellEditor().cancelCellEditing();
						}
					}
					break;
				}
				c = c.getParent();
			}
		}
	}
}
