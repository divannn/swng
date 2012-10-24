package EditableTableHeader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;


/**
 * @author idanilov
 *
 */
public class EditableHeaderSupport extends MouseAdapter 
    		implements TableColumnModelListener, FocusListener, ActionListener {

	private JTableHeader        tableHeader;
    private JTextField          editingComponent;
    private TableColumnModel    columnModel;
    private int                 editingColumn = -1;
    
    public EditableHeaderSupport(final JTable table) {
		//table header need to detect double clicks.
        tableHeader = table.getTableHeader();
        tableHeader.addMouseListener(this);
        
        //need to know if column events occur.
        columnModel = table.getColumnModel(); 
        columnModel.addColumnModelListener(this);
        
        //need to know when the JTextField finishes editing.
        editingComponent = new JTextField();  
        editingComponent.addActionListener(this); 

        //need to know when the JTextField loses focus.
        editingComponent.addFocusListener(this);
    } 

    public void focusGained(FocusEvent e) {
    	
    }
    
    public void focusLost(FocusEvent e) {
        endEditing();
    }
    
    //when the the JTextField content is changed it fires this event.
    public void actionPerformed(ActionEvent ae) {
        endEditing();
    } 

    /* Any column event need to end editing, things get ugly if header of
       column being edited is moved or resized */
    public void columnAdded(TableColumnModelEvent e) {
        endEditing();
    }

    public void columnMarginChanged(ChangeEvent e) {
        endEditing();
    }

    public void columnMoved(TableColumnModelEvent e) {
        endEditing();
    }

    public void columnRemoved(TableColumnModelEvent e) {
        endEditing();
    }
    
    public void columnSelectionChanged(ListSelectionEvent e) {
    	//don't bother.
    }
    
    public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
            int col = tableHeader.columnAtPoint(me.getPoint());
            // ... see if it's in the header.
            if (col != -1) {
                editingColumn = col; 
                // ... add JTextField to the header.
                tableHeader.add(editingComponent);
                // ... grab the text value from the header.
                editingComponent.setText(
                    (String) columnModel.getColumn(col).getHeaderValue());
                // ... resize the JTextField to overlay the header column.
                editingComponent.setBounds(tableHeader.getHeaderRect(col));
                editingComponent.requestFocus();
            }
        }
    } 
    
    public void endEditing() {
         //if where are done editing, make sure we really editing.
         if (editingColumn != -1) {
            // ... set the header value to the value of the JTextField.
            columnModel.getColumn(editingColumn).setHeaderValue(editingComponent.getText());
            // ... remove the JTextField and refesh header column area.
            tableHeader.remove(editingComponent);
            tableHeader.repaint(tableHeader.getHeaderRect(editingColumn)); 
            editingColumn = -1;
         } 
    }

}