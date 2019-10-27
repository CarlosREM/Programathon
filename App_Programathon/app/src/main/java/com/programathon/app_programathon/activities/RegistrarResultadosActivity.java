package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;
import com.programathon.app_programathon.model.ASQ3;
import com.programathon.app_programathon.model.TestCalculator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarResultadosActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private RequestQueue queue;

    JSONObject studentInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_resultados);
        this.queue = Volley.newRequestQueue(this);

        Intent i = getIntent();

        try {
             studentInfo = new JSONObject(i.getStringExtra("studentInfo"));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void changeDot(){
        //indicator.setSelectedItem(1, true);
    }

    public void onClick(View view){

        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        final String userName = prefs.getString("DNI",null);
        String url = ConfigConstants.getInstance().getAPI_URL() + "Attendance/GetAllAttendances";
        JSONObject loginData = null;
        try {
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");
            Log.d("Authorization: ",authorization);
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Response", response.toString());

                            int length = response.length();
                            length++;
                            addAttendance(length);
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
                                Toast.makeText(getApplicationContext(), "El profesor no tiene estudiantes asociados.", Toast.LENGTH_SHORT).show();                                Log.d("Error.Response", "Null Network Response");
                                Log.d("Error.Response", error.toString());
                                return;
                            }
                            Log.d("Error.Response", error.toString());

                            String msg = "Error: ";
                            assert getCurrentFocus() != null;
                            switch(error.networkResponse.statusCode) {
                                default:
                                    msg += "desconocido";
                                    break;
                            }
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> headers=new HashMap<>();
                    headers.put("accept","application/json");
                    headers.put("Authorization",authorization);
                    return headers;
                }
            };
            Log.d("GetRequest", getRequest.toString());
            queue.add(getRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addAttendance(final int attendanceId){

        JSONObject loginData = null;
        try {
            final JSONObject userInfo = new JSONObject(prefs.getString("UserInfo", null));
            String uid = userInfo.getString("uid");
            String url = ConfigConstants.getInstance().getAPI_URL() + "Attendance/AddAttendance";
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization  = loginData.getString("token_type") + " " + loginData.getString("access_token");


            JSONObject postparams = new JSONObject();
            postparams.put("id", attendanceId);
            String date = findViewById(R.id.editText2).toString();
            postparams.put("date", date);
            ASQ3 form = TestCalculator.getInstance().calculateForm(studentInfo.getString("dob"),null);
            postparams.put("formId", form.getId());
            postparams.put("studentId", studentInfo.getString("id"));
            postparams.put("applicatorId", uid);
            postparams.put("status", "Active");
            JSONObject formParam = new JSONObject();
            formParam.put("id", form.getId());
            formParam.put("name", form.getName());
            formParam.put("instructions", null);
            formParam.put("status", "Active");
            formParam.put("minAgeMonths", form.getMinMonths());
            formParam.put("minAgeDays", form.getMinDays());
            formParam.put("maxAgeMonths", form.getMaxMonths());
            formParam.put("maxAgeDays", form.getMaxDays());
            postparams.put("form", formParam);


            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            addResult(attendanceId);
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
                                Toast.makeText(getApplicationContext(), "El profesor no tiene estudiantes asociados.", Toast.LENGTH_SHORT).show();                                Log.d("Error.Response", "Null Network Response");
                                Log.d("Error.Response", error.toString());
                                return;
                            }
                            Log.d("Error.Response", error.toString());

                            String msg = "Error: ";
                            assert getCurrentFocus() != null;
                            switch(error.networkResponse.statusCode) {
                                default:
                                    msg += "desconocido";
                                    break;
                            }
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> headers=new HashMap<>();
                    headers.put("accept","application/json");
                    headers.put("Content-Type","application/json");
                    headers.put("Authorization",authorization);
                    return headers;
                }
            };

            Log.d("PostRequest", postRequest.toString());
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void addResult(int attendanceId){

        JSONObject loginData = null;
        try {
            String url = ConfigConstants.getInstance().getAPI_URL() + "Result/AddResults";
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");

            JSONObject postparams = new JSONObject();
            postparams.put("attendanceId", attendanceId);
            JSONArray resultList = new JSONArray();
            for(int i = 0; i<5; i++){
                JSONObject resultListSub = new JSONObject();
                resultListSub.put("areadId", i);
                JSONArray result = new JSONArray();

                for(int j = 0; j<6; j++) {
                    JSONObject resultSub = new JSONObject();
                    resultSub.put("id", j);
                    resultSub.put("index", i);
                    //resultSub.put("value", );
                    result.put(resultSub);
                }
                resultListSub.put("results", result);
                resultList.put(resultListSub);
            }
            postparams.put("resultList", resultList);


            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response", response.toString());
                            finish();
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
                                Toast.makeText(getApplicationContext(), "El profesor no tiene estudiantes asociados.", Toast.LENGTH_SHORT).show();
                                Log.d("Error.Response", "Null Network Response");
                                Log.d("Error.Response", error.toString());
                                return;
                            }
                            Log.d("Error.Response", error.toString());

                            String msg = "Error: ";
                            assert getCurrentFocus() != null;
                            switch(error.networkResponse.statusCode) {
                                default:
                                    msg += "desconocido";
                                    break;
                            }
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> headers=new HashMap<>();
                    headers.put("accept","application/json");
                    headers.put("Content-Type","application/json");
                    headers.put("Authorization",authorization);
                    return headers;
                }
            };

            Log.d("PostRequest", postRequest.toString());
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
