package TweakingTableEditing.TableCellEditorWithDialogEditor;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Uses the same button '..." as cell editor does in order to show this button all the time -
 * not only when cell editor is activated.
 * @author idanilov
 *
 */
public class ActionTableCellRenderer	
		implements TableCellRenderer {
	
    private TableCellRenderer delegate; 
    private InvokerButton button; 
 
    public ActionTableCellRenderer(final TableCellRenderer delegate) { 
        this.delegate = delegate; 
        button = new InvokerButton();
    } 
 
    public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus,int r, int c) { 
        JPanel result = new JPanel(new BorderLayout());
        Component comp = delegate.getTableCellRendererComponent(t,value,isSelected,hasFocus,r,c); 
        result.add(comp,BorderLayout.CENTER); 
        result.add(button,BorderLayout.EAST);
        return result; 
    }

}