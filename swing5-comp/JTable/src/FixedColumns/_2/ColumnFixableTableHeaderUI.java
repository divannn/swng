package FixedColumns._2;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sun.java.swing.plaf.windows.WindowsTableHeaderUI;

/**
 * @author idanilov
 *
 */
public class ColumnFixableTableHeaderUI extends WindowsTableHeaderUI {

	private static Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);

    public static ComponentUI createUI(JComponent h) {
        return new ColumnFixableTableHeaderUI();
    }
	
	protected MouseInputListener createMouseInputListener() {
		return new Handler();
	}

	/**
	 * This is copy of {@link BasicTableHeaderUI.MouseInputHandler} with customization.
	 * Unfortunatelly MouseInputHandler cannot be sublassed efficiently.
	 * @author idanilov
	 *
	 */
	private class Handler implements MouseInputListener {

		private int mouseXOffset;
		private Cursor otherCursor = resizeCursor;

		public void mouseClicked(MouseEvent e) {
		}

		private boolean canResize(TableColumn column) {
			return (column != null) && header.getResizingAllowed() && column.getResizable();
		}

		private TableColumn getResizingColumn(Point p) {
			return getResizingColumn(p, header.columnAtPoint(p));
		}

		private TableColumn getResizingColumn(Point p, int column) {
			if (column == -1) {
				return null;
			}
			Rectangle r = header.getHeaderRect(column);
			r.grow(-3, 0);
			if (r.contains(p)) {
				return null;
			}
			int midPoint = r.x + r.width / 2;
			int columnIndex;
			if (header.getComponentOrientation().isLeftToRight()) {
				columnIndex = (p.x < midPoint) ? column - 1 : column;
			} else {
				columnIndex = (p.x < midPoint) ? column : column - 1;
			}
			if (columnIndex == -1) {
				return null;
			}
			return header.getColumnModel().getColumn(columnIndex);
		}

		public void mousePressed(MouseEvent e) {
			header.setDraggedColumn(null);
			header.setResizingColumn(null);
			header.setDraggedDistance(0);

			Point p = e.getPoint();

			// First find which header cell was hit
			TableColumnModel columnModel = header.getColumnModel();
			int index = header.columnAtPoint(p);

			if (index != -1) {
				// The last 3 pixels + 3 pixels of next column are for resizing
				TableColumn resizingColumn = getResizingColumn(p, index);
				if (canResize(resizingColumn)) {
					header.setResizingColumn(resizingColumn);
					if (header.getComponentOrientation().isLeftToRight()) {
						mouseXOffset = p.x - resizingColumn.getWidth();
					} else {
						mouseXOffset = p.x + resizingColumn.getWidth();
					}
				} else if (header.getReorderingAllowed()) {
					TableColumn hitColumn = columnModel.getColumn(index);
					//#[ID]
					ColumnFixableTableHeader customHeader = (ColumnFixableTableHeader)header;
					if (hitColumn != null && !customHeader.isFixed(hitColumn.getModelIndex())) {
						header.setDraggedColumn(hitColumn);
					}
					//~[ID]
					mouseXOffset = p.x;
				}
			}
		}

		private void swapCursor() {
			Cursor tmp = header.getCursor();
			header.setCursor(otherCursor);
			otherCursor = tmp;
		}

		public void mouseMoved(MouseEvent e) {
			if (canResize(getResizingColumn(e.getPoint())) != (header.getCursor() == resizeCursor)) {
				swapCursor();
			}
		}

		public void mouseDragged(MouseEvent e) {
			int mouseX = e.getX();

			TableColumn resizingColumn = header.getResizingColumn();
			TableColumn draggedColumn = header.getDraggedColumn();

			boolean headerLeftToRight = header.getComponentOrientation().isLeftToRight();

			if (resizingColumn != null) {
				int oldWidth = resizingColumn.getWidth();
				int newWidth;
				if (headerLeftToRight) {
					newWidth = mouseX - mouseXOffset;
				} else {
					newWidth = mouseXOffset - mouseX;
				}
				resizingColumn.setWidth(newWidth);

				Container container;
				if ((header.getParent() == null)
						|| ((container = header.getParent().getParent()) == null)
						|| !(container instanceof JScrollPane)) {
					return;
				}

				if (!container.getComponentOrientation().isLeftToRight() && !headerLeftToRight) {
					JTable table = header.getTable();
					if (table != null) {
						JViewport viewport = ((JScrollPane) container).getViewport();
						int viewportWidth = viewport.getWidth();
						int diff = newWidth - oldWidth;
						int newHeaderWidth = table.getWidth() + diff;

						/* Resize a table */
						Dimension tableSize = table.getSize();
						tableSize.width += diff;
						table.setSize(tableSize);

						/* If this table is in AUTO_RESIZE_OFF mode and
						 * has a horizontal scrollbar, we need to update
						 * a view's position.
						 */
						if ((newHeaderWidth >= viewportWidth)
								&& (table.getAutoResizeMode() == JTable.AUTO_RESIZE_OFF)) {
							Point p = viewport.getViewPosition();
							p.x = Math.max(0, Math.min(newHeaderWidth - viewportWidth, p.x
									+ diff));
							viewport.setViewPosition(p);

							/* Update the original X offset value. */
							mouseXOffset += diff;
						}
					}
				}
			} else if (draggedColumn != null) {
				TableColumnModel cm = header.getColumnModel();
				int draggedDistance = mouseX - mouseXOffset;
				int direction = (draggedDistance < 0) ? -1 : 1;
				int columnIndex = viewIndexForColumn(draggedColumn);
				int newColumnIndex = columnIndex + (headerLeftToRight ? direction : -direction);
				if (0 <= newColumnIndex && newColumnIndex < cm.getColumnCount()) {
					int width = cm.getColumn(newColumnIndex).getWidth();
					if (Math.abs(draggedDistance) > (width / 2)) {
						//#[ID]
						ColumnFixableTableHeader customHeader = (ColumnFixableTableHeader)header;
						int modelColumnIndex = header.getTable().convertColumnIndexToModel(columnIndex);
						int newModelColumnIndex = header.getTable().convertColumnIndexToModel(newColumnIndex);
						if (!customHeader.isFixed(modelColumnIndex) && !customHeader.isFixed(newModelColumnIndex)) {
							mouseXOffset = mouseXOffset + direction * width;
							header.setDraggedDistance(draggedDistance - direction * width);
							cm.moveColumn(columnIndex, newColumnIndex);
							return;//way1 - uncomment to see (comment way2).
						}
						//return;//way2 -- uncomment to see (comment way1).
						//~[ID]
					}
				}
				setDraggedDistance(draggedDistance, columnIndex);
			}
		}

		public void mouseReleased(MouseEvent e) {
			setDraggedDistance(0, viewIndexForColumn(header.getDraggedColumn()));

			header.setResizingColumn(null);
			header.setDraggedColumn(null);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		//
		//	 Protected & Private Methods
		//

		private void setDraggedDistance(int draggedDistance, int column) {
			header.setDraggedDistance(draggedDistance);
			if (column != -1) {
				header.getColumnModel().moveColumn(column, column);
			}
		}

		private int viewIndexForColumn(TableColumn aColumn) {
			TableColumnModel cm = header.getColumnModel();
			for (int column = 0; column < cm.getColumnCount(); column++) {
				if (cm.getColumn(column) == aColumn) {
					return column;
				}
			}
			return -1;
		}
	}
}