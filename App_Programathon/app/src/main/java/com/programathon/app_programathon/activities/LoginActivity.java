package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;
import com.programathon.app_programathon.model.TestCalculator;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private RequestQueue queue;

    private EditText login_txtUsername;
    private EditText login_txtPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        this.queue = Volley.newRequestQueue(this);

        this.login_txtUsername = findViewById(R.id.login_txtUsername);
        this.login_txtPassword = findViewById(R.id.login_txtPassword);

        verifyUserInternetConnection();
    }

    private void verifyUserInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            Toast.makeText(getApplicationContext(), "Debe tener una conexion activa a internet.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    public void onClickBtnLogin(View view) {
        String username = login_txtUsername.getText().toString(),
                password = login_txtPassword.getText().toString();

        if (verifyLoginData()) {
            try {
                makeLoginRequest(username, password);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean verifyLoginData() {
        boolean result = true;

        assert getCurrentFocus() != null;
        if (login_txtUsername.getText().toString().isEmpty()) {
            Snackbar.make(getCurrentFocus(), "Debe llenar el campo de DNI.", Snackbar.LENGTH_SHORT).show();
            result = false;
        }
        else if (login_txtPassword.getText().toString().length() < 8) {
            Snackbar.make(getCurrentFocus(), "El campo contraseña debe tener 8 caracteres minimo.", Snackbar.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }

    private void makeLoginRequest(final String username, final String password) throws JSONException {
        String url = ConfigConstants.getInstance().getAPI_URL() + "LogIn";
        JSONObject postparams = new JSONObject();
        postparams.put("username", username);
        postparams.put("password", password);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,
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
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        Log.d("PostRequest", postRequest.toString());
        queue.add(postRequest);
    }


    /* Se consultaron los siguientes links para la creacion de este codigo:
        https://stackoverflow.com/questions/34191731/where-to-store-a-jwt-token
        https://medium.com/android-grid/android-sharedpreferences-example-writing-and-reading-values-7a677f4448c3
        https://developer.android.com/reference/android/content/SharedPreferences
        https://stackoverflow.com/questions/23024831/android-shared-preferences-example
        https://stackoverflow.com/questions/1944656/android-global-variable
        https://stackoverflow.com/questions/5140539/android-config-file
        https://androidclarified.com/android-volley-example/

     */
}
