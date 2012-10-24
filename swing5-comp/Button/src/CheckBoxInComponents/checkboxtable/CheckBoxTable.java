package CheckBoxInComponents.checkboxtable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import CheckBoxInComponents.ui.FlatCheckBox;
import CheckBoxInComponents.ui.FlatCheckBoxIcon;
import CheckBoxInComponents.util.CheckBoxUtil;

/**
 * JTable with ability to select row by checkbox.
 * <p>
 * Not intended to be modified - rows addition/removal is not suported.
 * Check icon designed fo Windows L&F only.
 * @author idanilov
 */
public class CheckBoxTable extends JTable
		implements ActionListener {

	private ListSelectionModel checkModel;
	private TableCellRenderer delegateCellRendererForCheckableColumn;
	private int checkableColumnIndex;
	
	private static final Dimension CHECK_SPOT = new Dimension(FlatCheckBoxIcon.SIZE,
			FlatCheckBoxIcon.SIZE);

	public CheckBoxTable() {
		this(new DefaultTableModel());
	}

	public CheckBoxTable(final int nRows, final int nCols) {
		this(new DefaultTableModel(nRows, nCols));
	}

	public CheckBoxTable(final TableModel model) {
		super(model);
		checkModel = new DefaultListSelectionModel();
		checkModel.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent lse) {
				if (lse.getValueIsAdjusting()) {
					return;
				}
				repaint();
			}

		});
		setCheckableColumn(0);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//only single selection allowed.
		setDelegateRendererForCheckableColumn(new DefaultTableCellRenderer());
		registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
				JComponent.WHEN_FOCUSED);
		addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				int row = rowAtPoint(me.getPoint());
				if (row < 0) {
					return;
				}
				int column = columnAtPoint(me.getPoint());
				if (column < 0) {
					return;
				}
				if (convertColumnIndexToModel(column) == checkableColumnIndex) {
					Rectangle clickArea = getCellRect(row, column, true);
					if (me.getX() > clickArea.x + CHECK_SPOT.width) {
						return;
					}
					if (me.getY() > clickArea.y + CHECK_SPOT.height) {
						return;
					}
					toggleSelection(row);
				}
			}
		});
	}
	
	/**Set column that will have checkbox button
	 * @param columnModelIndex
	 */
	public void setCheckableColumn(final int columnModelIndex) {
		TableColumn oldCheckableColumn = getColumnModel().getColumn(convertColumnIndexToView(checkableColumnIndex));
		oldCheckableColumn.setCellRenderer(null);
		if (columnModelIndex < getColumnModel().getColumnCount()) {
			checkableColumnIndex = columnModelIndex;
			TableColumn checkableColumn = getColumnModel().getColumn(convertColumnIndexToView(checkableColumnIndex));
			checkableColumn.setCellRenderer(new CheckListCellRenderer());
		}
	}
	
	public int getCheckableColumn() {
		return checkableColumnIndex;
	}
	/**
	 * @param render
	 */
	public void setDelegateRendererForCheckableColumn(final TableCellRenderer render) {
		delegateCellRendererForCheckableColumn = render;
	}

	public TableCellRenderer getDelegateCellRendererForCheckableColumn() {
		return delegateCellRendererForCheckableColumn;
	}

	/**
	 * @return Model indices of items that are currently checked.
	 */
	public int[] getCheckedIndices() {
		return CheckBoxUtil.getSelectedIndices(checkModel);
	}

	public boolean isChecked(final int i) {
		return checkModel.isSelectedIndex(i);
	}

	/**
	 * Check/uncheck list item in position i. 
	 * @param i item index
	 * @param state false to uncheck
	 */
	public void setChecked(final int i, final boolean state) {
		if (state) {
			checkModel.addSelectionInterval(i, i);
		} else {
			checkModel.removeSelectionInterval(i, i);
		}
	}

	/**
	 * Check items in passed indices.
	 * @param indices
	 */
	public void checkIndices(int[] indices) {
		int modelSize = getModel().getRowCount();
		for (int i = 0; i < indices.length; i++) {
			int nextIndex = indices[i];
			if (nextIndex < modelSize && nextIndex >= 0) {
				checkModel.addSelectionInterval(nextIndex, nextIndex);
			}
		}
	}

	/**
	 * Uncheck items in passed indices.
	 * @param indices
	 */
	public void clearIndices(int[] indices) {
		int modelSize = getModel().getRowCount();
		for (int i = 0; i < indices.length; i++) {
			int nextIndex = indices[i];
			if (nextIndex < modelSize && nextIndex >= 0) {
				checkModel.removeSelectionInterval(nextIndex, nextIndex);
			}
		}
	}

	public void setModel(final TableModel model) {
		if (checkModel != null) {//first call in constructor.
			checkModel.clearSelection();
		}
		super.setModel(model);
	}

	private void toggleSelection(final int index) {
		setChecked(index, !isChecked(index));
	}

	public void actionPerformed(final ActionEvent ae) {
		toggleSelection(getSelectedRow());
	}

	public void addCheckListener(final ListSelectionListener lse) {
		checkModel.addListSelectionListener(lse);
	}

	public void removeCheckListener(final ListSelectionListener lse) {
		checkModel.removeListSelectionListener(lse);
	}

	/**
	 * Compose plain checkbox and label.
	 * @author idanilov
	 *
	 */
	private class CheckListCellRenderer extends JPanel
			implements TableCellRenderer {

		private JCheckBox checkBox;

		public CheckListCellRenderer() {
			super(new BorderLayout(1, 0));
			setOpaque(false);
			checkBox = new FlatCheckBox();
			checkBox.setOpaque(false);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			Component delegateComp = delegateCellRendererForCheckableColumn
					.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			CheckBoxTable cbl = (CheckBoxTable) table;
			checkBox.setSelected(cbl.isChecked(row));
			removeAll();
			add(checkBox, BorderLayout.WEST);
			add(delegateComp, BorderLayout.CENTER);
			return this;
		}

	}

}
