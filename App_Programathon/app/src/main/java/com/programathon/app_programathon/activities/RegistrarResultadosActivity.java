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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarResultadosActivity extends AppCompatActivity {

    //DotIndicator indicator = findViewById(R.id.dotIndicator);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_resultados);

        Intent i = getIntent();

        try {
            JSONObject studentInfo = new JSONObject(i.getStringExtra("studentInfo"));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void changeDot(){
        //indicator.setSelectedItem(1, true);
    }

    public void addResult(View view) {

    }
/*
    public JSONArray getFormAreas(int formId) {

    }
*/
}
