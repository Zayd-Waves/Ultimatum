package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.electricpanda.ultimatum.entities.Pact;

public class PactDetailsActivity extends AppCompatActivity {

    private Pact currentPact;
    private Context mContext;
    private TextView habit, length, stakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact_details);

        mContext = this;
        currentPact = (Pact) getIntent().getSerializableExtra("pact");

        habit = (TextView) findViewById(R.id.habitText);
        length = (TextView) findViewById(R.id.lengthText);
        stakes = (TextView) findViewById(R.id.stakesText);

        habit.setText(currentPact.getHabit());
        length.setText(currentPact.getLength() + "");
        stakes.setText(currentPact.getStakes() + "");
    }
}
