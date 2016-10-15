package com.electricpanda.ultimatum;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.electricpanda.ultimatum.misc.NetworkManager;
import com.electricpanda.ultimatum.misc.PreferencesManager;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private Button startButton;
    private Context mContext;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        usernameField = (EditText)findViewById(R.id.usernameField);
        username = usernameField.getText().toString();
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerOrLoginUser(username);
            }
        });
    }

    private void registerOrLoginUser(final String username) {
        NetworkManager.registerUser(username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PreferencesManager.setUsername(mContext, username);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Network error. Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
