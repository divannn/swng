package FileChooser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * 1. Usage of file filter, file selection etc.
 * 2. Opens JFileChooser with nearest existed parent directory of specfied file.
 * @author idanilov
 * @jdk 1.5
 */
public class FileChooserDemo extends JFrame {

	private ExtendedControl extControl;
	
	public FileChooserDemo() {
		super(FileChooserDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		extControl = new ExtendedControl();
		extControl.getButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setMultiSelectionEnabled(false);
                fc.setFileFilter(FileUtil.EXCEL_FILE_FILTER);
                fc.setDialogTitle("Select Excel File");
                fc.setApproveButtonText("Select");
                String enteredFileName = getTargetFileName();
                if (enteredFileName.length() > 0) {
                	File targetFile = new File(enteredFileName);
					System.err.println("target file: " + targetFile.getPath());
					if (targetFile.exists()) {
						if (targetFile.isFile()) {
							System.err.println("target is a file and exists");		
							fc.setSelectedFile(targetFile);
						} else if (targetFile.isDirectory()) {
							System.err.println("target is a directory and exists");		
							fc.setCurrentDirectory(targetFile);
						} 	
					} else {
						System.err.println("target is not exists");		
						File nearExistingFolder = FileUtil.getNearestExistingFolder(targetFile.getParentFile());
						if (nearExistingFolder != null) {
							System.err.println("set near existing folder : " + nearExistingFolder.getPath());
							fc.setCurrentDirectory(nearExistingFolder);
						} else {
							System.err.println("set user home");
							fc.setCurrentDirectory(FileUtil.getUserHome());							
						}
						File notExistFile = new File(fc.getCurrentDirectory(),FileUtil.getShortTargetFileName(getTargetFileName()));
						fc.setSelectedFile(notExistFile);
					}
                } else {
                	System.err.println("set user home");
                    fc.setCurrentDirectory(FileUtil.getUserHome());
                }
                int returnVal = fc.showOpenDialog(FileChooserDemo.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (!FileUtil.EXCEL_FILE_FILTER.accept(file)){
                    	if (file.getAbsolutePath().endsWith(".")){
                    		file = new File(file.getAbsolutePath() + "xls");
                    	} else {
                    		file = new File(file.getAbsolutePath() + ".xls");
                    	}
                    }
                    JTextField tf = extControl.getTextField();
                    extControl.getTextField().setText(file.getAbsolutePath());
                    extControl.getTextField().setCaretPosition(tf.getText().length());
                    tf.requestFocusInWindow();
                }
            }
        });
		extControl.getTextField().setColumns(50);
		result.add(extControl,BorderLayout.NORTH);
		return result;
	}
	
    private String getTargetFileName() {
    	return extControl.getTextField().getText().trim();
    }
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame f = new FileChooserDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
