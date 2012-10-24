package ComboAutoCompletion;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author unkown
 * @author idanilov
 * @jdk 1.5
 */
public class ComboAutoCompletionDemo extends JFrame {

	private JComboBox cb;
	
	public ComboAutoCompletionDemo() {
		super(ComboAutoCompletionDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
        cb = new JComboBox(new Object[] {"1", "112", "ab", "abc", "abcf","fh","hdzx"});
        ComboAutoCompletion.install(cb, new ComboAutoCompletion.ILabelProvider() {
			public String getLabel(Object o) {
				return o.toString();
			}
        });
        result.add(cb);
		return result;
	}    		
	
	public static void main(final String [] args) {
    	 JFrame frame = new ComboAutoCompletionDemo();
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);            
    }

}
