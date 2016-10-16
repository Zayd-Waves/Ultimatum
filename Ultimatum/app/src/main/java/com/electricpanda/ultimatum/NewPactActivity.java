package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import java.util.ArrayList;
import java.util.Date;

public class NewPactActivity extends AppCompatActivity {

    private TextView createPactButton;
    private Context mContext;

    private EditText habitField;
    private DatePicker startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pact);
        mContext = this;

        createPactButton = (TextView) findViewById(R.id.minimal_button);
        createPactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPact();
            }
        });

        habitField  = (EditText) findViewById(R.id.habitField);
        startDate = (DatePicker) findViewById(R.id.startDateField);
        endDate = (DatePicker) findViewById(R.id.endDateField);
    }

    /* Pact creation logic here. */
    private void createPact() {
        Pact newPact = new Pact(
                "dsfdsf",
                habitField.getText().toString(),
                new Date(),
                new Date(),
                0,
                0);

        ArrayList<Pact> pactList = PreferencesManager.loadPacts(mContext);
        pactList.add(newPact);
        PreferencesManager.savePacts(mContext, pactList);

        finish();
    }
}
