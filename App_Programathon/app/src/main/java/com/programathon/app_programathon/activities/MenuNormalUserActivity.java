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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;
import com.programathon.app_programathon.model.TestCalculator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuNormalUserActivity extends AppCompatActivity {

    TextView usernameToolbar;
    private RequestQueue queue;
    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_normal_user);

        if(!flag) {
            flag = true;
            TestCalculator.getInstance().loadData(this);
        }

        this.queue = Volley.newRequestQueue(this);

        usernameToolbar = findViewById(R.id.usernameToolbar);

        SharedPreferences prefs;
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        try {
            JSONObject userInfo = new JSONObject(prefs.getString("UserInfo", null));
            String user = userInfo.getString("givenName") + " - " + userInfo.getString("role");
            usernameToolbar.setText(user);
            if(userInfo.getString("role").equals("Profesor")) {
                LinearLayout layoutButton = findViewById(R.id.Login_btnListarAsociados);
                layoutButton.setClickable(true);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void checkForStudents(View view){
        SharedPreferences prefs;
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        final String userName = prefs.getString("DNI",null);
        String url = ConfigConstants.getInstance().getAPI_URL() + "Student/GetMyStudents";
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

                            Intent i = new Intent(getBaseContext(), ListaEstudiantesActivity.class);
                            i.putExtra("myStudents", response.toString());
                            startActivity(i);

                            Log.d("Response", response.toString());

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

    private void makeLoginRequest(String testId) throws JSONException {
        String url = ConfigConstants.getInstance().getAPI_URL() + "FormPlan/GetByFormId?formHeaderId="+ testId;
        SharedPreferences prefs;
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);


        JSONObject loginData = null;

            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("Response", response.toString());

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

                            default:
                                msg += "desconocido";
                                break;
                        }
                        Log.d("msg:", msg);
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

        Log.d("PostRequest", postRequest.toString());
        queue.add(postRequest);
    }

    public void salirMensaje(View view){
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
        builder.setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("Aceptar", dialogInterface)
                .setNegativeButton("Cancelar", dialogInterface).show();
    }

    public void prueba(View view){
        TestCalculator calculator = TestCalculator.getInstance();
        String form = calculator.calculateDatesDiff("2016-12-30",null);
        Log.d("Formulario", form);
    }

}
