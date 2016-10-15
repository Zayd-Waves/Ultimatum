package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.PreferencesManager;
import com.github.paolorotolo.appintro.AppIntro;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        SharedPreferences prefs = PreferencesManager.getPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {

            /* It's the first time, the app is being run. Let's introduce things. */
            startAppIntroduction();
        } else {

            /* The user has already been introduced. Let's start the app regularly. */
            startApp();
        }

    }

    private void startAppIntroduction() {
        /* (we no longer have to do the intro after this). */
        SharedPreferences prefs = PreferencesManager.getPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstTime", true);
        editor.apply();

        Intent intent = new Intent(this, AppIntroduction.class);
        startActivity(intent);
        finish();
    }

    private void startApp() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
