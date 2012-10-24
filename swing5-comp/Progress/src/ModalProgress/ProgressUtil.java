package ModalProgress;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author idanilov
 *
 */
public class ProgressUtil {

    public static ProgressMonitorModel createModalProgressMonitor(Component owner, int total, boolean indeterminate, int milliSecondsToWait) { 
        ProgressMonitorModel result = new ProgressMonitorModel(total, indeterminate, milliSecondsToWait); 
        Window window = owner instanceof Window ? (Window)owner : SwingUtilities.getWindowAncestor(owner); 
        result.addChangeListener(new ProgressDialogOpener(window, result)); 
        return result; 
    } 
	
    private static class ProgressDialogOpener 
    		implements ChangeListener, ActionListener { 
        private ProgressMonitorModel model; 
        private Window owner; 
        private Timer timer; 
 
        public ProgressDialogOpener(Window owner, ProgressMonitorModel monitor) { 
            this.owner = owner; 
            this.model = monitor; 
        } 
 
        public void stateChanged(ChangeEvent ce) { 
            ProgressMonitorModel monitor = (ProgressMonitorModel)ce.getSource(); 
            if (monitor.getCurrent() != monitor.getTotal()) { 
                if (timer == null) { 
                    timer = new Timer(monitor.getMilliSecondsToWait(), this); 
                    timer.setRepeats(false); 
                    timer.start(); 
                } 
            } else { 
                if (timer != null && timer.isRunning()) { 
                    timer.stop();
                }
                monitor.removeChangeListener(this); 
            } 
        } 
 
        public void actionPerformed(ActionEvent e) { 
            model.removeChangeListener(this); 
            ProgressDialog dlg = owner instanceof Frame 
                    ? new ProgressDialog((Frame)owner, model) : new ProgressDialog((Dialog)owner, model); 
            dlg.pack(); 
            dlg.setLocationRelativeTo(null); 
            dlg.setVisible(true); 
        } 
    } 
}
