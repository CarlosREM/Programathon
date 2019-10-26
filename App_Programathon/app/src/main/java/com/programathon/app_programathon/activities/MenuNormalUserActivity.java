package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuNormalUserActivity extends AppCompatActivity {

    TextView usernameToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_normal_user);

        usernameToolbar = findViewById(R.id.usernameToolbar);

        SharedPreferences prefs;
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        try {
            JSONObject userInfo = new JSONObject(prefs.getString("UserInfo", null));
            String user = userInfo.getString("givenName") + " - " + userInfo.getString("role");
            usernameToolbar.setText(user);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
