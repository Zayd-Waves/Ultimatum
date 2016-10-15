package com.electricpanda.ultimatum.misc;

import android.content.Context;
import android.content.SharedPreferences;

import com.electricpanda.ultimatum.entities.Pact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Zayd on 10/15/16.
 */

public class PreferencesManager {

    public static final String PREFS_NAME = "preferences_file";

    /* GSON Constants. */
    private static final Type LIST_TYPE = new TypeToken<ArrayList<Pact>>() {}.getType();


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

    public static ArrayList<Pact> loadPacts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);

        ArrayList<Pact> pacts = new Gson().fromJson(prefs.getString("pactList", ""), LIST_TYPE);
        return pacts;
    }

    public static void savePacts(Context context, ArrayList<Pact> pacts){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String pactListString = gson.toJson(pacts);
        editor.putString("pactList", pactListString);
        editor.apply();
    }

}
