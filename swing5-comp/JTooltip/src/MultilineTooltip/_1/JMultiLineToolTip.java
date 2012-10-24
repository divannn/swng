package MultilineTooltip._1;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

/**
 * Multi line tooltip base on JTextArea.
 * There are 3 variants of using: 
 * <br>1. divide string by '\n'.
 * <br>2. set width in columns (chars).
 * <br>3. set width in pixels
 * @author idanilov
 * 
 */
public class JMultiLineToolTip extends JToolTip {

	private static final String uiClassID = "MultiLineToolTipUI";

	private int columns;
	private int fixedwidth;

	public void updateUI() {
		setUI(MultiLineToolTipUI.createUI(this));
	}

    public String getUIClassID() {
        return uiClassID;
    }
    
	/**
	 * Set desired width in text columns.
	 * @param columns
	 */
	public void setColumns(final int columns) {
		this.columns = columns;
		this.fixedwidth = 0;
	}

	public int getColumns() {
		return columns;
	}

	/**
	 * Set desired width in pixels.
	 * @param width
	 */
	public void setFixedWidth(final int width) {
		this.fixedwidth = width;
		this.columns = 0;
	}

	public int getFixedWidth() {
		return fixedwidth;
	}

	/**
	 * @author idanilov
	 * 
	 */
	private static class MultiLineToolTipUI extends BasicToolTipUI {
		
		private static MultiLineToolTipUI sharedInstance = new MultiLineToolTipUI();
		
		protected CellRendererPane rendererPane;
		private static JTextArea textArea;

		public static ComponentUI createUI(JComponent c) {
			return sharedInstance;
		}
		
		public void installUI(JComponent c) {
			super.installUI(c);
			textArea = new JTextArea();
			textArea.setWrapStyleWord(true);
			textArea.setFont(c.getFont());
			textArea.setBackground(c.getBackground());
			rendererPane = new CellRendererPane();
			rendererPane.add(textArea);//XXX : it seems that this line can be removed.
			c.add(rendererPane);
		}

		public void uninstallUI(JComponent c) {
			super.uninstallUI(c);
			c.remove(rendererPane);
			rendererPane = null;
		}

		public void paint(Graphics g, JComponent c) {
			Dimension size = c.getSize();
			rendererPane.paintComponent(g, textArea, c, 1, 1, size.width - 1,
					size.height - 1, true);
		}

		/** 
		 * XXX : set corresponding size of text area.
		 * @return size based on size of text area
		 */
		public Dimension getPreferredSize(JComponent c) {
			String tipText = ((JToolTip)c).getTipText();
			if (tipText == null || tipText.length() == 0) {
				return new Dimension(0, 0);
			}
			textArea.setText(tipText);
			int width = ((JMultiLineToolTip) c).getFixedWidth();
			int columns = ((JMultiLineToolTip) c).getColumns();
			if (columns > 0) {
				textArea.setColumns(columns);
				textArea.setLineWrap(true);
				Dimension d = textArea.getPreferredSize();
	            FontMetrics metrics = textArea.getFontMetrics(textArea.getFont());
				d.width = getMaxWidth(tipText,columns,metrics);
				textArea.setSize(d);
			} else if (width > 0) {
				textArea.setLineWrap(true);
				Dimension d = textArea.getPreferredSize();
				d.width = width;
				textArea.setSize(d);
			} else {
				textArea.setLineWrap(false);
				textArea.setSize(textArea.getPreferredSize());
			}
			Dimension result = new Dimension(textArea.getSize());
			result.width += c.getInsets().right + c.getInsets().left;
			return result;
		}

		private int getMaxWidth(final String s, final int colNum, final FontMetrics m) {
			int result = 0;
			char [] chars = s.toCharArray();
			int rowNum = chars.length/colNum;
			if (rowNum == 0) {
				result = m.stringWidth(s);
			} else {
				int [] widths = new int[rowNum];
				for (int i = 0; i < rowNum; i++) {
					int nextEndIndex = (i+1)*colNum;
					if (nextEndIndex < chars.length) {
						String nextRow = s.substring(i*colNum ,nextEndIndex);
						int nextRowWidth = m.stringWidth(nextRow);
						widths[i] = nextRowWidth;
						//System.err.println(" >>" + nextRow + " " + nextRowWidth);
					}
				}
				Arrays.sort(widths);
				result = widths[rowNum-1];//take the max width.				
			}
			return result;
		}
		
		public Dimension getMinimumSize(JComponent c) {
			return getPreferredSize(c);
		}

		public Dimension getMaximumSize(JComponent c) {
			return getPreferredSize(c);
		}
	}

}