package com.programathon.app_programathon.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.programathon.app_programathon.R;

public class TestArea extends ConstraintLayout {

    private Spinner spnRespuesta1,
                    spnRespuesta2,
                    spnRespuesta3,
                    spnRespuesta4,
                    spnRespuesta5,
                    spnRespuesta6;
    private TextView txtTotal;
    private ImageView imgState;

    int minValue, maxValue;

    public TestArea(Context context, String title, int minValue, int maxValue) {
        super(context);

        TextView txtTitle = findViewById(R.id.TestArea_txtTitulo);
        txtTitle.setText(title);

        spnRespuesta1 = findViewById(R.id.TestArea_spnRespuesta1);
        spnRespuesta2 = findViewById(R.id.TestArea_spnRespuesta2);
        spnRespuesta3 = findViewById(R.id.TestArea_spnRespuesta3);
        spnRespuesta4 = findViewById(R.id.TestArea_spnRespuesta4);
        spnRespuesta5 = findViewById(R.id.TestArea_spnRespuesta5);
        spnRespuesta6 = findViewById(R.id.TestArea_spnRespuesta6);
        txtTotal = findViewById(R.id.TestArea_total);
        imgState = findViewById(R.id.TestArea_imgState);

        this.minValue = minValue;
        this.maxValue = maxValue;

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
        int respuesta1 = (int) spnRespuesta1.getSelectedItem();
        int respuesta2 = (int) spnRespuesta2.getSelectedItem();
        int respuesta3 = (int) spnRespuesta3.getSelectedItem();
        int respuesta4 = (int) spnRespuesta4.getSelectedItem();
        int respuesta5 = (int) spnRespuesta5.getSelectedItem();
        int respuesta6 = (int) spnRespuesta6.getSelectedItem();

        int total = respuesta1 + respuesta2 + respuesta3 + respuesta4 + respuesta5 + respuesta6;

        txtTotal.setText(String.valueOf(total));


        int colorID = getStateIconColorID(total);

        imgState.setColorFilter(ContextCompat.getColor(getContext(), colorID), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private int getStateIconColorID(int totalValue) {
        int stateIconColor = R.color.testIconYellow;
        if (totalValue < minValue)
            stateIconColor = R.color.testIconRed;
        else if (maxValue < totalValue)
            stateIconColor = R.color.testIconGreen;

        return stateIconColor;
    }


}
