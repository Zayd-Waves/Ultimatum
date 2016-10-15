package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewPactActivity extends AppCompatActivity {

    private Button createPactButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pact);
        mContext = this;

        createPactButton = (Button)findViewById(R.id.createPactButton);
        createPactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPact();
            }
        });
    }

    private void createPact() {
        /* Pact creation logic here. */
        Pact pact1 = new Pact("Temp", 7);
        Pact pact2 = new Pact("Temp", 7);
        ArrayList<Pact> pactList = PreferencesManager.loadPacts(mContext);
        pactList.add(pact1);
        pactList.add(pact2);
        PreferencesManager.savePacts(mContext, pactList);

        finish();
    }
}
