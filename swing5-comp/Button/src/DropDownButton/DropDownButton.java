package DropDownButton;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * @author idanilov
 *
 */
public abstract class DropDownButton extends JComponent
		implements ChangeListener, PopupMenuListener, ActionListener, PropertyChangeListener {

	private JButton mainButton; 
    private JButton arrowButton;
    private JPopupMenu popup; 
    private boolean popupVisible; 
 
    public DropDownButton() {
    	createContents();
        popup = getPopupMenu(); 
        popup.addPopupMenuListener(this); 
        mainButton.getModel().addChangeListener(this); 
        arrowButton.getModel().addChangeListener(this); 
        arrowButton.addActionListener(this); 
        mainButton.addPropertyChangeListener("enabled", this);
    } 
 
    private void createContents() {
    	setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		arrowButton = new JButton(new ImageIcon(getClass().getResource("dropdown.gif")));
		arrowButton.setFocusable(false);
		mainButton = new JButton(new Icon() {//empty icon for aligning with arrow button wich has icon 16 pix in height.

			public int getIconHeight() {
				return 16;
			}

			public int getIconWidth() {
				return 0;
			}

			public void paintIcon(Component c, Graphics g, int x, int y) {
			}
			
		});
		mainButton.setFocusable(false);
		arrowButton.setMargin(new Insets(mainButton.getMargin().top, 0, mainButton.getMargin().bottom, 0)); 
		add(mainButton);
		add(arrowButton);
	}
    
    public void setIcon(final Icon icon) {
		mainButton.setIcon(icon);
	}

    public void setText(final String text) {
		mainButton.setText(text);
	}

    public void setTooltip(final String tooltip) {
		mainButton.setToolTipText(tooltip);
		arrowButton.setToolTipText(tooltip);
	}
    
    public void propertyChange(PropertyChangeEvent evt) { 
        arrowButton.setEnabled(mainButton.isEnabled()); 
    } 
 
    public void stateChanged(ChangeEvent e) { 
        if (e.getSource() == mainButton.getModel()) { 
            if (popupVisible && !mainButton.getModel().isRollover()) { 
                mainButton.getModel().setRollover(true); 
                return; 
            } 
            arrowButton.getModel().setRollover(mainButton.getModel().isRollover()); 
            arrowButton.setSelected(mainButton.getModel().isArmed() && mainButton.getModel().isPressed()); 
        } else { 
            if (popupVisible && !arrowButton.getModel().isSelected()) { 
                arrowButton.getModel().setSelected(true); 
                return; 
            } 
            mainButton.getModel().setRollover(arrowButton.getModel().isRollover()); 
        } 
    } 
 
    public void actionPerformed(ActionEvent ae) { 
         popup.show(mainButton, 0, mainButton.getHeight()); 
	} 
 
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) { 
        popupVisible = true; 
        mainButton.getModel().setRollover(true); 
        arrowButton.getModel().setSelected(true); 
    } 
 
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { 
        popupVisible = false; 
        mainButton.getModel().setRollover(false); 
        arrowButton.getModel().setSelected(false);
    } 
 
    public void popupMenuCanceled(PopupMenuEvent e) { 
        popupVisible = false; 
    } 
 
    protected abstract JPopupMenu getPopupMenu(); 
 
}
