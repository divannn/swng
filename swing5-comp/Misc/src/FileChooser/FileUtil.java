package FileChooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author idanilov
 *
 */
public abstract class FileUtil {
	
	public static final String TMP_DIR_PROPERTY = "java.io.tmpdir";
	public static final String USER_HOME_PROPERTY = "user.home";
	
	public static final String XLS = ".xls";
	
    public static FileFilter EXCEL_FILE_FILTER = new FileFilter() {
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			return "xls".equals(getExtension(f));
		}

		public String getDescription() {
			return "(*.xls) Excel files";
		}
    };
	
	public static String getExtension(final File f) {
        String result = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            result = s.substring(i+1).toLowerCase();
        }
        return result;
    }	
	
    public static String getShortTargetFileName(final String fileName) {
    	if (fileName == null) {
    		throw new IllegalArgumentException("null file name");
    	}
    	String fullPath = fileName;
    	return fullPath.substring(fullPath.lastIndexOf(File.separator));
    }

	public static File getUserHome() {
		File result = new File(System.getProperty(USER_HOME_PROPERTY));
		return result;
	}
	
	public static File getNearestExistingFolder(final File file) {
		File result = file;
		while (result != null) {
			if (result.exists() && result.isDirectory()) {
				break;
			} 
			result = result.getParentFile();
		}
		return result;
	}
	
}
