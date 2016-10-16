package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.AppConstants;
import com.electricpanda.ultimatum.misc.NetworkManager;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PactActivity extends AppCompatActivity {

    private Pact currentPact;
    private Context mContext;
    private TextView
            habit,
            length,
            stakes,
            message,
            todayText,
            friendText,
            myEntryButton1,
            myEntryButton2,
            friendEntryButton1,
            friendEntryButton2;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact);

        mContext = this;

        habit  = (TextView) findViewById(R.id.habitText);
        length = (TextView) findViewById(R.id.lengthText);
        stakes = (TextView) findViewById(R.id.stakesText);
        message = (TextView) findViewById(R.id.messageText);
        todayText = (TextView) findViewById(R.id.todayText);
        friendText = (TextView) findViewById(R.id.friendText);
        myEntryButton1 = (TextView) findViewById(R.id.minimal_button);
        currentPact = (Pact) getIntent().getSerializableExtra("pact");
        habit.setText("The \"" + currentPact.getHabit() + "\" Pact");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        long diff = currentPact.getEndDate().getTime() - today.getTime();
        long daysLeft = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        long diff2 = today.getTime() - currentPact.getStartDate().getTime();
        final long dayNumber = TimeUnit.DAYS.convert(diff2, TimeUnit.MILLISECONDS);
        myEntryButton2 = (TextView) findViewById(R.id.minimal_button2);
        friendEntryButton1 = (TextView) findViewById(R.id.minimal_button3);
        friendEntryButton2 = (TextView) findViewById(R.id.minimal_button4);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        myEntryButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] temp = currentPact.getFirstEntry();
                temp[(int)dayNumber] = "pending";
                currentPact.setFirstEntry(temp);
                todayText.setText("Wait for your friend to verify!");
                myEntryButton1.setVisibility(View.GONE);
                myEntryButton2.setVisibility(View.GONE);
                NetworkManager.updatePacts(currentPact, mContext, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });
        myEntryButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] temp = currentPact.getFirstEntry();
                temp[(int)dayNumber] = "failure";
                currentPact.setFirstEntry(temp);
                todayText.setText("Thanks for being so honest :)!");
                myEntryButton1.setVisibility(View.GONE);
                myEntryButton2.setVisibility(View.GONE);
                NetworkManager.updatePacts(currentPact, mContext, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });
        friendEntryButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] temp = currentPact.getSecondEntry();
                temp[(int)dayNumber] = "success";
                currentPact.setSecondEntry(temp);
                friendText.setText("Thanks for being so honest :)!");
                friendEntryButton1.setVisibility(View.GONE);
                friendEntryButton2.setVisibility(View.GONE);
                NetworkManager.updatePacts(currentPact, mContext, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });
        friendEntryButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] temp = currentPact.getSecondEntry();
                temp[(int)dayNumber] = "fail";
                currentPact.setSecondEntry(temp);
                friendText.setText("Thanks for being so honest :)!");
                friendEntryButton1.setVisibility(View.GONE);
                friendEntryButton2.setVisibility(View.GONE);
                NetworkManager.updatePacts(currentPact, mContext, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        if (currentPact.getEndDate().before(today)) {
            message.setText("Woah! It looks like this pact is already done! Why not try starting another one?");
            stakes.setVisibility(View.GONE);
            todayText.setVisibility(View.GONE);
            friendText.setVisibility(View.GONE);
            myEntryButton1.setVisibility(View.GONE);
            myEntryButton2.setVisibility(View.GONE);
            friendEntryButton1.setVisibility(View.GONE);
            friendEntryButton2.setVisibility(View.GONE);
        } else if (today.before(currentPact.getStartDate())) {
            message.setText("This pact hasn't started yet!");
            todayText.setVisibility(View.GONE);
            friendText.setVisibility(View.GONE);
            myEntryButton1.setVisibility(View.GONE);
            myEntryButton2.setVisibility(View.GONE);
            friendEntryButton1.setVisibility(View.GONE);
            friendEntryButton2.setVisibility(View.GONE);
        } else {

            message.setText(daysLeft + " day(s) left go!");
            stakes.setText("The winner of this pact will be receiving $" + currentPact.getStakes() + " paid by the loser.");


            String todayStatusOneself =  currentPact.getFirstEntry()[(int)dayNumber];
            String todayStatusPartner = currentPact.getSecondEntry()[(int)dayNumber];

            if (todayStatusOneself.equals("empty")) {
                todayText.setText("How did you do today? Did you follow the pact?");
            } else if (todayStatusOneself.equals("pending")) {
                todayText.setText("Wait for your friend to verify!");
                myEntryButton1.setVisibility(View.GONE);
                myEntryButton2.setVisibility(View.GONE);
            }  else if (todayStatusOneself.equals("success")) {
                todayText.setText("You did great today! Can't wait for tomorrow :)");
                myEntryButton1.setVisibility(View.GONE);
                myEntryButton2.setVisibility(View.GONE);
            } else if (todayStatusOneself.equals("fail")) {
                todayText.setText("Darn, try again tomorrow? :(");
                myEntryButton1.setVisibility(View.GONE);
                myEntryButton2.setVisibility(View.GONE);
            } else {
                todayText.setText("Oh this shouldn't happen.");
                myEntryButton1.setVisibility(View.GONE);
                myEntryButton2.setVisibility(View.GONE);
            }

            if (todayStatusPartner.equals("pending")) {
                friendText.setText("How did your partner do?");
            } else if (todayStatusPartner.equals("success")) {
                friendText.setText("Your friend succeeded! Hooray!");
                friendEntryButton1.setVisibility(View.GONE);
                friendEntryButton2.setVisibility(View.GONE);
            } else if (todayStatusPartner.equals("fail")) {
                friendText.setText("Your friend failed today :(");
                friendEntryButton1.setVisibility(View.GONE);
                friendEntryButton2.setVisibility(View.GONE);
            } else if (todayStatusPartner.equals("empty")) {
                friendText.setText("Looks like your friend hasn't answered yet...");
                friendEntryButton1.setVisibility(View.GONE);
                friendEntryButton2.setVisibility(View.GONE);
            } else {
                friendEntryButton1.setVisibility(View.GONE);
                friendEntryButton2.setVisibility(View.GONE);
            }
        }

    }

    public void goToPactDetails() {
        Intent intent = new Intent(this, PactDetailsActivity.class);
        intent.putExtra("pact", currentPact);
        startActivityForResult(intent, AppConstants.PACT_DETAILS_REQUEST_CODE);
    }
}
