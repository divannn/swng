package TreeTable.treetable.sort;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import TreeTable.treetable.TreeTable;
import TreeTable.treetable.icons.NumberedAscDescIcon;
import TreeTable.treetable.util.UiUtils;

/**
 * TreeTable with sorting capabilities.
 * Features:
 *
 * 1) This table model should be TreeTableSorter descendant
 * 2) Automatically adds mouse listener to header; this listener sorts by clicking
 * 3) Adds header renderer to display sorting icons
 * @author idanilov
 */
public class SortableTreeTable extends TreeTable {

	/** header renderer to display sorting icons */
	private HdrRenderer hdrRenderer = new HdrRenderer();
	private MouseListener currentListener;

	public SortableTreeTable(SortableTreeTableModel model) {
		super(model);
		init();
	}

	public SortableTreeTable() {
		init();
	}

	/** sets listeners and renderers */
	protected void init() {

		setHeaderRenderers();

		// can't add listener directly in constructor, since
		// at this moment table header doesn't exist
		addAncestorListener(new AncestorListener() {

			public void ancestorAdded(AncestorEvent event) {
				addMouseListenerToHeader();
			}

			public void ancestorRemoved(AncestorEvent event) {
				if (currentListener != null) {
					SortableTreeTable.this.getTableHeader().removeMouseListener(currentListener);
					currentListener = null;
				}
			}

			public void ancestorMoved(AncestorEvent event) {
			}

		});
	}

	/** sets hdrRenderer to each column header */
	protected void setHeaderRenderers() {
		TableColumn col;
		TableColumnModel col_model = getColumnModel();
		int size = col_model.getColumnCount();
		for (int i = 0; i < size; i++) {
			col = col_model.getColumn(i);
			col.setHeaderRenderer(hdrRenderer);
		}
	}

	/** just ti prevent setting model different from TreeTableModel (or its descendants) */
	public void setModel(TableModel model) {
		if (!(model instanceof SortableTreeTableModel)) {
			throw new IllegalArgumentException("TreeTable model must be a TreeTable subclass");
		}
		super.setModel(model);
		setHeaderRenderers(); // reset renderers, otherwise they'll disappear after model change
	}

	/** creates default (fake) model - overrides JTable method;
	 *  called from default constructor */
	protected TableModel createDefaultDataModel() {
		return SortableTreeTableModel.createDummySorterModel();
	}

	/** just a shortcut */
	public SortableTreeTableModel getSortableModel() {
		return (SortableTreeTableModel) getModel();
	}

	/** adds mouse listener, which allows sorting, to header  */
	protected void addMouseListenerToHeader() {

		setColumnSelectionAllowed(false);

		MouseListener listener = new MouseAdapter() {

			public void mouseClicked(MouseEvent ev) {

				TableModel model = getModel();
				if (!(model instanceof SortableTreeTableModel)) {
					return;
				}
				SortableTreeTableModel sorter = (SortableTreeTableModel) model;

				TableColumnModel col_model = getColumnModel();
				int view_col = col_model.getColumnIndexAtX(ev.getX());
				int column = convertColumnIndexToModel(view_col);
				if (ev.getClickCount() == 1 && column != -1) {
					int mod = ev.getModifiers();
					if ((mod & ev.BUTTON1_MASK) == ev.BUTTON1_MASK) {
						sorter.rotateConditions(column, (mod & ev.SHIFT_MASK) == ev.SHIFT_MASK);
						/*
						 boolean asc = true;
						 if (sorter.numSortingColumns() == 1) {
						 if (sorter.getSortingColumn(0) == column)
						 asc = ! sorter.isAscending(0);
						 }
						 */
						UiUtils.setWaitCursor(SortableTreeTable.this, true);
						Object[] objs = getSelectedObjects();
						/*
						 sorter.sortByColumn(column, asc, tbl);
						 */
						sorter.sort();

						ensureWidthToFit(view_col);
						sorter.fireTableChanged();
						setSelectedObjects(objs);
						UiUtils.setWaitCursor(SortableTreeTable.this, false);
					}
				}
			}
		};

		JTableHeader th = getTableHeader();
		//[ID]currentListener = new MouseProcessor(listener);
		currentListener = listener;
		th.addMouseListener(currentListener);
	}

	/**
	 * Header renderer for displaying sorting icons
	 */
	private class HdrRenderer extends JLabel
			implements TableCellRenderer {

		public HdrRenderer() {
			setBorder(BorderFactory.createCompoundBorder(UIManager
					.getBorder("TableHeader.cellBorder"), BorderFactory.createEmptyBorder(0, 2, 0,
					2)));
		}

		public Component getTableCellRendererComponent(JTable tbl, Object obj, boolean selected,
				boolean focused, int row, int col) {
			setIcon(null);
			int size = getSortableModel().getSortingColumnsCount();
			for (int i = 0; i < size; i++) {
				if (convertColumnIndexToView(getSortableModel().getSortingColumn(i)) == col) {
					setIcon(new NumberedAscDescIcon(i + 1, !getSortableModel().isAscending(i)));
					break;
				}
			}

			setText(tbl.getColumnName(col));

			if (tbl.getModel().getColumnClass(convertColumnIndexToModel(col)) == Integer.class) {
				setHorizontalAlignment(RIGHT);
			} else {
				setHorizontalAlignment(LEFT);
			}

			return this;
		}

		public String getToolTipText() {
			return null;
		}

		public String getToolTipText(MouseEvent ev) {
			return null;
		}
	}

}