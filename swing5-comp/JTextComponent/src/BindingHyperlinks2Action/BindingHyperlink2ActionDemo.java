package BindingHyperlinks2Action;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * @author santosh
 * @jdk 1.5
 */
public class BindingHyperlink2ActionDemo extends JFrame {

	public BindingHyperlink2ActionDemo() {
		super(BindingHyperlink2ActionDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
		JEditorPane editor = new JEditorPane(); 
        try {
			editor.setPage(getClass().getResource("rule.html"));
	        editor.setEditable(false); 
	        ActionMap actionMap = new ActionMap(); 
	        actionMap.put("selectPeople", new SelectPeopleAction()); 
	        editor.addHyperlinkListener(new ActionBasedHyperlinkListener(actionMap));
	        result.add(new JScrollPane(editor),BorderLayout.CENTER);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new BindingHyperlink2ActionDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
