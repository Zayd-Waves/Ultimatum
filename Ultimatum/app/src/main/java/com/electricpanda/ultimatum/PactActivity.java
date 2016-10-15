package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.AppConstants;

public class PactActivity extends AppCompatActivity {

    private Pact currentPact;
    private Context mContext;
    private TextView habit, length, stakes;
    private Button pactDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact);

        mContext = this;

        habit  = (TextView) findViewById(R.id.habitText);
        length = (TextView) findViewById(R.id.lengthText);
        stakes = (TextView) findViewById(R.id.stakesText);
        pactDetailsButton = (Button) findViewById(R.id.pactDetailsButton);

        currentPact = (Pact) getIntent().getSerializableExtra("pact");
        habit.setText(currentPact.getHabit());
        length.setText(currentPact.getLength() + "");
        stakes.setText(currentPact.getStakes() + "");

        pactDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPactDetails();
            }
        });
    }

    public void goToPactDetails() {
        Intent intent = new Intent(this, PactDetailsActivity.class);
        intent.putExtra("pact", currentPact);
        startActivityForResult(intent, AppConstants.PACT_DETAILS_REQUEST_CODE);
    }
}
