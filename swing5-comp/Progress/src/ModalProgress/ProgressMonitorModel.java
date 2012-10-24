package ModalProgress;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author idanilov
 *
 */
public class ProgressMonitorModel {

	private int total, current; 
	private boolean indeterminate; 
	private int milliSecondsToWait; 
	private String status; 
    private Vector<ChangeListener> listenersList; 
    private static final int DEFAULT_WAIT_BEFORE_POPUP = 500; 

	public ProgressMonitorModel(int total, boolean indeterminate) {
		this(total, indeterminate, DEFAULT_WAIT_BEFORE_POPUP);
	}
	
	public ProgressMonitorModel(int total, boolean indeterminate, int timeToWait) { 
		this.total = total; 
		this.indeterminate = indeterminate; 
		milliSecondsToWait = timeToWait;
		current = -1;
		listenersList = new Vector<ChangeListener>();
	} 
 
    public int getTotal() { 
        return total; 
    } 
 
    public void start(String startStatus) { 
        if (current != -1) { 
            throw new IllegalStateException("monitor already started");
        }
        this.status = startStatus; 
        current = 0; 
        fireChangeEvent(new ChangeEvent(this)); 
    } 
 
    public void done() {
        if (current != total) { 
            setCurrent(null, total);
        }
	}
    
    public int getMilliSecondsToWait() { 
        return milliSecondsToWait; 
    } 
 
    public int getCurrent(){ 
        return current; 
    } 
 
    public String getStatus(){ 
        return status; 
    } 
 
    public boolean isIndeterminate(){ 
        return indeterminate; 
    } 
 
    public void setCurrent(String status, int current){ 
        if (current == -1) { 
            throw new IllegalStateException("monitor not started yet");
        }
        this.current = current; 
        if (status != null) { 
            this.status = status;
        }
        fireChangeEvent(new ChangeEvent(this)); 
    } 
 
 
    public void addChangeListener(ChangeListener listener) { 
        listenersList.add(listener); 
    } 
 
    public void removeChangeListener(ChangeListener listener) { 
        listenersList.remove(listener); 
    } 
 
    private void fireChangeEvent(final ChangeEvent ce) { 
        ChangeListener listener[] = listenersList.toArray(new ChangeListener[listenersList.size()]); 
        for (int i=0; i<listener.length; i++) { 
            listener[i].stateChanged(ce); 
        }
    } 
}
