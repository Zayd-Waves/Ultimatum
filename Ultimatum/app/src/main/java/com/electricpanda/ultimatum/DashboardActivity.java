package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.AppConstants;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private Button newPactButton;
    private static ArrayList<Pact> pactList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Context mContext;

    private RecyclerView.LayoutManager mLayoutManager;
    private TextView emptyView;

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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        emptyView = (TextView)findViewById(R.id.empty_view);
        refreshEmptyView();

    }

    private void createPact() {
        Intent intent = new Intent(this, NewPactActivity.class);
        startActivityForResult(intent, AppConstants.NEW_PACT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.NEW_PACT_ACTIVITY_REQUEST_CODE) {
            pactList = PreferencesManager.loadPacts(mContext);
            refreshEmptyView();
        }
        /* Would add multiple else-if statements if there are multiple requests. */
    }

    private void refreshEmptyView() {
        if (pactList.isEmpty()) {
            emptyView.setText("No pacts! Click the button below to create one.");
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}
