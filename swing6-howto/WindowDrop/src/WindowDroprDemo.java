import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

/**
 * Top-Level drop abilities. Applicable for JFrame, JDialog, JWindow and JApplet.
 * Also shows some new JDK6 transfer API.
 * @author idanilov
 * @jdk 1.6
 */
public class WindowDroprDemo extends JFrame {

	private JDesktopPane desktop;
	
	public WindowDroprDemo() {
		super(WindowDroprDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
		setTransferHandler(new FileDropHandler());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		desktop = new JDesktopPane();
		result.add(desktop, BorderLayout.CENTER);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new WindowDroprDemo();
		f.pack();
		f.setMinimumSize(f.getPreferredSize());
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private class FileDropHandler extends TransferHandler {

		public boolean canImport(TransferSupport support) {
			// for the demo, we'll only support drops (not clipboard paste).
			if (!support.isDrop()) {
				return false;
			}

			//return false if the drop doesn't contain a list of files.
			if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				return false;
			}

			boolean copySupported = (COPY & support.getSourceDropActions()) == COPY;

			//if COPY is supported, choose COPY and accept the transfer.
			//needed to provide COPY cursor (default is MOVE).
			if (copySupported) {
				support.setDropAction(COPY);
				return true;
			}

			/* COPY isn't supported, so reject the transfer.
			 * Note: If you want to accept the transfer with the default
			 *       action anyway, you could instead return true.
			 */
			return false;
		}

		public boolean importData(TransferSupport support) {
			if (!canImport(support)) {
				return false;
			}
			Transferable t = support.getTransferable();
			try {
				Object data = t.getTransferData(DataFlavor.javaFileListFlavor);

				//data of type javaFileListFlavor is a list of files.
				List fileList = (List) data;

				for (Object next : fileList) {
					File nextFile = (File)next;
					JInternalFrame f = new JInternalFrame(nextFile.getName());
					f.setVisible(true);
					f.setSize(100,100);
					f.setResizable(true);
					f.setClosable(true);
					desktop.add(f);
				}
			} catch (UnsupportedFlavorException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
			return true;
		}
	}

}
