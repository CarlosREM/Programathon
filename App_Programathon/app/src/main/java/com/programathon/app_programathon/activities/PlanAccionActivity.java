package com.programathon.app_programathon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.programathon.app_programathon.R;
import com.programathon.app_programathon.globalconfig.ConfigConstants;
import com.programathon.app_programathon.model.ASQ3;
import com.programathon.app_programathon.model.SimpleListAdapter;
import com.programathon.app_programathon.model.SimpleListenerListAdapter;
import com.programathon.app_programathon.model.TestAreaModel;
import com.programathon.app_programathon.model.TestCalculator;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlanAccionActivity extends AppCompatActivity {

    private JSONObject studentInfo;
    private ArrayList<TestAreaModel> testAreaModels;
    private String formName;

    private ArrayList<JSONObject> estrategias;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_accion);

        Intent i = getIntent();
        try {
            this.queue = Volley.newRequestQueue(this);

            studentInfo = new JSONObject(i.getStringExtra("studentInfo"));
            testAreaModels = (ArrayList<TestAreaModel>) i.getSerializableExtra("testAreaModels");
            formName = i.getStringExtra("formName");

            fillTxtFields();
            loadMatrizRespuestas();
            loadRecyclerView();

            loadPlanesAccion();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void fillTxtFields() throws JSONException {
        String name = studentInfo.getString("firstName") + " " + studentInfo.getString("lastName");
        ((TextView) findViewById(R.id.PlanAccion_txtNombre)).setText(name);

        DateTime now = new DateTime();
        String date = now.toString().split("T")[0];
        ((TextView) findViewById(R.id.PlanAccion_txtFechaPlan)).setText(date);
        ((TextView) findViewById(R.id.PlanAccion_txtFechaApl)).setText(date);

        ((TextView) findViewById(R.id.PlanAccion_txtForm)).setText(formName);
    }

    private void loadMatrizRespuestas() {
        TableLayout matrizRespuestas = findViewById(R.id.PlanAccion_matrizResultados);
        TableRow matrizRow;
        for (int i = 1; i < matrizRespuestas.getChildCount(); i++) {
            matrizRow = (TableRow) matrizRespuestas.getChildAt(i);
            for (int j = 0; j < matrizRow.getChildCount(); j++) {
                if (j == 0)
                    ((TextView) matrizRow.getChildAt(j)).setText(testAreaModels.get(i-1).getName());
                else if (j <= 6)
                    ((TextView) matrizRow.getChildAt(j)).setText(String.valueOf(testAreaModels.get(i-1).getRespuesta(j)));
                else
                    ((TextView) matrizRow.getChildAt(j)).setText(String.valueOf(testAreaModels.get(i-1).getTotal()));
            }
        }
    }

    private void loadRecyclerView() {
        ArrayList<String> dataSet = new ArrayList<>();
        for (TestAreaModel areaModel : testAreaModels) {
            if (!areaModel.isVerde())
                dataSet.add(areaModel.getName());
        }

        SimpleListAdapter adapter = new SimpleListAdapter(dataSet);
        RecyclerView recyclerView = findViewById(R.id.PlanAccion_recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void loadPlanesAccion() throws JSONException {

        ASQ3 form = TestCalculator.getInstance().getFormByName(formName);

        String url = ConfigConstants.getInstance().getAPI_URL() + "FormPlan/GetByFormId?formHeaderId="+ form.getId();
        SharedPreferences prefs;
        prefs = getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);


        JSONObject loginData = null;

        loginData = new JSONObject(prefs.getString("LoginData", null));
        final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("Response", response.toString());

                        JSONObject estrategia;
                        ArrayList<String> names = new ArrayList<>(),
                                          descriptions = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                estrategia = new JSONObject(response.getJSONObject(i).getString("actionPlan"));
                                names.add(estrategia.getString("name"));
                                descriptions.add(estrategia.getString("description"));
                            }

                            SimpleListenerListAdapter adapter = new SimpleListenerListAdapter(names, descriptions);
                            RecyclerView recyclerView = findViewById(R.id.PlanAccion_recyclerActionPlan);
                            recyclerView.setAdapter(adapter);
                        }
                        catch(JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error == null) {
                            Log.d("Error.Response", "Null error");
                            return;
                        }
                        else if (error.networkResponse == null) {
                            Log.d("Error.Response", "Null Network Response");
                            Log.d("Error.Response", error.toString());
                            return;
                        }
                        Log.d("Error.Response", error.toString());

                        String msg = "Error: ";
                        assert getCurrentFocus() != null;
                        switch(error.networkResponse.statusCode) {

                            default:
                                msg += "desconocido";
                                break;
                        }
                        Log.d("msg:", msg);
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers=new HashMap<>();
                headers.put("accept","application/json");
                headers.put("Authorization",authorization);
                return headers;
            }
        };

        Log.d("PostRequest", postRequest.toString());
        queue.add(postRequest);
    }

}
