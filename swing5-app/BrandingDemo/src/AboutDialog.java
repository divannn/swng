import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import window.AbstractDialog;
import window.DialogConstant;

/**
 * @author idanilov
 *
 */
public class AboutDialog extends AbstractDialog {

	public AboutDialog(final JFrame parent) {
		super(parent,"About");
		setModal(true);
		setResizable(false);
		create();
	}
	
	protected JComponent createClientArea() {
		JComponent result = super.createClientArea();
		result.setBackground(Color.WHITE);
		URL url = AboutDialog.class.getResource("about.gif");
		ImageIcon image = new ImageIcon(url);
		JLabel imageLabel = new JLabel(image);
		result.add(imageLabel,BorderLayout.WEST);
		result.add(createTextComponent(),BorderLayout.CENTER);
		return result; 
	}
	
	private JComponent createTextComponent() {
		JTextPane result = new JTextPane();
        SimpleAttributeSet normal = new SimpleAttributeSet();
        SimpleAttributeSet bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, true);
        try {
			result.getDocument().insertString(
					result.getDocument().getLength(),
			        "Chicago Client\n",
			        bold);
			result.getDocument().insertString(
					result.getDocument().getLength(),
			        "Version: 1.0\nBuild id: 0000\n\n(c) Copyright Alcatel 2005",
			        normal);
		} catch (BadLocationException ble) {
			ble.printStackTrace();
		}
		result.setEditable(false);
		result.setFocusable(false);
		return result;
	}
	
	protected void createButtonsForButtonBar(final JComponent buttonBar) {
        JButton buttonOk = createButton(buttonBar, DialogConstant.OK_ID, DialogConstant.OK_LABEL);
        buttonOk.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                clickOnOk();
            }

        });
        setDefaultButton(DialogConstant.OK_ID);
    }
	
}
