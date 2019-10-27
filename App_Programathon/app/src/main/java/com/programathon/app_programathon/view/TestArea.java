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

        this.minValue = minValue;
        this.maxValue = maxValue;

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
        int respuesta1 = Integer.parseInt((String) spnRespuesta1.getSelectedItem());
        int respuesta2 = Integer.parseInt((String) spnRespuesta2.getSelectedItem());
        int respuesta3 = Integer.parseInt((String) spnRespuesta3.getSelectedItem());
        int respuesta4 = Integer.parseInt((String) spnRespuesta4.getSelectedItem());
        int respuesta5 = Integer.parseInt((String) spnRespuesta5.getSelectedItem());
        int respuesta6 = Integer.parseInt((String) spnRespuesta6.getSelectedItem());

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

    public int getTotal() {
        return Integer.parseInt(txtTotal.getText().toString());
    }


}
