package TweakingTableEditing.TableCellEditorWithDialogEditor;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

/**
 * Demo shows usage of [...] button to invoke Dialog from table cell editor.
 * Creates 2 table cell editors : one with editable textfield, second - with non-editable text field.
 * 
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class TableCellEditorWithDialogEditorDemo extends JFrame {

	public TableCellEditorWithDialogEditorDemo() {
		super(TableCellEditorWithDialogEditorDemo.class.getSimpleName());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(createContents());		
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		String [][] data = {
				{ "JavaTutorial", "This is sun's java tutorial.\nthis is best for beginners" }, 
				{ "JavaSwing", "This is book dedicated to swing.\nIt covers in-depth knowledge of swing" }, 
				{ "", "" }
		}; 
		String [] columnNames = { "Title of Book (editable)", "Description of Book (non-editable)" };
		JTable table = new JTable(data,columnNames) {
			//workaround for the problem - see SUN bug 4887999
			//(was fixed in 1.6 for all compound editors).
			public boolean isFocusCycleRoot() {
				return isEditing();
			}

		};
		table.setSurrendersFocusOnKeystroke(true);//request focus to cell editor on key type.
		//make 1st column.
		JTextField textField1 = new JTextField(); 
		textField1.setBorder(BorderFactory.createEmptyBorder()); 
		DefaultCellEditor editor1 = new DefaultCellEditor(textField1); 
		editor1.setClickCountToStart(1);//how many click to start editing.
		table.getColumnModel().getColumn(0).setCellEditor(new StringActionTableCellEditor(editor1,true));//editable.
		table.getColumnModel().getColumn(0).setCellRenderer(new ActionTableCellRenderer(new DefaultTableCellRenderer()));		
		//make 2d column.
		JTextField textField2 = new JTextField();
		textField2.setBorder(BorderFactory.createEmptyBorder()); 
		DefaultCellEditor editor2 = new DefaultCellEditor(textField2); 
		editor2.setClickCountToStart(1);
		table.getColumnModel().getColumn(1).setCellEditor(new StringActionTableCellEditor(editor2,false));//non-editable.
		table.getColumnModel().getColumn(1).setCellRenderer(new ActionTableCellRenderer(new DefaultTableCellRenderer()));		
		JScrollPane scrollPane = new JScrollPane(table);
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new  TableCellEditorWithDialogEditorDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class StringActionTableCellEditor extends ActionTableCellEditor {
		
	    public StringActionTableCellEditor(TableCellEditor editor,boolean isEditable) { 
	        super(editor,isEditable); 
	    } 
	 
	    protected void doEditCell(JTable t, Object curValue,int r, int c) { 
	        JTextArea textArea = new JTextArea(10,50); 
	        if (curValue != null) { 
	            textArea.setText((String)curValue); 
	            textArea.setCaretPosition(0); 
	        } 
	        int result = JOptionPane.showOptionDialog(table, 
	                new JScrollPane(textArea), table.getColumnName(column), 
	                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); 
	        if (result == JOptionPane.OK_OPTION) {
	            table.setValueAt(textArea.getText(), row, column);
	        }
	    } 
	}
	
}