import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import changeaware.ChangeAwareCheckBox;
import changeaware.ChangeAwareComboBox;
import changeaware.ChangeAwareList;
import changeaware.ChangeAwareRadioButton;
import changeaware.ChangeAwareSpinner;
import changeaware.ChangeAwareTable;
import changeaware.ChangeAwareTextField;

/**
 * @author idanilov
 *
 */
public class TrackChangesTab extends JPanel {

	ChangeAwareTextField textField;
	ChangeAwareComboBox nonEditableComboBox, editableComboBox;
	ChangeAwareSpinner nonEditableNumberSpinner;
	ChangeAwareCheckBox checkBox;
	ChangeAwareRadioButton radioButton1, radioButton2, radioButton3;
	ChangeAwareList list;
	ChangeAwareTable table;

	public TrackChangesTab() {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets.top = 5;
		gbc.insets.left = 5;
		add(new JLabel("Text field:"), gbc);
		gbc.gridx++;
		textField = createTextField();
		add(textField, gbc);

		gbc.gridx++;
		add(new JLabel("Number spinner:"), gbc);
		gbc.gridx++;
		gbc.fill = GridBagConstraints.BOTH;
		nonEditableNumberSpinner = createNumberSpinner();
		add(nonEditableNumberSpinner, gbc);

		gbc.gridx++;
		checkBox = new ChangeAwareCheckBox("checkbox");
		add(checkBox, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		add(new JLabel("Non editable combo (with full model change support):"), gbc);
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		nonEditableComboBox = createNonEditableCombo();
		add(nonEditableComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		add(new JLabel("Editable combo (supports only item selection change):"), gbc);
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		editableComboBox = createEditableCombo();
		add(editableComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(createRadioButtons(), gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(new JLabel("List:"), gbc);

		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(createListComponent(), gbc);

		gbc.gridy++;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(new JLabel("Table:"), gbc);
		gbc.gridy++;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(createTableComponent(), gbc);

		ButtonGroup bg = new ButtonGroup();
		bg.add(radioButton1);
		bg.add(radioButton2);
		bg.add(radioButton3);
	}

	private JPanel createRadioButtons() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.LINE_AXIS));
		radioButton1 = new ChangeAwareRadioButton("radio1");
		result.add(radioButton1);
		radioButton2 = new ChangeAwareRadioButton("radio2");
		result.add(radioButton2);
		radioButton3 = new ChangeAwareRadioButton("radio3");
		result.add(radioButton3);
		result.setBorder(BorderFactory.createTitledBorder("Group"));
		return result;
	}

	private ChangeAwareTextField createTextField() {
		final ChangeAwareTextField result = new ChangeAwareTextField();
		result.setColumns(10);
		result.setText("type here");
		result.setToolTipText("Press right click for options");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem setNewValueItem, setModelItem;

			{

				setNewValueItem = new JMenuItem("Set new value : \"new text\"");
				setNewValueItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						result.setText("new text");
					}

				});
				add(setNewValueItem);
				addSeparator();
				setModelItem = new JMenuItem("Set New Document");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						PlainDocument d = new PlainDocument();
						try {
							d.insertString(0, "new document", null);
						} catch (BadLocationException ble) {
							ble.printStackTrace();
						}
						result.setDocument(d);
					}

				});
				add(setModelItem);
			}

		});
		return result;
	}

	private ChangeAwareComboBox createNonEditableCombo() {
		DefaultComboBoxModel fullChangeAwareModel = new SampleComboBoxModelWithFullCopy(
				new String[] { "aaa", "bbb", "ccc", "ddd" });
		final ChangeAwareComboBox result = new ChangeAwareComboBox(fullChangeAwareModel);
		result.setEditable(false);
		result.setToolTipText("Press right click for options");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem selectItem, addItem, setModelItem;

			{
				selectItem = new JMenuItem("Select Item 2");
				selectItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						result.setSelectedIndex(1);
					}

				});
				add(selectItem);
				addSeparator();
				addItem = new JMenuItem("Add item \"xxx\" to the end of the list");
				addItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultComboBoxModel model = (DefaultComboBoxModel) result.getModel();
						model.addElement("xxx");
					}

				});
				add(addItem);
				addSeparator();
				setModelItem = new JMenuItem("Set New Model");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultComboBoxModel newComboModel = new SampleComboBoxModelWithFullCopy(
								new String[] { "otherItem1", "otherItem2" });
						result.setModel(newComboModel);
					}

				});
				add(setModelItem);
			}

		});
		return result;
	}

	private ChangeAwareComboBox createEditableCombo() {
		DefaultComboBoxModel selectedItemChangeAwareModel = new SampleComboBoxModel(new String[] {
				"111", "222", "333", "444" });
		final ChangeAwareComboBox result = new ChangeAwareComboBox(selectedItemChangeAwareModel);
		result.setEditable(true);
		result.setToolTipText("Press right click for options");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem selectItem, addItem, setModelItem;

			{
				selectItem = new JMenuItem("Select Item 2");
				selectItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						result.setSelectedIndex(1);
					}

				});
				add(selectItem);
				addSeparator();
				addItem = new JMenuItem("Add item \"xxx\" to the end of the list");
				addItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultComboBoxModel model = (DefaultComboBoxModel) result.getModel();
						model.addElement("xxx");
					}

				});
				add(addItem);
				addSeparator();
				setModelItem = new JMenuItem("Set New Model");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultComboBoxModel newComboModel = new SampleComboBoxModel(new String[] {
								"otherItem1", "otherItem2" });
						result.setModel(newComboModel);
					}

				});
				add(setModelItem);
			}

		});
		return result;
	}

	private ChangeAwareSpinner createNumberSpinner() {
		final ChangeAwareSpinner result = new ChangeAwareSpinner(
				new SpinnerNumberModel(3, 1, 10, 1));
		prepareNumberChooser(result);
		//XXX: make sure that spinner popup will be applied to text field.
		DefaultEditor editor = (DefaultEditor) result.getEditor();
		editor.setInheritsPopupMenu(true);
		JFormattedTextField tf = editor.getTextField();
		tf.setInheritsPopupMenu(true);
		result.setToolTipText("Press right click for options");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem setNewValueItem;

			{
				setNewValueItem = new JMenuItem("Set new value : 6");
				setNewValueItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						result.getModel().setValue(new Integer(6));
					}

				});
				add(setNewValueItem);
			}

		});
		//XXX:: update popup property - because editor is recreated after JSpinner#setModel(...).
		PropertyChangeListener changeListener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent pce) {
				if ("editor".equals(pce.getPropertyName())) {
					DefaultEditor newEditor = (DefaultEditor) pce.getNewValue();
					newEditor.setInheritsPopupMenu(true);
					JFormattedTextField newTf = newEditor.getTextField();
					newTf.setInheritsPopupMenu(true);
				}
			}
		};
		result.addPropertyChangeListener(changeListener);
		return result;
	}

	private void prepareNumberChooser(final JSpinner spinner) {
		JFormattedTextField numberTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DefaultFormatterFactory dff = (DefaultFormatterFactory) numberTextField
				.getFormatterFactory();
		NumberFormatter formatter = (NumberFormatter) dff.getDefaultFormatter();
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(true);
		formatter.setCommitsOnValidEdit(true);
	}

	private JComponent createListComponent() {
		JPanel result = new JPanel(new BorderLayout());
		list = new ChangeAwareList();
		ListModel lm = new SampleListModel(new String[] { "item1", "item2", "item3" });
		list.setModel(lm);
		list.setToolTipText("Press right click for options");
		list.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem addItem, removeItem, changeItem, setModelItem;

			{
				addItem = new JMenuItem("Insert");
				addItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						int selectedIndex = list.getSelectedIndex();
						DefaultListModel dlm = (DefaultListModel) list.getModel();
						if (selectedIndex == -1) {
							selectedIndex = 0;
						}
						dlm.insertElementAt("newItem", selectedIndex);
					}

				});
				removeItem = new JMenuItem("Remove");
				removeItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						int[] selectedIndices = list.getSelectedIndices();
						if (selectedIndices.length > 0) {
							Arrays.sort(selectedIndices);
							DefaultListModel dlm = (DefaultListModel) list.getModel();
							for (int i = selectedIndices.length - 1; i >= 0; i--) {
								dlm.removeElementAt(selectedIndices[i]);
							}
						}
					}

				});
				changeItem = new JMenuItem("Change item 0");
				changeItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultListModel dlm = (DefaultListModel) list.getModel();
						dlm.setElementAt("changedItem", 0);
					}

				});
				setModelItem = new JMenuItem("Set New Model");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						SampleListModel newListModel = new SampleListModel(new String[] {
								"otherItem1", "otherItem2", "otherItem3" });
						list.setModel(newListModel);
					}

				});

				add(addItem);
				add(removeItem);
				add(changeItem);
				addSeparator();
				add(setModelItem);
			}

		});
		JScrollPane sp = new JScrollPane(list);
		result.add(sp, BorderLayout.CENTER);
		return result;
	}

	private JComponent createTableComponent() {
		JPanel result = new JPanel(new BorderLayout());
		String[] columnNames = { "col1", "col2" };
		String[][] data = { { "value1", "value2" }, { "value3", "value4" } };
		table = new ChangeAwareTable(new SampleTableModel(data, columnNames));
		table.setPreferredScrollableViewportSize(new Dimension(200, 200));
		table.setToolTipText("Press right click for options");
		table.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem addRowItem, removeRowItem, changeCellItem, setModelItem;

			{
				addRowItem = new JMenuItem("Insert");
				addRowItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						int selectedIndex = table.getSelectedRow();
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();
						if (selectedIndex == -1) {
							selectedIndex = 0;
						}
						dtm.insertRow(selectedIndex, new String[] { "newValue1", "newValue2" });
					}

				});
				removeRowItem = new JMenuItem("Remove");
				removeRowItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						int[] selectedIndices = table.getSelectedRows();
						if (selectedIndices.length > 0) {
							Arrays.sort(selectedIndices);
							DefaultTableModel dtm = (DefaultTableModel) table.getModel();
							for (int i = selectedIndices.length - 1; i >= 0; i--) {
								dtm.removeRow(selectedIndices[i]);
							}
						}
					}

				});
				changeCellItem = new JMenuItem("Change cell [0,0]");
				changeCellItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();
						dtm.setValueAt("changedItem", 0, 0);
					}

				});
				setModelItem = new JMenuItem("Set New Model");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						String[] newColumnNames = { "newColumn1", "newColumn2" };
						String[][] newData = { { "object1", "object2" }, { "object3", "object4" } };
						table.setModel(new SampleTableModel(newData, newColumnNames));
					}

				});

				add(addRowItem);
				add(removeRowItem);
				addSeparator();
				add(changeCellItem);
				addSeparator();
				add(setModelItem);
			}

		});
		JScrollPane sp = new JScrollPane(table);
		result.add(sp, BorderLayout.CENTER);
		return result;
	}
}
