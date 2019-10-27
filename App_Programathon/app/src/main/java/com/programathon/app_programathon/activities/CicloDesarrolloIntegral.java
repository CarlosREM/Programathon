package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class CicloDesarrolloIntegral extends AppCompatActivity {

    JSONObject studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_desarrollo_integral);


        TextView txtName = findViewById(R.id.CicloDesarrollo_txtName),
                 txtGender = findViewById(R.id.CicloDesarrollo_txtGender),
                 txtDob = findViewById(R.id.CicloDesarrollo_txtDob),
                 txtEarlyBirth = findViewById(R.id.CicloDesarrollo_txtEarlyBirth),
                 txtJoinDate = findViewById(R.id.CicloDesarrollo_txtJoinDate),
                 txtStatus = findViewById(R.id.CicloDesarrollo_txtStatus),
                 txtYear = findViewById(R.id.CicloDesarrollo_txtYear),
                 txtSection = findViewById(R.id.CicloDesarrollo_txtSection);

        Intent intent = getIntent();
        try {
            studentInfo = new JSONObject(intent.getStringExtra("studentInfo"));

            String name = studentInfo.getString("firstName") + " " + studentInfo.getString("lastName");
            txtName.setText(name);
            txtGender.setText(studentInfo.getString("gender"));
            txtDob.setText(studentInfo.getString("dob").split("T")[0]);
            txtEarlyBirth.setText(studentInfo.getString("earlyBirthAmount"));
            txtJoinDate.setText(studentInfo.getString("joinDate").split("T")[0]);
            txtStatus.setText(studentInfo.getString("status"));

            JSONObject classroomInfo = studentInfo.getJSONObject("classRoom");

            txtYear.setText(classroomInfo.getString("classYear"));
            txtSection.setText(classroomInfo.getString("section"));



        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void startRegistrarResultadosActivity(View view) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("student info: ",studentInfo.toString());
        Intent i = new Intent(getBaseContext(), RegistrarResultadosActivity.class);
        i.putExtra("studentInfo", studentInfo.toString());
        startActivity(i);
    }
}
