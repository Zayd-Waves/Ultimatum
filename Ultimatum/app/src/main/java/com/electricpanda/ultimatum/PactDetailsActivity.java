package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.electricpanda.ultimatum.entities.Pact;

public class PactDetailsActivity extends AppCompatActivity {

    private Pact currentPact;
    private Context mContext;
    private TextView title, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact_details);

        mContext = this;
        currentPact = (Pact)getIntent().getSerializableExtra("pact");

        title = (TextView) findViewById(R.id.title);
        number = (TextView) findViewById(R.id.number);
        title.setText(currentPact.getName());
        number.setText(currentPact.getDays() + "");

    }
}
