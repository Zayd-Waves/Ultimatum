package com.electricpanda.ultimatum.misc;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

    private static final String     ping = "http://ec2-52-38-253-207.us-west-2.compute.amazonaws.com:8080/ping";
    private static final String     registerUrl = "http://ec2-52-38-253-207.us-west-2.compute.amazonaws.com:8080/users";
    private static final String     createPactUrl = "http://ec2-52-38-253-207.us-west-2.compute.amazonaws.com:8080/pacts";
    private static final String     getPactsUrl = "http://ec2-52-38-253-207.us-west-2.compute.amazonaws.com:8080/pacts";
    private static final String     pactUpdateUrl = "http://ec2-52-38-253-207.us-west-2.compute.amazonaws.com:8080/pacts";

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
                                     Response.Listener<JSONObject> response,
                                     Response.ErrorListener error ) {

        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest newRequest = new JsonObjectRequest(
                registerUrl,
                body,
                response,
                error
        );

        /* Add the request to the RequestQueue. */
        mRequestQueue.add(newRequest);
    }

    public static void createPact ( Pact newPact,
                                    Context context,
                                    Response.Listener<JSONObject> response,
                                    Response.ErrorListener error ) {

        JSONObject body = new JSONObject();

        try {
            body.put("habit", newPact.getHabit());
            body.put("start", AppUtils.convertDateToString(newPact.getStartDate()));
            body.put("end", AppUtils.convertDateToString(newPact.getEndDate()));
            body.put("length", newPact.getLength());
            String array = "[" + PreferencesManager.getUsername(context) + ", " + newPact.getPartnerName() + "]";
            JSONArray jsonArray = new JSONArray(array);
            body.put("users", jsonArray);
            body.put("stakes", newPact.getStakes());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest newRequest = new JsonObjectRequest(
                createPactUrl,
                body,
                response,
                error
        );

        /* Add the request to the RequestQueue. */
        mRequestQueue.add(newRequest);
    }

    public static void getPacts(Context context,
                                Response.Listener<JSONArray> response,
                                Response.ErrorListener error ) {

        CustomRequest newRequest = new CustomRequest (
                Request.Method.GET,
                getPactsUrl + "/" + PreferencesManager.getUsername(context),
                response,
                error
        );
        mRequestQueue.add(newRequest);

    }

    public static void updatePacts(Pact updatedPact,
                                   Context context,
                                   Response.Listener<JSONObject> response,
                                   Response.ErrorListener errorListener ) {

        JSONObject body = new JSONObject();
        try {
            body.put("habit", updatedPact.getHabit());
            body.put("start", AppUtils.convertDateToString(updatedPact.getStartDate()));
            body.put("end", AppUtils.convertDateToString(updatedPact.getEndDate()));
            body.put("length", updatedPact.getLength());
            String array = "[" + updatedPact.getUsers().get(0) + ", " + updatedPact.getUsers().get(1) + "]";
            JSONArray jsonArray = new JSONArray(array);
            body.put("users", jsonArray);
            body.put("stakes", updatedPact.getStakes());
            body.put("createDate", updatedPact.getCreationDate());

            String allEntries = "[{\"username\": \"" + updatedPact.getFirstEntryUsername() + "\", \"entries\": [";
            for (int i = 0; i < updatedPact.getFirstEntry().length; i++) {
                int j = i;
                allEntries += "\"" + updatedPact.getFirstEntry()[i] + "\"";
                if (j++ == updatedPact.getFirstEntry().length) {
                    break;
                }
                allEntries += ",";
            }
            allEntries = allEntries.substring(0, allEntries.length()-1);
            allEntries += "]}, {\"username\": \"" + updatedPact.getSecondEntryUsername() + "\", \"entries\": [";
            for (int i = 0; i < updatedPact.getSecondEntry().length; i++) {
                int j = i;
                allEntries += "\"" + updatedPact.getSecondEntry()[i] + "\"";
                if (j++ == updatedPact.getSecondEntry().length) {
                    break;
                }
                allEntries += ",";
            }
            allEntries = allEntries.substring(0, allEntries.length()-1);
            allEntries += "]}]";

            Log.d("HELLO", allEntries);
            JSONArray jsonArray1 = new JSONArray(allEntries);
            body.put("allEntries", jsonArray1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("HELLO",  body + "");
        JsonObjectRequest newRequest = new JsonObjectRequest(
                Request.Method.PUT,
                pactUpdateUrl + "/" + updatedPact.getId(),
                body,
                response,
                errorListener
        );

        /* Add the request to the RequestQueue. */
        mRequestQueue.add(newRequest);
    }

}

