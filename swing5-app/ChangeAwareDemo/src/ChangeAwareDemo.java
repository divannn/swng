import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import window.AbstractDialog;
import window.DialogConstant;
import window.MessageDialog;
import changeaware.ChangeAwareManager;
import changeaware.StateChangeEvent;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class ChangeAwareDemo extends AbstractDialog {

	private enum OPERATION {
		/**
		 * Not changed.
		 */
		NONE,
		/**
		 * Changed and valid.
		 */
		EDIT,
		/**
		 * Changed and invalid.
		 */
		INVALID
	}

	private OPERATION currentOperation;

	private JButton buttonOk;
	private JButton buttonApply;
	private JButton buttonRevert;

	private JTabbedPane tabPane;
	private TrackChangesTab tab1;
	private TrackInvalidChangesTab tab2;

	private ChangeAwareManager controlsManager;

	public ChangeAwareDemo() {
		super((JFrame) null, ChangeAwareDemo.class.getSimpleName());
		setModal(true);
		controlsManager = new ChangeAwareManager() {

			public void stateChanged(final StateChangeEvent sce) {
				boolean isAnythingChanged = isSomethingChanged();
				if (isAnythingChanged) {
					boolean isChangedToInvalid = isSomethingInvalid();
					if (isChangedToInvalid) {
						setInvalidState();
					} else {
						setEditState();
					}
				} else {
					setClearState();
				}
			}
		};
		create();
	}

	protected void handleDefaultClose() {
		clickOnCancel();
	}

	protected void clickOnCancel() {
		if (isDirty()) {
			if (isInvalid()) {
				int ret = MessageDialog.createYesNoDialog(ChangeAwareDemo.this, "Confirm",
						"There are invalid changes. Close anyway?", MessageDialog.ICON_TYPE.ERROR)
						.open();
				if (ret == DialogConstant.YES_ID) {
					super.clickOnCancel();
				}
			} else {
				int ret = MessageDialog.createYesNoCancelDialog(ChangeAwareDemo.this, "Confirm",
						"There are unsaved changes. Save?", MessageDialog.ICON_TYPE.QUESTION)
						.open();
				if (ret == DialogConstant.YES_ID) {
					//do save here.
					super.clickOnCancel();
				} else if (ret == DialogConstant.NO_ID) {
					super.clickOnCancel();
				}
			}
		} else {
			super.clickOnCancel();
		}
	}

	protected void initDialog() {
		setClearState();
		tab1.radioButton1.setSelected(true);
		controlsManager.markConrolsState();
	}

	protected JComponent createClientArea() {
		JPanel result = new JPanel(new BorderLayout(0, 5));
		tabPane = new JTabbedPane();
		tab1 = new TrackChangesTab();
		controlsManager.registerComponent(tab1.textField);
		controlsManager.registerComponent(tab1.checkBox);
		controlsManager.registerComponent(tab1.radioButton1);
		controlsManager.registerComponent(tab1.radioButton2);
		controlsManager.registerComponent(tab1.radioButton3);
		controlsManager.registerComponent(tab1.editableComboBox);
		controlsManager.registerComponent(tab1.nonEditableComboBox);
		controlsManager.registerComponent(tab1.nonEditableNumberSpinner);
		controlsManager.registerComponent(tab1.list);
		controlsManager.registerComponent(tab1.table);
		tabPane.addTab("Track Changes", tab1);

		tab2 = new TrackInvalidChangesTab();
		controlsManager.registerComponent(tab2.invalidTextField);
		controlsManager.registerComponent(tab2.invalidPasswordField);
		controlsManager.registerComponent(tab2.invalidNumberSpinner);
		controlsManager.registerComponent(tab2.invalidTimeSpinner);
		controlsManager.registerComponent(tab2.invalidNonEditableCombo);
		controlsManager.registerComponent(tab2.invalidEditableCombo);
		controlsManager.registerComponent(tab2.invalidTable);
		tabPane.addTab("Track Invalid Changes", tab2);
		result.add(tabPane, BorderLayout.CENTER);
		return result;
	}

	protected void createButtonsForButtonBar(JComponent buttonBar) {
		super.createButtonsForButtonBar(buttonBar);
		buttonOk = getButton(DialogConstant.OK_ID);
		buttonApply = createButton(buttonBar, DialogConstant.APPLY_ID, DialogConstant.APPLY_LABEL);
		buttonApply.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clickOnApply();
			}

		});
		buttonRevert = createButton(buttonBar, DialogConstant.REVERT_ID,
				DialogConstant.REVERT_LABEL);
		buttonRevert.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clickOnRevert();
			}

		});
	}

	private boolean isDirty() {
		return (currentOperation != OPERATION.NONE);
	}

	private boolean isClear() {
		return (currentOperation == OPERATION.NONE);
	}

	private boolean isEdit() {
		return (currentOperation == OPERATION.EDIT);
	}

	private boolean isInvalid() {
		return (currentOperation == OPERATION.INVALID);
	}

	private void setEditState() {
		if (isEdit()) {
			return;
		}
		if (!isDirty()) {
			changeTitle(true);
		}
		buttonOk.setEnabled(true);
		buttonApply.setEnabled(true);
		buttonRevert.setEnabled(true);
		currentOperation = OPERATION.EDIT;
	}

	private void setInvalidState() {
		if (isInvalid()) {
			return;
		}
		if (!isDirty()) {
			changeTitle(true);
		}
		buttonOk.setEnabled(false);
		buttonApply.setEnabled(false);
		buttonRevert.setEnabled(true);
		currentOperation = OPERATION.INVALID;
	}

	private void setClearState() {
		if (isClear()) {
			return;
		}
		currentOperation = OPERATION.NONE;
		buttonOk.setEnabled(true);
		buttonApply.setEnabled(false);
		buttonRevert.setEnabled(false);
		changeTitle(false);
	}

	private void changeTitle(final boolean isDirty) {
		String currentTitle = getTitle();
		if (isDirty) {
			setTitle(currentTitle + '*');
		} else {
			if (currentTitle.lastIndexOf('*') >= 0) {
				setTitle(currentTitle.substring(0, getTitle().length() - 1));
			}
		}
	}

	private void clickOnApply() {
		setClearState();
		controlsManager.clearConrolsState();
		controlsManager.markConrolsState();
	}

	private void clickOnRevert() {
		setClearState();
		controlsManager.revertConrolsState();
		controlsManager.markConrolsState();
	}

	public void dispose() {
		controlsManager.unregisterAll();
		super.dispose();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ChangeAwareDemo d = new ChangeAwareDemo();
		d.pack();
		d.setLocationRelativeTo(null);
		d.open();
		System.exit(0);
	}

}