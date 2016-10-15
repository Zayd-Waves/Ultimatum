package com.electricpanda.ultimatum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewPactActivity extends AppCompatActivity {

    Button createPactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pact);

        createPactButton = (Button)findViewById(R.id.createPactButton);
        createPactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPact();
            }
        });
    }

    private void createPact() {
        /* Pact creation logice here. */
        finish();
    }
}
