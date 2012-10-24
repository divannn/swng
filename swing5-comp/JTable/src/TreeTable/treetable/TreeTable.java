package TreeTable.treetable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import TreeTable.treetable.icons.CompoundIcon;
import TreeTable.treetable.icons.EmptyIcon;
import TreeTable.treetable.icons.PlusMinusIcon;


/**
 * @author idanilov
 *
 */
public class TreeTable extends JTable {

	public static final int EXPANDED_ICON = 1;
	public static final int COLLAPSED_ICON = 2;
	public static final int LEAF_ICON = 3;

	private boolean expandOnDblClick;

	protected Icon expandedIcon;
	protected Icon leafIcon;
	protected Icon collapsedIcon;

	protected TreeNodeRenderer treeNodeRenderer = new TreeNodeRenderer();
	protected int treeColumnIndex;

	public TreeTable() {
		super();
		setListeners();
	}

	public TreeTable(TreeTableModel model) {
		super(model);
		setListeners();
	}

	protected TableModel createDefaultDataModel() {
		return new DefaultTreeTableModel();
	}

	/** just ti prevent setting model different from TreeTableModel (or its descendants) */
	public void setModel(TableModel model) {
		if (!(model instanceof TreeTableModel)) {
			throw new IllegalArgumentException("TreeTable model must be a TreeTableModel subclass");
		}
		super.setModel(model);
	}

	public TreeTableModel getTreeTableModel() {
		return (TreeTableModel) getModel();
	}

	public void setExpandedIcon(Icon icon) {
		expandedIcon = icon;
	}

	public final Icon getExpandedIcon() {
		return expandedIcon;
	}

	public void setCollapsedIcon(Icon icon) {
		collapsedIcon = icon;
	}

	public final Icon getCollapsedIcon() {
		return collapsedIcon;
	}

	public void setLeafIcon(Icon icon) {
		leafIcon = icon;
	}

	public final Icon getLeafIcon() {
		return leafIcon;
	}

	public boolean getExpandOnDblClick() {
		return expandOnDblClick;
	}

	public void setExpandOnDblClick(boolean expand) {
		expandOnDblClick = expand;
	}

	/**
	 * Called by node renderer to specify, what icon should we display.
	 * If icons for all branches are the same, you needn't override this method -
	 * just use setCollapsedIcon, setExpandedIcon, setLeafIcon insted.
	 * If icons depends on nodes, then you should override this method.
	 *
	 * @param node node
	 * @param iconType specifies icon type - can be EXPANDED_ICON, COLLAPSED_ICON or LEAF_ICON.
	 *  parameter is superfluous - nevertheless it speeds up execution a little,
	 *  since TreeTable already knows what kind of leaf we have
	 *
	 */
	public Icon getNodeIcon(Object node, int iconType) {
		// implementation node:
		// NB: get<xxxx>Icon are all final, so we may use corresponding fields
		// instead of methods - this also speeds up a little
		switch (iconType) {
			case EXPANDED_ICON:
				return expandedIcon;
			case COLLAPSED_ICON:
				return collapsedIcon;
			case LEAF_ICON:
				return leafIcon;
		}
		return null;
	}

	public int getTreeColumnIndex() {
		return treeColumnIndex;
	}

	public void setTreeColumnIndex(int index) {
		treeColumnIndex = index;
	}

	public TableCellRenderer getCellRenderer(int row, int col) {
		if (convertColumnIndexToModel(col) == treeColumnIndex) {
			return treeNodeRenderer;
		} else {
			return super.getCellRenderer(row, col);
		}
	}

