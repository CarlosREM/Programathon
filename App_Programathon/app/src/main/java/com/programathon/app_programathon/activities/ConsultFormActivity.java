package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConsultFormActivity extends AppCompatActivity {

    EditText txtId;
    Button btnConsult;
    LinearLayout layoutTabla;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_form);

        txtId = findViewById(R.id.ConsultForm_txtId);
        btnConsult = findViewById(R.id.ConsultForm_btnConsult);
        layoutTabla = findViewById(R.id.ConsultForm_layoutTabla);
        recyclerView = findViewById(R.id.ConsultForm_recyclerView);
    }

    public void onClickBtnConsult(View view) {
        String id = txtId.getText().toString();

        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        String date = df.format(currentDate);



    }
/*
    public void makeStudentByDniRequest(final int id) throws JSONException {
        SharedPreferences prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        String url = ConfigConstants.getInstance().getAPI_URL() + "Student/GetByDNI";
        JSONObject postparams = new JSONObject();
        postparams.put("dni", id);
        JSONObject loginData = null;

        try {
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization =  "Bearer " + loginData.getString("access_token");
            Log.d("Authorization: ",authorization);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url, postparams,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());

                        SharedPreferences prefs;
                        SharedPreferences.Editor edit;
                        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
                        edit=prefs.edit();
                        try {
                            JSONObject loginData = response.getJSONObject("LoginData"),
                                    userInfo = response.getJSONObject("UserInfo");
                            String saveToken = loginData.getString("access_token");
                            edit.putString("access_token", saveToken);
                            edit.putString("LoginData", loginData.toString());
                            edit.putString("UserInfo", userInfo.toString());
                            edit.putString("DNI",username);
                            edit.apply();

                            Intent i = new Intent(getBaseContext(), MenuNormalUserActivity.class);
                            startActivity(i);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error == null) {
                            Log.d("Error.Response", "Null error");
                            return;
                        }
                        else if (error.networkResponse == null) {
                            Log.d("Error.Response", "Null Network Response");
                            Log.d("Error.Response", error.toString());
                            return;
                        }
                        Log.d("Error.Response", error.toString());

                        String msg = "Error: ";
                        assert getCurrentFocus() != null;
                        switch(error.networkResponse.statusCode) {
                            case 401:
                                msg += "Usuario y/o contraseña invalidos";
                                break;
                            default:
                                msg += "desconocido";
                                break;
                        }
                        Snackbar.make(getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).show();
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers=new HashMap<>();
                headers.put("accept","application/json");
                headers.put("Authorization",);
                return headers;
            }
        };

        Log.d("PostRequest", postRequest.toString());
        queue.add(postRequest);
    }*/



}
