package TweakingTableEditing.TableCellEditorWithDialogEditor;

import java.awt.Insets;

import javax.swing.JButton;

/**
 * @author idanilov
 *
 */
public class InvokerButton extends JButton {

	public InvokerButton() {
		super("...");
		//setRequestFocusEnabled(false);
        setFocusPainted(false); 
        setMargin(new Insets(0, 0, 0, 0)); 
	}

}
