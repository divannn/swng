package MultilineTableCellEditor._2;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * Example of useage multiline cell editor inline.
 * If text occupies several lines - row height of editing cell is updated so that editor fit in it.
 * @author idanilov
 * @jdk 1.5
 */
public class MultilneCellEditorDemo2 extends JFrame {

	public MultilneCellEditorDemo2() {
		super(MultilneCellEditorDemo2.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JTable table = new JTable(10, 3);
		//set multilne cell renderer for columns 1, 2.
		TableColumnModel cmodel = table.getColumnModel();
		TextAreaRenderer textAreaRenderer = new TextAreaRenderer();
		cmodel.getColumn(0).setHeaderValue("Plain");
		cmodel.getColumn(1).setHeaderValue("Multiline");
		cmodel.getColumn(1).setCellRenderer(textAreaRenderer);
		cmodel.getColumn(2).setHeaderValue("Multiline");
		cmodel.getColumn(2).setCellRenderer(new TextAreaRenderer());
		TextAreaEditor textEditor = new TextAreaEditor();
		cmodel.getColumn(1).setCellEditor(textEditor);
		cmodel.getColumn(2).setCellEditor(textEditor);

		//init some cells.
		String str = "The lazy dog jumps over the quick brown fox";
		for (int column = 0; column < 3; column++) {
			table.setValueAt(str, 0, column);
			table.setValueAt(str, 4, column);
		}
		String longString = str;
		for (int i = 0; i < 5; i++) {
			longString+= str;
		}
		table.setValueAt(longString, 4, 2);
		
		result.add(new JScrollPane(table), BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new MultilneCellEditorDemo2();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
