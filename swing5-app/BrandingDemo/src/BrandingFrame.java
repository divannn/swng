import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import window.AbstractFrame;

/**
 * @author idanilov
 *
 */
public class BrandingFrame extends AbstractFrame {

	private static Image appImage = Toolkit.getDefaultToolkit().createImage(BrandingFrame.class.getResource("window16.gif"));
	
	private JInternalFrame f1;
	
	public BrandingFrame() {
		super("BrandingDemo");
		setIconImage(appImage);
		setJMenuBar(createMenuBar());
		create();
		//just to select one frame.
		addWindowListener(new WindowAdapter() {

			public void windowActivated(WindowEvent e) {
				try {
					f1.setSelected(true);
				} catch (PropertyVetoException e1) {
				}
			}
			
		});
	}

	protected void create() {
		super.create();
		getContentPane().validate();
	}
	
	protected JComponent createContents() {
		CustomDesktopPane dp = new CustomDesktopPane();
		dp.setPreferredSize(new Dimension(800,400));//just to make scroll pane work ;).
		f1 = createInternalFrame("frame1");
		dp.add(f1);
		JInternalFrame f2 = createInternalFrame("frame2");
		f2.setLocation(100,100);
		dp.add(f2);
		JInternalFrame f3 = createInternalFrame("frame3");
		f3.setLocation(200,200);
		dp.add(f3);

		JScrollPane scrollPane = new JScrollPane(dp);
		JPanel result = new JPanel(new BorderLayout());
		result.add(scrollPane,BorderLayout.CENTER);
		return result;
	}
	
	protected JInternalFrame createInternalFrame(final String title) {
	    JInternalFrame frame = new JInternalFrame(title);
	    frame.setBounds(0,0,300,200);
	    frame.setClosable(true);
	    frame.setResizable(true);
	    frame.setIconifiable(true);
	    frame.setFrameIcon(new ImageIcon(appImage));
	    frame.setVisible(true);//internal frame is invisible by default.
	    return frame;
	}
	
	protected JComponent createClientArea() {
		return null;
	}	
	
	private JMenuBar createMenuBar() {
		JMenuBar result = new JMenuBar();
		JMenu aboutMenu = new JMenu("About");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic('a');
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		aboutItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AboutDialog ad = new AboutDialog(BrandingFrame.this);
				ad.pack();
				ad.setLocationRelativeTo(null);
				ad.open();
			}
			
		});
		aboutMenu.add(aboutItem);
		result.add(aboutMenu);
		return result;
	}
	
}
