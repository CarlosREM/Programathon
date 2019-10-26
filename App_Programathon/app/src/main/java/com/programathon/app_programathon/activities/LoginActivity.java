package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.queue = Volley.newRequestQueue(this);
    }

    public void onClickBtnLogin(View view) {
        String username = ((EditText) findViewById(R.id.login_txtUsername)).getText().toString(),
                password = ((EditText) findViewById(R.id.login_txtPassword)).getText().toString();
/*
        try {
            makeLoginRequest(username, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
/*
    public void makeLoginRequest(final String username, final String password) throws JSONException {
        String url = ConfigConstants.getInstance().getAPI_URL() + "LogIn";
        JSONObject postparams = new JSONObject();
        postparams.put("username", username);
        postparams.put("password", password);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers=new HashMap<>();
                headers.put("accept","application/json");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        Log.d("PostRequest", postRequest.toString());
        queue.add(postRequest);
    }*/

}
