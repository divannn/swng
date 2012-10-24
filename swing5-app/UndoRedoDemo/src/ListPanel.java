import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author idanilov
 *
 */
public class ListPanel extends JPanel
		implements ListSelectionListener {

	protected DefaultListModel listModel;
	protected JList list;

	protected Action addAction = new AbstractAction("Add") {

		public void actionPerformed(ActionEvent e) {
			String input = JOptionPane.showInputDialog(ListPanel.this, "Items to be added:");
			if (input != null) {
				StringTokenizer stok = new StringTokenizer(input, ", ");
				while (stok.hasMoreTokens()) {
					listModel.addElement(stok.nextToken());
				}
			}
		}
	};

	protected Action deleteAction = new AbstractAction("Delete") {

		public void actionPerformed(ActionEvent e) {
			int[] rows = list.getSelectedIndices();
			for (int i = 0; i < rows.length; i++) {
				listModel.removeElementAt(rows[i] - i);
			}
		}
	};

	public ListPanel() {
		super(new BorderLayout(5, 0));
		listModel = new UndoableListModel();
		list = new JList(listModel);

		list.setVisibleRowCount(15);
		add(new JScrollPane(this.list));

		JPanel buttons = new JPanel(new GridLayout(0, 1));
		buttons.add(new JButton(addAction));
		buttons.add(new JButton(deleteAction));

		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.add(buttons, BorderLayout.NORTH);
		add(buttonsPanel, BorderLayout.EAST);

		list.addListSelectionListener(this);
		valueChanged(null);
	}

	public DefaultListModel getListModel() {
		return listModel;
	}

	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		deleteAction.setEnabled(list.getSelectedIndices().length > 0);
	}

}
