package com.lutzenberger.sascha.file;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lutzenberger.sascha.swandata.R;
import com.lutzenberger.sascha.xml.XmlUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import static com.lutzenberger.sascha.swandata.Constants.context;

/**
 * This is the class taking care of saving the preference file to the SwanData directory, and
 * loading in the preferences from the SwanData directory.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 11.08.2015
 */
public final class PreferenceFileUtils {
    @NonNull
    private static final SharedPreferences PREFERENCES;
    @NonNull
    private static final File SETTINGS_FILE;

    static {
        PREFERENCES = PreferenceManager.getDefaultSharedPreferences(context);
        SETTINGS_FILE = new File(Directories.EXPORTED_SETTINGS_DIRECTORY, "preferences.xml");
    }

    //This method saves the preferences to an xml file.
    public static void exportSharedPreferences() {
        //The FileOutputStream to which the xml file should be written to
        FileOutputStream fos = null;

        try {
            //Set the FileOutputStream to the SettingsFile
            fos = new FileOutputStream(SETTINGS_FILE);

            //Write the SharedPreferences Map to the xml file.
            XmlUtils.writeMapXml(PREFERENCES.getAll(), fos);

            //Display a message that everything was successfully done to the user
            Toast.makeText(context, context.getString(R.string.message_settings_export_success),
                    Toast.LENGTH_LONG).show();
        } catch (IOException | XmlPullParserException e) {
            //Display an error message to the user
            Toast.makeText(context, context.getString(R.string.message_settings_export_failed),
                    Toast.LENGTH_LONG).show();
            //If an error occurs just print out the stack trace helps for debugging only
            e.printStackTrace();
        } finally {
            //No matter what happened, just flush and close the FileOutputStream
            if(fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    //Nothing sensible to do here
                }
            }
        }
    }

    //This method loads the preferences from the xml file where the preferences were saved to
    public static void importSharedPreferences() {
        //The FileInputStream to load the preferences from the xml file
        FileInputStream fis = null;

        try {
            //Set the FileInputStream to the settings file
            fis = new FileInputStream(SETTINGS_FILE);
            //Get the map saved in the settings file
            HashMap<?, ?> entries = XmlUtils.readMapXml(fis);

            //Get the Editor to edit the shared preferences
            Editor prefEditor = PREFERENCES.edit();
            //delete all entries from the SharedPreferences
            prefEditor.clear();

            //For each entry from the XML file
            for(Entry entry : entries.entrySet()){
                //Get the key of the entry
                String key = (String) entry.getKey();
                //Get the value of the entry
                Object value = entry.getValue();

                //Values are either of the Type boolean or String
                if(value instanceof Boolean) {
                    prefEditor.putBoolean(key, (Boolean) value);
                } else if(value instanceof String) {
                    prefEditor.putString(key, (String) value);
                }
            }
            //Apply those changes to the settings stored in memory and write it to the preference
            //file later in an async task. Ignore the result of that
            prefEditor.apply();

            //Display a message that everything was successfully done to the user
            Toast.makeText(context, context.getString(R.string.message_settings_import_success),
                    Toast.LENGTH_LONG).show();
        } catch (IOException | XmlPullParserException e) {
            //Display an error message to the user
            Toast.makeText(context, context.getString(R.string.message_settings_import_failed),
                    Toast.LENGTH_LONG).show();
            //If an error occurs just print out the stack trace helps for debugging only
            e.printStackTrace();
            //failed to do the task
        } finally {
            //No matter what happened, close the file stream
            if(fis != null)
                try { fis.close(); } catch (IOException e) { /*Nothing sensible to do now*/}

            //Load in the default values just in case not all values were saved and loaded from the
            //file.
            loadInPreferencesDefaultValues();
        }
    }

    /**
     * This method deletes all the preferences saved and reloads the default values defined in the
     * xml files in the folder res/xml/pref_swan_xxx.xml
     */
    public static void resetDefaultSettings() {
        Editor editor = PREFERENCES.edit();
        editor.clear();
        editor.apply();

        loadInPreferencesDefaultValues();

        Toast.makeText(context, context.getString(R.string.message_settings_reset_success),
                Toast.LENGTH_LONG).show();
    }

    /**
     * Public method used for loading the default values of the preferences for not already
     * initialized values.
     */
    public static void loadInPreferencesDefaultValues(){
        //Load in the default Settings, this does not reset preferences back to their default value
        PreferenceManager.setDefaultValues(context, R.xml.pref_data_file, true);
        PreferenceManager.setDefaultValues(context, R.xml.pref_general, true);
        PreferenceManager.setDefaultValues(context, R.xml.pref_swan_codes, true);
        PreferenceManager.setDefaultValues(context, R.xml.pref_swan_codes_columns, true);
        PreferenceManager.setDefaultValues(context, R.xml.pref_swan_data, true);
        PreferenceManager.setDefaultValues(context, R.xml.pref_swan_data_columns, true);
    }
}
