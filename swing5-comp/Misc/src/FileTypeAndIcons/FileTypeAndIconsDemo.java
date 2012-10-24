package FileTypeAndIcons;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

/**
 * Shows usage of ShellFolder to get file description and big file icon.
 * <br>
 * ShellFolder is a wrapper for the metadata of the selected file. 
 * From this object, you can retrieve both the icon and a text description of the file's type. 
 * If you run this on an MP3 file, it might print the string "type = MPEG Layer 3 Audio" 
 * and show a 32 by 32 pixel iTunes MP3 icon.
 * @author idanilov
 * @jdk 1.5
 */
public class FileTypeAndIconsDemo extends JFrame {
	
	public FileTypeAndIconsDemo() {
		super(FileTypeAndIconsDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel(new GridLayout(1,0));
        //create a File instance of an existing files - plain file and folder.
        File file = new File(FileTypeAndIconsDemo.class.getResource("TestFile.txt").getFile());
        File folder = new File(".");
		
        //small icons.
		FileSystemView fsv = new JFileChooser().getFileSystemView();
		Icon smallFileIcon = fsv.getSystemIcon(file);
		JLabel smallFileLabel = new JLabel(smallFileIcon);
		smallFileLabel.setToolTipText("Small file icon 16x16");
		result.add(smallFileLabel);

		Icon smallFolderIcon = fsv.getSystemIcon(folder);
		JLabel smallFolderLabel = new JLabel(smallFolderIcon);
		smallFolderLabel.setToolTipText("Small folder icon 16x16");
		result.add(smallFolderLabel);
		
		//big icons.
        sun.awt.shell.ShellFolder sfForFile,sfForFolder;
		try {
			sfForFile = sun.awt.shell.ShellFolder.getShellFolder(file);
	        Icon fileIcon = new ImageIcon(sfForFile.getIcon(true));
	        System.err.println("file type = " + sfForFile.getFolderType());		
			JLabel bigFileLabel = new JLabel(fileIcon);
			bigFileLabel.setToolTipText("Big file icon 32x32");
			result.add(bigFileLabel);
			
			sfForFolder  = sun.awt.shell.ShellFolder.getShellFolder(folder);
	        Icon folderIcon = new ImageIcon(sfForFolder.getIcon(true));
	        System.err.println("folder type = " + sfForFolder.getFolderType());		
			JLabel bigFolderLabel = new JLabel(folderIcon);
			bigFolderLabel.setToolTipText("Big folder icon 32x32");
			result.add(bigFolderLabel);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e){ 
            e.printStackTrace(); 
        }
		JFrame f = new FileTypeAndIconsDemo();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}