package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.electricpanda.ultimatum.adapters.PactRecyclerViewAdapter;
import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.interfaces.PactListInteractionListener;
import com.electricpanda.ultimatum.misc.AppConstants;
import com.electricpanda.ultimatum.misc.AppUtils;
import com.electricpanda.ultimatum.misc.NetworkManager;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements PactListInteractionListener {

    private TextView newPactButton;
    private static ArrayList<Pact> pactList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private Context mContext;

    private RecyclerView.LayoutManager mLayoutManager;
    private TextView emptyView;
    private ScrollView emptyViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = this;
        newPactButton = (TextView) findViewById(R.id.minimal_button);
        newPactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPact();
            }
        });

        pactList = PreferencesManager.loadPacts(mContext);

        mAdapter = new PactRecyclerViewAdapter(pactList, mContext, this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        emptyView = (TextView)findViewById(R.id.empty_view);
        emptyViewContainer = (ScrollView)findViewById(R.id.empty);
        refreshEmptyView();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPacts();
            }
        });
    }

    private void fetchPacts() {
        NetworkManager.getPacts(mContext, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject pact = new JSONObject();
                String[] arr = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        pact = (JSONObject)response.get(i);
                        JSONArray arrJson = pact.getJSONArray("users");
                        Toast.makeText(mContext, arrJson + "", Toast.LENGTH_SHORT).show();
                        arr = new String[arrJson.length()];
                        for(int j = 0; j<arrJson.length(); j++)
                            arr[j] = arrJson.getString(j);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Pact p = new Pact(  pact.optString("habit"),
                            AppUtils.convertStringToDate(pact.optString("start")),
                            AppUtils.convertStringToDate(pact.optString("end")),
                            pact.optInt("length"),
                            pact.optInt("stakes"));
                    p.setUsers(arr);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Network error.", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void createPact() {
        Intent intent = new Intent(this, NewPactActivity.class);
        startActivityForResult(intent, AppConstants.NEW_PACT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.NEW_PACT_ACTIVITY_REQUEST_CODE) {
            pactList = PreferencesManager.loadPacts(mContext);
            mAdapter = new PactRecyclerViewAdapter(pactList, mContext, this);
            recyclerView.setAdapter(mAdapter);

            refreshEmptyView();
        }
        /* Would add multiple else-if statements if there are multiple requests. */
    }

    private void refreshEmptyView() {
        if (pactList.isEmpty()) {
            emptyView.setText("No pacts! Click the button below to create one.");
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyViewContainer.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyViewContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPactListClick(int position) {
        Intent intent = new Intent(this, PactActivity.class);
        intent.putExtra("pact", pactList.get(position));
        startActivityForResult(intent, AppConstants.PACT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
            Handle action bar item clicks here. The action bar will
            automatically handle clicks on the Home/Up button, so long
            as you specify a parent activity in AndroidManifest.xml.
        */
        int id = item.getItemId();

        if (id == R.id.action_about) {
            sendToAboutActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendToAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}
