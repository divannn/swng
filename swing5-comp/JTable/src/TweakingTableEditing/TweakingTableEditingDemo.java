package TweakingTableEditing;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * Enhancements:
 * <br>1. Remove borders for common editors - text field and combo.
 * <br>2. Open popup for combo editor automatically.
 * <br>3. Add rows on tabbing to a cell beyond the last cell.
 * <br>
 * <br> Also there are two useful properties in JTable that controls some aspects of editing.
 * <br><strong>"JTable.autoStartsEdit"</strong>:
 * The value is of type Java.lang.Boolean. This property tells whether JTable should start edit when a key is types. If the value of this property is null, it defaults to Boolean.TRUE. So this feature is enabled by default in JTable.
 * <br><strong>"terminateEditOnFocusLost"</strong>:
 * The value is of type Java.lang.Boolean. This property tells whether what to do when focus goes outside of JTable. It first tries to commit the changes in editor, if can't be committed then the changes are cancelled. If the value of this property is null, it default to Boolean.FALSE. So this feature is disabled by default in JTable. You can enable this feature as follows:
 * table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
 * <br>
 * <br>
 * Also there are useful methods for editing table (not used here):
 * <br>
 * {@link DefaultCellEditor#setClickCountToStart(int)}
 * <br>
 * {@link JTable#setSurrendersFocusOnKeystroke(boolean)})
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class TweakingTableEditingDemo extends JFrame {

	private String [] columnNames = {
		"Combo editor","col2","col3",
	};
	
	private String [][] data = {	
		{"1","2","3",},
		{"6","7","8",},
		{"11","12","13",},
		{"16","17","18",},
		{"21","22","23",},
	};

	public TweakingTableEditingDemo() {
		super(TweakingTableEditingDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JPanel createContents() {
		JPanel result = new JPanel(new GridLayout(2,1));
		result.add(createDefaultTable());
		result.add(createImprovedTable());
		return result;
	}
	
	private JPanel createDefaultTable() {
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		JTable table = new JTable(dtm);
		JComboBox combo = new JComboBox(new String[] {"item1", "item2", "item3"}); 
		table.getColumn(columnNames[0]).setCellEditor(new DefaultCellEditor(combo));
		table.setPreferredScrollableViewportSize(new Dimension(300,100));
		JScrollPane sp = new JScrollPane(table);
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Default table:"),BorderLayout.NORTH);
		result.add(sp,BorderLayout.CENTER);
		return result;
	}
	
	private JPanel createImprovedTable() {
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		JTable table = new JTable(dtm) {
			// add rows on tabbing to a cell beyond the last cell.
			private final KeyStroke TAB = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);

			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				AWTEvent currentEvent = EventQueue.getCurrentEvent();
				if (currentEvent instanceof KeyEvent) {
					KeyEvent ke = (KeyEvent) currentEvent;
					if (ke.getSource() != this) {
						return;
					}
					// focus change with keyboard.
					if (rowIndex == 0 && columnIndex == 0
							&& KeyStroke.getKeyStrokeForEvent(ke).equals(TAB)) {
						((DefaultTableModel) getModel()).addRow(new Object[getColumnCount()]);
						rowIndex = getRowCount() - 1;
					}
				}
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
			}
		};
		//uncomment this line to start editing in cell only by F2.
		//table.putClientProperty("JTable.autoStartsEdit",Boolean.FALSE);
		//uncomment this line to stop editing on table focus lost. 
		//table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		JTextField tf = new JTextField(); 
		tf.setBorder(BorderFactory.createEmptyBorder());// remove border.
		table.setDefaultEditor(Object.class, new DefaultCellEditor(tf));
		
		JComboBox combo = new JComboBox(new String[] { "item1", "item2", "item3" }) {

			// make JComboBox popup open when user invokes editing with F2 key.
			public void processFocusEvent(FocusEvent fe) {
				super.processFocusEvent(fe);
				Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager()
						.getFocusOwner();
				if (isDisplayable() && fe.getID() == FocusEvent.FOCUS_GAINED && focusOwner == this
						&& !isPopupVisible()) {
					showPopup();
				}
			}
		}; 
		combo.setBorder(BorderFactory.createEmptyBorder());// remove border.
		table.getColumn(columnNames[0]).setCellEditor(new DefaultCellEditor(combo));
		
		table.setPreferredScrollableViewportSize(new Dimension(300,100));
		JScrollPane sp = new JScrollPane(table);
		
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JLabel("Improved table:"),BorderLayout.NORTH);
		result.add(sp,BorderLayout.CENTER);
		return result;
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
		TweakingTableEditingDemo f = new TweakingTableEditingDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}