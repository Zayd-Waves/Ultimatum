package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.AppConstants;

import org.w3c.dom.Text;

public class PactActivity extends AppCompatActivity {

    private Pact currentPact;
    private Context mContext;
    private TextView title, number;
    private Button pactDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact);

        mContext = this;

        title = (TextView) findViewById(R.id.title);
        number = (TextView) findViewById(R.id.number);
        pactDetailsButton = (Button) findViewById(R.id.pactDetailsButton);

        currentPact = (Pact)getIntent().getSerializableExtra("pact");

        title.setText(currentPact.getName());
        number.setText(currentPact.getDays() + "");
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
