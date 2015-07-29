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
    public final static class Booleans {
        public static final String GENERAL_SHOW_NON_EMPTY = "show_non_empty";

        public static final String SWAN_CODES_SHOW_STORAGE = "show_storage";
        public static final String SWAN_CODES_SHOW_TUBE = "show_tube";
        public static final String SWAN_CODES_SHOW_BIRD = "show_bird";
        public static final String SWAN_CODES_SHOW_DATE_SAMPLED = "show_date_sampled";
        public static final String SWAN_CODES_SHOW_TIME = "show_time";
        public static final String SWAN_CODES_SHOW_BOX_NUMBER = "show_box_number";
        public static final String SWAN_CODES_SHOW_MEDIA_ALIQUOTED_DATE = "show_media_aliquoted_date";
        public static final String SWAN_CODES_SHOW_NOTES = "show_notes";
        public static final String SWAN_CODES_SHOW_BAD_SAMPLE_COMMENTS = "show_bad_sample_comments";

        public static final String SWAN_DATA_SHOW_SWID = "show_swid";
        public static final String SWAN_DATA_SHOW_BTO = "show_bto";
        public static final String SWAN_DATA_SHOW_DARVIC = "show_darvic";
        public static final String SWAN_DATA_SHOW_SEX = "show_sex";
        public static final String SWAN_DATA_SHOW_SEX2 = "show_sex2";
        public static final String SWAN_DATA_SHOW_WAS_COB = "show_was_cob";
        public static final String SWAN_DATA_SHOW_WAS_PEN = "show_was_pen";
        public static final String SWAN_DATA_SHOW_WAS_COB_DATE = "show_was_cob_date";
        public static final String SWAN_DATA_SHOW_WAS_PEN_DATE = "show_was_pen_date";
        public static final String SWAN_DATA_SHOW_AGE = "show_age";
        public static final String SWAN_DATA_SHOW_ACTUAL_AGE = "show_actual_age";
        public static final String SWAN_DATA_SHOW_HATCHED = "show_hatched";
        public static final String SWAN_DATA_SHOW_RING_DATE = "show_ring_date";
        public static final String SWAN_DATA_SHOW_ADOB = "show_adob";
        public static final String SWAN_DATA_SHOW_ADDZ = "show_addz";
        public static final String SWAN_DATA_SHOW_ADDD = "show_addd";
        public static final String SWAN_DATA_SHOW_MINUS_DAR = "show_minus_dar";
        public static final String SWAN_DATA_SHOW_MINUS_Z = "show_minus_z";
        public static final String SWAN_DATA_SHOW_TAG_NO = "show_tag_no";
        public static final String SWAN_DATA_SHOW_COB = "show_cob";
        public static final String SWAN_DATA_SHOW_PEN = "show_pen";
        public static final String SWAN_DATA_SHOW_NEST_SITE = "show_nest_site";
        public static final String SWAN_DATA_SHOW_NEST_NO = "show_nest_no";
        public static final String SWAN_DATA_SHOW_REARING_PEN = "show_rearing_pen";
        public static final String SWAN_DATA_SHOW_NO_CYGS = "show_no_cygs";
        public static final String SWAN_DATA_SHOW_GPS = "show_gps";
        public static final String SWAN_DATA_SHOW_COMMENTS = "show_comments";
        public static final String SWAN_DATA_SHOW_HIDDEN_COMMENTS = "show_hidden_comments";
        public static final String SWAN_DATA_SHOW_MANUAL_COMMENTS = "show_manual_comments";
        public static final String SWAN_DATA_SHOW_ADDED_SWID = "show_added_swid";
        public static final String SWAN_DATA_SHOW_BTO_ADDED_SWID = "show_bto_added_swid";
        public static final String SWAN_DATA_SHOW_OLDDAR = "show_olddar";
        public static final String SWAN_DATA_SHOW_WEIGHT = "show_weight";
        public static final String SWAN_DATA_SHOW_OLDZ = "show_oldz";
    }

    public static final class Strings {
        public static final String DATA_FILE_SWAN_DATA = "swan_data_file_name";
        public static final String DATA_FILE_SWAN_CODES = "swan_codes_file_name";
    }



    private static final SharedPreferences pref =
            PreferenceManager.getDefaultSharedPreferences(Constants.context);

    private SettingsManager() {
    }

    /**
     * This method returns a String value associated to the given preferenceKey,
     * if the Key is not used an empty String will be returned.
     *
     * @param preferenceKey One of the SettingsManager.Strings constants defined.
     * @return The current setting.
     */
    public static String getString(String preferenceKey){
        return pref.getString(preferenceKey, "");
    }

    /**
     * This method returns a boolean value associated to the given preferenceKey,
     * if the Key is not used false will be returned.
     *
     * @param preferenceKey One of the SettingsManager.Booleans constants defined.
     * @return The current setting.
     */
    public static boolean getBoolean(String preferenceKey){
        return pref.getBoolean(preferenceKey, false);
    }
}
