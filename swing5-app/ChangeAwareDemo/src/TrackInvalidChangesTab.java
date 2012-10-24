import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import changeaware.ChangeAwareComboBox;
import changeaware.ChangeAwarePasswordField;
import changeaware.ChangeAwareSpinner2;
import changeaware.ChangeAwareTable2;
import changeaware.ChangeAwareTextField;
import changeaware.ChangeAwareSpinner2.DateFormatterEx;
import changeaware.ChangeAwareSpinner2.NumberFormatterEx;

/**
 * @author idanilov
 *
 */
public class TrackInvalidChangesTab extends JPanel {

	ChangeAwareTextField invalidTextField;
	ChangeAwarePasswordField invalidPasswordField;
	ChangeAwareSpinner2 invalidNumberSpinner;
	ChangeAwareSpinner2 invalidTimeSpinner;
	ChangeAwareComboBox invalidNonEditableCombo, invalidEditableCombo;

	ChangeAwareTable2 invalidTable;

	public TrackInvalidChangesTab() {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets.top = 5;
		add(new JLabel("Text field:"), gbc);
		gbc.gridy++;
		invalidTextField = createTextField();
		add(invalidTextField, gbc);

		gbc.gridy++;
		add(new JLabel("Password field:"), gbc);
		gbc.gridy++;
		invalidPasswordField = createPasswordField();
		add(invalidPasswordField, gbc);

		gbc.gridy++;
		add(new JLabel("Number spinner:"), gbc);
		gbc.gridy++;
		invalidNumberSpinner = createNumberSpinner();
		add(invalidNumberSpinner, gbc);

		gbc.gridy++;
		add(new JLabel("Date spinner (time):"), gbc);
		gbc.gridy++;
		invalidTimeSpinner = createTimeSpinner();
		add(invalidTimeSpinner, gbc);

		gbc.gridy++;
		add(new JLabel("Non editable combo (with full model change support):"), gbc);
		gbc.gridy++;
		invalidNonEditableCombo = createNonEditableCombo();
		add(invalidNonEditableCombo, gbc);

		gbc.gridy++;
		add(new JLabel("Editable combo (supports only item selection change):"), gbc);
		gbc.gridy++;
		invalidEditableCombo = createEditableCombo();
		add(invalidEditableCombo, gbc);

		gbc.gridy++;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(new JLabel("Table:"), gbc);
		gbc.gridy++;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(createTableComponent(), gbc);
	}

