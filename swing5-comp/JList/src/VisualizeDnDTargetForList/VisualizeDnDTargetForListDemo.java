package VisualizeDnDTargetForList;

import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class VisualizeDnDTargetForListDemo extends JFrame {

	public VisualizeDnDTargetForListDemo() {
		super(VisualizeDnDTargetForListDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(1, 2));
		DefaultListModel dlm1 = new DefaultListModel();
		dlm1.addElement("111");
		dlm1.addElement("222");
		dlm1.addElement("333");
		dlm1.addElement("444");
		dlm1.addElement("555");
		JList sourceList = new JList(dlm1);
		sourceList.setDragEnabled(true);
		result.add(new JScrollPane(sourceList));
		DefaultListModel dlm2 = new DefaultListModel();
		dlm2.addElement("aa");
		dlm2.addElement("bb");
		dlm2.addElement("cc");

		JList targetList = new JList(dlm2);
		new DropTarget(targetList, new SampleDropListener());
		result.add(new JScrollPane(targetList));
		return result;
	}

	public static void main(String[] args) {
		JFrame f = new VisualizeDnDTargetForListDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private static class SampleDropListener extends ListDropListener {

		public void drop(DropTargetDropEvent dtde) {
			super.drop(dtde);
			JList list = (JList) dtde.getDropTargetContext().getComponent();
			DefaultListModel listModel = (DefaultListModel) list.getModel();
			try {
				String data = (String) dtde.getTransferable().getTransferData(
						DataFlavor.stringFlavor);
				if (before == null) {
					listModel.setElementAt(data, listIndex);
					list.setSelectedIndex(listIndex);
				} else if (before.equals(Boolean.TRUE)) {
					listModel.insertElementAt(data, listIndex);
					list.setSelectedIndex(listIndex);
				} else {
					if (listIndex < listModel.size()) {
						listModel.insertElementAt(data, listIndex + 1);
						list.setSelectedIndex(listIndex + 1);
					} else {
						listModel.addElement(data);
						list.setSelectedIndex(listModel.getSize() - 1);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}