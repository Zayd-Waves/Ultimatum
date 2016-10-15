package com.electricpanda.ultimatum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    Button newPactButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        newPactButton = (Button)findViewById(R.id.newPactButton);
        newPactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPact();
            }
        });
    }

    private void createPact() {
        Intent intent = new Intent(this, NewPactActivity.class);
        startActivity(intent);
    }
}