	private ChangeAwareTextField createTextField() {
		final ChangeAwareTextField result = new ChangeAwareTextField() {

			protected boolean checkValidity() {
				return getText().length() == 3;
			}
		};
		result.setColumns(10);
		result.setText("123");
		result.setToolTipText("Valid length of text is 3");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem setInvalidValueItem, setModelItem;

			{

				setInvalidValueItem = new JMenuItem("Set invalid value : \"new text\"");
				setInvalidValueItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.setText("new text");
					}

				});
				add(setInvalidValueItem);
				addSeparator();
				setModelItem = new JMenuItem("Set New Document");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
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

	private ChangeAwarePasswordField createPasswordField() {
		final ChangeAwarePasswordField result = new ChangeAwarePasswordField() {

			private static final String PASSWORD = "pwd";

			protected boolean checkValidity() {
				return new String(getPassword()).equals(PASSWORD);
			}
		};
		result.setColumns(10);
		result.setText("pwd");
		result.setToolTipText("Valid password is \"pwd\"");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem setInvalidValueItem, setModelItem;

			{

				setInvalidValueItem = new JMenuItem("Set invalid value : \"new text\"");
				setInvalidValueItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.setText("new text");
					}

				});
				add(setInvalidValueItem);
				addSeparator();
				setModelItem = new JMenuItem("Set New Document");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
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

	private ChangeAwareSpinner2 createNumberSpinner() {
		SpinnerNumberModel model = new SpinnerNumberModel(5, 1, 10, 1);
		final ChangeAwareSpinner2 result = new ChangeAwareSpinner2(model) {

			//overriden to check min/max.
			protected boolean checkValidity() {
				boolean r = false;
				JFormattedTextField ftf = getTextField();
				try {
					/*ftf.commitEdit();//XXX: not use commitEdit - it results to JFormattedTextField#setValue(...) and to restore format pattern.
					 boolean isValid = ftf.isEditValid();*/
					Object res = ftf.getFormatter().stringToValue(ftf.getText());
					SpinnerNumberModel m = (SpinnerNumberModel) getModel();
					if (res != null) {
						r = (m.getMinimum().compareTo(res) <= 0)
								&& (m.getMaximum().compareTo(res) >= 0);
					}
				} catch (ParseException pe) {
					r = false;
				}
				return r;
			}
		};
		prepareNumberChooser(result);
		//		XXX: make sure that spinner popup will be applied to text field.
		DefaultEditor editor = (DefaultEditor) result.getEditor();
		editor.setInheritsPopupMenu(true);
		JFormattedTextField tf = editor.getTextField();
		tf.setInheritsPopupMenu(true);

		//		InputMap im = tf.getInputMap(JComponent.WHEN_FOCUSED);
		//		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"none");
		//XXX : really strange behaviour. If I will set tooltip - the JFormattedTextField#CancelAction() 
		//is not invoked by ESC!!! Istead some "hide-tooltip" action is invoked!!! 
		tf.setToolTipText("Valid format: integers in range " + "[" + model.getMinimum() + ","
				+ model.getMaximum() + "]");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem setInvalidValueItem1, setInvalidValueItem2, setNewValueItem;

			{
				setInvalidValueItem1 = new JMenuItem("Set invalid value : xxx");
				setInvalidValueItem1.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.getTextField().setText("xxx");
					}

				});
				add(setInvalidValueItem1);

				setInvalidValueItem2 = new JMenuItem("Set invalid value : 12");
				setInvalidValueItem2.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.getTextField().setText("12");
					}

				});
				add(setInvalidValueItem2);

				setNewValueItem = new JMenuItem("Set new value : 9");
				setNewValueItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.getTextField().setText("9");
					}

				});
				add(setNewValueItem);
			}

		});
		return result;
	}

	private ChangeAwareSpinner2 createTimeSpinner() {
		final ChangeAwareSpinner2 result = new ChangeAwareSpinner2(new SpinnerDateModel());
		prepareTimeChooser(result);
		//		XXX: make sure that spinner popup will be applied to text field.
		DefaultEditor editor = (DefaultEditor) result.getEditor();
		editor.setInheritsPopupMenu(true);
		JFormattedTextField tf = editor.getTextField();
		tf.setInheritsPopupMenu(true);
		//		InputMap im = tf.getInputMap(JComponent.WHEN_FOCUSED);
		//		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"none");
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem setInvalidValueItem;

			{
				setInvalidValueItem = new JMenuItem("Set invalid value : xxx");
				setInvalidValueItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.getTextField().setText("xxx");
					}

				});
				add(setInvalidValueItem);
			}

		});
		return result;
	}

	private ChangeAwareComboBox createNonEditableCombo() {
		DefaultComboBoxModel dcm = new SampleComboBoxModelWithFullCopy(new String[] { "111", "222",
				"333", "444" });
		final ChangeAwareComboBox result = new ChangeAwareComboBox(dcm) {

			protected boolean checkValidity() {
				return (getSelectedItem() != null);
			}
		};
		result.setEditable(false);
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem unselectItem, addItem, setModelItem;

			{

				unselectItem = new JMenuItem("Clear selection");
				unselectItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.setSelectedIndex(-1);
					}

				});
				add(unselectItem);
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

					public void actionPerformed(ActionEvent e) {
						DefaultComboBoxModel newComboModel = new SampleComboBoxModelWithFullCopy(
								new String[] { "otherItem1", "otherItem2" });
						newComboModel.setSelectedItem(null);
						result.setModel(newComboModel);
					}

				});
				add(setModelItem);
			}

		});
		result.setToolTipText("Valid value is non-null selection");
		return result;
	}

	private ChangeAwareComboBox createEditableCombo() {
		final String item0 = "aaa";
		final String item1 = "bbb";
		final String item2 = "ccc";
		final String item3 = "ddd";
		DefaultComboBoxModel dcm = new SampleComboBoxModel(new String[] { item0, item1, item2,
				item3 });
		final ChangeAwareComboBox result = new ChangeAwareComboBox(dcm) {

			protected boolean checkValidity() {
				Object editedValue = getEditor().getItem();
				return (editedValue != null && (item1.equals(editedValue) || item2
						.equals(editedValue)));
			}
		};
		result.setSelectedItem(item1);
		result.setEditable(true);
		result.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem selectItem, addItem, setModelItem;

			{

				selectItem = new JMenuItem("Select invalid item 1");
				selectItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						result.setSelectedIndex(0);
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

					public void actionPerformed(ActionEvent e) {
						DefaultComboBoxModel newComboModel = new SampleComboBoxModel(new String[] {
								"otherAAA", "otherBBB" });
						result.setModel(newComboModel);
					}

				});
				add(setModelItem);
			}

		});
		result.setToolTipText("Valid values are '" + item1 + "' and '" + item2 + "'");
		return result;
	}

	private void prepareTimeChooser(final JSpinner spinner) {
		JFormattedTextField timeTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		DateFormatter tf = createTimeFormatter(false, true, false, false);
		DefaultFormatterFactory timeFactory = new DefaultFormatterFactory(tf, null, null, null);
		timeTextField.setFormatterFactory(timeFactory);
		//XXX : really strange behaviour. If I will set tooltip - the JFormattedTextField#CancelAction() 
		//is not invoked by ESC!!! Istead some "hide-tooltip" action is invoked!!! 
		timeTextField.setToolTipText("Valid format:"
				+ ((SimpleDateFormat) tf.getFormat()).toPattern());
		adjustFont(timeTextField);
	}

	private void adjustFont(final JFormattedTextField ftf) {
		JFormattedTextField defaultFTF = new JFormattedTextField();
		ftf.setFont(defaultFTF.getFont());
	}

	private DateFormatter createTimeFormatter(final boolean isLeninent,
			final boolean allowsInvalid, final boolean overwrite, final boolean commitOnValid) {
		DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
		timeFormat.setLenient(isLeninent);
		//DateFormatter result = new DateFormatter(timeFormat);
		DateFormatter result = new DateFormatterEx(timeFormat);
		result.setAllowsInvalid(allowsInvalid);
		result.setOverwriteMode(overwrite);
		result.setCommitsOnValidEdit(commitOnValid);
		return result;
	}

	private void prepareNumberChooser(final JSpinner spinner) {
		JFormattedTextField numberTextField = ((DefaultEditor) spinner.getEditor()).getTextField();
		NumberFormatter tf = createNumberFormatter(true, false, false);
		DefaultFormatterFactory timeFactory = new DefaultFormatterFactory(tf, null, null, null);
		numberTextField.setFormatterFactory(timeFactory);
		adjustFont(numberTextField);
	}

	private NumberFormatter createNumberFormatter(final boolean allowsInvalid,
			final boolean overwrite, final boolean commitOnValid) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		//NumberFormatter result = new NumberFormatter(numberFormat);
		NumberFormatter result = new NumberFormatterEx(numberFormat);
		result.setAllowsInvalid(allowsInvalid);
		result.setOverwriteMode(overwrite);
		result.setCommitsOnValidEdit(commitOnValid);
		result.setValueClass(Integer.class); //XXX : important!!!
		return result;
	}

	private JComponent createTableComponent() {
		JPanel result = new JPanel(new BorderLayout());
		String[] columnNames = { "col1", "col2" };
		String[][] data = { { "value1", "value2" }, { "value3", "value4" } };
		invalidTable = new ChangeAwareTable2(new SampleTableModel(data, columnNames)) {

			protected boolean checkValidity(int row, int col) {
				boolean rslt = true;
				Object value = getModel().getValueAt(row, col);
				if (value == null || value.toString().trim().length() == 0) {
					rslt = false;
				}
				return rslt;
			}

		};
		invalidTable.setPreferredScrollableViewportSize(new Dimension(200, 200));
		invalidTable.setToolTipText("Valid values are not empty strings");
		invalidTable.setComponentPopupMenu(new JPopupMenu() {

			private JMenuItem changeCellItem, changeCellItem2Invalid, setModelItem;

			{
				changeCellItem = new JMenuItem("Change cell [0,0]");
				changeCellItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultTableModel dtm = (DefaultTableModel) invalidTable.getModel();
						dtm.setValueAt("changedItem", 0, 0);
					}

				});
				changeCellItem2Invalid = new JMenuItem("Change cell [1,1] to invalid value: \" \"");
				changeCellItem2Invalid.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						DefaultTableModel dtm = (DefaultTableModel) invalidTable.getModel();
						dtm.setValueAt("", 1, 1);
					}

				});

				setModelItem = new JMenuItem("Set New Model");
				setModelItem.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ae) {
						String[] newColumnNames = { "newColumn1", "newColumn2" };
						String[][] newData = { { "object1", "object2" }, { "object3", "object4" } };
						invalidTable.setModel(new SampleTableModel(newData, newColumnNames));
					}

				});

				add(changeCellItem);
				add(changeCellItem2Invalid);
				addSeparator();
				add(setModelItem);
			}

		});
		JScrollPane sp = new JScrollPane(invalidTable);
		result.add(sp, BorderLayout.CENTER);
		return result;
	}

}
