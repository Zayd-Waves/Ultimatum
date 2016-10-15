package com.electricpanda.ultimatum;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.electricpanda.ultimatum.misc.CustomSlide;
import com.github.paolorotolo.appintro.AppIntro;

public class AppIntroduction extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(CustomSlide.newInstance(R.layout.first_slide));
        addSlide(CustomSlide.newInstance(R.layout.second_slide));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        goToDashboard();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        goToDashboard();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }


    private void goToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}
