import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clipboard.ClipboardEvent;
import clipboard.ClipboardListener;

/**
 * Way to implement defered cut/paste.
 * Demo using JList. Cutted object changes his icon and font. 
 * For simplicity - result of paste operation is performed 
 * to the end of the JList. Once data pasted from clipboard 
 * - clipboard is cleared and further paste operation are prohibited. 
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class DeferedCutPasteDemo extends JFrame {

	public DeferedCutPasteDemo() {
		super(DeferedCutPasteDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(new JScrollPane(createList(1, 10)), BorderLayout.CENTER);
		return result;
	}

	private JList createList(final int from, final int to) {
		DefaultListModel dlm = new DefaultListModel();
		for (int i = from; i <= to; i++) {
			dlm.addElement("Item " + i);
		}
		JList result = new JList(dlm);
		TransferHandler listTransferHandler = new ListTransferHandler(
				new ListClipboardOwner(result));
		result.setTransferHandler(listTransferHandler);
		result.setCellRenderer(new DimmedCellRenderer());
		result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		result.setComponentPopupMenu(createPopupMenu(result));
		return result;
	}

	private JPopupMenu createPopupMenu(final JList list) {
		JPopupMenu result = new JPopupMenu();
		final Action cutAction = new CutAction(list, TransferHandler.getCutAction());
		cutAction.putValue(Action.NAME, "Cut");
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
		cutAction.setEnabled(false);
		result.add(cutAction);

		final Action pasteAction = new PasteAction(list, TransferHandler.getPasteAction());
		pasteAction.putValue(Action.NAME, "Paste");
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
		pasteAction.setEnabled(false);
		result.add(pasteAction);

		//trick: disable copy action for simplicity
		//- in order not to highligh copied list item on Ctrl-C
		//(item is put to clipboard anyway).
		//"copy" action defined in parent map.
		list.getActionMap().getParent().put("copy", null);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new DeferedCutPasteDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	/**
	 * Redirector action. Forwards to default TransferHandler actions.
	 * @author idanilov
	 *
	 */
	private static class AbstractTransferAction extends AbstractAction {

		protected Action delegate;
		protected JComponent source;

		public AbstractTransferAction(final JComponent source, final Action delegate) {
			this.source = source;
			this.delegate = delegate;
		}

		public void actionPerformed(ActionEvent ae) {
			delegate.actionPerformed(new ActionEvent(source, ActionEvent.ACTION_PERFORMED, null));
		}

	}

	private static class CutAction extends AbstractTransferAction
			implements ListSelectionListener {

		private CutAction(final JComponent source, final Action delegate) {
			super(source, delegate);
			((JList) source).getSelectionModel().addListSelectionListener(this);
		}

		public void valueChanged(ListSelectionEvent lse) {
			if (!lse.getValueIsAdjusting()) {
				update();
			}
		}

		public void setEnabled(boolean newValue) {
			super.setEnabled(newValue);
			delegate.setEnabled(newValue);
		}

		private void update() {
			boolean canCut = canCut();
			setEnabled(canCut);
		}

		private boolean canCut() {
			return !((JList) source).getSelectionModel().isSelectionEmpty();
		}
	}

	private static class PasteAction extends AbstractTransferAction
			implements ClipboardListener, FlavorListener {

		private PasteAction(final JComponent source, final Action delegate) {
			super(source, delegate);
			Clipboard cb = source.getToolkit().getSystemClipboard();
			cb.addFlavorListener(this);
			ListTransferHandler lth = (ListTransferHandler) source.getTransferHandler();
			lth.addClipboardListener(this);
		}

		public void contentChanged(final ClipboardEvent ce) {
			//System.err.println("content change");
			update();
		}

		public void flavorsChanged(FlavorEvent e) {
			//System.err.println("flavor change ");
			update();
		}

		public void setEnabled(boolean newValue) {
			super.setEnabled(newValue);
			delegate.setEnabled(newValue);
		}

		private void update() {
			boolean canPaste = canPaste();
			setEnabled(canPaste);
		}

		private boolean canPaste() {
			boolean result = false;
			Clipboard cb = source.getToolkit().getSystemClipboard();
			try {
				Transferable t = cb.getContents(null);
				if (t != null && t.isDataFlavorSupported(ItemTransferable.ITEM_FLAVOR)) {
					Object s = t.getTransferData(ItemTransferable.ITEM_FLAVOR);
					result = ListTransferHandler.canImport(s);
				}
			} catch (UnsupportedFlavorException ufe) {
				ufe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return result;
		}

	}

	/**
	 * Paint dimmed icon for item which has been cutted but not pasted yet.
	 * @author idanilov
	 * 
	 */
	public static class DimmedCellRenderer extends DefaultListCellRenderer {

		private static ImageIcon icon = new ImageIcon(DimmedCellRenderer.class
				.getResource("item.png"));
		private static Icon ghostIcon = new ImageIcon(ImageUtil.createGhostImage(icon.getImage()));
		private static Font font = new JLabel().getFont();
		private static Font italic = font.deriveFont(Font.ITALIC);

		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			ListClipboardOwner owner = (ListClipboardOwner) list
					.getClientProperty(ListClipboardOwner.CLIP_BOARD_OWNER);
			boolean isCutted = owner != null && owner.getIndex() == index;
			setIcon(isCutted ? ghostIcon : icon);
			setFont(isCutted ? italic : font);
			return this;
		}
	}

}