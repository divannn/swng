package PropertiesChecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.Set;

/**
 * Intended to find unused i18n properties keys in java source files. 
 * @author idanilov
 * @jdk 1.5
 */
public class PropertiesChecker {

    private static final FilenameFilter JAVA_FILE_FILTER = new JavaFileNameFilter();
    private static final String END_LINE = System.getProperty("line.separator");

    private static final String CHECKED_FILES_LOG = "checked_files_log.txt";
    private static final String CHECKED_KEYS_LOG = "checked_keys_log.txt";
    private static final String ERROR_LOG = "error_log.txt";

    public static void main(final String[] args) {
        if (args.length != 2) {
            System.err.println("\n Usage: " + PropertiesChecker.class.getSimpleName()
                    + " <src_file_or_folder> <properties_file>");
            System.exit(1);
        }
        String startDirPath = args[0];
        String propFile = args[1];
        System.out.println("Search started...");
        long start = System.currentTimeMillis();
        Set<String> keys = null;
        try {
            FileInputStream propReader = new FileInputStream(propFile);
            PropertyResourceBundle bundle = new PropertyResourceBundle(propReader);
            propReader.close();
            keys = checkBundle(bundle);
            if (keys != null && keys.size() > 0) {
                search(startDirPath, keys);
            }
        } catch (IOException ioe) {
            System.err.println("Failed to process resouce bundle: " + propFile);
            ioe.printStackTrace(System.err);
        }
        long end = System.currentTimeMillis();
        double time = (end - start) / 1000;
        System.out.println("Finished. Took " + time + " sec.");
    }

    /**
     * @param bundle
     * @return set of keys
     */
    private static Set<String> checkBundle(final PropertyResourceBundle bundle) {
        System.out.print("Checking resource bundle...");
        Enumeration<String> keysEnum = bundle.getKeys();
        List<String> sourceKeys = Collections.list(keysEnum);
        System.out.println("Found " + sourceKeys.size() + " keys.");
        Set<String> uniqueKeys = new HashSet<String>(sourceKeys);
        if (sourceKeys.size() > 0) {
            logKeys(sourceKeys);
            //TODO : implement check for uniqueness of keys in properties file.
            //Unfortunately PropertyResourceBundle returns set of keys - so impossible to analize them.
            /*try {
            	BufferedWriter errorLogWriter = new BufferedWriter(new FileWriter(ERROR_LOG));
            	for (String nextKey : sourceKeys) {
            		boolean exists = !uniqueKeys.add(nextKey);
            		if (exists) {
            			errorLogWriter.write("Key duplicates : " + nextKey + END_LINE);	
            		}
            	}
            	errorLogWriter.close();
            	if (sourceKeys.size() == uniqueKeys.size()) {
            		System.out.println("Ok.");		
            	} else {
            		System.out.println("Key duplicates found. See " + ERROR_LOG + " for details.");		
            	}
            } catch(IOException ioe) {
            	ioe.printStackTrace();
            }*/
        }
        return uniqueKeys;
    }

    public static void search(final String startDir, final Set<String> keys) {
        List<String> fileList = new ArrayList<String>();
        System.out.print("Scan directory structure...");
        getFileList(new File(startDir), fileList);
        System.out.println("Found " + fileList.size() + " files.");
        if (fileList.size() > 0) {
            System.out.println("Process files...");
            //log files.
            logFileList(fileList);
            long unusedKeysCount = 0;
            try {
                BufferedWriter errorLogWriter = new BufferedWriter(new FileWriter(ERROR_LOG));
                for (String nextKey : keys) {
                    boolean nextIsKeyUsed = false;
                    for (String nextFileName : fileList) {
                        nextIsKeyUsed = containsString(nextFileName, nextKey);
                        if (nextIsKeyUsed) {
                            break;
                        }
                    }
                    if (!nextIsKeyUsed) {
                        unusedKeysCount++;
                        errorLogWriter.write("Key is not used: " + nextKey + END_LINE);
                    }
                }
                if (unusedKeysCount == 0) {
                    System.out.println("Ok.");
                } else {
                    System.out.println(unusedKeysCount + " unused keys found. See " + ERROR_LOG
                            + " for details.");
                }
                errorLogWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private static void logKeys(final List<String> keys) {
        BufferedWriter keysLogWriter;
        try {
            keysLogWriter = new BufferedWriter(new FileWriter(CHECKED_KEYS_LOG));
            for (String nextKey : keys) {
                keysLogWriter.write(nextKey + END_LINE);
            }
            keysLogWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void logFileList(final List<String> fileList) {
        BufferedWriter filesLogWriter;
        try {
            filesLogWriter = new BufferedWriter(new FileWriter(CHECKED_FILES_LOG));
            for (String nextFileName : fileList) {
                filesLogWriter.write(nextFileName + END_LINE);
            }
            filesLogWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void getFileList(final File f, final List<String> storage) {
        //recurse if a directory.
        if (f.isDirectory()) {
            File entries[] = f.listFiles();
            if (entries != null) {
                for (int i = 0; i < entries.length; i++) {
                    getFileList(entries[i], storage);
                }
            }
        } else if (f.isFile() && JAVA_FILE_FILTER.accept(f.getParentFile(), f.getName())) {
            String filePath = f.getPath();
            if (!storage.contains(filePath)) {
                storage.add(filePath);
            }
        }
    }

    //check whether a file contains the specified string.
    private static boolean containsString(final String fileName, final String str) {
        boolean result = false;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String nextStr;
            while ((nextStr = br.readLine()) != null) {
                if (nextStr.indexOf(str) != -1) {
                    result = true;
                    break;
                }
            }
            br.close();
        } catch (IOException ioe) {
            System.err.println("Failed to process file: " + fileName);
            ioe.printStackTrace();
        }
        return result;
    }

    private static class JavaFileNameFilter
            implements FilenameFilter {

        private static final String JAVA_EXT = ".java";

        public boolean accept(File dir, String name) {
            if (name != null) {
                return name.endsWith(JAVA_EXT);
            }
            return false;
        }

    }

}