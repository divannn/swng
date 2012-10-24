import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import action.IContextAction;

/**
 * @author idanilov
 * 
 */
public abstract class TextSelectionAction extends AbstractAction
		implements ChangeListener, IContextAction {

	protected JTextComponent text;
	protected String selection;

	public TextSelectionAction(final JTextComponent t) {
		text = t;
		text.getCaret().addChangeListener(this);
	}

	public void stateChanged(ChangeEvent e) {
		selection = text.getSelectedText();
		update();
	}

	public void caretUpdate(CaretEvent ce) {
		selection = text.getSelectedText();
		update();
	}

	protected void update() {
		setEnabled(canDo());
	}

	protected boolean canDo() {
		return false;
	}

	public JComponent getContext() {
		return text;
	}

}