	protected void setListeners() {
		addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent ev) {
				if ((ev.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
					int x = ev.getX();
					int y = ev.getY();
					int row = rowAtPoint(new Point(x, y));
					int col = columnAtPoint(new Point(x, y));
					if (convertColumnIndexToModel(col) == treeColumnIndex) {
						Rectangle r = getCellRect(row, col, false);
						TreeTableModel model = getTreeTableModel();
						Object rowObj = model.getRowMapper().mapRowToObject(row);
						//if( ev.getClickCount() == 1 ) {
						if (treeNodeRenderer.isInsideTreeIcon(r, rowObj, x, y)) {
							switchExpandedState(rowObj);
							getSelectionModel().setSelectionInterval(row, row);
						}
						//}
						else if (ev.getClickCount() == 2) {
							if (expandOnDblClick) {
								//if( ! treeNodeRenderer.isInsideTreeIcon(r, rowObj, x, y) ) {
								switchExpandedState(rowObj);
								getSelectionModel().setSelectionInterval(row, row);
								//}
							}
						}
					}
				}
			}
		});

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				//27924 - QA Dialogs: Improve UI functionality. (KeyEvent.VK_ENTER)
				if (keyCode != KeyEvent.VK_ADD && keyCode != KeyEvent.VK_SUBTRACT
						&& keyCode != KeyEvent.VK_ENTER) {

					//30141 - QA Audit and Metrics: add result tables mousewheel support
					// I don't undestand why JTable doesn't process mousewheel in this class
					// To fix bug I catch mousewheel events and convert them to UP and DOWN
					if (e.getModifiers() == KeyEvent.CTRL_MASK
							&& (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN)) {
						//e.setModifiers(0);
						//processKeyEvent(e);
						KeyEvent ne = new KeyEvent((Component) (e.getSource()), e.getID(), e
								.getWhen(), 0, keyCode);
						e.consume();
						processKeyEvent(ne);
					}

					return;
				}

				int row = getSelectedRow();
				if (row < 0) {
					return;
				}

				TreeTableModel model = getTreeTableModel();
				Object rowObj = model.getRowMapper().mapRowToObject(row);
				if (!model.isLeaf(rowObj)) {
					TreeExpansionModel expModel = model.getExpansionModel();
					boolean expanded = expModel.isExpanded(rowObj);
					//27924 - QA Dialogs: Improve UI functionality. (KeyEvent.VK_ENTER)
					if ((keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_ENTER) && !expanded) {
						expModel.expand(rowObj);
						//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
						getSelectionModel().setSelectionInterval(row, row);
						//revalidate();
					} else
					//27924 - QA Dialogs: Improve UI functionality. (KeyEvent.VK_ENTER)
					if ((keyCode == KeyEvent.VK_SUBTRACT || keyCode == KeyEvent.VK_ENTER)
							&& expanded) {
						expModel.collapse(rowObj);
						//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
						getSelectionModel().setSelectionInterval(row, row);
						//revalidate();
					} else {
					}

					if (keyCode == KeyEvent.VK_ENTER) {
						e.consume();
					}

				} else {
				}
			}
		});
	}

	public void switchExpandedState(Object rowObj) {
		TreeTableModel model = getTreeTableModel();
		if (!model.isLeaf(rowObj)) {
			TreeExpansionModel expModel = model.getExpansionModel();
			if (expModel.isExpanded(rowObj)) {
				expModel.collapse(rowObj);
			} else {
				expModel.expand(rowObj);
			}
			//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
			// revalidate();
		}
	}

	public void expand(Object node) {
		TreeTableModel model = getTreeTableModel();
		TreeExpansionModel expModel = model.getExpansionModel();
		expModel.expand(node);
		//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
	}

	public void collapse(Object node) {
		TreeTableModel model = getTreeTableModel();
		TreeExpansionModel expModel = model.getExpansionModel();
		expModel.collapse(node);
		//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
	}

	public void expandChildren(Object node) {
		TreeTableModel model = getTreeTableModel();
		TreeExpansionModel expModel = model.getExpansionModel();
		int cnt = model.getChildCount(node);
		for (int i = 0; i < cnt; i++) {
			expModel.expand(model.getChildAt(node, i));
		}
		//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
	}

	public void collapseChildren(Object node) {
		TreeTableModel model = getTreeTableModel();
		TreeExpansionModel expModel = model.getExpansionModel();
		int cnt = model.getChildCount(node);
		for (int i = 0; i < cnt; i++) {
			expModel.collapse(model.getChildAt(node, i));
		}
		//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
	}

	public void expandAll(Object node) {
		TreeTableModel model = getTreeTableModel();
		TreeExpansionModel expModel = model.getExpansionModel();
		expandOrCollapseAll(true, model, expModel, node);
		//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
	}

	public void collapseAll(Object node) {
		TreeTableModel model = getTreeTableModel();
		TreeExpansionModel expModel = model.getExpansionModel();
		expandOrCollapseAll(false, model, expModel, node);
		//model.fireTableChanged(); // now TreeExpansionModel itself is responsible for notifying listeners
	}

	private void expandOrCollapseAll(boolean expand, TreeTableModel model,
			TreeExpansionModel expModel, Object node) {
		if (expand) {
			expModel.expand(node);
		} else {
			expModel.collapse(node);
		}
		int cnt = model.getChildCount(node);
		for (int i = 0; i < cnt; i++) {
			expandOrCollapseAll(expand, model, expModel, model.getChildAt(node, i));
		}
	}

	/**
	 almost the same as Column.sizeWidthToFit,
	 but it only make column grow (if necessary), not shrink
	 @param colIdx column to size
	 */
	public void ensureWidthToFit(int colIdx) {
		TableColumnModel model = getColumnModel();
		TableColumn col = model.getColumn(colIdx);
		TableCellRenderer headerRenderer = col.getHeaderRenderer();
		if (headerRenderer == null) {
			return;
		}
		Component comp = headerRenderer.getTableCellRendererComponent(this, col.getHeaderValue(),
				false, false, -1, colIdx);

		int width = comp.getPreferredSize().width;

		if (col.getPreferredWidth() < width)
			col.setPreferredWidth(width);

		if (col.getWidth() < width) {
			col.setWidth(width);
		}
	}

	public Object[] getSelectedObjects() {
		int[] rows = getSelectedRows();
		int cnt = rows.length;
		Object[] objs = new Object[cnt];
		RowMapper mapper = getTreeTableModel().getRowMapper();
		for (int i = 0; i < cnt; i++) {
			objs[i] = mapper.mapRowToObject(rows[i]);
		}
		return objs;
	}

	public void setSelectedObjects(Object[] objs) {
		selectionModel.clearSelection();
		int cnt = objs.length;
		//int[] rows = new int[cnt];
		RowMapper mapper = getTreeTableModel().getRowMapper();
		for (int i = 0; i < cnt; i++) {
			int row = mapper.mapObjectToRow(objs[i]);
			if (row >= 0) {
				selectionModel.addSelectionInterval(row, row);
			}
		}
	}

	protected class TreeNodeRenderer extends JLabel
			implements TableCellRenderer {

		private CompoundIcon collapsedNode;
		private CompoundIcon expandedNode;
		private CompoundIcon leafNode;

		private int stdIconWidth;
		private int stdIconHeight;

		protected int getIndent(Object rowObj) {
			int level = getTreeTableModel().getNodeLevel(rowObj);
			if (!getTreeTableModel().isRootVisible()) {
				level--;
			}
			return 4 + level * stdIconWidth;
		}

		public Icon getDisabledIcon() {
			return getIcon();
		}

		// ToDo: optimize border creation: use two array of borders
		protected Border getBorder(Object rowObj, boolean focused) {
			int indent = getIndent(rowObj);
			Border border;
			if (focused) {
				border = BorderFactory.createCompoundBorder(BorderFactory
						.createLineBorder(Color.gray), BorderFactory.createEmptyBorder(0,
						indent - 1, 0, 0));
			} else {
				border = BorderFactory.createEmptyBorder(0, indent, 0, 0);
			}
			return border;
		}

		protected TreeNodeRenderer() {
			setOpaque(true);

			PlusMinusIcon plusIcon = new PlusMinusIcon(PlusMinusIcon.PLUS, false);
			PlusMinusIcon minusIcon = new PlusMinusIcon(PlusMinusIcon.MINUS, false);
			EmptyIcon emptyIcon = new EmptyIcon(Math.max(plusIcon.getIconWidth(), minusIcon
					.getIconWidth()), Math.max(plusIcon.getIconHeight(), minusIcon.getIconHeight()));

			collapsedNode = new CompoundIcon(plusIcon, null);
			expandedNode = new CompoundIcon(minusIcon, null);
			leafNode = new CompoundIcon(emptyIcon, null);

			stdIconWidth = Math.max(expandedNode.getIconWidth(), collapsedNode.getIconWidth());
			stdIconHeight = Math.max(expandedNode.getIconHeight(), collapsedNode.getIconHeight());
		}

		protected boolean isInsideTreeIcon(Rectangle cellRect, Object rowObj, int x, int y) {
			int indent = getIndent(rowObj);
			cellRect.x += indent;
			Icon icon = expandedNode.getLeftIcon();
			cellRect.width = (icon == null) ? 0 : icon.getIconWidth();
			boolean inside = cellRect.contains(x, y);
			return inside;
		}

		public Component getTableCellRendererComponent(JTable tbl, Object obj, boolean selected,
				boolean focused, int row, int col) {
			TreeTable table = (TreeTable) tbl;

			if (selected) {
				setForeground(tbl.getSelectionForeground());
				setBackground(tbl.getSelectionBackground());
			} else {
				setForeground(tbl.getForeground());
				setBackground(tbl.getBackground());
			}

			TreeTableModel model = table.getTreeTableModel();
			Object rowObj = model.getRowMapper().mapRowToObject(row);

			setBorder(getBorder(rowObj, focused));

			if (model.isLeaf(rowObj)) {
				leafNode.setRightIcon(getNodeIcon(rowObj, LEAF_ICON));
				setIcon(leafNode);
			} else if (model.getExpansionModel().isExpanded(rowObj)) {
				expandedNode.setRightIcon(getNodeIcon(rowObj, EXPANDED_ICON));
				setIcon(expandedNode);
			} else {
				collapsedNode.setRightIcon(getNodeIcon(rowObj, COLLAPSED_ICON));
				setIcon(collapsedNode);
			}

			setText(getText(obj, row, col));
			return this;
		}

		protected String getText(Object obj, int row, int col) {
			return obj == null ? "" : obj.toString();
		}
	}

}
