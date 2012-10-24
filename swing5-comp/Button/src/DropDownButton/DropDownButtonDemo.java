package DropDownButton;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class DropDownButtonDemo extends JFrame {

	public DropDownButtonDemo() {
		super(DropDownButtonDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JPanel createContents() {
		JPanel result = new JPanel(new BorderLayout());
		JToolBar toolBar = new JToolBar();
		DropDownButton textImageButton = new DropDownButton() {

			protected JPopupMenu getPopupMenu() {
				JPopupMenu r = new JPopupMenu();
				JMenuItem mi1 = new JMenuItem("item1");
				JMenuItem mi2 = new JMenuItem("item2");
				JMenuItem mi3 = new JMenuItem("item3");
				r.add(mi1);
				r.add(mi2);
				r.add(mi3);
				return r;
			}
			
		};
		textImageButton.setText("Text & Image");
		textImageButton.setTooltip("Text & Image Button");
		textImageButton.setIcon(new ImageIcon(DropDownButtonDemo.class.getResource("image.gif")));
		DropDownButton imageButton = new DropDownButton() {

			protected JPopupMenu getPopupMenu() {
				JPopupMenu r = new JPopupMenu();
				JMenuItem mi1 = new JMenuItem("item1");
				JMenuItem mi2 = new JMenuItem("item2");
				r.add(mi1);
				r.add(mi2);
				return r;
			}
			
		};
		imageButton.setTooltip("Image Button");
		imageButton.setIcon(new ImageIcon(DropDownButtonDemo.class.getResource("image.gif")));
		DropDownButton textButton = new DropDownButton() {

			protected JPopupMenu getPopupMenu() {
				JPopupMenu r = new JPopupMenu();
				JMenuItem mi1 = new JMenuItem("item1");
				r.add(mi1);
				return r;
			}
			
		};
		textButton.setText("Text");
		textButton.setTooltip("Text Button");
		
		toolBar.add(textImageButton);
		toolBar.add(imageButton);
		toolBar.add(textButton);
		
		result.add(toolBar,BorderLayout.NORTH);
		return result;
	}
	
	public static void main(String[] args) {
		/*try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }	*/			
        JFrame f = new DropDownButtonDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}

}
