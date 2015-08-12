package com.lutzenberger.sascha.file;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lutzenberger.sascha.swandata.R;

import java.io.File;

import static com.lutzenberger.sascha.swandata.Constants.context;

/**
 * This class handles the directories and the file names of the whole app.
 *
 * The directory given by Environment.getExternalStorageDirectory() needs to be existent.
 *
 * @author Sascha Lutzenberger
 * @version 1.1 - 11.08.2015
 *
 */
public final class Directories {
    //Constant for the SwanData directory
    @NonNull
    public static final File SWAN_DATA_DIRECTORY;
    //Constant for the Settings directory where exported settings will be stored
    @NonNull
    static final File EXPORTED_SETTINGS_DIRECTORY;

    //The root directory of the phone internal storage
    @NonNull
    private static final File BASE_DIRECTORY;
    //The documents directory in the root directory
    @NonNull
    private static final File DOCUMENT_DIRECTORY;
    private static boolean existent = false;

    static {
        //Get the path of the root directory
        BASE_DIRECTORY = Environment.getExternalStorageDirectory();
        //Get the path of the Documents directory
        DOCUMENT_DIRECTORY = new File(BASE_DIRECTORY, "Documents");
        //Set the path of the SwanData directory
        SWAN_DATA_DIRECTORY = new File(DOCUMENT_DIRECTORY,"SwanData");
        //Set the path of the Settings directory
        EXPORTED_SETTINGS_DIRECTORY = new File(SWAN_DATA_DIRECTORY, "Settings");
    }

    private Directories(){
    }

    /**
     * This method returns true if everything is setup correctly if anything fails to do false
     * will be returned
     */
    public static void setUpIfNotExistent() {
        //If the SwanData directory and Settings directory exist all the directories are set up
        if(SWAN_DATA_DIRECTORY.exists() && EXPORTED_SETTINGS_DIRECTORY.exists()) {
            existent = true;
            return;
        }
        //Base directory needs to be existent, if it doesn't exist end method...
        if(!fileExists(BASE_DIRECTORY)) {
            Toast.makeText(context, context.getString(R.string.directory_base_not_existent),
                    Toast.LENGTH_LONG).show();
            return;
        }

        try {
            //Create the documents directory if it fails to do so display the given error message
            createDirectory(DOCUMENT_DIRECTORY, context.getString(
                    R.string.directory_failed_create_document));

            //Create the SwanData directory if it fails to do so display the given error message
            createDirectory(SWAN_DATA_DIRECTORY, context.getString(
                    R.string.directory_failed_create_swan));

            //Create the Settings directory if it fails to do so display the given error message
            createDirectory(EXPORTED_SETTINGS_DIRECTORY, context.getString(
                    R.string.directory_failed_create_settings));
        } catch (DirectoryNotCreatedException e) {
            return;
        }

        if(!existent){
            Toast.makeText(context, context.getString(R.string.directory_all_created),
                    Toast.LENGTH_LONG).show();
        }
    }

    private static void createDirectory(File dir, String errorMessage) throws
            DirectoryNotCreatedException {
        boolean result = true;

        if(!fileExists(dir))
            result = createDirectory(dir);
        if(!result) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            throw new DirectoryNotCreatedException();
        }
    }

    /**
     * Method checking if a directory is already existent.
     * Returns true if the directory already exists. Returns false if the directory needs to be
     *
     * created.
     * @param directory The directory which has to be checked for existence.
     * @return If the directory exists.
     */
    private static boolean fileExists(File directory){
        if(directory == null) {
            return false;
        }

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
        if(directory == null) {
            return false;
        }

        return directory.mkdir();
    }

    //Exception for not creating the directories.
    private static class DirectoryNotCreatedException extends Exception {
    }
}
