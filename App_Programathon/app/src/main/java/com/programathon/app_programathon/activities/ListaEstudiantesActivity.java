package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.programathon.app_programathon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaEstudiantesActivity extends AppCompatActivity {

    private TextView header1,
                     header2;
    private RecyclerView recyclerView;

    private JSONArray listStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiantes);

        header1 = findViewById(R.id.ListaEstudiantes_header1);
        header2 = findViewById(R.id.ListaEstudiantes_header2);
        recyclerView = findViewById(R.id.ListaEstudiante_recyclerView);

        header1.setText("Nombre");
        header2.setText("ASQ-3");

        Intent i = getIntent();
        try {
            listStudents = new JSONArray(i.getStringExtra("myStudents"));

            ArrayList<String> studentNames = getStudentNames(listStudents);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getStudentNames(JSONArray studentList) {

        ArrayList<String> studentNames = new ArrayList<>();

        try {
            JSONObject student;
            for (int i = 0; i < studentList.length(); i++) {
                student = studentList.getJSONObject(i);
                studentNames.add(student.get("firstName") + " " + student.get("lastName"));
            }
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }

        return studentNames;
    }
}
