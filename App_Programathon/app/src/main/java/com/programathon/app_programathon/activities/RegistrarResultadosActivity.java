package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.model.formArea;
import com.programathon.app_programathon.view.TestArea;
import com.programathon.app_programathon.globalconfig.ConfigConstants;
import com.programathon.app_programathon.model.ASQ3;
import com.programathon.app_programathon.model.TestCalculator;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarResultadosActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private RequestQueue queue;
    private String newAttendanceId;

    JSONObject studentInfo;

    private TextView txtForm;
    private ArrayList<TestArea> testAreaArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_resultados);

        txtForm = findViewById(R.id.RegistrarResults_txtForm);
        this.queue = Volley.newRequestQueue(this);

        Intent i = getIntent();

        try {
             studentInfo = new JSONObject(i.getStringExtra("studentInfo"));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBtnConsultarClick(View view) {
        try {
            String dob = studentInfo.getString("dob").split("T")[0];
            ASQ3 form = TestCalculator.getInstance().calculateForm(dob, null);
            txtForm.setText(form.getName());
            List<formArea> formAreaArray = form.getAreas();
            LinearLayout layoutAreas = findViewById(R.id.RegistrarResults_layoutAreas);
            TestArea testArea;
            Log.d("formAreaArray length", String.valueOf(formAreaArray.size()));
            for (formArea area : formAreaArray) {
                testArea = new TestArea(RegistrarResultadosActivity.this, area.getName(), area.getMinValue(), area.getMaxValue());
                testAreaArray.add(testArea);
                layoutAreas.addView(testArea);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBtnGuardarClick(View view){

        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        final String userName = prefs.getString("DNI",null);
        String url = ConfigConstants.getInstance().getAPI_URL() + "Attendance/AddAttendance";
        JSONObject loginData = null;



        try {
            final JSONObject userInfo = new JSONObject(prefs.getString("UserInfo", null));
            final int uid = userInfo.getInt("uid");
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");
            Log.d("Authorization: ",authorization);

            JSONObject postparams = new JSONObject();

            String date = new DateTime().toString();
            postparams.put("date", new LocalDate().toString());
            ASQ3 form = TestCalculator.getInstance().calculateForm(studentInfo.getString("dob").split("T")[0],null);
            postparams.put("formId", form.getId()+"");
            postparams.put("studentId", studentInfo.getString("id")+"");
            postparams.put("applicatorId", uid +"");
            postparams.put("status", "Active");
            JSONObject formParam = new JSONObject();
            formParam.put("id", studentInfo.getString("id")+"");
            formParam.put("name", form.getName());
            formParam.put("instructions", "string");
            formParam.put("status", "Active");
            formParam.put("minAgeMonths", form.getMinMonths()+"");
            formParam.put("minAgeDays", form.getMinDays()+"");
            formParam.put("maxAgeMonths", form.getMaxMonths()+"");
            formParam.put("maxAgeDays", form.getMaxDays()+"");
            postparams.put("form", formParam);

            final String mRequestBody = postparams.toString();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegistrarResultadosActivity.this, "Attendance ingresado: " + response, Toast.LENGTH_SHORT).show();
                            newAttendanceId = response;
                            addResult(response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegistrarResultadosActivity.this, "Error ingresando Attend", Toast.LENGTH_SHORT).show();
                            if(error.networkResponse.statusCode == 400)
                                Toast.makeText(RegistrarResultadosActivity.this, "Registro ya existente", Toast.LENGTH_SHORT).show();


                            Log.d("pip" , "Error: " + error
                                    + "\nStatus Code " + error.networkResponse.statusCode
                                    + "\nResponse Data " + error.networkResponse.data
                                    + "\nCause " + error.getCause()
                                    + "\nmessage" + error.getMessage());
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

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            Log.d("PostRequest", postRequest.toString());
            Log.d("PostRequest", new String(postRequest.getBody()));
            Log.d("PostRequest", postRequest.getHeaders().toString());
            Log.d("PostRequest", postRequest.getBodyContentType());
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }


    public void addResult(String attendanceId){
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        final String userName = prefs.getString("DNI",null);
        String url = ConfigConstants.getInstance().getAPI_URL() + "Result/AddResults";
        JSONObject loginData = null;



        try {
            final JSONObject userInfo = new JSONObject(prefs.getString("UserInfo", null));
            final int uid = userInfo.getInt("uid");
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");
            Log.d("Authorization: ",authorization);

            JSONObject postparams = new JSONObject();
            postparams.put("attendanceId", attendanceId);
            JSONArray resultList = new JSONArray();
            for(int i = 1; i<=5; i++){
                JSONObject resultListSub = new JSONObject();
                resultListSub.put("areaId", i);
                JSONArray result = new JSONArray();
                TestArea testArea = testAreaArray.get(i-1);
                for(int j = 1; j<=6; j++) {
                    JSONObject resultSub = new JSONObject();
                    resultSub.put("index", j);
                    resultSub.put("value", testArea.getRespuestas(j));
                    result.put(resultSub);
                }
                resultListSub.put("results", result);
                resultList.put(resultListSub);
            }
            postparams.put("resultList", resultList);

            final String mRequestBody = postparams.toString();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegistrarResultadosActivity.this, "Resultados ingresados: " + response, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegistrarResultadosActivity.this, "Error ingresando Resultados", Toast.LENGTH_SHORT).show();
                            if(error.networkResponse.statusCode == 400)
                                Toast.makeText(RegistrarResultadosActivity.this, "Registro ya existente", Toast.LENGTH_SHORT).show();

                            Log.d("pip" , "Error: " + error
                                    + "\nStatus Code " + error.networkResponse.statusCode
                                    + "\nResponse Data " + error.networkResponse.data
                                    + "\nCause " + error.getCause()
                                    + "\nmessage" + error.getMessage());
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

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            Log.d("PostRequest", postRequest.toString());
            Log.d("PostRequest", new String(postRequest.getBody()));
            Log.d("PostRequest", postRequest.getHeaders().toString());
            Log.d("PostRequest", postRequest.getBodyContentType());
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

    }

    public void onBtnCancelarClick(View view) {
        DialogInterface.OnClickListener dialogInterface = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;

                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de la ventana? Perdera cualquier dato no guardado")
                .setPositiveButton("Salir", dialogInterface)
                .setNegativeButton("Cancelar", dialogInterface).show();
    }


}
