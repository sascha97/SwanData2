package com.lutzenberger.sascha.file;

import android.os.Environment;
import android.widget.Toast;

import static com.lutzenberger.sascha.swandata.Constants.context;
import com.lutzenberger.sascha.swandata.R;

import java.io.File;

/**
 * This class handles the directories and the file names of the whole app.
 *
 * The directory given by Environment.getExternalStorageDirectory() needs to be existent.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 31.07.2015
 *
 */
public final class Directories {
    public static final File SWAN_DATA_DIRECTORY;

    private static final File BASE_DIRECTORY;
    private static final File DOCUMENT_DIRECTORY;
    private static boolean existent = false;

    static {
        BASE_DIRECTORY = Environment.getExternalStorageDirectory();
        DOCUMENT_DIRECTORY = new File(BASE_DIRECTORY.getPath() + "/Documents");
        SWAN_DATA_DIRECTORY = new File(DOCUMENT_DIRECTORY.getPath() + "/SwanData");
    }

    private Directories(){
    }

    /**
     * This method returns true if everything is setup correctly if anything fails to do false
     * will be returned
     * @return If everything is setted up correctly true otherwise false
     */
    public static boolean setUpIfNotExistent() {
        if(SWAN_DATA_DIRECTORY.exists()) {
            existent = true;
            return true;
        }
        //Base directory needs to be existent, if it doesn't exist end method...
        if(!fileExists(BASE_DIRECTORY)) {
            Toast.makeText(context, context.getString(R.string.directory_base_not_existent),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        boolean result = true;
        if(!fileExists(DOCUMENT_DIRECTORY))
            result = createDirectory(DOCUMENT_DIRECTORY);
        if(!result){
            Toast.makeText(context, context.getString(R.string.directory_failed_create_document),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(!fileExists(SWAN_DATA_DIRECTORY))
            result = createDirectory(SWAN_DATA_DIRECTORY);
        if(!result){
            Toast.makeText(context, context.getString(R.string.directory_failed_create_swan),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if(!existent){
            Toast.makeText(context, context.getString(R.string.directory_all_created),
                    Toast.LENGTH_LONG).show();
        }

        return true;
    }

    /**
     * Method checking if a directory is already existent.
     * Returns true if the direcory already exists. Returns false if the directory needs to be
     *
     * created.
     * @param directory The directory which has to be checked for existence.
     * @return If the directory exists.
     */
    private static boolean fileExists(File directory){
        return directory.exists();
    }

    /**
     * Method for creating a directory...
     * Returns true if directory was created successfully, returns false if directory was not
     * created successfully.
     *
     * @param directory The directory which needs to be created.
     * @return If the directory was created successfully.
     */
    private static boolean createDirectory(File directory){
        return directory.mkdir();
    }
}
