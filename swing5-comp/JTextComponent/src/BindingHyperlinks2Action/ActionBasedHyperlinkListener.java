package BindingHyperlinks2Action;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * @author idanilov
 * 
 */
public class ActionBasedHyperlinkListener
		implements HyperlinkListener {

	private ActionMap actionMap;

	public ActionBasedHyperlinkListener(ActionMap actionMap) {
		this.actionMap = actionMap;
	}

	public void hyperlinkUpdate(HyperlinkEvent he) {
		if (he.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
			return;
		}
		String href = he.getDescription();
		Action action = actionMap.get(href);
		if (action != null) {
			action.actionPerformed(new ActionEvent(he, ActionEvent.ACTION_PERFORMED, href));
		}
	}

}
