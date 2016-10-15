package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.electricpanda.ultimatum.adapters.PactRecyclerViewAdapter;
import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.interfaces.PactListInteractionListener;
import com.electricpanda.ultimatum.misc.AppConstants;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements PactListInteractionListener {

    private Button newPactButton;
    private static ArrayList<Pact> pactList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private Context mContext;

    private RecyclerView.LayoutManager mLayoutManager;
    private TextView emptyView;
    private ScrollView emptyViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = this;
        newPactButton = (Button)findViewById(R.id.newPactButton);
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
        //refreshEmptyView();

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

            //refreshEmptyView();
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
}
