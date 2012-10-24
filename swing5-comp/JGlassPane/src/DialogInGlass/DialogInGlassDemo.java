package DialogInGlass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Sheet window demo. Sheet simulates modal JDialog without title decoration.
 * Sheet is adheres to JFrame or JDialog.
 * For simplicity JOptionPane content is taken.
 * @author swinghacks
 * @author idanilov
 * @jdk 1.5
 */
public class DialogInGlassDemo extends JFrame 
		implements PropertyChangeListener {
	
	private JPanel glassPane;
	
	public DialogInGlassDemo() {
		super(DialogInGlassDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
		glassPane = new JPanel(new GridBagLayout());
		glassPane.setOpaque(false);
		setGlassPane(glassPane);		
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		ImageIcon icon = new ImageIcon(DialogInGlassDemo.class.getResource("me.jpg"));
		JLabel label = new JLabel(icon);
		result.add(label,BorderLayout.CENTER);
		return result;
	}
	
	//build JOptionPane dialog and hold onto it.
	private JDialog createDialog() {
		JOptionPane optionPane = new JOptionPane("Do you want to save?",
				JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
		optionPane.addPropertyChangeListener(this);
		JDialog result = optionPane.createDialog(this, "irrelevant");
		return result;
	}	
	
	private JComponent showJDialogAsSheet(final JDialog dialog) {
		JComponent sheet = (JComponent)dialog.getContentPane();
		sheet.setBorder(new LineBorder(Color.BLACK,1));
		glassPane.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		glassPane.add(sheet, gbc);
		gbc.gridy = 1;
		glassPane.add(Box.createGlue(), gbc);
		glassPane.validate();
		showSheet(true);
		return sheet;
	}	
	
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals(JOptionPane.VALUE_PROPERTY)) {
			System.out.println("Selected option " + pce.getNewValue());
			showSheet(false);
		}
	}
	
	private void showSheet(final boolean show) {
		glassPane.setVisible(show);
	}
	
	public static void main(String[] args) {
		DialogInGlassDemo f = new DialogInGlassDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		//pause for effect, then show the sheet.
		try {
			Thread.sleep(1500);
		} catch(InterruptedException ie) {
		}
		JDialog d = f.createDialog();
		f.showJDialogAsSheet(d);	
	}
	
}
