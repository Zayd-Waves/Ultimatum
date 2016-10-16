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

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PactActivity extends AppCompatActivity {

    private Pact currentPact;
    private Context mContext;
    private TextView habit, length, stakes, message;
    private TextView pactDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact);

        mContext = this;

        habit  = (TextView) findViewById(R.id.habitText);
        length = (TextView) findViewById(R.id.lengthText);
        stakes = (TextView) findViewById(R.id.stakesText);
        message = (TextView) findViewById(R.id.messageText);
        pactDetailsButton = (TextView) findViewById(R.id.minimal_button);

        currentPact = (Pact) getIntent().getSerializableExtra("pact");
        habit.setText("The \"" + currentPact.getHabit() + "\" Pact");

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        if (currentPact.getEndDate().before(today)) {
            message.setText("Woah! It looks like this pact is already done! Why not try starting another one?");
        } else if (today.before(currentPact.getStartDate())) {
            message.setText("This pact hasn't started yet!");
        } else {
            long diff = currentPact.getEndDate().getTime() - today.getTime();
            long daysLeft = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            message.setText(daysLeft + " day(s) left go!");
        }
        stakes.setText("The winner of this pact will be receiving $" + currentPact.getStakes() + " paid by the loser.");

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
