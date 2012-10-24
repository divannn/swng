import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;

import action.GloballyContextSensitiveAction;
import action.IGlobalActionProvider;

/**
 * Shows notion of global context action. Any component can provide action 
 * with common meaning (f.e. "selectAll"). Global action (used by menu and toolbar) 
 * listen to focused comonent change and update itself enabled state accordingly 
 * specific action of current focused component.
 * <br>
 * Usage of "Select All" and "Clear All" for various component are shown.
 * The idea is to change default behaviour in way that this action should 
 * be disabled if action is not relevant. F.e. "Select All" is disbled when 
 * all items already selected. Default build-in "SelectAll" action 
 * (for JList,JTree,JTable)is always enabled.
 * <br><strong>Note:</strong><br>
 * For JList,JTable and JTree default build in actions not used becuase they are extend UIAction.
 * This class is proprietary Swing class and it doesn't fire events about actions changes 
 * (f.e. enablement state).  
 * @author idanilov
 * @jdk 1.5
 */
public class GlobalActionsDemo extends JFrame {

	private Action selectAllGlobalAction;
	private Action clearAllGlobalAction;

	public GlobalActionsDemo() {
		super(GlobalActionsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectAllGlobalAction = new GloballyContextSensitiveAction(
				IGlobalActionProvider.SELECT_ALL_ACTION_NAME, "Select All");
		clearAllGlobalAction = new GloballyContextSensitiveAction(
				IGlobalActionProvider.CLEAR_ALL_ACTION_NAME, "Clear All");
		setJMenuBar(createMenuBar());
		setContentPane(createContents());
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createToolBar(), BorderLayout.NORTH);
		result.add(createControls(), BorderLayout.CENTER);
		return result;
	}

	private JComponent createControls() {
		JPanel result = new JPanel(new BorderLayout());

		JList list = new ListWithGlobalActions(new String[] { "item1", "item2", "item3" }) {

			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(100, 100);
			}
		};
		result.add(new JScrollPane(list), BorderLayout.WEST);

		JTree tree = new TreeWithGlobalActions() {

			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(100, 100);
			}
		};
		result.add(new JScrollPane(tree), BorderLayout.EAST);

		JTable table = new TableWithGlobalActions(5, 3);
		table.setPreferredScrollableViewportSize(new Dimension(200, 100));
		result.add(new JScrollPane(table), BorderLayout.NORTH);

		JTextArea textArea = new TextAreaWithGlobalActions("Text\n\ttext\n\t\ttext");
		result.add(new JScrollPane(textArea), BorderLayout.SOUTH);

		//controls that don't have global actions.
		result.add(createControlWithoutGlobalActions(), BorderLayout.CENTER);

		return result;
	}

	private JToolBar createToolBar() {
		JToolBar result = new JToolBar();
		JButton b1 = result.add(selectAllGlobalAction);
		b1.setFocusable(false); //very important!!!
		//otherwise Swing will try to transfer focus to this button
		//spoiling global context action mechanics.

		JButton b2 = result.add(clearAllGlobalAction);
		b2.setFocusable(false); //very importanat!!!
		return result;
	}

	private JMenuBar createMenuBar() {
		JMenuBar result = new JMenuBar();
		JMenu menu = new JMenu("Action");
		JMenuItem selectAllItem = new JMenuItem(selectAllGlobalAction);
		menu.add(selectAllItem);
		JMenuItem clearAllItem = new JMenuItem(clearAllGlobalAction);
		menu.add(clearAllItem);
		result.add(menu);
		return result;
	}

	private JComponent createControlWithoutGlobalActions() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
		JLabel l = new JLabel("Control without global actions:");
		result.add(l);
		JComboBox cb = new JComboBox(new String[] { "item1", "item2" });
		cb.setMaximumSize(cb.getPreferredSize());
		result.add(cb);
		return result;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new GlobalActionsDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
