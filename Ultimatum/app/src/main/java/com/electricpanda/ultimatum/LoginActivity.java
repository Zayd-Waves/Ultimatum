package com.electricpanda.ultimatum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.electricpanda.ultimatum.misc.NetworkManager;
import com.electricpanda.ultimatum.misc.PreferencesManager;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private TextView startButton;
    private Context mContext;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        usernameField = (EditText)findViewById(R.id.usernameField);
        startButton = (TextView) findViewById(R.id.minimal_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                if (isUsernameValid(username)) {
                    registerOrLoginUser(username);
                } else {
                    Toast.makeText(mContext, "Please input a valid username!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerOrLoginUser(final String username) {
        NetworkManager.registerUser(username, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PreferencesManager.setUsername(mContext, username);
                Toast.makeText(mContext, "Welcome " + username, Toast.LENGTH_SHORT).show();
                goToDashboard();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Network error. Please check your internet connection and try again." + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isUsernameValid(String username) {
        boolean valid = true;
        if (username.isEmpty()) { valid = false; }
        return valid;
    }
    public void goToDashboard() {
        Intent intent = new Intent(mContext, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
