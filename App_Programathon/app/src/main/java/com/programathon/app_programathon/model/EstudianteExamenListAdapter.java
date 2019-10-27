package com.programathon.app_programathon.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programathon.app_programathon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EstudianteExamenListAdapter extends RecyclerView.Adapter<EstudianteExamenListAdapter.ItemViewHolder> {

    private JSONArray studentArray;
    private ArrayList<String> testNameArray;
    private OnRecycleItemListener listener;

    public EstudianteExamenListAdapter(JSONArray studentArray, ArrayList<String> testNameArray, OnRecycleItemListener listener) {
        this.studentArray = studentArray;
        this.testNameArray = testNameArray;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        try {
            holder.setStudentInfo(studentArray.getJSONObject(position));
            holder.columna2.setText(testNameArray.get(position));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return studentArray.length();
    }

    public JSONObject getStudentInfo(int position) throws JSONException {
        return studentArray.getJSONObject(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView columna1;
        private TextView columna2;
        private OnRecycleItemListener listener;

        ItemViewHolder(@NonNull View itemView, OnRecycleItemListener listener) {
            super(itemView);
            this.columna1 = itemView.findViewById(R.id.Row_Columna1);
            this.columna2 = itemView.findViewById(R.id.Row_Columna2);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        void setStudentInfo(JSONObject studentInfo) throws JSONException {
            String name = studentInfo.getString("firstName") + " " + studentInfo.getString("lastName");
            this.columna1.setText(name);
        }

        @Override
        public void onClick(View view) {
            listener.OnItemClick(getAdapterPosition());
        }
    }
}
