package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Zayd on 10/15/16.
 */

public class PreferencesManager {
    public static final String PREFS_NAME = "preferences_file";


    public static void hideAppIntro(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putBoolean("hideAppIntro", true);
        editor.apply();
    }

    public static boolean shouldShowAppIntro(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        /* Should return false by default if there aren't any preferences saved already. */
        return prefs.getBoolean("hideAppIntro", false);
    }
}
