package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.entities.User;
import com.electricpanda.ultimatum.misc.NetworkManager;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewPactActivity extends AppCompatActivity {

    private TextView createPactButton;
    private Context mContext;

    private EditText habitField, stakesField, partnerField;
    private DatePicker startDate, endDate;
    private String errorMessage = "";

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
        stakesField = (EditText) findViewById(R.id.stakesField);
        partnerField = (EditText) findViewById(R.id.partnerTextField);
        startDate = (DatePicker) findViewById(R.id.startDateField);
        endDate = (DatePicker) findViewById(R.id.endDateField);
    }

    /* Pact creation logic here. */
    private void createPact() {

        if (isFormValid()) {

            /* Date. */
            int day = startDate.getDayOfMonth();
            int month = startDate.getMonth();
            int year = startDate.getYear();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Date start = calendar.getTime();

            int day2 = endDate.getDayOfMonth();
            int month2 = endDate.getMonth();
            int year2 = endDate.getYear();
            Calendar calendar2 = Calendar.getInstance();
            calendar.set(year2, month2, day2);
            Date end = calendar2.getTime();

            String habit = habitField.getText().toString();
            int length = day2 - day;
            int stakes = Integer.parseInt(stakesField.getText().toString());
            String partnerName = partnerField.getText().toString();

            Pact newPact = new Pact(
                    habit,
                    start,
                    end,
                    length,
                    stakes,
                    partnerName);

            NetworkManager.createPact(newPact, mContext, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(mContext, "Successfully created pact.", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mContext, "Network error.", Toast.LENGTH_SHORT).show();
                }
            });

            finish();
        } else {
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFormValid() {
        boolean valid = true;

        /* Date validity. */
        int day = startDate.getDayOfMonth();
        int month = startDate.getMonth();
        int year = startDate.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date start = calendar.getTime();

        int day2 = endDate.getDayOfMonth();
        int month2 = endDate.getMonth();
        int year2 = endDate.getYear();
        Calendar calendar2 = Calendar.getInstance();
        calendar.set(year2, month2, day2);
        Date end = calendar2.getTime();
        /*                   */


        if (partnerField.getText().toString().equals("")) {
            valid = false;
            errorMessage = "Must choose a partner!";
        }
        if (stakesField.getText().toString().equals("") || Integer.parseInt(stakesField.getText().toString()) == 0) {
            valid = false;
            errorMessage = "Stakes must not be zero or empty!";
        }
        if (habitField.getText().toString().equals("")) {
            valid = false;
            errorMessage = "Habit cannot be left blank!";
        }
        return valid;
    }
}
