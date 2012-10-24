package ModalProgress;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author santosh
 * @author idanilov
 * @jdk 1.5
 */
public class ModalProgressDemo extends JFrame {

	private Action heavyAction = new AbstractAction("Database Query") {

		public void actionPerformed(ActionEvent e) {
			setEnabled(false);
			new Thread(heavyRunnable).start();
		}
	};

	private Runnable heavyRunnable = new Runnable() {

		public void run() {
			ProgressMonitorModel monitorModel = ProgressUtil.createModalProgressMonitor(
					ModalProgressDemo.this, 100, false, 1000);
			monitorModel.start("Fetching 1 of 10 records from database...");
			try {
				for (int i = 0; i < 10; i++) {
					fetchRecord(i);
					monitorModel.setCurrent("Fetching " + (i + 1) + " of 10 records from database",
							(i + 1) * 10);
				}
			} finally {
				monitorModel.done();
			}
			heavyAction.setEnabled(true);
		}

		private void fetchRecord(int index) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public ModalProgressDemo() {
		super(ModalProgressDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new FlowLayout());
		result.add(new JButton(heavyAction));
		return result;
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModalProgressDemo f = new ModalProgressDemo();
		f.setSize(300, 200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
