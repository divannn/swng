package FixedColumns._2;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;


/**
 * @author idanilov
 *
 */
public class ColumnFixableTableHeader extends JTableHeader {

	private int [] fixedColumnIndices;
	
	public ColumnFixableTableHeader(final TableColumnModel model) {
		super(model);
	}
	
	public void setFixedColumnIndices(final int[] fixedColumnIndices) {
		if (fixedColumnIndices == null) {
			throw new IllegalArgumentException("Specify non-null array.");
		}
		this.fixedColumnIndices = fixedColumnIndices;
	}
	
	public boolean isFixed(final int modelIndex) {
		boolean result = false;
		if (fixedColumnIndices != null) {
			for (int next : fixedColumnIndices) {
				if (modelIndex == next) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * @return fixed column model indices
	 */
	public int[] getFixedColumnIndices() {
		return fixedColumnIndices;
	}
	
	public void updateUI() {
		setUI(ColumnFixableTableHeaderUI.createUI(this));
		resizeAndRepaint();
		invalidate();
	}

}
