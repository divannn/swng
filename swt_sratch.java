public class XXX {

	public XXX() {
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setText(XXX.class.getSimpleName());
	    createContents(shell,display);
	    shell.pack();
	    shell.setLocation(300,300);
	    shell.open();
	    while (!shell.isDisposed()) {
	    	if (!display.readAndDispatch()) {
	    		display.sleep();
	    	}
	    }
	    display.dispose();
	}
	
	private void createContents(final Shell shell,final Display display) {
	    shell.setLayout(new FillLayout());
	}

	public static void main(String[] args) {
		new XXX();
	}

}