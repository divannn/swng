package ModalProgress;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author idanilov
 *
 */
public class ProgressDialog extends JDialog 
		implements ChangeListener {
	
	private JLabel statusLabel; 
	private JProgressBar progressBar; 
	private ProgressMonitorModel monitorModel; 
 
    public ProgressDialog(Frame owner, ProgressMonitorModel monitor) { 
        super(owner, "Progress", true); 
        create(monitor); 
    } 
 
    public ProgressDialog(Dialog owner, ProgressMonitorModel monitor) { 
    	super(owner, "Progress", true);
        create(monitor); 
    } 
 
    private void create(ProgressMonitorModel model) { 
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
        monitorModel = model; 
        progressBar = new JProgressBar(0, model.getTotal()); 
        if (model.isIndeterminate()) { 
            progressBar.setIndeterminate(true);
        } else { 
            progressBar.setValue(model.getCurrent());
        }
        statusLabel = new JLabel();
        statusLabel.setText(model.getStatus()); 
        JPanel contents = (JPanel)getContentPane(); 
        contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        contents.add(statusLabel, BorderLayout.NORTH); 
        contents.add(progressBar); 
        monitorModel.addChangeListener(this); 
    } 
 
    public void stateChanged(final ChangeEvent ce) { 
        //ensure EDT thread. 
        if (SwingUtilities.isEventDispatchThread()) { 
            if (monitorModel.getCurrent() != monitorModel.getTotal()) { 
                statusLabel.setText(monitorModel.getStatus()); 
                if (!monitorModel.isIndeterminate()) { 
                    progressBar.setValue(monitorModel.getCurrent());
                }
            } else {
                dispose();
            } 
        } else {
            SwingUtilities.invokeLater(new Runnable() { 
                public void run(){ 
                    stateChanged(ce); 
                } 
            }); 
        }
    } 

    public void dispose() {
    	monitorModel.removeChangeListener(this);
    	super.dispose();
    }
}
