package com.lutzenberger.sascha.swandata;

import android.content.Context;


/**
 * Constants used (might not the best way to do it but it works).
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 29.07.2015
 *
 */
public class Constants {
    //The Context of the application
    public static Context context;
    //The flag indicating any change
    private static boolean changed = false;

    public static void hasChanged() {
        changed = true;
    }

    public static void resetChanged() {
        changed = false;
    }

    public static boolean isChanged() {
        return changed;
    }
}
