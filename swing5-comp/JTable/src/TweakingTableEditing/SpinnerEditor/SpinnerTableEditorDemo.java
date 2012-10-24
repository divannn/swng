package TweakingTableEditing.SpinnerEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * There are some problems when JSpinner used as editor in JTable:
 * <br>1)if editor is JSpinner , JTable doesn't get focus after editing finished.
 * The same occured if cell editor is any compound component, f.i. editable JCombobox. 
 * <br>2)editor is painted with artifacts if was opened by mouse (dbl click by default).
 * <br>3)when editor is activate - textfield in spinner doesn't get focus by default.
 * <p>
 * See in the code coments how to cope with problems.
 * <p> 
 * Also border removed from spinner for better look. 
 * @author idanilov
 * @jdk 1.5
 */
public class SpinnerTableEditorDemo extends JFrame {

	private String [] columnNames = {
		"Number spinner","List spinner",
	};
	
	private Object [][] data = {	
		{"1",new Level("2"),},
		{"6",new Level("7"),},
		{"11",new Level("1"),},
		{"16",new Level(""),},
		{"21",new Level("3"),},
	};

	public SpinnerTableEditorDemo() {
		super(SpinnerTableEditorDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		JTable table = new JTable(dtm) {
			
			//workaround for the problem 1).
			//see SUN bug 4887999 (was fixed in 1.6 for all compound editors).
			public boolean isFocusCycleRoot() {
				return isEditing();
			}
			
		};
		TableColumn firstColumn = table.getColumnModel().getColumn(0);
		firstColumn.setCellEditor(new SpinnerNumberEditor());
		
		TableColumn secondColumn = table.getColumnModel().getColumn(1);
		secondColumn.setCellEditor(new SpinnerListEditor());
			
		table.putClientProperty("JTable.autoStartsEdit",Boolean.FALSE);//do not start editor automatically.
		table.putClientProperty("terminateEditOnFocusLost",Boolean.TRUE);//terminate editor when table loses focus.

		table.setPreferredScrollableViewportSize(new Dimension(300,100));
		JScrollPane sp = new JScrollPane(table);
		JPanel result = new JPanel(new BorderLayout());
		result.add(sp,BorderLayout.CENTER);
		//just to demonstrate issue 1). Focus goes here instead of table component.
		result.add(new JButton("OK"),BorderLayout.SOUTH);
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
		SpinnerTableEditorDemo f = new SpinnerTableEditorDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}