package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewPactActivity extends AppCompatActivity {

    private Button createPactButton;
    private Context mContext;

    private EditText nameEditText, numberEditText;

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

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        numberEditText = (EditText) findViewById(R.id.numberEditText);
    }

    private void createPact() {
        /* Pact creation logic here. */
        Pact newPact = new Pact(nameEditText.getText().toString(), Integer.parseInt(numberEditText.getText().toString()));
        ArrayList<Pact> pactList = PreferencesManager.loadPacts(mContext);
        pactList.add(newPact);
        PreferencesManager.savePacts(mContext, pactList);

        finish();
    }
}
