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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuNormalUserActivity extends AppCompatActivity {

    TextView usernameToolbar;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_normal_user);

        this.queue = Volley.newRequestQueue(this);

        usernameToolbar = findViewById(R.id.usernameToolbar);

        SharedPreferences prefs;
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        try {
            JSONObject userInfo = new JSONObject(prefs.getString("UserInfo", null));
            String user = userInfo.getString("givenName") + " - " + userInfo.getString("role");
            usernameToolbar.setText(user);
            if(userInfo.getString("role").equals("Profesor")){
                LinearLayout layoutButton = findViewById(R.id.layoutListarAsociados);
                layoutButton.setVisibility(LinearLayout.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkForStudents(View view){
        SharedPreferences prefs;
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        String userName = prefs.getString("DNI",null);
        String url = ConfigConstants.getInstance().getAPI_URL() + "Student/GetByDNI?dni=1001";
        JSONObject loginData = null;
        try {
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization =  "Bearer " + loginData.getString("access_token");
            Log.d("Authorization: ",authorization);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
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



}
