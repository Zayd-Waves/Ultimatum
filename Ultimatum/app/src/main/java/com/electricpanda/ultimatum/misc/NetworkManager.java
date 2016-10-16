package com.electricpanda.ultimatum.misc;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

    private static final String     ping = "http://ec2-52-38-253-207.us-west-2.compute.amazonaws.com:8080/ping";
    private static final String     registerUrl = "http://ec2-52-38-253-207.us-west-2.compute.amazonaws.com:8080/users";

    private static RequestQueue mRequestQueue;
    private static Context mContext;

    public static void instantiateRequestQueue( Context applicationContext ) {
        mRequestQueue = Volley.newRequestQueue(applicationContext);
        mContext = applicationContext;
    }

    public static void ping ( Response.Listener<String> response,
                              Response.ErrorListener error ) {

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("example_parameter", "example_value");
        } catch (JSONException e) {

        }
        StringRequest newRequest = new StringRequest (
                Request.Method.GET,
                ping,
                response,
                error
        );
        mRequestQueue.add(newRequest);
    }

    public static void registerUser( final String username,
                                     Response.Listener<String> response,
                                     Response.ErrorListener error ) {
        StringRequest newRequest = new StringRequest(
                Request.Method.POST,
                registerUrl,
                response,
                error
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();

                /* The POST parameters. */
                params.put("username", username);
                return params;
            }
        };

        /* Add the request to the RequestQueue. */
        mRequestQueue.add(newRequest);
    }

}

