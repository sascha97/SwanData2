package com.lutzenberger.sascha.settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lutzenberger.sascha.swandata.Constants;

import java.util.Set;

/**
 * This class handles all the Settings, all the Preferences have to be got from here.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 29.07.2015
 */
public final class SettingsManager {
    public static final String DATA_FILE_SWAN_DATA = "swan_data_file_name";
    public static final String DATA_FILE_SWAN_CODES = "swan_codes_file_name";

    private static final SharedPreferences pref =
            PreferenceManager.getDefaultSharedPreferences(Constants.context);

    private SettingsManager() {
    }

    /**
     * This method returns a String to the given preferenceKey, if the Key is not used an empty
     * String will be returned.
     *
     * @param preferenceKey One of the SettingsManager constants defined.
     * @return The current setting.
     */
    public static String getString(String preferenceKey){
        return pref.getString(preferenceKey, "");
    }
}
