package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //startAppIntroduction();
        boolean isItTheFirstLaunch = PreferencesManager.shouldShowAppIntro(mContext);
        if(isItTheFirstLaunch) {
            /* After this launch, we don't need to show the intro anymore. So we'll hide it.
             * Along with other first-time safety checks. */
            PreferencesManager.hideAppIntro(mContext);
            PreferencesManager.savePacts(mContext, new ArrayList<Pact>());
            // Toast.makeText(mContext, "First Launch", Toast.LENGTH_SHORT).show();
            startAppIntroduction();


        } else {
            //PreferencesManager.savePacts(mContext, new ArrayList<Pact>());
            //Toast.makeText(mContext, "not the first Launch", Toast.LENGTH_SHORT).show();
            PreferencesManager.savePacts(mContext, new ArrayList<Pact>());
            startApp();
        }


    }

    private void startAppIntroduction() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startApp() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
