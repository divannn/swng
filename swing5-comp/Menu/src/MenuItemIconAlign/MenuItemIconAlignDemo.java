package MenuItemIconAlign;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * By default menu items aligned ugly. There are two problems:
 * <br> 
 * 1) Menu items with icon show their text shifted to the right. 
 * Empty transparent icon is used for simulating left inset of the menu item text.
 * <br>
 * <strong>Note:</strong>It is assumed that all icons have the same size 16x16.
 * <br>
 * 2) Radio menu item's selection mark by default have size 6x6 
 * whereas check menu item's selection mark - 9x9. So this icon is replaced.
 * <br>
 * Note: This demo made for Windows L&F.
 * @author idanilov
 * @jdk 1.5
 */
public class MenuItemIconAlignDemo extends JFrame {

	private static final Icon TRANSPARENT_ICON = new ImageIcon(MenuItemIconAlignDemo.class.getResource("empty.png"));
	
	public MenuItemIconAlignDemo() {
		super(MenuItemIconAlignDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar result = new JMenuBar();
		result.add(createDefaultMenu());
		result.add(createImprovedMenu());
		return result;
	}
	
	private JMenu createDefaultMenu() {
		JMenu result = new JMenu("Default Menu");
		JMenuItem plainMenuItem = new JMenuItem("Item");
		assignAccelerator(plainMenuItem);
		result.add(plainMenuItem);
		
		JMenuItem plainMenuItemWithIcon = new JMenuItem("Item With Icon");
		assignAccelerator(plainMenuItemWithIcon);
		Icon icon1 = new ImageIcon(MenuItemIconAlignDemo.class.getResource("dots1.png"));
		plainMenuItemWithIcon.setIcon(icon1);
		result.add(plainMenuItemWithIcon);
		
		result.addSeparator();
		
		JMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("Checkbox Item",true);
		assignAccelerator(checkBoxMenuItem);
		result.add(checkBoxMenuItem);
		
		JMenuItem checkBoxMenuItemWithIcon = new JCheckBoxMenuItem("Checkbox Item With Icon",true);
		assignAccelerator(checkBoxMenuItemWithIcon);
		Icon icon2 = new ImageIcon(MenuItemIconAlignDemo.class.getResource("dots2.png"));
		checkBoxMenuItemWithIcon.setIcon(icon2);
		result.add(checkBoxMenuItemWithIcon);
		
		result.addSeparator();
		
		JMenuItem radioBoxMenuItem = new JRadioButtonMenuItem("Radio Item",true);
		assignAccelerator(radioBoxMenuItem);
		result.add(radioBoxMenuItem);
		
		JMenuItem radioMenuItemWithIcon = new JRadioButtonMenuItem("Radio Item With Icon",true);
		assignAccelerator(radioMenuItemWithIcon);
		Icon icon3 = new ImageIcon(MenuItemIconAlignDemo.class.getResource("dots3.png"));
		radioMenuItemWithIcon.setIcon(icon3);
		result.add(radioMenuItemWithIcon);
		return result;
	}

	private JMenu createImprovedMenu() {
		JMenu result = new JMenu("Improved Menu");
		JMenuItem plainMenuItem = new JMenuItem("Item");
		plainMenuItem.setIcon(TRANSPARENT_ICON);
		assignAccelerator(plainMenuItem);
		result.add(plainMenuItem);
		
		JMenuItem plainMenuItemWithIcon = new JMenuItem("Item With Icon");
		assignAccelerator(plainMenuItemWithIcon);
		Icon icon1 = new ImageIcon(MenuItemIconAlignDemo.class.getResource("dots1.png"));
		plainMenuItemWithIcon.setIcon(icon1);
		result.add(plainMenuItemWithIcon);
		
		result.addSeparator();
		
		JMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("Checkbox Item",true);
		checkBoxMenuItem.setIcon(TRANSPARENT_ICON);
		assignAccelerator(checkBoxMenuItem);
		result.add(checkBoxMenuItem);
		
		JMenuItem checkBoxMenuItemWithIcon = new JCheckBoxMenuItem("Checkbox Item With Icon",true);
		assignAccelerator(checkBoxMenuItemWithIcon);
		Icon icon2 = new ImageIcon(MenuItemIconAlignDemo.class.getResource("dots2.png"));
		checkBoxMenuItemWithIcon.setIcon(icon2);
		result.add(checkBoxMenuItemWithIcon);
		
		result.addSeparator();
		
		JMenuItem radioBoxMenuItem = new JRadioButtonMenuItemEx("Radio Item",true);
		radioBoxMenuItem.setIcon(TRANSPARENT_ICON);
		assignAccelerator(radioBoxMenuItem);
		result.add(radioBoxMenuItem);
		
		JMenuItem radioMenuItemWithIcon = new JRadioButtonMenuItemEx("Radio Item With Icon",true);
		assignAccelerator(radioMenuItemWithIcon);
		Icon icon3 = new ImageIcon(MenuItemIconAlignDemo.class.getResource("dots3.png"));
		radioMenuItemWithIcon.setIcon(icon3);
		result.add(radioMenuItemWithIcon);
		return result;
	}
	
	private static void assignAccelerator(final JMenuItem item) {
		if (item == null) {
			return;
		}
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK));
	}
	
	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
		JFrame f = new MenuItemIconAlignDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	} 
}