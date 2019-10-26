package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.programathon.app_programathon.R;

public class MenuNormalUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_normal_user);
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
