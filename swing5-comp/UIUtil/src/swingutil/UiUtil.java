package swingutil;

import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Stack;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public abstract class UiUtil {

	private static final int TAB_SIZE = 4;

	private static PropertyChangeListener focusTracker = new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {
			Object newVal = evt.getNewValue();
			String n = newVal != null ? newVal.getClass().toString() : "null";
			Object oldVal = evt.getOldValue();
			String o = oldVal != null ? oldVal.getClass().toString() : "null";
			System.err.println(">>> focus owner: " + o + " -> " + n);
		}

	};

	/**
	 * Prints <code>comp</code>'s up hierarchy starting from
	 * <code>comp</code>. Root container is printed first. <br>
	 * <code>comp</code> is marked as "|||".
	 * 
	 * @param comp
	 */
	public static int printSupertypeHierarchy(final Component comp,
			final boolean printSelf) {
		if (comp == null) {
			throw new IllegalArgumentException("Specify non-null component.");
		}
		// collect.
		Stack<Component> stack = new Stack<Component>();
		stack.push(comp);
		Container parent = comp.getParent();
		while (parent != null) {
			stack.push(parent);
			parent = parent.getParent();
		}
		// print.
		int indent = 0;
		Component next = null;
		do {
			next = stack.pop();
			if (next == comp) {// start comp.
				if (printSelf) {
					printSelfComponent(comp,indent);
					indent += TAB_SIZE;
				}
			} else {
				printComponent(next,indent);
				indent += TAB_SIZE;
			}
		} while (!stack.isEmpty());
		return indent;
	}

	/**
	 * Prints <code>comp</code>'s down hierarchy starting from
	 * <code>comp</code>. Deepest component is printed last. <br>
	 * <code>comp</code> is marked as "|||".
	 * 
	 * @param comp
	 */
	public static void printSubtypeHierarchy(final Component comp,
			final boolean printSelf) {
		printSubtypeHierarchy(comp, comp, 0, printSelf);
	}

	/**
	 * <code>c</code> needed for checking passed comp - because of recursion.
	 * <code>c</code> should be the same as <code>comp</code>.
	 */
	private static void printSubtypeHierarchy(final Component comp,
			final Component c, int indent, final boolean printSelf) {
		if (comp == null) {
			throw new IllegalArgumentException("Specify non-null component.");
		}
		if (comp == c) {
			if (printSelf) {
				printSelfComponent(comp,indent);
				indent += TAB_SIZE;
			}
		} else {
			printComponent(comp,indent);
			indent += TAB_SIZE;
		}
		if (comp instanceof Container) {
			Container cont = (Container) comp;
			for (int i = 0; i < cont.getComponentCount(); i++) {
				Component next = cont.getComponent(i);
				printSubtypeHierarchy(next, c, indent, printSelf);
			}
		}
	}

	/**
	 * Prints <code>comp</code>'s full hierarchy. Deepest component is
	 * printed last. <br>
	 * <code>comp</code> is marked as "|||".
	 * 
	 * @param comp
	 */
	public static void printHierarchy(final Component comp) {
		if (comp == null) {
			throw new IllegalArgumentException("Specify non-null component.");
		}
		int indentation = printSupertypeHierarchy(comp, false);
		printSubtypeHierarchy(comp, comp, indentation, true);
	}

	private static void printSelfComponent(final Component comp,int indent) {
		printIndentation(indent);
		System.err.println("||| comp:" + comp);
		indent += TAB_SIZE;
	}

	private static void printComponent(final Component comp,int indent) {
		printIndentation(indent);
		System.err.println(">>> " + comp);
		indent += TAB_SIZE;
	}	
	
	private static void printIndentation(final int indent) {
		for (int i = indent; i > 0; --i) {
			System.err.print(" ");
		}
	}

	/**
	 * @param comp
	 *            target component
	 * @param when
	 *            focus condition: <br>
	 *            WHEN_IN_FOCUSED_WINDOW, WHEN_FOCUSED,
	 *            WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
	 * @param showAll
	 *            include parent maps
	 */
	public static void printInputMap(final JComponent comp, final int when,
			final boolean showAll) {
		if (comp == null) {
			throw new IllegalArgumentException("Specify non-null component.");
		}
		System.out.println(">>> Input map for: " + when);
		InputMap im = comp.getInputMap(when);
		if (im != null) {
			KeyStroke[] targetKeys = showAll ? im.allKeys() : im.keys();
			if (targetKeys != null) {
				for (KeyStroke nextStroke : targetKeys) {
					System.out.println("   >>> key stroke: " + nextStroke
							+ " action key: " + im.get(nextStroke));
				}
			} else {
				System.out.println("   no key strokes");
			}
		} else {
			System.out.println("   no key strokes");
		}
	}

	/**
	 * @param comp
	 *            target component
	 * @param when
	 *            focus condition
	 * @param showAll
	 *            include parent maps
	 */
	public static void printActionMap(final JComponent comp,
			final boolean showAll) {
		if (comp == null) {
			throw new IllegalArgumentException("Specify non-null component.");
		}
		ActionMap am = comp.getActionMap();
		if (am != null) {
			Object[] targetKeys = showAll ? am.allKeys() : am.keys();
			if (targetKeys != null) {
				for (Object nextKey : am.allKeys()) {
					System.out.println(">>> action key: " + nextKey
							+ " action: " + am.get(nextKey).getClass());
				}
			} else {
				System.out.println("no actions");
			}
		} else {
			System.out.println("no actions");
		}
	}

	/**
	 * Prints UI defaults for particular JComponent under current L&F. <br>
	 * Usage: <code>printDefaults(new JTable());</code>
	 * 
	 * @param comp
	 *            JComponent or null to get all UI default entries.
	 */
	public static void printDefaults(final JComponent comp) {
		String prefix = null;
		if (comp != null) {
			prefix = comp.getClass().getSimpleName().substring(1);// cut first
																	// 'J'.
		}
		UIDefaults defaults = UIManager.getDefaults();
		Enumeration e = defaults.keys();
		ArrayList<String> entriesList = new ArrayList<String>();
		while (e.hasMoreElements()) {
			Object nextKey = e.nextElement();
			String nextEntry = nextKey + " = " + defaults.get(nextKey);
			if (prefix == null) {// get all keys.
				entriesList.add(nextEntry);
			} else {
				if (nextKey.toString().startsWith(prefix)
						&& ".".equals(nextKey.toString().substring(
								prefix.length(), prefix.length() + 1))) {
					entriesList.add(nextEntry);
				}
			}
		}
		Collections.sort(entriesList);
		System.out.println(entriesList.size() + " entries found.");
		for (String nextEntry : entriesList) {
			System.out.println(nextEntry);
		}
		System.out
				.println("-----------------------------------------------------------");
	}

	public static void startTrackingFocusOwnerChange() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addPropertyChangeListener("permanentFocusOwner", focusTracker);
	}

	public static void stopTrackingFocusOwnerChange() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.removePropertyChangeListener("permanentFocusOwner",
						focusTracker);
	}

	/*
	 * For quick test, public static void main(String[] args) { try {
	 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
	 * catch (Exception e) { e.printStackTrace(); } printInputMap(new
	 * JFormattedTextField(),JComponent.WHEN_FOCUSED,true); }
	 */

}