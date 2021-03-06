package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.programathon.app_programathon.R;
import com.programathon.app_programathon.model.EstudianteExamenListAdapter;
import com.programathon.app_programathon.model.OnRecycleItemListener;
import com.programathon.app_programathon.model.TestCalculator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaEstudiantesActivity extends AppCompatActivity implements OnRecycleItemListener {

    private EstudianteExamenListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiantes);

        Intent i = getIntent();
        try {
            JSONArray myStudents = new JSONArray(i.getStringExtra("myStudents"));
            setupRecyclerView(myStudents);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupRecyclerView(JSONArray myStudents) {
        RecyclerView recyclerView = findViewById(R.id.ListaEstudiante_recyclerView);

        try {
            ArrayList<String> testNameArray = new ArrayList<>();
            JSONObject studentInfo;
            String dob;
            for (int i = 0; i < myStudents.length(); i++) {
                studentInfo = myStudents.getJSONObject(i);
                Log.d("StudentInfo", studentInfo.toString());
                dob = studentInfo.getString("dob").split("T")[0];
                testNameArray.add(TestCalculator.getInstance().calculateDatesDiff(dob, null));
            }

            //setup adapter
            EstudianteExamenListAdapter adapter = new EstudianteExamenListAdapter(myStudents, testNameArray, this);
            this.adapter = adapter;
            recyclerView.setAdapter(adapter);
            }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void OnItemClick(int position) {
        try {
            JSONObject studentInfo = adapter.getStudentInfo(position);
            Intent i = new Intent(getBaseContext(), CicloDesarrolloIntegral.class);
            i.putExtra("studentInfo", studentInfo.toString());
            startActivity(i);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
