package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.programathon.app_programathon.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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




}
