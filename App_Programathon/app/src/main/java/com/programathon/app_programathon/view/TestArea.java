package com.programathon.app_programathon.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.programathon.app_programathon.R;
import com.programathon.app_programathon.model.TestAreaModel;

public class TestArea extends ConstraintLayout {

    private Spinner spnRespuesta1,
                    spnRespuesta2,
                    spnRespuesta3,
                    spnRespuesta4,
                    spnRespuesta5,
                    spnRespuesta6;
    private TextView txtTotal;
    private ImageView imgState;

    private int minValue, maxValue;
    private boolean verde = false;


    public TestArea(Context context, String title, String minValue, String maxValue) {
        super(context);
        this.minValue = (int) Float.parseFloat(minValue);
        this.maxValue = (int) Float.parseFloat(maxValue);

        init(context, title);

    }

    private void init(Context context, String title) {


        View v = LayoutInflater.from(context).inflate(R.layout.test_area_layout, this);
        TextView txtTitle = v.findViewById(R.id.TestArea_txtTitulo);
        txtTitle.setText(title);

        spnRespuesta1 = v.findViewById(R.id.TestArea_spnRespuesta1);
        spnRespuesta2 = v.findViewById(R.id.TestArea_spnRespuesta2);
        spnRespuesta3 = v.findViewById(R.id.TestArea_spnRespuesta3);
        spnRespuesta4 = v.findViewById(R.id.TestArea_spnRespuesta4);
        spnRespuesta5 = v.findViewById(R.id.TestArea_spnRespuesta5);
        spnRespuesta6 = v.findViewById(R.id.TestArea_spnRespuesta6);
        txtTotal = v.findViewById(R.id.TestArea_total);
        imgState = v.findViewById(R.id.TestArea_imgState);

        setupSpinnerListeners();
    }

    private void setupSpinnerListeners() {
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        };

        spnRespuesta1.setOnItemSelectedListener(listener);
        spnRespuesta2.setOnItemSelectedListener(listener);
        spnRespuesta3.setOnItemSelectedListener(listener);
        spnRespuesta4.setOnItemSelectedListener(listener);
        spnRespuesta5.setOnItemSelectedListener(listener);
        spnRespuesta6.setOnItemSelectedListener(listener);
    }

    private void updateTotal() {
        int total = 0;
        if (!spnRespuesta1.getSelectedItem().equals("."))
            total += Integer.parseInt((String) spnRespuesta1.getSelectedItem());

        if (!spnRespuesta2.getSelectedItem().equals("."))
            total += Integer.parseInt((String) spnRespuesta2.getSelectedItem());

        if (!spnRespuesta3.getSelectedItem().equals("."))
            total += Integer.parseInt((String) spnRespuesta3.getSelectedItem());

        if (!spnRespuesta4.getSelectedItem().equals("."))
            total += Integer.parseInt((String) spnRespuesta4.getSelectedItem());

        if (!spnRespuesta5.getSelectedItem().equals("."))
            total += Integer.parseInt((String) spnRespuesta5.getSelectedItem());

        if (!spnRespuesta6.getSelectedItem().equals("."))
            total += Integer.parseInt((String) spnRespuesta6.getSelectedItem());


        txtTotal.setText(String.valueOf(total));

        int colorID = getStateIconColorID(total);

        imgState.setColorFilter(ContextCompat.getColor(getContext(), colorID), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private int getStateIconColorID(int totalValue) {
        verde = false;
        int stateIconColor = R.color.testIconYellow;
        if (totalValue < minValue)
            stateIconColor = R.color.testIconRed;
        else if (maxValue < totalValue) {
            stateIconColor = R.color.testIconGreen;
            verde = true;
        }
        return stateIconColor;
    }

    public boolean checkEmpty() {
        return spnRespuesta1.getSelectedItem().equals(".") |
                spnRespuesta2.getSelectedItem().equals(".") |
                spnRespuesta3.getSelectedItem().equals(".") |
                spnRespuesta4.getSelectedItem().equals(".") |
                spnRespuesta5.getSelectedItem().equals(".") |
                spnRespuesta6.getSelectedItem().equals(".");
    }

    public String getRespuestas(int num) {
        String respuesta = "";
        switch(num) {
            case 1:
                respuesta = (String) spnRespuesta1.getSelectedItem();
                break;
            case 2:
                respuesta = (String) spnRespuesta2.getSelectedItem();
                break;
            case 3:
                respuesta = (String) spnRespuesta3.getSelectedItem();
                break;
            case 4:
                respuesta = (String) spnRespuesta4.getSelectedItem();
                break;
            case 5:
                respuesta = (String) spnRespuesta5.getSelectedItem();
                break;
            case 6:
                respuesta = (String) spnRespuesta6.getSelectedItem();
                break;
        }
        return respuesta;
    }

    public String getTotal() {
        return txtTotal.getText().toString();
    }

    public boolean isVerde() {
        return verde;
    }

    public TestAreaModel getModel() {
        String name = ((TextView) findViewById(R.id.TestArea_txtTitulo)).getText().toString();
        int respuesta1 = Integer.parseInt(getRespuestas(1)),
                respuesta2 = Integer.parseInt(getRespuestas(2)),
                respuesta3 = Integer.parseInt(getRespuestas(3)),
                respuesta4 = Integer.parseInt(getRespuestas(4)),
                respuesta5 = Integer.parseInt(getRespuestas(5)),
                respuesta6 = Integer.parseInt(getRespuestas(6)),
                total = Integer.parseInt(getTotal());

        return new TestAreaModel(name,
                                 respuesta1,
                                 respuesta2,
                                 respuesta3,
                                 respuesta4,
                                 respuesta5,
                                 respuesta6,
                                 total,
                                 verde);
    }


}
