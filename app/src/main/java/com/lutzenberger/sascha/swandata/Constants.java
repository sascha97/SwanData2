package com.lutzenberger.sascha.swandata;

import android.content.Context;


/**
 * Constants used (might not the best way to do it but it works).
 *
 * @author Sascha Lutzenberger
 * @version 1.1 - 08.08.2015
 *
 */
public final class Constants {
    private Constants() {
    }

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
