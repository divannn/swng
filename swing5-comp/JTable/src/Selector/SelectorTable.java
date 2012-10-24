package Selector;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * @author idanilov
 *
 */
public class SelectorTable extends JTable {

	public SelectorTable(final SelectorTableModel tm) {
		super(tm);
		setTableHeader(null);
		setShowGrid(false);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//allow to select cells.
		setCellSelectionEnabled(true);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		SelectorItemCellRenderer cellRenderer = new SelectorItemCellRenderer();
		setDefaultRenderer(SelectorItem.class, cellRenderer);

		Dimension preferredCellDimension = calculateCellDimension(new Dimension(0, 0), cellRenderer);
		setRowHeight(preferredCellDimension.height);
		for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
			TableColumn nextColumn = getColumnModel().getColumn(i);
			nextColumn.setPreferredWidth(preferredCellDimension.width);
		}
		Dimension viewPortPreferredSize = new Dimension(preferredCellDimension.width
				* tm.getColumnCount(), preferredCellDimension.height * tm.getRowCount());
		setPreferredScrollableViewportSize(viewPortPreferredSize);
	}

	private Dimension calculateCellDimension(final Dimension result, final JLabel label) {
		SelectorTableModel tm = (SelectorTableModel) getModel();
		for (int i = 0; i < tm.size(); i++) {
			label.setText(tm.get(i).getName());
			label.setIcon(tm.get(i).getIcon());
			result.height = Math.max(result.height, label.getPreferredSize().height);
			result.width = Math.max(result.width, label.getPreferredSize().width);
		}
		return result;
	}

	private boolean canSelect(final int ind) {
		return canSelect(((SelectorTableModel) getModel()).get(ind));
	}

	private boolean canSelect(final int row, final int column) {
		return canSelect(((SelectorTableModel) getModel()).getValueAt(row, column));
	}

	/**
	 * @param o
	 * @return true if <code>o</code> can be selected.
	 */
	//XXX: maybe move to table model?
	protected boolean canSelect(final Object o) {
		if (o != null) {
			return ((SelectorItem) o).isEnabled();
		}
		return false;
	}

/*	Needed if chooser will have ability to invoke somthing on double click.
	public void processMouseEvent(MouseEvent me) {
		if (me.isConsumed()) {
			return;
		}
		if ((((me.getID() == MouseEvent.MOUSE_PRESSED)
				&& ((me.getModifiers() & InputEvent.BUTTON1_MASK) > 0) && ((me.getModifiers() & InputEvent.CTRL_MASK) == 0)) || ((me
				.getID() == MouseEvent.MOUSE_CLICKED)
				&& ((me.getModifiers() & InputEvent.BUTTON1_MASK) > 0) && (me.getClickCount() >= 2) && ((me
				.getModifiers() & InputEvent.CTRL_MASK) == 0)))
				&& canSelect(rowAtPoint(me.getPoint()), columnAtPoint(me.getPoint()))) {
			super.processMouseEvent(me);
		} else {
			me.consume();
		}
	}*/

/*	protected void processMouseMotionEvent(MouseEvent me) {
		if (me.isConsumed()) {
			return;
		}
		if (me.getID() == MouseEvent.MOUSE_PRESSED
				&& canSelect(rowAtPoint(me.getPoint()), columnAtPoint(me.getPoint()))) {
			super.processMouseMotionEvent(me);
		} else {
			me.consume();
		}
	}*/

	public void changeSelection(int row, int column, boolean toggle, boolean extend) {
		//forbid selection empty cells.
		if (canSelect(row, column)) {
			super.changeSelection(row, column, false, false);
		}
	}

	private class SelectorItemCellRenderer extends DefaultTableCellRenderer {

		public SelectorItemCellRenderer() {
			setHorizontalAlignment(CENTER);
			setVerticalTextPosition(BOTTOM);
			setHorizontalTextPosition(CENTER);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel result = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			return result;
		}

		protected void setValue(Object value) {
			if (value == null) {
				setText(null);
				setIcon(null);
			} else if (value instanceof SelectorItem) {
				SelectorItem item = (SelectorItem) value;
				setText(item.getName());
				setIcon(item.getIcon());
				setEnabled(canSelect(value));
			}
		}
	}

}
