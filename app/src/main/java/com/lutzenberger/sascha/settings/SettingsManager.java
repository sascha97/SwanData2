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

        public static final String SWAN_CODES_NAME_STORAGE = "name_storage";
        public static final String SWAN_CODES_NAME_TUBE = "name_tube";
        public static final String SWAN_CODES_NAME_BIRD = "name_bird";
        public static final String SWAN_CODES_NAME_DATE_SAMPLED = "name_date_sampled";
        public static final String SWAN_CODES_NAME_TIME = "name_time";
        public static final String SWAN_CODES_NAME_BOX_NUMBER = "name_box_number";
        public static final String SWAN_CODES_NAME_MEDIA_ALIQUOTED_DATE = "name_media_aliquoted_date";
        public static final String SWAN_CODES_NAME_NOTES = "name_notes";
        public static final String SWAN_CODES_NAME_BAD_SAMPLE_COMMENTS = "name_bad_sample_comments";

        public static final String SWAN_DATA_NAME_SWID = "name_swid";
        public static final String SWAN_DATA_NAME_BTO = "name_bto";
        public static final String SWAN_DATA_NAME_DARVIC = "name_darvic";
        public static final String SWAN_DATA_NAME_SEX = "name_sex";
        public static final String SWAN_DATA_NAME_SEX2 = "name_sex2";
        public static final String SWAN_DATA_NAME_WAS_COB = "name_was_cob";
        public static final String SWAN_DATA_NAME_WAS_PEN = "name_was_pen";
        public static final String SWAN_DATA_NAME_WAS_COB_DATE = "name_was_cob_date";
        public static final String SWAN_DATA_NAME_WAS_PEN_DATE = "name_was_pen_date";
        public static final String SWAN_DATA_NAME_AGE = "name_age";
        public static final String SWAN_DATA_NAME_ACTUAL_AGE = "name_actual_age";
        public static final String SWAN_DATA_NAME_HATCHED = "name_hatched";
        public static final String SWAN_DATA_NAME_RING_DATE = "name_ring_date";
        public static final String SWAN_DATA_NAME_ADOB = "name_adob";
        public static final String SWAN_DATA_NAME_ADDZ = "name_addz";
        public static final String SWAN_DATA_NAME_ADDD = "name_addd";
        public static final String SWAN_DATA_NAME_MINUS_DAR = "name_minus_dar";
        public static final String SWAN_DATA_NAME_MINUS_Z = "name_minus_z";
        public static final String SWAN_DATA_NAME_TAG_NO = "name_tag_no";
        public static final String SWAN_DATA_NAME_COB = "name_cob";
        public static final String SWAN_DATA_NAME_PEN = "name_pen";
        public static final String SWAN_DATA_NAME_NEST_SITE = "name_nest_site";
        public static final String SWAN_DATA_NAME_NEST_NO = "name_nest_no";
        public static final String SWAN_DATA_NAME_REARING_PEN = "name_rearing_pen";
        public static final String SWAN_DATA_NAME_NO_CYGS = "name_no_cygs";
        public static final String SWAN_DATA_NAME_GPS = "name_gps";
        public static final String SWAN_DATA_NAME_COMMENTS = "name_comments";
        public static final String SWAN_DATA_NAME_HIDDEN_COMMENTS = "name_hidden_comments";
        public static final String SWAN_DATA_NAME_MANUAL_COMMENTS = "name_manual_comments";
        public static final String SWAN_DATA_NAME_ADDED_SWID = "name_added_swid";
        public static final String SWAN_DATA_NAME_BTO_ADDED_SWID = "name_bto_added_swid";
        public static final String SWAN_DATA_NAME_OLDDAR = "name_olddar";
        public static final String SWAN_DATA_NAME_WEIGHT = "name_weight";
        public static final String SWAN_DATA_NAME_OLDZ = "name_oldz";
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
