package com.programathon.app_programathon.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.programathon.app_programathon.globalconfig.ConfigConstants;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestCalculator {
    private static TestCalculator instance;
    private List<ASQ3> questionaries = new ArrayList<>();
    private RequestQueue queue;
    private Context context;

    private TestCalculator() {
    }

    public static TestCalculator getInstance(){
        if(instance == null){
            instance = new TestCalculator();
        }
        return instance;
    }

    public void loadData(final Context context){
        if(queue == null)
            queue = Volley.newRequestQueue(context);
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        final String userName = prefs.getString("DNI",null);
        String url = ConfigConstants.getInstance().getAPI_URL() + "Form/GetAllFormHeaders";
        JSONObject loginData = null;
        try {
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");
            Log.d("Authorization: ",authorization);
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Response", response.toString());
                            for(int i=0; i< response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    ASQ3 test = new ASQ3(object.getInt("id"),object.getString("name"),
                                                            object.getInt("minAgeMonths"),object.getInt("maxAgeMonths"),
                                                            object.getInt("minAgeDays"),object.getInt("maxAgeDays"));
                                    questionaries.add(test);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            loadAreas(context);
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
                                Toast.makeText(context, "El profesor no tiene estudiantes asociados.", Toast.LENGTH_SHORT).show();
                                Log.d("Error.Response", "Null Network Response");
                                Log.d("Error.Response", error.toString());
                                return;
                            }
                            Log.d("Error.Response", error.toString());

                            String msg = "Error: ";
                            switch(error.networkResponse.statusCode) {
                                default:
                                    msg += "desconocido";
                                    break;
                            }
                            Toast.makeText(context, "No se pudieron cargar los ASQ-3.", Toast.LENGTH_SHORT).show();
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
            Log.d("GetRequest", getRequest.toString());
            queue.add(getRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadAreas(final Context context){
        SharedPreferences prefs;
        prefs = context.getSharedPreferences(ConfigConstants.getInstance().getPREFS_NAME(), Context.MODE_PRIVATE);
        final String userName = prefs.getString("DNI",null);
        JSONObject loginData = null;
        try {
            loginData = new JSONObject(prefs.getString("LoginData", null));
            final String authorization = loginData.getString("token_type") + " " + loginData.getString("access_token");
            Log.d("Authorization: ",authorization);

            for(int i = 0; i < TestCalculator.getInstance().questionaries.size(); i++) {
                String url = ConfigConstants.getInstance().getAPI_URL() + "Areas/GetAreaLimitsByFormId?formId="
                            +TestCalculator.getInstance().questionaries.get(i).getId();
                final ASQ3 t = TestCalculator.getInstance().questionaries.get(i);
                JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>()
                        {
                            @Override
                            public void onResponse(JSONArray response) {

                                ArrayList<formArea> temp = new ArrayList<>();
                                for(int i=0; i< response.length(); i++) {
                                    try {
                                        JSONObject object = response.getJSONObject(i);
                                        formArea area = new formArea(object.getString("formId"), object.getString("areaId"), object.getString("minValue"), object.getString("maxValue"));
                                        temp.add(area);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                t.setAreas(temp);
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
                                    Toast.makeText(context, "El profesor no tiene estudiantes asociados.", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", "Null Network Response");
                                    Log.d("Error.Response", error.toString());
                                    return;
                                }
                                Log.d("Error.Response", error.toString());

                                String msg = "Error: ";
                                switch(error.networkResponse.statusCode) {
                                    default:
                                        msg += "desconocido";
                                        break;
                                }
                                Toast.makeText(context, "No se pudieron cargar los ASQ-3.", Toast.LENGTH_SHORT).show();
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
                Log.d("GetRequest", getRequest.toString());
                queue.add(getRequest);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String calculateDatesDiff(String bornDateString, DateTime lastApplicationDate){
        if(lastApplicationDate == null){
            lastApplicationDate = new DateTime();
        }
        Log.d("bornDateString", bornDateString);

        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        DateTime bornDate = formatter.parseDateTime(bornDateString);
        Log.d("Born date:",bornDate.toString());
        Months diffMonths = Months.monthsBetween(bornDate,lastApplicationDate);
        DateTime temp = bornDate.plusMonths(diffMonths.getMonths());
        Days diffDays = Days.daysBetween(temp, lastApplicationDate);

        Log.d("Months mae: ", String.valueOf(diffMonths.getMonths()));
        Log.d("Days mae: ", String.valueOf(diffDays.getDays()));
        for(int i=0; i<questionaries.size();i++){

            Log.d("Min months", String.valueOf(questionaries.get(i).getMinMonths()));
            Log.d("Max months", String.valueOf(questionaries.get(i).getMaxMonths()));
            Log.d("Min days", String.valueOf(questionaries.get(i).getMinDays()));
            Log.d("Max days", String.valueOf(questionaries.get(i).getMaxDays()));
            if (diffMonths.getMonths() > questionaries.get(i).getMinMonths() && diffMonths.getMonths() < questionaries.get(i).getMaxMonths()) {
                return questionaries.get(i).getName();
            }
            if (diffMonths.getMonths() == questionaries.get(i).getMinMonths()) {
                if (diffDays.getDays() >= questionaries.get(i).getMinDays()) {
                    return questionaries.get(i).getName();
                }
            }
            if (diffMonths.getMonths() == questionaries.get(i).getMaxMonths()) {
                if (diffDays.getDays() <= questionaries.get(i).getMaxDays()) {
                    return questionaries.get(i).getName();
                }
            }
        }
        return "No hay formulario asociado.";
    }



    public ASQ3 calculateForm(String bornDateString, DateTime lastApplicationDate){
        if(lastApplicationDate == null){
            lastApplicationDate = new DateTime();
        }
        Log.d("bornDateString", bornDateString);

        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        DateTime bornDate = formatter.parseDateTime(bornDateString);
        Log.d("Born date:",bornDate.toString());
        Months diffMonths = Months.monthsBetween(bornDate,lastApplicationDate);
        DateTime temp = bornDate.plusMonths(diffMonths.getMonths());
        Days diffDays = Days.daysBetween(temp, lastApplicationDate);

        Log.d("Months mae: ", String.valueOf(diffMonths.getMonths()));
        Log.d("Days mae: ", String.valueOf(diffDays.getDays()));
        for(int i=0; i<questionaries.size();i++){

            Log.d("Min months", String.valueOf(questionaries.get(i).getMinMonths()));
            Log.d("Max months", String.valueOf(questionaries.get(i).getMaxMonths()));
            Log.d("Min days", String.valueOf(questionaries.get(i).getMinDays()));
            Log.d("Max days", String.valueOf(questionaries.get(i).getMaxDays()));
            if (diffMonths.getMonths() > questionaries.get(i).getMinMonths() && diffMonths.getMonths() < questionaries.get(i).getMaxMonths()) {
                return questionaries.get(i);
            }
            if (diffMonths.getMonths() == questionaries.get(i).getMinMonths()) {
                if (diffDays.getDays() >= questionaries.get(i).getMinDays())
                    return questionaries.get(i);
            }
            if (diffMonths.getMonths() == questionaries.get(i).getMinMonths()) {
                if (diffDays.getDays() >= questionaries.get(i).getMinDays()) {
                    return questionaries.get(i);
                }
            }
            if (diffMonths.getMonths() == questionaries.get(i).getMaxMonths()) {
                if (diffDays.getDays() <= questionaries.get(i).getMaxDays()) {
                    return questionaries.get(i);
                }
            }
        }
        return null;
    }

    public ASQ3 getFormByName(String name) {
        for (ASQ3 form : this.questionaries) {
            if (form.getName().equals(name))
                return form;
        }
        return null;
    }

}
